package org.xsk.domain.common;

import cn.hutool.core.util.StrUtil;

public abstract class Code<T> extends ValueObject {
    T code;

    public Code(T code) {
        this.code = code;
    }

    public T value() {
        return code;
    }

    public static boolean isNullVal(Code<?> code) {
        return code == null || code.value() == null;
    }

    public static boolean isEmptyVal(Code<String> code) {
        if (code == null) return true;
        String value = code.value();
        return StrUtil.isEmpty(value);
    }
}
