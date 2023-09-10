package com.pengchang.strategy.entity;

import com.pengchang.strategy.enums.AviatorResultStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;


/**
 * 表达式执行结果
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class AviatorResult<T> {

    private AviatorResultStatusEnum status;

    private String msg;

    private T result;

    public static <T> AviatorResult<T> success(T result) {
        return new AviatorResult<T>(AviatorResultStatusEnum.SUCCESS, "success", result);
    }

    public static <T> AviatorResult<T> executeError(String msg) {
        if (StringUtils.isEmpty(msg)) {
            msg = "execute error";
        }
        return new AviatorResult<T>(AviatorResultStatusEnum.EXECUTE_ERROR, msg, null);
    }

    public static <T> AviatorResult<T> compileError() {
        return new AviatorResult<T>(AviatorResultStatusEnum.COMPILE_ERROR, "compile error", null);
    }

    public static <T> AviatorResult<T> castResultError() {
        return new AviatorResult<T>(AviatorResultStatusEnum.CAST_RESULT_ERROR, "cast result error", null);
    }

    public boolean isSuccess() {
        return AviatorResultStatusEnum.SUCCESS.equals(this.getStatus());
    }

}
