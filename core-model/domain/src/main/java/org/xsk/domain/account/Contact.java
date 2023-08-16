package org.xsk.domain.account;

import cn.hutool.core.util.StrUtil;
import lombok.ToString;
import org.xsk.domain.common.AggregateComponent;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;

@ToString
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
    protected DomainSpecificationValidator<? extends AggregateComponent> specificationValidator() {
        return new DomainSpecificationValidator<Contact>(this) {
            @Override
            protected void validSpecification() {
                throwIllegalStateException(StrUtil.length(object.email) > 50, "contact email length cant be greater than 50");
                throwIllegalStateException(StrUtil.length(object.phone) > 50, "contact phone length cant be greater than 50");
            }
        };
    }
}
