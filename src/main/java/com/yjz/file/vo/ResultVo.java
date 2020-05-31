package com.yjz.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author youjianzhao
 * @Date 2020/5/31 14:59
 * @Version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = -3661042809296339614L;

    /**
     * 错误码
     */
    private String code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;
}
