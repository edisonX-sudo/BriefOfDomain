package org.xsk.domain.calendar;

import org.xsk.domain.common.NotFoundEntityDomainException;

public class CalendarRuleRepoImpl extends CalendarRuleRepo{
    @Override
    protected CalendarRule findInternal(CalendarRuleCode id) {
        return null;
    }

    @Override
    protected NotFoundEntityDomainException notFoundException(CalendarRuleCode id) {
        return null;
    }

    @Override
    protected void saveInternal(CalendarRule entity) {

    }
}
