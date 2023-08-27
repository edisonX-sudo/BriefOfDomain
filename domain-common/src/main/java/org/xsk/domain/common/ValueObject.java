package org.xsk.domain.common;

import java.io.Serializable;

public abstract class ValueObject extends AggregateComponent implements Serializable {
    protected static <T extends ValueObject> T cloneObject(T obj) {
        return FrameworkIntegration.cloneValueObject(obj);
    }
}
