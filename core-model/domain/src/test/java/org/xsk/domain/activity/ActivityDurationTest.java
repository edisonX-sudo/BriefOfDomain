package org.xsk.domain.activity;

import org.xsk.domain.common.IllegalStateDomainException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ActivityDurationTest {
    //不用拉起整个容器就测试了一个领域逻辑(测试成本低,测试速度快),本身也很容易就能达到100%覆盖率,也是可以集成到ci的轻型测试

    @org.junit.jupiter.api.Test
    void isNowInDurationRetTrue() {
        assertTrue(new ActivityDuration(LocalDateTime.now().plusHours(-1), LocalDateTime.now().plusHours(2)).isNowInDuration());
    }

    @org.junit.jupiter.api.Test
    void isNowInDurationRetFalse() {
        assertFalse(new ActivityDuration(LocalDateTime.now().plusHours(-2), LocalDateTime.now().plusHours(-1)).isNowInDuration());
    }

    @org.junit.jupiter.api.Test
    void startEndTimeException() {
        assertThrowsExactly(IllegalStateDomainException.class, () -> new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusHours(-2)));
    }

    @org.junit.jupiter.api.Test
    void startEndTimeException1() {
        assertThrowsExactly(IllegalStateDomainException.class, () -> new ActivityDuration(null, LocalDateTime.now().plusHours(-2)));
    }

    @org.junit.jupiter.api.Test
    void startEndTimeException2() {
        assertThrowsExactly(IllegalStateDomainException.class, () -> new ActivityDuration(null, null));
    }

    @org.junit.jupiter.api.Test
    void startEndTimeException3() {
        assertThrowsExactly(IllegalStateDomainException.class, () -> new ActivityDuration(LocalDateTime.now(), null));
    }
}