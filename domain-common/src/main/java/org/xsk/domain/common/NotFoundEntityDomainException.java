package org.xsk.domain.common;

public abstract class NotFoundEntityDomainException extends DomainException {
    protected NotFoundEntityDomainException(String message) {
        super(message);
    }

}
