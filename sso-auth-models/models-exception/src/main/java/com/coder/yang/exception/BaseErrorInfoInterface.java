package com.coder.yang.exception;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/8 13:57
 * @description ：
 */
public interface BaseErrorInfoInterface {
    /**
     * 错误码
     * @return code
     */
    int getRespCode();

    /**
     * 错误描述
     * @return msg
     */
    String getRespDesc();
}
