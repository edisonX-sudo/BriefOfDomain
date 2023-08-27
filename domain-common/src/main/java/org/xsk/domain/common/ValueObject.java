package org.xsk.domain.common;

import cn.hutool.core.util.ObjectUtil;

import java.io.Serializable;

public abstract class ValueObject extends AggregateComponent implements Serializable {
    protected <T extends ValueObject> T cloneObject() {
        return (T) ObjectUtil.clone(this);
    }
}
