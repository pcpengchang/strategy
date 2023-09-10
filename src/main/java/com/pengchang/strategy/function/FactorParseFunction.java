package com.pengchang.strategy.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.pengchang.strategy.entity.FactorInfoVO;
import com.pengchang.strategy.entity.RuleExpressionExecuteContext;
import com.pengchang.strategy.enums.VarTypeEnum;
import com.pengchang.strategy.utils.AviatorUtils;
import com.pengchang.strategy.utils.FactorParser;


import java.util.Map;

/**
 * 因子结果解析
 */
public class FactorParseFunction extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        long factorId = FunctionUtils.getNumberValue(arg1, env).longValue();
        //通过枚举获取因子的返回值
        RuleExpressionExecuteContext context
                = (RuleExpressionExecuteContext) env.get(AviatorUtils.RULE_EXPRESSION_EXECUTE_CONTEXT);
        //System.out.println(FunctionUtils.getJavaObject(arg1, env));
//        RuleExpressionExecuteContext context = (RuleExpressionExecuteContext) FunctionUtils.getJavaObject(arg1, env);
        Map<Long, Object> factorResultMap = context.getFactorId2ResultMap();
        Map<Long, FactorInfoVO> factorItemMap = context.getFactorItemMap();
        //根据返回值类型 返回对应的对象
        VarTypeEnum resultTypeEnum = factorItemMap.get(factorId).getResultTypeEnum();
        return FactorParser.parse(factorResultMap.get(factorId), resultTypeEnum);
    }

    @Override
    public String getName() {
        return "ruleFactor";
    }
}
