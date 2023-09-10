package com.pengchang.strategy.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.pengchang.strategy.enums.ValueParseTypeEnum;
import com.pengchang.strategy.enums.VarTypeEnum;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author pengchang
 * @date 2023/08/19 01:07
 **/
public class FactorParamParseHelper {
    /**
     * 参数解析
     */
    public static List<Object> parse(Object paramValue, VarTypeEnum varType, ValueParseTypeEnum parseType) {
        if (paramValue == null || varType == null || parseType == null) {
            return null;
        }
        List<Object> result;
        if (parseType == ValueParseTypeEnum.LIST) {
            //如果是多值，则paramValue需要用,分隔
            List<String> listValue = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(paramValue.toString());
            result = listValue.stream().map(subParamValue -> VarTypeEnum.toDestType(subParamValue, varType)).collect(Collectors.toList());
        } else if (parseType == ValueParseTypeEnum.SINGLE){
            Object parseObject = VarTypeEnum.toDestType(paramValue, varType);
            result = Lists.newArrayList(parseObject);
        } else {
            return null;
        }
        if (result.stream().anyMatch(Objects::isNull)) {
            return null;
        }
        return result;
    }

    public static boolean isValid(Object paramValue, VarTypeEnum varType, ValueParseTypeEnum parseType) {
         List<Object> parseResult = parse(paramValue, varType, parseType);
        return CollectionUtils.isNotEmpty(parseResult);
    }
}
