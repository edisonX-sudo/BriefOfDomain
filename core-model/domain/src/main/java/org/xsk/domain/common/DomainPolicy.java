package org.xsk.domain.common;

public abstract class DomainPolicy<E extends DomainEvent> {

    public SubscribePoint subscribePoint() {
        return SubscribePoint.BEFORE_DATA_COMMIT;
    }

    public abstract void subscribe(E e);

    //订阅触发节点
    public enum SubscribePoint {
        BEFORE_DATA_COMMIT,//执行在数据事务提交前
        AFTER_DATA_COMMIT,//执行在数据事务提交后
        ASYNC,//事件触发时马上异步执行
    }
}
