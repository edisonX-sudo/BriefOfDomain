package org.xsk.domain.common;

public abstract class DomainSpecificationValidator<T> {

    public abstract void validSpecification(T object);

    protected IllegalStateDomainException.Builder newIllegalStateExceptionBuilder() {
        return new IllegalStateDomainException.Builder();
    }
}