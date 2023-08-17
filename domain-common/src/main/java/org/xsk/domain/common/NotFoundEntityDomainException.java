package org.xsk.domain.common;

public abstract class NotFoundEntityDomainException extends DomainException {
    private final Id<?> id;

    protected NotFoundEntityDomainException(Id<?> id, String message) {
        super(message + ": " + id.value());
        this.id = id;
    }

}
