package org.xsk.domain.common;

public abstract class DomainSpecificationValidator<T> {

    public abstract void validSpecification(T object);

    protected void throwIllegalStateException(String message) {
        throw new IllegalStateDomainException(message);
    }
}