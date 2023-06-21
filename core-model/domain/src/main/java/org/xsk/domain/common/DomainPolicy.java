package org.xsk.domain.common;

public abstract class DomainPolicy<E extends DomainEvent> {

    public DomainPolicy() {
        init();
    }

    private void init() {
        EventBus.registerPolicy(this);
    }

    public SubscribePoint subscribePoint() {
        return SubscribePoint.BEFORE_MAIN_PROCESS_COMPLETED;
    }

    public abstract void subscribe(E e);

    protected abstract Class<E> eventClass();


    //订阅触发节点
    public enum SubscribePoint {
        BEFORE_MAIN_PROCESS_COMPLETED,//执行在主流程完成(DB数据事务提交)前
        AFTER_MAIN_PROCESS_COMPLETED,//执行在主流程完成(DB数据事务提交)后
        ASYNC_IMMEDIATELY,//事件触发时马上异步执行
        SYNC_IMMEDIATELY,//事件触发时马上同步执行
    }
}
