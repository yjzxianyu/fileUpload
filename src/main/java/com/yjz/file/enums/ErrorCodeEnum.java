package com.yjz.file.enums;

import lombok.Getter;

/**
 * @Description
 * @Author youjianzhao
 * @Date 2020/5/31 14:51
 * @Version
 */
@Getter
public enum ErrorCodeEnum {


    SUCCESS("0000","请求成功"),



    PARAM_IS_NULL("0102", "参数为空"),
    FILE_HANDLE_ERROR("0103", "文件处理异常");

    /**
     * 提示码
     */
    private String code;

    /**
     * 提示内容
     */
    private String msg;

    ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
