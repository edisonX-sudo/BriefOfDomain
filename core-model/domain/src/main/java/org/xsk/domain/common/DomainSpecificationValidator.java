package org.xsk.domain.common;

public interface DomainSpecificationValidator<T> {

    void validSpecification(T object);
}