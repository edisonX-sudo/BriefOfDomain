package org.xsk.domain.calendar;

import org.xsk.domain.common.AggregateComponent;
import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.ValueObject;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class NatureDay extends ValueObject {
    LocalDate date;


    public NatureDay(LocalDate date) {
        this.date = date;
        validSpecification();
    }

    Boolean isWorkDay() {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return DayOfWeek.SATURDAY != dayOfWeek && DayOfWeek.SUNDAY != dayOfWeek;
    }


    static List<NatureDay> assembleRangeDays(NatureDay natureDayBegin, NatureDay natureDayEnd) {
        ArrayList<NatureDay> res = new ArrayList<>();
        LocalDate itDate = natureDayBegin.date;
        res.add(new NatureDay(itDate));
        while (itDate.isBefore(natureDayEnd.date)) {
            itDate = itDate.plus(1, ChronoUnit.DAYS);
            res.add(new NatureDay(itDate));
        }
        return res;
    }

    @Override
    protected DomainSpecificationValidator<? extends AggregateComponent>  specificationValidator() {
        return new Validator.NatureDaySpecificationValidator(this);
    }
}
