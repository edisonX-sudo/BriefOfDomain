package org.xsk.domain.common;

public abstract class DomainSpecificationValidator<T> {

    public abstract void validSpecification(T object);

    protected IllegalStateDomainException.SubjectBuilder newIllegalStateExceptionBuilder() {
        return IllegalStateDomainException.newBuilder();
    }
}