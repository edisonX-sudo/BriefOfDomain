# 领域层的主要采用:
1. 采用数据库对象和领域对象分离
   * 若采用数据库对象为领域对象,必然导致数据库的结构影响领域对象(即技术概念侵入业务概念).例子: 有时为了加速sql查询,会让本来要联查表获得的数据耦合到主表内. 使得本来仅需保证必要字段一致性的领域对象,为了加速性能,持久了本不该持久的概念. 这种情况通过数据库对象和领域对象分离可以轻松解决
   ![difference_between_designs.png](difference_between_designs.png)
   * 一旦数据库对象和领域对象分离,领域的字段就可以不用再遵循orm的映射规定,业务开发者能更加自由的设计承载更大业务价值的模型
   ```java
        //活动,领域实体
        class Activity{
            ActivityId id;//活动id
            String name;//活动名称
            String remark;//活动描述
            ActivityTimeRange range;//活动时间范围
        
            //现在活动是否可用(正在进行中)
            boolean isNowActivityAvailable(){
                return range.isNowActivityAvailable();
            }
        }
        
        //活动时间范围,本质上是值对象
        class ActivityTimeRange{
            LocalDateTime start;//开始时间
            LocalDateTime end;//结束时间
        
            //现在活动是否可用(正在进行中)
            boolean isNowActivityAvailable(){
                //此处完全是业务逻辑代码,值对象易于构建,便于测试
                //(以往测试业务逻辑往往要起整个服务)
                LocalDateTime now = LocalDateTime.now();
                return start.isBefore(now) && end.isAfter(now);
            }
        }
        //活动id,本质上是值对象
        class ActivityId{
            String id;
        }
   ```
2. 采用一个上下文内的领域对象,repo,factory等领域对象在一个包内
   * 因为一个上下文内的领域对象高度内聚,代表他们之间存在着较多的数据概念引用和部分数据共享,在一个包内可以让领域对象减少不该暴露的public的get/set方法(利用设置包级别数据共享),减少领域对象的概念暴露(减少认知和使用的复杂度)和被外界通过public的set方法影响内部一致性的风险  
   ![ideology_3.png](ideology_3.png)
   领域对象,repo,factory等领域对象在一个包内的例子(包级访问)
   ```java
        interface ActivityRepository{//活动仓储对象
            void save(Activity activity);//保存活动
        }

        class ActivityRepositoryImpl implements ActivityRepository{
        
            @Override
            public void save(Activity activity) {
                ActivityPo activityPo = new ActivityPo();
                activityPo.setId(activity.id.id);//包级访问使得对象不用再暴露公共的setter方法
                activityPo.setName(activity.name);//避免上图例子的walk()方法暴露setter的风险同时降低了概念暴露也降低了对象的使用复杂度
                activityPo.setRemark(activity.remark);
                activityPo.setTimeRangeStart(activity.range.start.toString());
                activityPo.setTimeRangeEnd(activity.range.end.toString());
                updatePo(activityPo);
            }
            
            public void updatePo(ActivityPo activityPo){
                //插入数据库的代码...
            }
        }
        
        @Data
        class ActivityPo{
            Long id;
            String name;
            String remark;
            String timeRangeStart;
            String timeRangeEnd;
        }
   ```

[//]: # (# 妥协:)

[//]: # (1. 仅在msgcenter上实现,评估效果&#40;不合适改回&#41;)