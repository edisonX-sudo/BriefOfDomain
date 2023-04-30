package org.xsk.domain.common;

public class IllegalStateDomainException extends DomainException {
    protected IllegalStateDomainException(String message) {
        super(message);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String subject;
        private String operator;
        private Object object;

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder operator(String operator) {
            this.operator = operator;
            return this;
        }

        public Builder object(Object object) {
            this.object = object;
            return this;
        }

        public IllegalStateDomainException build() {
            return new IllegalStateDomainException(subject.trim() + " " + operator.trim() + " " + object.toString().trim());
        }

        public void throwException() throws IllegalStateDomainException{
            throw build();
        }
    }
}
