# BriefOfDomain
# DDD目标
    降低系统的复杂度,基于`人接受复杂度的能力有限的`这一理论.
# DDD降低复杂度的手段
* 通用语言
  * 程序概念和业务术语的一致,人脑就不用维护业务术语和程序代码的映射,从而减少复杂度
* 限界上下文
  * 将内聚业务划分壁垒(可以利用包/jar/进程做壁垒),防止纠葛(多条复杂度如毛线般纠缠),从而限制复杂度发散
* 利用六边形架构的内外层分割(分割了业务复杂度和技术复杂度,防止纠葛,限制复杂度发散)
  * 内层领域层
    * 内含(内聚)业务复杂度: 业务约束,概念
  * 外层技术层
    * 内含技术复杂度: 领域概念和技术pojo转换,mysql,redis等具体实现
# 本项目采用思想
## 基于六边形结构
![ideology_1.png](img/ideology_1.png)
## 依赖流向
![ideology_2.png](img/ideology_2.png)
## 领域战术
![ideology_3.png](img/ideology_3.png)
# 本项目主要模块
## 写模块
![main_write_mod_related.png](img/main_write_mod_related.png)
## 读模块
![main_read_mod_related.png](img/main_read_mod_related.png)
## Q&A
### 核心模块中实体耦合jpa框架的优劣?  
* 好处是一定程度的减少代码编写  
* 坏处是实体设计从此和表结构一定程度耦合,无法随心所欲的设计实体,要考虑表结构,字段,性能等  
  _在此举例说明下领域实体直接面向表对领域的限制(如由于接入JPA,直接在JPA的@Entity上设计领域实体)_  
  ![example_relation.png](img/example_relation.png)  
  _面向领域设计领域实体和面向表设计领域实体(如因由于耦合的jpa等原因)的区别_  
  ![difference_between_designs.png](img/difference_between_designs.png)    
  _面向表设计领域实体的其他限制_    
      若我们采用了面向表来设计实体,如采用了设计1,如若读服务因为某种原因需要优化读取,给Permission表加了冗余字段role_id(即变成了设计3),
  则这种改变会直接影响到领域层([core-model](core-model))的设计,即富含业务知识的领域(业务)层([core-model](core-model))因为技术上的改变而被改变,带上了技术复杂度.  
      而若采用面向业务关系的设计,领域(业务)层([core-model](core-model))不需要感知这种变化,只要在实体和POJO转换的领域实现层([domain-impl](domain-impl),属于六边形外围的技术层)添加上字段转换即可,
  从而避免了需要保证一致性的领域层([core-model](core-model))耦合技术概念,并为技术优化改变实体设计的尴尬境地.    
  __故结论是最好不要采用核心模块中实体耦合jpa等技术框架__
### 为什么要用DDD,不用不行么
![complexity.png](img%2Fcomplexity.png)
* 用ddd是为了减少复杂度,如果当前项目复杂度不大,未来需求(即可预期的复杂度)也在可以控制的范围内,就可以不用ddd,
因为初期ddd的设计成本比较大(如上图所示,ddd是在高复杂度增效的),反而得不偿失
* 从ddd本身这个手段来讲,如果有其他手段和ddd定位相同,但能更高效的`降低系统的复杂度`,且避免了ddd的所有缺点,
我们完全可以`摈弃DDD(加强语气)`,因为`降低系统的复杂度`才是我们的最终追求(且铭记这句话,避免陷入教条主义),
但目前来看,采用ddd仍然降低系统复杂度的一个强力手段
   
  