package org.xsk.domain.common;

public abstract class DomainPolicy {

    public SubscribePoint subscribePoint() {
        return SubscribePoint.BEFORE_DATA_COMMIT;
    }

    //订阅触发节点
    public enum SubscribePoint {
        BEFORE_DATA_COMMIT,//数据事务提交前
        AFTER_DATA_COMMIT,//数据事务提交后
        ASYNC,//事件提交时异步执行
    }
}
