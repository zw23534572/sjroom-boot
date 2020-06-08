# 数据化运营平台-基础组件集

## 组件功能列表

| 组件名                   | 描述                                | 状态    |
| ------------------------ | ----------------------------------- | ------- |
| sjroom-boot     | 父依赖，版本、插件等版本和依赖管理  | 完成    |
| sjroom-hystrix    | hystrix组件扩展                     | 完成    |
| sjroom-feign      | feign组件扩展                       | 完成    |
| sjroom-mybatis    | mybatis 代码生成和基础依赖          | 完成    |
| sjroom-lock       | 基于 redis 分布式锁                 | 完成    |
| sjroom-idgen      | 基于 redis id生成组件               | 完成    |
| sjroom-core       | 核心功能组件集，utils、基础组件接口 | 完成 |
| sjroom-autoconfig | boot 通用功能自动配置               | 完成 |
| sjroom-log        | 日志组件                            | 完成 |
| sjroom-cache      | cache组件扩展                       | 80%     |
| sjroom-datasource | 多数据源读写分离                    | 0%      |
| sjroom-http | http 远程调用工具包                    | 完成      |

## TODO

1. 完善服务上下文，目前可传递配置的上下文。

2. 缓存组件的开发。

3. 多数据源组件开发。

## 公共请求头

### 前端传递

| Header            | 是否必须 | 简介               |
| ----------------- | -------- | ------------------ |
| x-client-language | 是       | 语言               |
| x-auth-token      | 否       | 需要认证的接口传递 |
| x-random-no       | 是       | 随机数             |
| x-menu-button     | 否       | 按钮id             |
| x-tenant-id       | 否       | 租户id             |

### 网关生成

| Header       | 是否必须 | 简介                                  |
| ------------ | -------- | ------------------------------------- |
| x-request-id | 是       | 请求id（agw20190425115730031）        |
| x-client-ip  | 是       | 从 x-forwarded-for 获取浏览器ip，透传 |
| x-client-ua  | 是       | 客户端 userAgent                      |
| x-account-id | 否       | 网关认证完毕后的用户信息头            |
| x-x-role-id | 否       | 网关认证完毕后的用户角色头            |


注意：网关需要清洗外部传递的头信息。

### 服务间透传请求头

| Header            | 是否必须 | 简介                            |
| ----------------- | -------- | ------------------------------- |
| x-client-language | 是       | 语言                            |
| x-client-ua       | 是       | 客户端 userAgent                |
| x-client-ip       | 否       | 从 x-forwarded-for 获取浏览器ip |
| x-request-id      | 否       | 请求id                          |
| x-menu-button     | 否       | 按钮id                          |
| x-tenant-id       | 否       | 租户id                          |
| x-account-id      | 否       | 网关认证完毕后的用户信息头。    |
| x-x-role-id | 否       | 网关认证完毕后的用户角色头            |

## 公共响应头

| Header       | 是否必须 | 简介     |
| ------------ | -------- | -------- |
| x-random-no  | 是       | 原样返回 |
| x-request-id | 是       | 请求id   |

