package org.xsk.domain.common;

import cn.hutool.core.util.ObjectUtil;

import java.io.Serializable;

public abstract class ValueObject extends AggregateComponent implements Serializable {
    protected static <T extends ValueObject> T cloneObject(T obj) {
        return (T) ObjectUtil.clone(obj);
    }
}
