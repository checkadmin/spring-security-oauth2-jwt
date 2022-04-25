package com.coder.yang.bo;

import lombok.Data;
import java.util.List;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/25 14:19
 * @description ：
 */
@Data
public class LogoutBO {
    private List<Token> tokenList;
    @Data
    public static class Token{
        private String accessToken;
        private String refreshToken;
    }
}
