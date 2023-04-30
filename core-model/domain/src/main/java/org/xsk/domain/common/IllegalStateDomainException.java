package org.xsk.domain.common;

import lombok.NonNull;

public class IllegalStateDomainException extends DomainException {
    protected IllegalStateDomainException(String message) {
        super(message);
    }

    static SubjectBuilder newBuilder() {
        return new SubjectBuilder();
    }

    public static class SubjectBuilder {

        private SubjectBuilder() {
        }

        public ConditionBuilder subject(@NonNull String subject) {
            return new ConditionBuilder(subject);
        }
    }

    public static class ConditionBuilder {
        private String subject;

        private ConditionBuilder(String subject) {
            this.subject = subject;
        }

        public ObjectBuilder condition(@NonNull String condition) {
            return new ObjectBuilder(this.subject, condition);
        }
    }

    public static class ObjectBuilder {
        private String subject;
        private String condition;
        private String object;

        private ObjectBuilder(String subject, String condition) {
            this.subject = subject;
            this.condition = condition;
        }

        public ObjectBuilder object(@NonNull Object object) {
            this.object = object.toString();
            return this;
        }

        public IllegalStateDomainException build() {
            return new IllegalStateDomainException(subject.trim() + " " + condition.trim() + " " + object.trim());
        }

        public void throwException() throws IllegalStateDomainException {
            throw build();
        }
    }


}
