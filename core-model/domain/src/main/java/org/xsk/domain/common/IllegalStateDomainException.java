package org.xsk.domain.common;

import lombok.NonNull;

public class IllegalStateDomainException extends DomainException {
    protected IllegalStateDomainException(String message) {
        super(message);
    }

}
