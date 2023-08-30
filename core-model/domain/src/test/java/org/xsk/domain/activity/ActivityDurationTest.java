package org.xsk.domain.activity;

import org.xsk.domain.common.IllegalStateDomainException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ActivityDurationTest {
    //不用拉起整个容器就能测试,是可以集成到ci的轻型测试

    @org.junit.jupiter.api.Test
    void isNowInDuration() {
        assertTrue(new ActivityDuration(LocalDateTime.now().plusHours(-1), LocalDateTime.now().plusHours(2)).isNowInDuration());
    }

    @org.junit.jupiter.api.Test
    void isNowNotInDuration() {
        assertFalse(new ActivityDuration(LocalDateTime.now().plusHours(-2), LocalDateTime.now().plusHours(-1)).isNowInDuration());
    }

    @org.junit.jupiter.api.Test
    void startEndTimeException() {
        assertThrowsExactly(IllegalStateDomainException.class, () -> new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusHours(-2)));
    }
}