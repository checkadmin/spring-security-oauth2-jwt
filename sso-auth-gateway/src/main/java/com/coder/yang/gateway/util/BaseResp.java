package com.coder.yang.gateway.util;

import com.coder.yang.exception.ErrCodeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/11 17:39
 * @description ：
 */
@Getter
@Setter
public class BaseResp<T> implements Serializable {
    private static final long serialVersionUID = 1795413689518255885L;
    private int respCode;
    private String respDesc;
    private LocalDateTime curDate;
    private T obj;

    public static BaseResp fail(int respCode){
        BaseResp returnResp = new BaseResp<>();
        returnResp.setRespCode(respCode);
        returnResp.setRespDesc(ErrCodeEnum.getRespDesc(respCode));
        return returnResp;
    }
}
