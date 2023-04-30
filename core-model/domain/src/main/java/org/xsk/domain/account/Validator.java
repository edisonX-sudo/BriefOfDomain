package org.xsk.domain.account;

import org.xsk.domain.common.DomainSpecificationValidator;

class Validator {
    static class AccountSpecificationValidator extends DomainSpecificationValidator<Account> {

        @Override
        public void validSpecification(Account account) {
            if (account.name.length() > 50) {
                newIllegalStateExceptionBuilder()
                        .subject("account name length")
                        .condition("cant be greater than")
                        .object(50)
                        .throwException();
            }
        }
    }

    static class ContactSpecificationValidator extends DomainSpecificationValidator<Contact> {

        @Override
        public void validSpecification(Contact contact) {
            if (contact.email.length() > 50) {
                newIllegalStateExceptionBuilder()
                        .subject("contact email length")
                        .condition("cant be greater than")
                        .object(50)
                        .throwException();
            }
            if (contact.phone.length() > 50) {
                newIllegalStateExceptionBuilder()
                        .subject("contact phone length")
                        .condition("cant be greater than")
                        .object(50)
                        .throwException();
            }
        }

    }
}
