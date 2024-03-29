package org.xsk.domain.common;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.Map;

public interface DomainSpecificationValidator {

    void validSpecification();

    default String defaultThrowMsg(String subjectName){
        return String.format("%s specification validation failed", subjectName);
    }

    default void throwOnCondition(Boolean condition, String msg) {
        if (condition)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnNull(Object obj, String msg) {
        throwOnCondition(obj == null, msg);
    }

    //Integer throw
    default void throwOnGt(Integer len, Integer limit, String msg) {
        throwOnCondition(len > limit, msg);
    }

    default void throwOnGte(Integer len, Integer limit, String msg) {
        throwOnCondition(len >= limit, msg);
    }

    default void throwOnLt(Integer len, Integer limit, String msg) {
        throwOnCondition(len < limit, msg);
    }

    default void throwOnLte(Integer len, Integer limit, String msg) {
        throwOnCondition(len <= limit, msg);
    }

    //CharSequence throw
    default void throwOnBlank(CharSequence str, String msg) {
        throwOnCondition(StrUtil.isBlank(str), msg);
    }

    default void throwOnEmpty(CharSequence str, String msg) {
        throwOnCondition(StrUtil.isEmpty(str), msg);
    }

    default void throwOnGt(CharSequence str, Integer limit, String msg) {
        throwOnCondition(StrUtil.length(str) > limit, msg);
    }

    default void throwOnGte(CharSequence str, Integer limit, String msg) {
        throwOnCondition(StrUtil.length(str) >= limit, msg);
    }

    default void throwOnLt(CharSequence str, Integer limit, String msg) {
        throwOnCondition(StrUtil.length(str) < limit, msg);
    }

    default void throwOnLte(CharSequence str, Integer limit, String msg) {
        throwOnCondition(StrUtil.length(str) <= limit, msg);
    }

    //Collection throw
    default void throwOnGt(Collection<?> col, Integer limit, String msg) {
        throwOnCondition(CollUtil.size(col) > limit, msg);
    }

    default void throwOnGte(Collection<?> col, Integer limit, String msg) {
        throwOnCondition(CollUtil.size(col) >= limit, msg);
    }

    default void throwOnLt(Collection<?> col, Integer limit, String msg) {
        throwOnCondition(CollUtil.size(col) < limit, msg);
    }

    default void throwOnLte(Collection<?> col, Integer limit, String msg) {
        throwOnCondition(CollUtil.size(col) <= limit, msg);
    }

    //Map throw
    default void throwOnGt(Map<?, ?> map, Integer limit, String msg) {
        throwOnCondition(CollUtil.size(map) > limit, msg);
    }

    default void throwOnGte(Map<?, ?> map, Integer limit, String msg) {
        throwOnCondition(CollUtil.size(map) >= limit, msg);
    }

    default void throwOnLt(Map<?, ?> map, Integer limit, String msg) {
        throwOnCondition(CollUtil.size(map) < limit, msg);
    }

    default void throwOnLte(Map<?, ?> map, Integer limit, String msg) {
        throwOnCondition(CollUtil.size(map) <= limit, msg);
    }
}