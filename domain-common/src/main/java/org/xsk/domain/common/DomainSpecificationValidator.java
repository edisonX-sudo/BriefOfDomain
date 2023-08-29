package org.xsk.domain.common;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.Map;

public interface DomainSpecificationValidator {

    void validSpecification();

    default void throwOnCondition(Boolean condition, String msg) {
        if (condition)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnNull(Object obj, String msg) {
        if (obj == null)
            throw new IllegalStateDomainException(msg);
    }

    //Integer throw
    default void throwOnGt(Integer len, Integer limit, String msg) {
        if (len > limit)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnGte(Integer len, Integer limit, String msg) {
        if (len >= limit)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnLt(Integer len, Integer limit, String msg) {
        if (len < limit)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnLte(Integer len, Integer limit, String msg) {
        if (len <= limit)
            throw new IllegalStateDomainException(msg);
    }

    //CharSequence throw
    default void throwOnBlank(CharSequence str, String msg) {
        if (StrUtil.isBlank(str))
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnEmpty(CharSequence str, String msg) {
        if (StrUtil.isEmpty(str))
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnGt(CharSequence str, Integer limit, String msg) {
        if (StrUtil.length(str) > limit)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnGte(CharSequence str, Integer limit, String msg) {
        if (StrUtil.length(str) >= limit)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnLt(CharSequence str, Integer limit, String msg) {
        if (StrUtil.length(str) < limit)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnLte(CharSequence str, Integer limit, String msg) {
        if (StrUtil.length(str) <= limit)
            throw new IllegalStateDomainException(msg);
    }

    //Collection throw
    default void throwOnGt(Collection<?> col, Integer limit, String msg) {
        if (CollUtil.size(col) > limit)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnGte(Collection<?> col, Integer limit, String msg) {
        if (CollUtil.size(col) >= limit)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnLt(Collection<?> col, Integer limit, String msg) {
        if (CollUtil.size(col) < limit)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnLte(Collection<?> col, Integer limit, String msg) {
        if (CollUtil.size(col) <= limit)
            throw new IllegalStateDomainException(msg);
    }

    //Map throw
    default void throwOnGt(Map<?, ?> map, Integer limit, String msg) {
        if (CollUtil.size(map) > limit)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnGte(Map<?, ?> map, Integer limit, String msg) {
        if (CollUtil.size(map) >= limit)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnLt(Map<?, ?> map, Integer limit, String msg) {
        if (CollUtil.size(map) < limit)
            throw new IllegalStateDomainException(msg);
    }

    default void throwOnLte(Map<?, ?> map, Integer limit, String msg) {
        if (CollUtil.size(map) <= limit)
            throw new IllegalStateDomainException(msg);
    }
}