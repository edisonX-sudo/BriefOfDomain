package org.xsk.domain.account;

import cn.hutool.core.util.StrUtil;
import org.xsk.domain.common.AggregateComponent;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;


public class PhysicalAddress extends ValueObject {
    String country;
    String city;
    String street;

    public PhysicalAddress(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
        validSpecification();
    }

    public String country() {
        return country;
    }

    public String city() {
        return city;
    }

    public String street() {
        return street;
    }

    @Override
    protected DomainSpecificationValidator<? extends AggregateComponent> specificationValidator() {
        return new DomainSpecificationValidator<PhysicalAddress>(this) {
            @Override
            protected void validSpecification() {
                throwIllegalStateException(StrUtil.isEmpty(country), "physical address country cant not be empty");
                throwIllegalStateException(StrUtil.length(country) > 50, "physical address country content over limit");
                throwIllegalStateException(StrUtil.isEmpty(city), "physical address city cant not be empty");
                throwIllegalStateException(StrUtil.length(city) > 50, "physical address city content over limit");
                throwIllegalStateException(StrUtil.isEmpty(street), "physical address street cant not be empty");
                throwIllegalStateException(StrUtil.length(street) > 50, "physical address street content over limit");
            }
        };
    }
}
