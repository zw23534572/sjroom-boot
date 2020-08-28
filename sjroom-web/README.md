## 简介

sjroom-web 是专门针对web入口进行封装的基础组件，让使用者尽量少配置就可以达到开箱即用。

目前有以下功能：

1.统一异常拦截
2.统一正常封包


### 使用1：统一异常拦截

测试代码

```java
/**
 * 统一异常拦截
 *
 * @return
 */
@GetMapping("/test1")
public List<String> test1() {
    Assert.throwFail("500", "某某异常");
    return null;
}
```

输出结果

```
{
  "data": null,
  "stateCode": "500",
  "stateMsg": "系统错误: 某某异常"
}
```


### 使用2：统一正常封包

测试代码

```java
/**
  * 返回正常的实体     
*/
@RequestMapping("/test2")
@ApiOperation("test2")
@ResponseBody
public List<String> test2() {
  List<String> stringList = new ArrayList<>();
  stringList.add("测试1");
  stringList.add("测试2");
  return stringList;
}
```

输出结果

```
{
	"code":200,
	"data":[
		"测试1",
		"测试2"
	],
	"msg":"成功",
	"success":true
}
```

> 注意： 需要有@ApiOperation的注解，才有封包效果。




