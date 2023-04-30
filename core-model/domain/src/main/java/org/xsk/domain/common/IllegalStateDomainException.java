package org.xsk.domain.common;

import lombok.NonNull;

public class IllegalStateDomainException extends DomainException {
    protected IllegalStateDomainException(String message) {
        super(message);
    }

    static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String subject;
        private String operator;
        private Object object;

        Builder() {
        }

        public Builder subject(@NonNull String subject) {
            this.subject = subject;
            return this;
        }

        public Builder operator(@NonNull String operator) {
            this.operator = operator;
            return this;
        }

        public Builder object(@NonNull Object object) {
            this.object = object;
            return this;
        }

        public IllegalStateDomainException build() {
            return new IllegalStateDomainException(subject.trim() + " " + operator.trim() + " " + object.toString().trim());
        }

        public void throwException() throws IllegalStateDomainException {
            throw build();
        }
    }
}
