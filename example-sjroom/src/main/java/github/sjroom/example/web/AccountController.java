package github.sjroom.example.web;

import github.sjroom.core.page.PageResult;
import github.sjroom.core.page.PageUtil;
import github.sjroom.example.bean.vo.AccountPageReqVo;
import github.sjroom.example.bean.vo.AccountReqVo;
import github.sjroom.example.bean.vo.AccountRespVo;
import github.sjroom.example.service.IAccountServiceComp;
import github.sjroom.mybatis.annotation.FillField;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <B>说明： 控制器</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-12-16 14:14
 */
@Slf4j
@RestController
@RequestMapping("api/account")
@Api(" 控制器")
public class AccountController {
	@Autowired
	private IAccountServiceComp accountServiceComp;

	@ApiOperation(value = "查看", notes = "传入id")
	@PostMapping("find")
	@FillField
	public AccountRespVo find(@Validated @RequestBody IdVo idVo) {
		return accountServiceComp.find(idVo);
	}

	@ApiOperation("分页")
	@PostMapping("page")
	@FillField
	public PageResult page(@Validated @RequestBody AccountPageReqVo reqVo) {
		return PageUtil.toPageResult(accountServiceComp.page(reqVo), AccountRespVo.class);
	}

	@ApiOperation("列表")
	@PostMapping("list")
	@FillField
	public List<AccountRespVo> list(@Validated @RequestBody AccountReqVo reqVo) {
		return accountServiceComp.list(reqVo);
	}

	@ApiOperation("创建")
	@PostMapping("create")
	public Long create(@Validated @RequestBody AccountReqVo accountReqVo) {
		return accountServiceComp.create(accountReqVo);
	}

	@ApiOperation("更新")
	@PostMapping("update")
	public void update(@Validated @RequestBody AccountReqVo accountReqVo) {
		accountServiceComp.update(accountReqVo);
	}

	@ApiOperation(value = "批量更新", notes = "传入id")
	@PostMapping("batch-update")
	public void batchUpdate(@Validated @RequestBody IdStatusListVo idStatusListVo) {
		accountServiceComp.updateBatch(idStatusListVo);
	}
}
