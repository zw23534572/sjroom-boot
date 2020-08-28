package github.sjroom.example.controller;

import github.sjroom.example.bean.vo.AccountPageReqVo;
import github.sjroom.example.bean.vo.AccountReqVo;
import github.sjroom.example.bean.vo.AccountRespVo;
import github.sjroom.example.service.IAccountServiceComp;
import github.sjroom.core.mybatis.page.PageResult;
import github.sjroom.core.mybatis.page.PageUtil;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdListVo;
import github.sjroom.web.vo.IdVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <B>说明：平台账号表 控制器</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2020-08-28 14:49
 */
@Slf4j
@Validated
@RestController
@RequestMapping("account")
@Api("平台账号表 控制器")
public class AccountController {
	@Autowired
	private IAccountServiceComp iAccountServiceComp;

	@ApiOperation(value = "查看", notes = "传入id")
	@PostMapping("find")
	public AccountRespVo find(@Validated @RequestBody IdVo<Long> idVo) {
		return iAccountServiceComp.find(idVo);
	}

	@ApiOperation("分页")
	@PostMapping("page")
	public PageResult page(@Validated @RequestBody AccountPageReqVo reqVo) {
		return PageUtil.toPageResult(iAccountServiceComp.page(reqVo), AccountRespVo.class);
	}

	@ApiOperation("列表")
	@PostMapping("list")
	public List<AccountRespVo> list(@RequestBody AccountReqVo reqVo) {
		return iAccountServiceComp.list(reqVo);
	}

	@ApiOperation("创建")
	@PostMapping("create")
	public Long create(@Validated @RequestBody AccountReqVo accountReqVo) {
		return iAccountServiceComp.create(accountReqVo);
	}

	@ApiOperation("更新")
	@PostMapping("update")
	public void update(@Validated @RequestBody AccountReqVo accountReqVo) {
		iAccountServiceComp.update(accountReqVo);
	}

	@ApiOperation(value = "批量更新", notes = "传入id")
	@PostMapping("batch-update")
	public void batchUpdate(@Validated @RequestBody IdStatusListVo idStatusListVo) {
		iAccountServiceComp.updateBatch(idStatusListVo);
	}

	@ApiOperation(value = "批量删除", notes = "传入id")
	@PostMapping("batch-remove")
	public void removeUpdate(@Validated @RequestBody IdListVo<Long> idListVo) {
		iAccountServiceComp.removeBatch(idListVo);
	}
}
