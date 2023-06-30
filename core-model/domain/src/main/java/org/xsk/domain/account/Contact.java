package org.xsk.domain.account;

import org.xsk.domain.common.ValueObject;

public class Contact extends ValueObject {
    String phone;
    String email;

    public Contact(String phone, String email) {
        this.phone = phone;
        this.email = email;
        new Validator.ContactSpecificationValidator(this).validSpecification();
    }

    public String phone() {
        return phone;
    }

    public String email() {
        return email;
    }
}
