package org.xsk.domain.common;

public abstract class NotFoundEntityDomainException extends DomainException {

    protected NotFoundEntityDomainException(String message) {
        this(null, message, true);
    }

    protected NotFoundEntityDomainException(Id<?> id, String message) {
        this(id, message, true);
    }

    protected NotFoundEntityDomainException(Id<?> id, String message, boolean msgWithId) {
        super(msgWithId && id != null ? message + ": " + id.value() : message);
    }

}
