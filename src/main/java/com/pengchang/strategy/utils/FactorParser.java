package com.pengchang.strategy.utils;

import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import com.pengchang.strategy.enums.VarTypeEnum;

/**
 * 因子数值解析器
 */
public class FactorParser {

    public static AviatorObject parse(Object object, VarTypeEnum type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case LONG:
                return AviatorLong.valueOf(ClassCastUtils.object2Long(object));
            case BOOLEAN:
                return AviatorBoolean.valueOf(ClassCastUtils.object2Boolean(object));
            case STRING:
                return new AviatorString(ClassCastUtils.object2String(object));
            default:
                break;
        }
        return null;
    }
}
