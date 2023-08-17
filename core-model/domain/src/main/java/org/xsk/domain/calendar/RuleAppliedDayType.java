package org.xsk.domain.calendar;

public enum RuleAppliedDayType {
    NATURE_DAY(false), TO_WORK_DAY(true), TO_REST_DAY(true),
    ;
    final boolean applied;

    RuleAppliedDayType(boolean applied) {
        this.applied = applied;
    }
}
