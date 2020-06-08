## sjroom-mybatis 简介.
java 是专门针对web入口进行封装的基础组件，让使用者尽量少配置就可以达到开箱即用。
> Mybatis 增强工具包 - 只做增强不做改变，简化CRUD操作。采用了mybatis -plus 的核心思想构建。

## 项目特性
无侵入：Mybatis-Plus 在 Mybatis 的基础上进行扩展，只做增强不做改变，引入 Mybatis-Plus 不会对您现有的 Mybatis 构架产生任何影响，而且 MP 支持所有 Mybatis 原生的特性
依赖少：仅仅依赖 Mybatis 以及 Mybatis-Spring
损耗小：启动即会自动注入基本CURD，性能基本无损耗，直接面向对象操作
通用CRUD操作：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
支持代码生成：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 层代码，支持模板引擎.

## 项目配置
当项目引入的公司组件 sjroom
再声明以下pom文件即可.
```xml
<dependency>
    <groupId>github.sjroom</groupId>
    <artifactId>sjroom-mybatis</artifactId>
</dependency>
```

## 业务主键
1. 实体中添加注解
```java
@TableBId
```

2. dao-comp（BaseService） 中扩展的方法
```java
/**
 * 根据 ID 查询
 *
 * @param id 主键ID
 */
T getByBId(Serializable id);

/**
 * 查询（根据ID 批量查询）
 *
 * @param idList 主键ID列表(不能为 null 以及 empty)
 * @return 集合
 */
List<T> getBatchBIds(Collection<? extends Serializable> idList);

/**
 * 根据 ID 修改
 *
 * @param entity 实体对象
 */
boolean updateByBId(T entity);

/**
 * 根据 ID LIST 批量修改，采用的是一条语句的形式 update XXX in (idList)
 *
 * @param entityList 实体对象
 */
boolean updateBatchByBIds(List<T> entityList);

/**
 * 根据 业务id 对 entity LIST 批量修改，采用的是执行多条语句的形式
 *
 * @param entityList 实体对象
 */
boolean updateBatchByBId(List<T> entityList);

/**
 * 根据 业务id 对 entity LIST 批量修改，采用的是执行多条语句的形式
 *
 * @param entityList 实体对象集合
 * @param batchSize  更新批次数量
 */
boolean updateBatchByBId(Collection<T> entityList, int batchSize);

/**
 * 根据 ID 删除
 *
 * @param id 主键ID
 */
boolean removeByBId(Serializable id);

/**
 * 删除（根据ID 批量删除）
 *
 * @param idList 主键ID列表(不能为 null 以及 empty)
 */
boolean removeBatchBIds(Collection<? extends Serializable> idList);
```

3. dao（IMapper）中扩展的方法
```java
selectByBId()
selectBatchByBIds()
updateByBId()
updateBatchByBIds()
updateBatchByBId()
updateBatchByBId()
deleteByBId()
deleteBatchByBIds()
```

## save vs Insert
1. 保存时如果有 `@TableBId` 会自动填充该字段。

2. 也可手动设置
```java
UtilId.getBId();
```

## 代码生成
```java
Properties props = getProperties();
ConfigGenerator configGenerator = new ConfigGenerator();
configGenerator.setDbUrl(props.getProperty("spring.datasource.url"));
configGenerator.setDbUser(props.getProperty("spring.datasource.username"));
configGenerator.setDbPassword(props.getProperty("spring.datasource.password"));
configGenerator.setDbSchema(props.getProperty("spring.datasource.dbSchema"));
configGenerator.setAuthor(System.getProperty("user.name"));


//  ---  需要更改的值
// 包名
configGenerator.setBasePackage("github.sjroom.dop.bc.message");
// 需要生成的表,多个为 "sys_msg_info,sys_msg_info12,sys_msg_info33"
configGenerator.setGenerateTableName("sys_msg_info");
// 替换表名前缀
configGenerator.setPrefixTableName("sys_");
new AutoGenerator(configGenerator).run();
```

### 生成的结构如下. 以(sys_msg_info为例)
```xml
├──gen_code
│   ├─github.sjroom.dop.bc.message
│      ├─bean 		
│         ├─bo         
│           ├─MsgInfoPageReqBo.java   #分页对象
│           ├─MsgInfoReqBo.java       #
│           └─MsgInfoRespBo.java       
│         ├─entity     
│           ├─MsgInfo.java            
│         └─vo
│             ├─MsgInfoPageReqVo.java   #分页对象
│             ├─MsgInfoReqVo.java      
│             └─MsgInfoRespVo.java       
│      ├─code 		                 
│         └─ApiCode.java  # 错误码定义		
│      ├─controller 		                 
│         └─MsgInfoController.java  	
│      ├─dao
│         ├─impl
│           └─MsgInfoDaoCompImpl.java 		        
│         ├─MsgInfoDao.java
│         └─MsgInfoDaoComp.java   
│      ├─dao
│         └─impl
│            └─MsgInfoServiceCompImpl.java 		        
│      ├─MsgInfoService.java
│      └─MsgInfoServiceComp.java
```
