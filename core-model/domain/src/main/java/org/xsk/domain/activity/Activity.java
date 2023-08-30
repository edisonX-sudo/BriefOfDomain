package org.xsk.domain.activity;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.Entity;

import java.time.LocalDateTime;

public class Activity extends Entity<ActivityId> {
    // TODO: 2023/8/30 内聚和包的关系&内聚为什么会适合包结构
    //  DDD是一种复杂度分而治之的思想，而包&值对象是提供复杂度分而治之的工具
    LocalDateTime startTime;
    LocalDateTime endTime;
    ActivityDuration duration;

    public void meth1(){}

    public boolean isNowInDuration(){
        //使用此方法一定要导出整个实体
        //更大范围的选择调用(isNowInDuration/meth1),调用时会迷茫
        //复杂度被相对不合适的划分,因为就和startTime/endTime相关
        //单测也会变得复杂,要构建这个实体
        LocalDateTime now = LocalDateTime.now();
        return startTime.isBefore(now) && endTime.isAfter(now);
    }

    public boolean isNowInDuration1(){
        //更细粒度数据导出
        //更小范围的选择调用
        //复杂度被更合适的划分
        //更便于单测,只要构建Vo便能测试业务相关的领域逻辑
        return duration.isNowInDuration();
    }

    @Override
    protected DomainSpecificationValidator specificationValidator() {
        return null;
    }

    @Override
    public ActivityId id() {
        return null;
    }
}
