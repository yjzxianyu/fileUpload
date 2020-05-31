package com.yjz.file.exception;

import com.yjz.file.enums.ErrorCodeEnum;
import lombok.Getter;

/**
 * @Description
 * @Author youjianzhao
 * @Date 2020/5/31 14:48
 * @Version
 */
@Getter
public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(ErrorCodeEnum errorEnum) {
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
    }


    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }
}
