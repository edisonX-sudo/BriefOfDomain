package org.xsk.domain.common;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class EventBus {
    final static Map<DomainPolicy.SubscribePoint, Set<DomainPolicy<?>>> SUBSCRIBE_POINT_POLICY_MAP = new ConcurrentHashMap<>();
    final static ThreadLocal<Queue<DomainEvent>> BEFORE_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE = ThreadLocal.withInitial(ArrayDeque::new);
    final static ThreadLocal<Queue<DomainEvent>> AFTER_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE = ThreadLocal.withInitial(ArrayDeque::new);

    public static <E extends DomainEvent> void fire(E event) {
        // : 2023/4/14 实现上根据订阅者DomainPolicy.subscribePoint()的值,
        //  决定事务前(false)投递给哪些订阅者,事务后(true)投递给哪些订阅者,还是马上异步执行
        @SuppressWarnings("unchecked")
        Set<DomainPolicy<E>> asyncDomainPolicies = (Set) SUBSCRIBE_POINT_POLICY_MAP.get(DomainPolicy.SubscribePoint.ASYNC_IMMEDIATELY);
        asyncDomainPolicies.parallelStream().forEach(eDomainPolicy -> executeIfNecessary(event, eDomainPolicy, false));
        @SuppressWarnings("unchecked")
        Set<DomainPolicy<E>> sycDomainPolicies = (Set) SUBSCRIBE_POINT_POLICY_MAP.get(DomainPolicy.SubscribePoint.SYNC_IMMEDIATELY);
        sycDomainPolicies.forEach(eDomainPolicy -> executeIfNecessary(event, eDomainPolicy, true));
        // : 2023/6/21 add event 2 BEFORE_EVENT_EMITTED,AFTER_EVENT_EMITTED policy wait list
        BEFORE_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE.get().add(event);
        AFTER_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE.get().add(event);
    }

    static void triggerBeforeMainProcessCompleted() {
        triggerMainProcessCompleted(BEFORE_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE, DomainPolicy.SubscribePoint.BEFORE_MAIN_PROCESS_COMPLETED);
    }

    static void triggerAfterMainProcessCompleted() {
        triggerMainProcessCompleted(AFTER_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE, DomainPolicy.SubscribePoint.AFTER_MAIN_PROCESS_COMPLETED);
    }

    private static void triggerMainProcessCompleted(ThreadLocal<Queue<DomainEvent>> afterEventEmittedEventWaitingQueue, DomainPolicy.SubscribePoint afterEventEmitted) {
        try {
            Queue<DomainEvent> domainEvents = afterEventEmittedEventWaitingQueue.get();
            while (!domainEvents.isEmpty()) {
                DomainEvent domainEvent = domainEvents.poll();
                @SuppressWarnings("unchecked")
                Set<DomainPolicy<DomainEvent>> domainPolicies = (Set) SUBSCRIBE_POINT_POLICY_MAP.get(afterEventEmitted);
                domainPolicies.forEach(domainPolicy -> executeIfNecessary(domainEvent, domainPolicy, true));
            }
        } finally {
            afterEventEmittedEventWaitingQueue.remove();
        }
    }

    static void cleanEventQueue(){
        BEFORE_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE.remove();
        AFTER_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE.remove();
    }

    private static <E extends DomainEvent> void executeIfNecessary(E event, DomainPolicy<E> eDomainPolicy, boolean throwException) {
        if (isPolicySubscribeEvent(eDomainPolicy, event)) {
            try {
                eDomainPolicy.subscribe(event);
            } catch (Exception exception) {
                log.warn("fire event {} error", event.getClass().getSimpleName(), exception);
                if (throwException)
                    throw exception;
            }
        }
    }

    private static boolean isPolicySubscribeEvent(DomainPolicy<?> policy, DomainEvent event) {
        return policy.eventClass().isInstance(event);
    }

    static void registerPolicy(DomainPolicy<?> policy) {
        Set<DomainPolicy<?>> domainPolicies = SUBSCRIBE_POINT_POLICY_MAP.computeIfAbsent(
                policy.subscribePoint(),
                k -> ConcurrentHashMap.newKeySet()
        );
        domainPolicies.add(policy);
    }

//    public static void main(String[] args) {
//        DeviceBecameOffline obj = new DeviceBecameOffline(new DeviceId(1L));
//        System.out.println(obj.toString());
//    }
}
