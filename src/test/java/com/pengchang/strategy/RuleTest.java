package com.pengchang.strategy;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.pengchang.strategy.entity.FactorInfoVO;
import com.pengchang.strategy.entity.RuleExpressionExecuteContext;
import com.pengchang.strategy.enums.OperatorTypeEnum;
import com.pengchang.strategy.enums.VarTypeEnum;
import com.pengchang.strategy.flagship.DefaultRuleFactory;
import com.pengchang.strategy.flagship.model.*;
import com.pengchang.strategy.flagship.model.user.UserMemberFeatureDTO;
import com.pengchang.strategy.flagship.rule.DisplayRuleDTO;
import com.pengchang.strategy.flagship.rule.FilterRuleDTO;
import com.pengchang.strategy.function.FactorParseFunction;
import com.pengchang.strategy.utils.ExpressionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pengchang.strategy.utils.AviatorUtils.RULE_EXPRESSION_EXECUTE_CONTEXT;
import static com.pengchang.strategy.utils.ExpressionUtils.generateExpression;

public class RuleTest {

    // 模拟通过后台配置
    FilterRuleDTO filterRule = DefaultRuleFactory.filterRule;
    DisplayRuleDTO displayRule = DefaultRuleFactory.displayRule;

    /**
     * 一个引擎对应多个规则，产运通过配置引擎可快速上下线某个规则
     * 引擎表 flagship_recommend_engine: engine_id、rule_ids、feature_data（EngineDetailParam的json形式）
     * 规则表 flagship_recommend_rule: rule_id、rule_type（分为过滤和展示）、rule_detail（规则详情）
     * <p>
     * 有两种方式判断是否通过规则，一种是走function，另外一种是直接判断字段的true和false
     */
    @Test
    public void test() {
        RecommendEngineDTO engine = new RecommendEngineDTO();
        engine.setId(1L);
        // 1、配置特征
        // 1.1、规则配置，从规则表中读取
        engine.setFilterRule(filterRule);
        engine.setDisplayRule(displayRule);
        FlagshipRecommendFeatureDTO flagshipRecommendFeature = new FlagshipRecommendFeatureDTO();
        flagshipRecommendFeature.setEngine(engine);
        // 1.2、其他业务配置，从引擎表中读取
        RecommendDisplayFeatureDTO recommendDisplayFeatureDTO = new RecommendDisplayFeatureDTO();
        recommendDisplayFeatureDTO.setOpen(true);
        RecommendMemberFeatureDTO recommendMemberFeatureDTO = new RecommendMemberFeatureDTO();
        recommendMemberFeatureDTO.setOpen(true);
        flagshipRecommendFeature.setDisplayFeature(recommendDisplayFeatureDTO);
        flagshipRecommendFeature.setMemberFeature(recommendMemberFeatureDTO);

        // 2、用户特征
        UserFeatureDTO userFeature = new UserFeatureDTO();
        UserMemberFeatureDTO userMemberFeatureDTO = new UserMemberFeatureDTO();
        userMemberFeatureDTO.setMemberFeature(true);
        userFeature.setUserMemberFeature(userMemberFeatureDTO);


        // 循环匹配每个规则
        boolean holdFlag = true;
        FilterRuleDTO filterRule = flagshipRecommendFeature.getEngine().getFilterRule();
        for (String rule : filterRule.getRules()) {
            boolean expResult = ExpressionUtils.calculateBoolean(rule, flagshipRecommendFeature, userFeature);
            if (!expResult) {
                holdFlag = false;
                break;
            }
        }
        if (holdFlag) {
            System.out.println("匹配成功");
        }
    }

    public static void main(String[] args) {
        // 外层为或，内层为且. 比如 List[List[A,B,C], List[M,N]] 表示 A && B && C || M && N

        List<FactorInfoVO> factorInfoVOList = new ArrayList<>();
        // 表达式填充
        FactorInfoVO factorInfoVO = new FactorInfoVO();
        Map<String, String> variableInfo = new HashMap<>();
        // variableInfo.put("rightsIds", "1001,1002");
        // factorInfoVO.setVariableInfo(variableInfo);
        factorInfoVO.setOperatorTypeEnum(OperatorTypeEnum.EQUAL);
        factorInfoVO.setOperand("1");
        factorInfoVOList.add(factorInfoVO);
        // factorInfoVO.setVariableInfo(variableInfo);
        factorInfoVO.setOperatorTypeEnum(OperatorTypeEnum.IN);
        factorInfoVO.setOperand("2,3");
        factorInfoVOList.add(factorInfoVO);

        // 因子生成表达式 因子id、操作符、操作数
        List<String> expressionList = Lists.newArrayList();
        AviatorEvaluator.addFunction(new FactorParseFunction());
        for (int i = 1; i <= 2; i++) {
            FactorInfoVO item = factorInfoVOList.get(i - 1);
            expressionList.add(generateExpression((long) i, item.getOperatorTypeEnum(), item.getOperand()));
        }

        String expression = StringUtils.join(expressionList, "&&");
        Expression compiledExpression = AviatorEvaluator.compile(expression, true);
        System.out.println(expression);

        // 数据填充
        RuleExpressionExecuteContext context = new RuleExpressionExecuteContext();
        context.setFactorId2ResultMap(ImmutableMap.of(1L, 2, 2L, 100))
                .setFactorItemMap(ImmutableMap.of(
                        1L, new FactorInfoVO().setResultTypeEnum(VarTypeEnum.LONG),
                        2L, new FactorInfoVO().setResultTypeEnum(VarTypeEnum.LONG))
                );
        Map<String, Object> env = Maps.newHashMap();
        env.put(RULE_EXPRESSION_EXECUTE_CONTEXT, context);

        // 执行表达式
        Object result = compiledExpression.execute(env);
        System.out.println(result);
    }
}
