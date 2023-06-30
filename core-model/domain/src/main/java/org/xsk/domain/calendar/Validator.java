package org.xsk.domain.calendar;

import cn.hutool.core.util.StrUtil;
import org.xsk.domain.common.DomainSpecificationValidator;

class Validator {
    static class CalendarRuleSpecificationValidator extends DomainSpecificationValidator<CalendarRule> {
        public CalendarRuleSpecificationValidator(CalendarRule object) {
            super(object);
        }

        @Override
        public void validSpecification() {
            throwIllegalStateException(object.code == null, "calendar rule code date cant be null");
            throwIllegalStateException(object.daySubRules == null, "calendar rule day rule cant be null");
        }
    }

    static class NatureDaySpecificationValidator extends DomainSpecificationValidator<NatureDay> {
        public NatureDaySpecificationValidator(NatureDay object) {
            super(object);
        }

        @Override
        public void validSpecification() {
            throwIllegalStateException(object.date == null, "nature day date cant be null");
        }
    }

    static class RuleAppliedDaySpecificationValidator extends DomainSpecificationValidator<RuleAppliedDay> {

        public RuleAppliedDaySpecificationValidator(RuleAppliedDay object) {
            super(object);
        }

        @Override
        public void validSpecification() {
            throwIllegalStateException(object.natureDay == null, "rule applied day natureDay cant be null");
            throwIllegalStateException(object.type == null, "rule applied day type cant be null");
        }
    }

    static class DaySubRuleSpecificationValidator extends DomainSpecificationValidator<DaySubRule> {
        public DaySubRuleSpecificationValidator(DaySubRule object) {
            super(object);
        }

        @Override
        public void validSpecification() {
            throwIllegalStateException(object.date == null, "day sub rule cant be null");
            throwIllegalStateException(object.ruleType == null, "day rule type cant be null");
            throwIllegalStateException(StrUtil.length(object.description) > 50, "day rule description length over limit");
        }
    }

    static class CalendarRuleCodeSpecificationValidator extends DomainSpecificationValidator<CalendarRuleCode> {
        public CalendarRuleCodeSpecificationValidator(CalendarRuleCode object) {
            super(object);
        }

        @Override
        public void validSpecification() {
            throwIllegalStateException(StrUtil.isEmpty(object.value()), "rule code cant be empty");
        }
    }
}
