package org.xsk.domain.account;

import cn.hutool.core.util.StrUtil;
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
    protected DomainSpecificationValidator specificationValidator() {
        return new DomainSpecificationValidator() {
            @Override
            public void validSpecification() {
                throwOnCondition(StrUtil.isEmpty(country), "physical address country cant not be empty");
                throwOnCondition(StrUtil.length(country) > 50, "physical address country content over limit");
                throwOnCondition(StrUtil.isEmpty(city), "physical address city cant not be empty");
                throwOnCondition(StrUtil.length(city) > 50, "physical address city content over limit");
                throwOnCondition(StrUtil.isEmpty(street), "physical address street cant not be empty");
                throwOnCondition(StrUtil.length(street) > 50, "physical address street content over limit");
            }
        };
    }
}
