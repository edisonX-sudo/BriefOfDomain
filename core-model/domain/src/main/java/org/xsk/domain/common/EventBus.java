package org.xsk.domain.common;

public class EventBus {
    public static void fire(DomainEvent event){
        // TODO: 2023/4/14 实现上根据订阅者DomainPolicy.receiveAfterDataCommit()的值,决定事务前(false)投递给哪些订阅者,事务后(true)投递给哪些订阅者
    }

}
