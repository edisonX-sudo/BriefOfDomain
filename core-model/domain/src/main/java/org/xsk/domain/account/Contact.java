package org.xsk.domain.account;

import cn.hutool.core.util.StrUtil;
import lombok.ToString;
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
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnCondition(StrUtil.length(Contact.this.email) > 50, "contact email length cant be greater than 50");
                throwOnCondition(StrUtil.length(Contact.this.phone) > 50, "contact phone length cant be greater than 50");
            }
        };
    }
}
