package org.xsk.domain.account;

import org.xsk.domain.account.exception.IllegalAccountStateException;
import org.xsk.domain.common.DomainSpecificationValidator;

class Validator {
    static class AccountSpecificationValidator implements DomainSpecificationValidator<Account> {

        @Override
        public void validSpecification(Account account) {
            if (account.name.length() > 50) {
                throw new IllegalAccountStateException("account name length cant greater than 50");
            }
        }
    }

    static class ContactSpecificationValidator implements DomainSpecificationValidator<Contact> {

        @Override
        public void validSpecification(Contact contact) {
            if (contact.email.length() > 50) {
                throw new IllegalAccountStateException("contact email length cant greater than 50");
            }
            if (contact.phone.length() > 50) {
                throw new IllegalAccountStateException("contact phone length cant greater than 50");
            }
        }

    }
}
