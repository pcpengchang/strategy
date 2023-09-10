package com.pengchang.strategy;

import com.google.common.collect.*;
import com.pengchang.strategy.entity.*;
import com.pengchang.strategy.enums.ExistInTypeEnum;
import com.pengchang.strategy.enums.OperatorTypeEnum;
import com.pengchang.strategy.enums.VarTypeEnum;
import com.pengchang.strategy.strategy.FactorFetchStrategyFactory;
import com.pengchang.strategy.utils.ExpressionUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author pengchang
 * @date 2023/08/18 23:46
 **/
public class RuleFactorTest {

    public static List<FactorInfoVO> factorInfoVOS;

    // 模拟从数据库中取数
    static {
        factorInfoVOS = ImmutableList.of(new FactorInfoVO(1L, OperatorTypeEnum.EQUAL, "1", VarTypeEnum.LONG),
                new FactorInfoVO(2L, OperatorTypeEnum.IN, "1,2,3", VarTypeEnum.LONG));
    }

    /**
     * 一个因子对应一个表达式对应一个结果，一一匹配
     */
    @Test
    void test() {
        // 因子id
        FactorGroupDTO factorGroupDTO = new FactorGroupDTO().setFactorIds(Lists.newArrayList());
        // “且”的关系
        factorGroupDTO.getFactorIds().add(Lists.newArrayList(1L));

        // “或”的关系
        factorGroupDTO.getFactorIds().add(Lists.newArrayList(2L));

        // 因子实例
        Map<Long, FactorInfoVO> factorId2ItemMap = factorInfoVOS.stream()
                .collect(Collectors.toMap(FactorInfoVO::getId, Function.identity(), (d1, d2) -> d1));

        // 获取表达式
        String expression = ExpressionUtils.generateExpression(factorId2ItemMap, factorGroupDTO);
        System.out.println(expression);

        // 数据填充
        RuleExpressionExecuteContext context = new RuleExpressionExecuteContext();
        context.setFactorId2ResultMap(ImmutableMap.of(1L, 4, 2L, 4))
                .setFactorItemMap(ImmutableMap.of(
                        1L, new FactorInfoVO().setResultTypeEnum(VarTypeEnum.LONG),
                        2L, new FactorInfoVO().setResultTypeEnum(VarTypeEnum.LONG))
                );

        Optional<Boolean> expResult = ExpressionUtils.calculateBoolean(expression, context);

//        Optional<Boolean> expResult = ExpressionUtils.calculateBoolean(expression, new UserMemberFeatureDTO(),
//                new RecommendDisplayFeatureDTO());
        System.out.println(expResult);
    }

    /**
     * 常规表达式
     */
//    @Test
//    void testMemberFilter() {
//        String expression = "(userMemberFeature.open||recommendDisplayFeature.open)&&memberFilter(userMemberFeature,recommendDisplayFeature)";
//        UserMemberFeatureDTO userMemberFeature = new UserMemberFeatureDTO();
//        RecommendDisplayFeatureDTO recommendDisplayFeature = new RecommendDisplayFeatureDTO();
//        Optional<Boolean> expResult = ExpressionUtils.calculateBoolean(expression, userMemberFeature,
//                recommendDisplayFeature);
//        System.out.println(expResult);
//    }

    @Test
    void test2() {
        // 因子id
        FactorGroupDTO factorGroupDTO = new FactorGroupDTO().setFactorIds(Lists.newArrayList());
        factorGroupDTO.getFactorIds().add(Lists.newArrayList(1L, 2L));

        // 因子实例
        factorInfoVOS = ImmutableList.of(new FactorInfoVO(1L, OperatorTypeEnum.EQUAL, "true", VarTypeEnum.BOOLEAN),
                new FactorInfoVO(2L, OperatorTypeEnum.EQUAL, "false", VarTypeEnum.BOOLEAN));
        Map<Long, FactorInfoVO> factorId2ItemMap = factorInfoVOS.stream()
                .collect(Collectors.toMap(FactorInfoVO::getId, Function.identity(), (d1, d2) -> d1));

        // 获取表达式
        String expression = ExpressionUtils.generateExpression(factorId2ItemMap, factorGroupDTO);

        String dimension = "test1";

        // 找到对应【维度id-规则-因子】
        RuleInfoVO ruleInfoVO = new RuleInfoVO();
        ruleInfoVO.setFactorIds(Sets.newHashSet(1L, 2L));
        ruleInfoVO.setExpression(expression);
        ruleInfoVO.setExistInTypeEnum(ExistInTypeEnum.EXIST_PASS);
        Map<String, Set<RuleInfoVO>> validRuleMap = Maps.newHashMap();
        validRuleMap.put(dimension, Sets.newHashSet(ruleInfoVO));

        Set<FactorFetchDTO> factorFetchDTOS = Sets.newHashSet(new FactorFetchDTO());
        FactorFetchDTO factorFetchDTO = new FactorFetchDTO();
        factorFetchDTO.setFactorId(1L);
        factorFetchDTOS.add(factorFetchDTO);

        // 取数
        FactorFetchStrategyFactory factorFetchStrategy = new FactorFetchStrategyFactory();
        DomainBatchFactorFetchResponse domainBatchFactorFetchResponse
                = factorFetchStrategy.batchFactorFetch(ImmutableMap.of(dimension, factorFetchDTOS));

        validRuleMap.forEach((dimensionId, ruleSet) -> {
            if (CollectionUtils.isEmpty(ruleSet)) {
                return;
            }
            DomainFactorFetchResponse factorFetchResponse =
                    domainBatchFactorFetchResponse.getDimensionId2ResultMap().get(dimensionId);
            ruleSet.forEach(ruleItem -> {
                Optional<Boolean> expResult = ExpressionUtils.calculateBoolean(ruleItem.getExpression(),
                        buildExpressionExecuteContext(ruleItem, factorFetchResponse));
                boolean calculateSuc = expResult.isPresent();
                if (calculateSuc && expResult.get()) {
                    System.out.println("规则匹配成功");
                }
            });
        });
    }

    private static RuleExpressionExecuteContext buildExpressionExecuteContext(RuleInfoVO ruleInfoVO,
                                                                              DomainFactorFetchResponse factorFetchResponse) {
        Map<Long, Object> factorId2ResultMap = Maps.newHashMap();
        Map<Long, FactorFetchDTO> factorId2FetchDTOMap = Maps.newHashMap();
        Map<Long, FactorInfoVO> factorId2FactorItemMap = Maps.newHashMap();
        factorFetchResponse.getFetch2Result().forEach((factorFetchDTO, result) ->
                factorId2FetchDTOMap.put(factorFetchDTO.getFactorId(), factorFetchDTO));
        Map<FactorFetchDTO, Object> fetchDTO2ResultMap
                = Optional.of(factorFetchResponse).map(DomainFactorFetchResponse::getFetch2Result).orElse(Collections.emptyMap());
        ruleInfoVO.getFactorIds().forEach(factorId -> {
            FactorFetchDTO factorFetchDTO = factorId2FetchDTOMap.get(factorId);
            factorInfoVOS.forEach(factor -> {
                if (factor.getId().equals(factorId)) {
                    factorId2FactorItemMap.put(factorId, factor);
                }
            });
            if (factorFetchDTO != null) {
                Object result = fetchDTO2ResultMap.get(factorFetchDTO);
                factorId2ResultMap.put(factorId, result);
            }
        });
        return new RuleExpressionExecuteContext().setFactorItemMap(factorId2FactorItemMap)
                .setFactorId2ResultMap(factorId2ResultMap);
    }
}
