package org.xsk.domain.account;

import org.xsk.domain.common.DomainSpecificationValidator;

class Validator {
    static class AccountSpecificationValidator extends DomainSpecificationValidator<Account> {

        @Override
        public void validSpecification(Account account) {
            if (account.name.length() > 50) {
                throwIllegalStateException("account name length cant be greater than 50");
            }
        }
    }

    static class ContactSpecificationValidator extends DomainSpecificationValidator<Contact> {

        @Override
        public void validSpecification(Contact contact) {
            if (contact.email.length() > 50) {
                throwIllegalStateException("contact email length cant be greater than 50");
            }
            if (contact.phone.length() > 50) {
                throwIllegalStateException("contact phone length cant be greater than 50");
            }
        }

    }
}
