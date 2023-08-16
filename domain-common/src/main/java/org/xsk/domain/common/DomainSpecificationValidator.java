package org.xsk.domain.common;

import java.util.function.BiConsumer;


public interface DomainSpecificationValidator {

    void validSpecification(BiConsumer<Boolean, String> throwIllegalStateException);

}