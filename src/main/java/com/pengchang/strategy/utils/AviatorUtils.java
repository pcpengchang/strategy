package com.pengchang.strategy.utils;

import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.pengchang.strategy.entity.AviatorResult;
import com.pengchang.strategy.entity.RuleExpressionExecuteContext;
import com.pengchang.strategy.flagship.model.FlagshipRecommendFeatureDTO;
import com.pengchang.strategy.flagship.model.UserFeatureDTO;
import com.pengchang.strategy.function.BrowseFilterFunction;
import com.pengchang.strategy.function.FactorParseFunction;
import com.pengchang.strategy.function.MemberFilterFunction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Map;

/**
 * 规则执行工具
 */
@Slf4j
public class AviatorUtils {

    public static final String RULE_EXPRESSION_EXECUTE_CONTEXT = "ruleExpressionExecuteContext";

//    public static final String SINGLE_USER_FEATURE_KEY = "userMemberFeature";
//
//    public static final String SINGLE_RECOMMEND_FEATURE_KEY = "recommendDisplayFeature";

    public static final String SINGLE_FLAGSHIP_FEATURE_KEY = "flagshipFeature";

    public static final String SINGLE_USER_FEATURE_KEY = "userFeature";

    /**
     * 缓存key对应的编译结果
     * 如果后期编译的表达式太多metaspace压力大调整jvm参数，或修改缓存方案
     */
    private static final Map<String, Expression> CACHE_KEY_2_EXPRESSION = Maps.newConcurrentMap();

    // 自定义函数注册
    static {
        AviatorEvaluator.addFunction(new FactorParseFunction());
        AviatorEvaluator.addFunction(new MemberFilterFunction());
        AviatorEvaluator.addFunction(new BrowseFilterFunction());
    }

    public static Map<String, Expression> getCacheKey2Expression() {
        return CACHE_KEY_2_EXPRESSION;
    }

    /**
     * 清除表达式的编译结果缓存
     */
    public static void invalidateExpressionByKey(String cacheKey) {
        AviatorEvaluator.getInstance().invalidateCacheByKey(cacheKey);
        CACHE_KEY_2_EXPRESSION.remove(cacheKey);
    }

    /**
     * 执行表达式
     *
     * @param expressionStr 表达式字符串
     * @param params        参数
     * @param resultType    返回值类型
     * @param <T>           返回值类型
     * @return 表达式执行结果
     */
    public static <T> AviatorResult<T> executeExpression(String expressionStr,
                                                         Map<String, Object> params,
                                                         Class<T> resultType) {
        if (StringUtils.isBlank(expressionStr)) {
            return AviatorResult.success(null);
        }

        //获取缓存的表达式编译结果
        Expression compiledExpression = AviatorUtils.getCompiledExpression(expressionStr);
        if (null == compiledExpression) {
            return AviatorResult.compileError();
        }
        Object result;
        //执行aviator表达式
        try {
            result = compiledExpression.execute(params);
        } catch (Exception e) {
            log.error("表达式执行失败:{}", expressionStr, e);
            return AviatorResult.executeError(e.getMessage());
        }
        if (result == null) {
            log.error("表达式执行返回结果为空,expressionStr:{}", expressionStr, new RuntimeException());
            return AviatorResult.success(null);
        }
        //表达式执行返回结果是Object，将Object对象转换成指定的类型
        try {
            return AviatorResult.success(ClassCastUtils.tryCastObject(result, resultType));
        } catch (Exception e) {
            log.error("cast object error", e);
            return AviatorResult.castResultError();
        }
    }

    /**
     * 获取表达式编译结果, 编译成功将存入缓存
     *
     * @param expressionStr 表达式字符串
     * @return 表达式执行结果
     */
    public static Expression getCompiledExpression(String expressionStr) {
        //获取缓存的表达式编译结果
        Expression expression = CACHE_KEY_2_EXPRESSION.get(expressionStr);
        if (expression != null) {
            return expression;
        }
        Expression compiledExpression = null;
        try {
            //全部缓存编译结果
            compiledExpression = AviatorEvaluator.compile(expressionStr, true);
            if (compiledExpression != null) {
                CACHE_KEY_2_EXPRESSION.put(expressionStr, compiledExpression);
            }
        } catch (Exception e) {
            log.error("表达式编译失败, expression:{}", expressionStr, e);
        }
        return compiledExpression;
    }

    /**
     * 构建表达式执行环境参数
     */
    public static Map<String, Object> buildSingleFeatureEnv(RuleExpressionExecuteContext ruleExpressionExecuteContext) {
        if (ruleExpressionExecuteContext == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> env = Maps.newHashMapWithExpectedSize(1);
        env.put(RULE_EXPRESSION_EXECUTE_CONTEXT, ruleExpressionExecuteContext);
        return env;
    }


//    public static Map<String, Object> buildSingleFeatureEnv(UserMemberFeatureDTO userMemberFeature,
//                                                            RecommendDisplayFeatureDTO recommendDisplayFeature) {
//        if (userMemberFeature == null || recommendDisplayFeature == null) {
//            return Collections.emptyMap();
//        }
//        Map<String, Object> env = Maps.newHashMapWithExpectedSize(2);
//        env.put(SINGLE_USER_FEATURE_KEY, userMemberFeature);
//        env.put(SINGLE_RECOMMEND_FEATURE_KEY, recommendDisplayFeature);
//        return env;
//    }

    public static Map<String, Object> buildSingleFeatureEnv(FlagshipRecommendFeatureDTO flagshipFeature,
                                                            UserFeatureDTO userFeature) {
        if (flagshipFeature == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> env = Maps.newHashMapWithExpectedSize(2);
        env.put(SINGLE_FLAGSHIP_FEATURE_KEY, flagshipFeature);
        env.put(SINGLE_USER_FEATURE_KEY, userFeature);

        return env;
    }

}
