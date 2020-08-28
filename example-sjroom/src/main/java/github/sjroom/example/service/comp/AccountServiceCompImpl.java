package github.sjroom.example.service.comp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.core.code.BaseErrorCode;
import github.sjroom.core.mybatis.enums.StatusEnum;
import github.sjroom.core.exception.Assert;
import github.sjroom.core.mybatis.page.PageUtil;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.example.bean.bo.AccountBo;
import github.sjroom.example.bean.entity.Account;
import github.sjroom.example.bean.vo.AccountPageReqVo;
import github.sjroom.example.bean.vo.AccountReqVo;
import github.sjroom.example.bean.vo.AccountRespVo;
import github.sjroom.example.service.IAccountService;
import github.sjroom.example.service.IAccountServiceComp;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import github.sjroom.web.vo.IdListVo;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-28 14:49
 */
@Slf4j
@Service
@Validated
public class AccountServiceCompImpl implements IAccountServiceComp {
	@Autowired
	private IAccountService accountService;

	@Override
	public AccountRespVo find(IdVo<Long> idVo) {
		AccountBo accountBo = accountService.findByBId(idVo.getId());
		return BeanUtil.copy(accountBo, AccountRespVo.class);
	}

	@Override
	public IPage<AccountRespVo> page(AccountPageReqVo reqVo) {
		IPage<AccountBo> accountBoIPage = accountService.findPage(this.buildParams(reqVo));
		this.buildResult(accountBoIPage.getRecords());
		return PageUtil.toPage(accountBoIPage, AccountRespVo.class);
	}

	@Override
	public List<AccountRespVo> list(AccountReqVo reqVo) {
		List<AccountBo> accountBos = accountService.findList(BeanUtil.copy(reqVo, AccountBo.class));
		return BeanUtil.copy(accountBos, AccountRespVo.class);
	}

	@Override
	public Long create(AccountReqVo reqVo) {
		AccountBo accountBo = this.validatedParams(reqVo);
		Account account = this.fetchEntityData(accountBo);
		accountService.save(account);
		return account.getAccountId();
	}

	@Override
	public void update(AccountReqVo reqVo) {
		AccountBo accountBo = this.validatedParams(reqVo);
		Account account = this.fetchEntityData(accountBo);
		account.setUpdatedAt(new Date());
		accountService.updateByBId(account);
	}

	@Override
	public void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo) {
		List<Account> accounts = accountService.getBatchBIds(idStatusListVo.getIdList());
		if (CollectionUtil.isEmpty(accounts)) {
			log.warn("AccountServiceCompImpl accountBos is empty");
			return;
		}
		accounts.stream().forEach(account -> {
			account.setStatus(idStatusListVo.getStatus());
			account.setUpdatedAt(new Date());
		});
		accountService.updateBatchByBIds(accounts);
		return;
	}

	@Override
	public void removeBatch(IdListVo<Long> idListVo) {
		if (CollectionUtil.isEmpty(idListVo.getIdList())) {
			log.warn("AccountServiceCompImpl removeBatch idListVo is empty");
			return;
		}

		List<AccountBo> accounts = accountService.findByBIds(idListVo.getIdList());
		if (CollectionUtil.isNotEmpty(accounts)) {
			accounts = accounts.stream().filter(x -> x.getStatus() == StatusEnum.UN_ENABLE).collect(Collectors.toList());
			Assert.throwOnFalse(accounts.size() > 0, BaseErrorCode.PARAM_ERROR, "必须有一个未启用状态，才能进行");
		}

		accountService.removeBatchBIds(idListVo.getIdList());
	}

	/**
	 * 构建参数
	 *
	 * @param reqVo
	 * @return
	 */
	private AccountBo buildParams(AccountPageReqVo reqVo) {
		AccountBo accountBo = BeanUtil.copy(reqVo, AccountBo.class);
		return accountBo;
	}

	/**
	 * 构建返回参数
	 *
	 * @param accountBos
	 */
	private void buildResult(List<AccountBo> accountBos) {
		if (CollectionUtil.isEmpty(accountBos)) {
			log.warn("AccountServiceCompImpl buildResult is empty");
			return;
		}
		// 实现业务逻辑
	}

	/**
	 * 验证参数
	 *
	 * @param reqVo
	 * @return
	 */
	private AccountBo validatedParams(AccountReqVo reqVo) {
		AccountBo accountBo = BeanUtil.copy(reqVo, AccountBo.class);
		return accountBo;
	}

	/**
	 * 获取实体数据
	 *
	 * @param accountBo
	 * @return
	 */
	private Account fetchEntityData(AccountBo accountBo) {
		Account account = BeanUtil.copy(accountBo, Account.class);
		return account;
	}
}
