package org.xsk.domain.account;

import cn.hutool.core.util.StrUtil;
import org.xsk.domain.common.DomainSpecificationValidator;

class Validator {
    static class AccountSpecificationValidator extends DomainSpecificationValidator<Account> {

        public AccountSpecificationValidator(Account object) {
            super(object);
        }


        @Override
        protected void validSpecification() {
            throwIllegalStateException(StrUtil.length(object.name) > 50, "account name length cant be greater than 50");
        }
    }

    static class ContactSpecificationValidator extends DomainSpecificationValidator<Contact> {

        public ContactSpecificationValidator(Contact object) {
            super(object);
        }

        @Override
        protected void validSpecification() {
            throwIllegalStateException(StrUtil.length(object.email) > 50, "contact email length cant be greater than 50");
            throwIllegalStateException(StrUtil.length(object.phone) > 50, "contact phone length cant be greater than 50");
        }
    }
}
