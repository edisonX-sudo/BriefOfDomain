# BriefOfDomain
# DDD目标
降低系统的复杂度,基于`人接受复杂度的能力有限的`这一理论.
# DDD降低复杂度的手段
* 通用语言
  * 程序概念和业务术语的一致,人脑就不用维护属于和程序的映射,从而减少复杂度
* 限界上下文
  * 将内聚业务划分壁垒(包级别/jar级别/进程级别),防止纠葛(复杂度如毛线般纠缠),从而限制复杂度
* 利用六边形架构的内外层分割(分割了业务复杂度和技术复杂度,防止纠葛,限制复杂度)
  * 内层领域层
    * 内含(内聚)业务复杂度: 业务约束,概念
  * 外层技术层
    * 内含技术复杂度: 领域概念和技术pojo转换,mysql,redis等具体实现
# Key Ideology
## 基于六边形结构
![ideology_1.png](img/ideology_1.png)
## 依赖流向
![ideology_2.png](img/ideology_2.png)
## 领域战术
![ideology_3.png](img/ideology_3.png)
# Main Mod
## write mod
![main_write_mod_related.png](img/main_write_mod_related.png)
## read mod
![main_read_mod_related.png](img/main_read_mod_related.png)
## Q&A
* 核心模块中实体耦合jpa框架的优劣?  
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
  