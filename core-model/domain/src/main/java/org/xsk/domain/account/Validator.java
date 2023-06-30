package org.xsk.domain.account;

import org.xsk.domain.common.DomainSpecificationValidator;

class Validator {
    static class AccountSpecificationValidator extends DomainSpecificationValidator<Account> {

        public AccountSpecificationValidator(Account object) {
            super(object);
        }


        @Override
        public void validSpecification() {
            throwIllegalStateException(object.name.length() > 50, "account name length cant be greater than 50");
        }
    }

    static class ContactSpecificationValidator extends DomainSpecificationValidator<Contact> {

        public ContactSpecificationValidator(Contact object) {
            super(object);
        }

        @Override
        public void validSpecification() {
            throwIllegalStateException(object.email.length() > 50, "contact email length cant be greater than 50");
            throwIllegalStateException(object.phone.length() > 50, "contact phone length cant be greater than 50");
        }
    }
}
