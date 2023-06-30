package org.xsk.domain.common;

public abstract class DomainSpecificationValidator<T> {

    protected final T object;

    public DomainSpecificationValidator(T object) {
        this.object = object;
    }

    public abstract void validSpecification();

    protected void throwIllegalStateException(String message) {
        throw new IllegalStateDomainException(message);
    }

    protected void throwIllegalStateException(boolean condition, String message) {
        if (condition) {
            throwIllegalStateException(message);
        }
    }
}