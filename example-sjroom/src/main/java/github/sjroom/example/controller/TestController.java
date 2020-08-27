package github.sjroom.example.controller;

import github.sjroom.core.exception.Assert;
import github.sjroom.example.bean.vo.TestReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(" 控制器")
@RestController
@Slf4j
public class TestController {
	@ApiOperation("全局异常拦截")
	@GetMapping("/test1")
	public List<String> test1() {
		Assert.throwFail("500", "某某异常");
		return null;
	}

	@ApiOperation("数据封包")
	@GetMapping("test2")
	@ResponseBody
	public List<String> test2() {
		List<String> stringList = new ArrayList<>();
		stringList.add("测试1");
		stringList.add("测试2");
		return stringList;
	}

	@ApiOperation("参数验证")
	@PostMapping("test3")
	@ResponseBody
	public void test3(@Validated @RequestBody TestReqVo reqVo) {
		log.info("test3 reqVo:{}", reqVo);
	}
}
