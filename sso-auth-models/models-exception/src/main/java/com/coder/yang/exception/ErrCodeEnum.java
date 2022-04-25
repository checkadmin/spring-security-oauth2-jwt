package com.coder.yang.exception;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/8 13:57
 * @description ：
 */
public enum ErrCodeEnum implements BaseErrorInfoInterface{
    /**
     * 成功
     */
    GLOBAL_SUCCESS(0, "成功"),
    /**
     * 业务失败状态码
     */
    GlOBAL_PARAMS_ERROR(300, "业务错误"),
    /**
     * 系统错误
     */
    GlOBAL_ACCESSTOKEN_ERROR(400, "token验证失败"),


    GLOBAL_OTHER_ERROR(500, "服务器繁忙，请稍后再试"),

    ;


    /** 错误码 */
    private int respCode;

    /** 错误描述 */
    private String respDesc;

    ErrCodeEnum(int respCode, String respDesc) {
        this.respCode = respCode;
        this.respDesc = respDesc;
    }


    @Override
    public int getRespCode() {
        return respCode;
    }

    @Override
    public String getRespDesc() {
        return respDesc;
    }

    public static String getRespDesc(int respCode){
        for (ErrCodeEnum value : ErrCodeEnum.values()){
            if (value.respCode == respCode){
                return value.getRespDesc();
            }
        }
        return null;
    }
}
