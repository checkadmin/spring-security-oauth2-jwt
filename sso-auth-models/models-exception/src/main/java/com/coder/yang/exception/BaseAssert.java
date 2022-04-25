package com.coder.yang.exception;

import java.util.Collection;
import java.util.Map;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/8 10:22
 * @description ：断言类，帮助验证参数。
 */
public class BaseAssert {

    public static void isTrue(boolean expression, int respCode, String errMessage) {
        if (!expression) {
            throw new BizException(respCode, errMessage);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] Must be true");
    }

    public static void isTrue(boolean expression, String errMessage) {
        if (!expression) {
            throw new BizException(errMessage);
        }
    }

    public static void isFalse(boolean expression, int respCode, String errMessage) {
        if (expression) {
            throw new BizException(respCode, errMessage);
        }
    }

    public static void isFalse(boolean expression, String errMessage) {
        if (expression) {
            throw new BizException(errMessage);
        }
    }

    public static void isFalse(boolean expression) {
        isFalse(expression, "[Assertion failed] Must be false");
    }


    public static void notNull(Object object, int respCode, String errMessage) {
        if (object == null) {
            throw new BizException(respCode, errMessage);
        }
    }

    public static void notNull(Object object, String errMessage) {
        if (object == null) {
            throw new BizException(errMessage);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] Must not null");
    }

    public static void notEmpty(Collection<?> collection, int respCode, String errMessage) {
        if (collection == null || collection.isEmpty()) {
            throw new BizException(respCode, errMessage);
        }
    }

    public static void notEmpty(Collection<?> collection, String errMessage) {
        if (collection == null || collection.isEmpty()) {
            throw new BizException(errMessage);
        }
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "[Assertion failed] Collection must not be empty: it must contain at least 1 element");
    }

    public static void notEmpty(Map<?, ?> map, int respCode, String errMessage) {
        if (map == null || map.isEmpty()) {
            throw new BizException(respCode, errMessage);
        }
    }

    public static void notEmpty(Map<?, ?> map, String errMessage) {
        if (map == null || map.isEmpty()) {
            throw new BizException(errMessage);
        }
    }

    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "[Assertion failed] Map must not be empty: it must contain at least one entry");
    }
}
