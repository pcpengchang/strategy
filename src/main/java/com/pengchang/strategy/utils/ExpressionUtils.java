package com.pengchang.strategy.utils;

import com.pengchang.strategy.entity.AviatorResult;
import com.pengchang.strategy.entity.FactorGroupDTO;
import com.pengchang.strategy.entity.FactorInfoVO;
import com.pengchang.strategy.entity.RuleExpressionExecuteContext;
import com.pengchang.strategy.enums.OperatorTypeEnum;
import com.pengchang.strategy.flagship.model.FlagshipRecommendFeatureDTO;
import com.pengchang.strategy.flagship.model.UserFeatureDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 规则表达式生成和执行
 */
public class ExpressionUtils {

    private static final String AND = "&&";

    private static final String OR = "||";

    /**
     * in操作的规则表达式
     */
    private static final String INCLUDE_FORMAT = "(include(seq.set(%s),ruleFactor(%d)))";

    /**
     * 通用的比较操作表达式
     */
    private static final String COMMON_COMPARE_FORMAT = "(ruleFactor(%d)%s%s)";

    public static String generateExpression(Map<Long, FactorInfoVO> factorId2ItemMap, FactorGroupDTO factorGroupDTO) {
        if (factorGroupDTO == null || CollectionUtils.isEmpty(factorGroupDTO.getFactorIds())) {
            return null;
        }

        //内层为交 &&，外层为并 ||
        List<String> expressionList = factorGroupDTO.getFactorIds().stream().map(factorIdList -> {
            List<String> subExpressionList = factorIdList.stream().map(factorId -> {
                        FactorInfoVO factorItem = factorId2ItemMap.get(factorId);
                        return generateExpression(factorId, factorItem.getOperatorTypeEnum(), factorItem.getOperand());
                    })
                    .collect(Collectors.toList());
            if (subExpressionList.stream().anyMatch(StringUtils::isBlank)) {
                return null;
            }
            return StringUtils.join(subExpressionList, AND);
        }).collect(Collectors.toList());
        if (expressionList.stream().anyMatch(StringUtils::isBlank)) {
            return null;
        }
        return StringUtils.join(expressionList, OR);
    }

    /**
     * 一个因子生成表达式
     *
     * @param factorId     因子的id
     * @param operatorType 操作符
     * @param operand      操作数
     * @return 表达式，举例 (ruleFactor(1)>3)，blank表示生成失败。
     */
    public static String generateExpression(Long factorId, OperatorTypeEnum operatorType, String operand) {
        if (factorId == null || operatorType == null || StringUtils.isBlank(operand)) {
            return null;
        }
        String expression = null;
        switch (operatorType) {
            case EQUAL:
            case LAGER_THAN:
            case SMALL_THAN:
            case LAGER_THAN_OR_EQUAL:
            case SMALL_THAN_OR_EQUAL: {
                expression = String.format(COMMON_COMPARE_FORMAT, factorId, operatorType.getOperatorKey(), operand);
                break;
            }
            case IN: {
                //in操作，注意如果是字符串则operand是'a','b','c'格式，当前不区分处理
                expression = String.format(INCLUDE_FORMAT, operand, factorId);
                break;
            }
            default: {
                break;
            }
        }
        return expression;
    }

    /**
     * 计算boolean类型结果
     */
    public static Optional<Boolean> calculateBoolean(String expression, RuleExpressionExecuteContext context) {
        if (context == null || StringUtils.isBlank(expression)) {
            return Optional.of(true);
        }
        AviatorResult<Boolean> result = AviatorUtils.executeExpression(expression,
                AviatorUtils.buildSingleFeatureEnv(context), Boolean.class);
        if (result.isSuccess() && result.getResult() != null) {
            return Optional.of(result.getResult());
        }
        return Optional.empty();
    }

//    public static Optional<Boolean> calculateBoolean(String expression, UserMemberFeatureDTO userMemberFeature,
//                                           RecommendDisplayFeatureDTO recommendDisplayFeature) {
//        if (userMemberFeature == null || recommendDisplayFeature == null || StringUtils.isBlank(expression)) {
//            return Optional.of(true);
//        }
//        AviatorResult<Boolean> result = AviatorUtils.executeExpression(expression,
//                AviatorUtils.buildSingleFeatureEnv(userMemberFeature, recommendDisplayFeature), Boolean.class);
//        if (result.isSuccess() && result.getResult() != null) {
//            return Optional.of(result.getResult());
//        }
//        return Optional.empty();
//    }

    public static boolean calculateBoolean(String expression, FlagshipRecommendFeatureDTO flagshipFeature,
                                           UserFeatureDTO userFeature) {
        if (flagshipFeature == null || StringUtils.isBlank(expression)) {
            return false;
        }
        AviatorResult<Boolean> result = AviatorUtils.executeExpression(expression,
                AviatorUtils.buildSingleFeatureEnv(flagshipFeature, userFeature), Boolean.class);
        if (result.isSuccess() && result.getResult() != null) {
            return result.getResult();
        }
        return false;
    }
}
