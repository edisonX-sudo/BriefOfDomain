package org.xsk.domain.common;

public abstract class DomainPolicy<E extends DomainEvent> {

    public DomainPolicy() {
        init();
    }

    private void init() {
        EventBus.registerPolicy(this);
    }

    public SubscribePoint subscribePoint() {
        return SubscribePoint.BEFORE_EVENT_EMITTED;
    }

    public abstract void subscribe(E e);

    protected abstract Class<E> eventClass();


    //订阅触发节点
    public enum SubscribePoint {
        BEFORE_EVENT_EMITTED,//执行在事件发生(DB数据事务提交)前
        AFTER_EVENT_EMITTED,//执行在事件发生(DB数据事务提交)后
        ASYNC,//事件触发时马上异步执行
        SYNC,//事件触发时马上同步执行
    }
}
