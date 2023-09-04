package org.xsk.domain.activity;

import org.xsk.domain.common.DomainSpecificationValidator;
import org.xsk.domain.common.Entity;

import java.time.LocalDateTime;

public class Activity extends Entity<ActivityId> {

    LocalDateTime startTime;
    LocalDateTime endTime;
    ActivityDuration duration;

    public void meth1(){}
    // TODO: 2023/8/30 内聚和包的关系&内聚为什么会适合包结构
    //  DDD是一种复杂度分而治之的思想，而包&值对象是提供复杂度分而治之的工具
    //  理论支持
    //  An explanation of what it is from Steve McConnell's Code Complete:
    //      Cohesion refers to how closely all the routines in a class or all the code in a routine support a central purpose.
    //      Classes that contain strongly related functionality are described as having strong cohesion(所有的在类内的方法应该都围绕着一个单一的内聚职责[vo就很符合]), and the heuristic goal
    //      is to make cohesion as strong as possible. Cohesion is a useful tool for managing complexity
    //      because the more code in a class supports a central purpose, the more easily your brain can remember everything the code does.
    //      Some way of achieving it from Uncle Bob's Clean Code:
    //  Classes should have a small number of instance variables. Each of the methods of a class should manipulate one or
    //      more of those variables. In general the more variables a method manipulates the more cohesive that method is to its class.(方法操作的类对象愈多,类越内聚[vo就很符合])
    //      A class in which each variable is used by each method is maximally cohesive.
    //      In general it is neither advisable nor possible to create such maximally cohesive classes; on the other hand,
    //      we would like cohesion to be high. When cohesion is high, it means that the methods and variables of the class are
    //      co-dependent and hang together as a logical whole.

    public boolean isNowInDuration(){
        //使用此方法一定要导出整个实体
        //更大范围的选择调用(感知isNowInDuration/感知meth1),调用时会迷茫
        //复杂度被相对不合适的划分,因为就和startTime/endTime相关
        //单测也会变得复杂,要构建这个实体
        LocalDateTime now = LocalDateTime.now();
        return startTime.isBefore(now) && endTime.isAfter(now);
    }

    public boolean isNowInDuration1(){
        //更细粒度数据导出
        //更小范围的选择调用
        //复杂度被更合适的划分
        //更便于单测,只要构建Vo便能测试业务相关的领域逻辑: 见org.xsk.domain.activity.ActivityDurationTest
        //ps: 理论上VO模型不受限制后开发者可以构建出任何更加贴近抽象本质的模型
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
