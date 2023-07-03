package org.xsk.domain.account;

import org.xsk.domain.common.AggregateComponent;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;

public class Contact extends ValueObject {
    String phone;
    String email;

    public Contact(String phone, String email) {
        this.phone = phone;
        this.email = email;
        validSpecification();
    }

    public String phone() {
        return phone;
    }

    public String email() {
        return email;
    }

    @Override
    protected DomainSpecificationValidator<? extends AggregateComponent>  specificationValidator() {
        return new Validator.ContactSpecificationValidator(this);
    }
}
