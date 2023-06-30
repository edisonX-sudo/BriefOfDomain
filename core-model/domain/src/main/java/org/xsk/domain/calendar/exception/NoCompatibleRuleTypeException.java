package org.xsk.domain.calendar.exception;

import org.xsk.domain.common.DomainException;

public class NoCompatibleRuleTypeException extends DomainException {
    public NoCompatibleRuleTypeException() {
        super("没匹配的规则类型");
    }
}
