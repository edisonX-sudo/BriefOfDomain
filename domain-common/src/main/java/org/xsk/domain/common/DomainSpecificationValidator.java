package org.xsk.domain.common;

public abstract class DomainSpecificationValidator<T> {

    protected final T object;

    public DomainSpecificationValidator(T object) {
        this.object = object;
    }

    protected abstract void validSpecification();

    protected static void throwIllegalStateException(String message) {
        throw new IllegalStateDomainException(message);
    }

    protected static void throwIllegalStateException(boolean condition, String message) {
        if (condition) {
            throwIllegalStateException(message);
        }
    }
}