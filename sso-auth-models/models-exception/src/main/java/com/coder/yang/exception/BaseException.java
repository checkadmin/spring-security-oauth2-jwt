package com.coder.yang.exception;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/8 9:55
 * @description ：异常父类
 */
public abstract class BaseException extends RuntimeException{
    private int respCode;

    /**
     * 返回信息+错误码
     * @param errMessage 异常信息
     * @param respCode 状态码
     */
    public BaseException(int respCode,String errMessage) {
        super(errMessage);
        this.respCode = respCode;
    }

    /**
     * 只返回异常信息
     * @param errMessage 异常信息
     */
    public BaseException(String errMessage) {
        super(errMessage);
    }

    /**
     * 返回异常信息+异常
     * @param errMessage 异常信息
     * @param e 异常
     */
    public BaseException(String errMessage, Throwable e) {
        super(errMessage, e);
    }

    /**
     * 返回状态码+异常信息+异常
     * @param respCode 状态码
     * @param errMessage 异常信息
     * @param e 异常
     */
    public BaseException(int respCode, String errMessage, Throwable e) {
        super(errMessage, e);
        this.respCode = respCode;
    }

    public int getErrCode() {
        return respCode;
    }

    public void setErrCode(int respCode) {
        this.respCode = respCode;
    }
}
