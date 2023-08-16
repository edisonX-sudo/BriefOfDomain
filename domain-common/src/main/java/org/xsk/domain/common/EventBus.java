package org.xsk.domain.common;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
public class EventBus {
    final static Map<DomainPolicy.SubscribePoint, Set<DomainPolicy<?>>> SUBSCRIBE_POINT_POLICY_MAP = new ConcurrentHashMap<>();
    final static ThreadLocal<Queue<DomainEvent>> BEFORE_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE = ThreadLocal.withInitial(ArrayDeque::new);
    final static ThreadLocal<Queue<DomainEvent>> AFTER_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE = ThreadLocal.withInitial(ArrayDeque::new);
    static Consumer<DomainEvent> consumeEventNeedRecord;

    public static <E extends DomainEvent> void fire(E event) {
        // : 2023/4/14 实现上根据订阅者DomainPolicy.subscribePoint()的值,
        //  决定事务前(false)投递给哪些订阅者,事务后(true)投递给哪些订阅者,还是马上异步执行
        @SuppressWarnings("unchecked")
        Set<DomainPolicy<E>> asyncDomainPolicies = (Set) SUBSCRIBE_POINT_POLICY_MAP.getOrDefault(DomainPolicy.SubscribePoint.ASYNC_IMMEDIATELY, new HashSet<>());
        asyncDomainPolicies.parallelStream().forEach(eDomainPolicy -> executeIfSubscribe(eDomainPolicy, event, false));
        @SuppressWarnings("unchecked")
        Set<DomainPolicy<E>> sycDomainPolicies = (Set) SUBSCRIBE_POINT_POLICY_MAP.getOrDefault(DomainPolicy.SubscribePoint.SYNC_IMMEDIATELY, new HashSet<>());
        sycDomainPolicies.forEach(eDomainPolicy -> executeIfSubscribe(eDomainPolicy, event, true));
        // : 2023/6/21 add event 2 BEFORE_EVENT_EMITTED,AFTER_EVENT_EMITTED policy wait list
        BEFORE_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE.get().add(event);
        AFTER_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE.get().add(event);
    }

    static void triggerBeforeMainProcessCompleted() {
        triggerMainProcessCompleted(
                BEFORE_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE,
                DomainPolicy.SubscribePoint.BEFORE_MAIN_PROCESS_COMPLETED,
                true,
                domainEvent -> {
                });
    }

    static void triggerAfterMainProcessCompleted() {
        triggerMainProcessCompleted(
                AFTER_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE,
                DomainPolicy.SubscribePoint.AFTER_MAIN_PROCESS_COMPLETED,
                false,
                domainEvent -> {
                    if (DomainEvent.RecordStatus.DO_RECORD.equals(domainEvent.recordInfo()) && consumeEventNeedRecord != null) {
                        consumeEventNeedRecord.accept(domainEvent);
                    }
                });
    }

    private static void triggerMainProcessCompleted(ThreadLocal<Queue<DomainEvent>> afterEventEmittedEventWaitingQueue, DomainPolicy.SubscribePoint afterEventEmitted, boolean throwWhileForeachEvents, Consumer<DomainEvent> afterEventConsumed) {
        try {
            Queue<DomainEvent> domainEvents = afterEventEmittedEventWaitingQueue.get();
            while (!domainEvents.isEmpty()) {
                DomainEvent domainEvent = domainEvents.poll();
                @SuppressWarnings("unchecked")
                Set<DomainPolicy<DomainEvent>> domainPolicies = (Set) SUBSCRIBE_POINT_POLICY_MAP.getOrDefault(afterEventEmitted,new HashSet<>());
                domainPolicies.forEach(domainPolicy -> executeIfSubscribe(domainPolicy, domainEvent, throwWhileForeachEvents));
                afterEventConsumed.accept(domainEvent);
            }
        } finally {
            afterEventEmittedEventWaitingQueue.remove();
        }
    }

    private static <E extends DomainEvent> void executeIfSubscribe(DomainPolicy<E> domainPolicy, E event, boolean throwException) {
        if (isPolicySubscribeEvent(domainPolicy, event)) {
            try {
                log.info("fire event: {}, invoke policy: {}", event.getClass().getSimpleName(), domainPolicy.getClass().getSimpleName());
                domainPolicy.subscribe(event);
            } catch (Exception exception) {
                log.warn(
                        String.format("error occurred when firing event: %s, domainPolicy: %s, throwException: %s",
                                event.getClass().getSimpleName(), domainPolicy.getClass().getSimpleName(), throwException)
                        , exception
                );
                if (throwException)
                    throw exception;
            }
        }
    }

    private static boolean isPolicySubscribeEvent(DomainPolicy<?> policy, DomainEvent event) {
        return policy.eventClass().isInstance(event);
    }

    static void cleanEventQueue() {
        BEFORE_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE.remove();
        AFTER_MAIN_PROCESS_COMPLETED_EVENT_WAITING_QUEUE.remove();
    }

    static void registerPolicy(DomainPolicy<?> policy) {
        Set<DomainPolicy<?>> domainPolicies = SUBSCRIBE_POINT_POLICY_MAP.computeIfAbsent(
                policy.subscribePoint(),
                k -> ConcurrentHashMap.newKeySet()
        );
        domainPolicies.add(policy);
    }

}
