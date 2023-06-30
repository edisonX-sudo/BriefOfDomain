package org.xsk.domain.calendar;

public enum RuleAppliedDayType {
    NATURE_DAY(false), TO_WORK_DAY(false), TO_REST_DAY(false),
    ;
    boolean applied;

    RuleAppliedDayType(boolean applied) {
        this.applied = applied;
    }
}
