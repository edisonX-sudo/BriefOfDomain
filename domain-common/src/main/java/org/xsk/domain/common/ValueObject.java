package org.xsk.domain.common;

import cn.hutool.core.util.ObjectUtil;

public abstract class ValueObject extends AggregateComponent {
    protected static <T extends ValueObject> T cloneObject(T obj) {
        return ObjectUtil.clone(obj);
    }
}
