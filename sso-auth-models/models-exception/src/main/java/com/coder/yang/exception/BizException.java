package com.coder.yang.exception;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/8 10:10
 * @description ：业务异常
 */
public class BizException  extends BaseException {


    public BizException(int respCode,String errMessage) {
        super(respCode,errMessage);
    }

    public BizException(String errMessage) {
        super(ErrCodeEnum.GlOBAL_PARAMS_ERROR.getRespCode(),errMessage);
    }

    public BizException(String errMessage, Throwable e) {
        super(ErrCodeEnum.GlOBAL_PARAMS_ERROR.getRespCode(),errMessage, e);
    }

    public BizException(int respCode, String errMessage, Throwable e) {
        super(respCode, errMessage, e);
    }
}
