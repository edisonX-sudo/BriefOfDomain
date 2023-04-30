package org.xsk.domain.account;

import org.xsk.domain.account.exception.IllegalAccountStateException;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.IllegalStateDomainException;

class Validator {
    static class AccountSpecificationValidator implements DomainSpecificationValidator<Account> {

        @Override
        public void validSpecification(Account account) {
            if (account.name.length() > 50) {
                IllegalStateDomainException.newBuilder()
                        .object("account name length")
                        .operator("cant be greater than")
                        .object(50)
                        .throwException();
            }
        }
    }

    static class ContactSpecificationValidator implements DomainSpecificationValidator<Contact> {

        @Override
        public void validSpecification(Contact contact) {
            if (contact.email.length() > 50) {
                IllegalStateDomainException.newBuilder()
                        .object("contact email length")
                        .operator("cant be greater than")
                        .object(50)
                        .throwException();
            }
            if (contact.phone.length() > 50) {
                IllegalStateDomainException.newBuilder()
                        .object("contact phone length")
                        .operator("cant be greater than")
                        .object(50)
                        .throwException();
            }
        }

    }
}
