package github.sjroom.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.core.page.PageUtil;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.core.utils.StringUtil;
import github.sjroom.example.bean.bo.AccountBo;
import github.sjroom.example.bean.entity.Account;
import github.sjroom.example.dao.IAccountDao;
import github.sjroom.example.service.IAccountService;
import github.sjroom.mybatis.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <B>说明：服务实现</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-12-16 14:14
 */
@Slf4j
@Service
@Validated
public class AccountServiceImpl extends BaseServiceImpl<IAccountDao, Account> implements IAccountService {

	@Autowired
	IAccountDao accountDao;

	@Override
	public AccountBo findByBId(Long bid) {
		log.info("AccountServiceImpl findByBId params:{}", bid);
		Account account = super.getByBId(bid);
		return BeanUtil.copy(account, AccountBo.class);
	}

	@Override
	public List<AccountBo> findByBIds(Set<Long> accountIds) {
		log.info("AccountServiceImpl findByBIds params:{}", accountIds);
		List<Account> accounts = super.getBatchBIds(accountIds);
		return BeanUtil.copy(accounts, AccountBo.class);
	}

	@Override
	public List<AccountBo> findList(AccountBo accountBo) {
		log.info("AccountServiceImpl findList params:{}", accountBo);
		List<Account> accounts = super.list(this.query(accountBo));
		return BeanUtil.copy(accounts, AccountBo.class);
	}

	@Override
	public Map<Long, AccountBo> findMap(AccountBo accountBo) {
		log.info("AccountServiceImpl findMap params:{}", accountBo);
		List<AccountBo> accountBos = this.findList(accountBo);
		if (CollectionUtil.isEmpty(accountBos)) {
			log.warn("AccountServiceImpl find accountBos is empty");
			return Collections.emptyMap();
		}
		return accountBos.stream().collect(Collectors.toMap(AccountBo::getAccountId, Function.identity()));
	}

	@Override
	public IPage<AccountBo> findPage(AccountBo model) {
		log.info("AccountServiceImpl findPage params:{}", model);
//		IPage<Account> modelPage = accountDao.findPage(PageUtil.toPage(model), model);

		IPage<Account> modelPage = super.page(PageUtil.toPage(model), this.query(model));
		return PageUtil.toPage(modelPage, AccountBo.class);
	}

	private LambdaQueryWrapper<Account> query(AccountBo model) {
		return new LambdaQueryWrapper<Account>()
			.eq(ObjectUtil.isNotNull(model.getAccountId()), Account::getAccountId, model.getAccountId())
			.in(CollectionUtil.isNotEmpty(model.getAccountIds()), Account::getAccountId, model.getAccountIds())
			.eq(ObjectUtil.isNotNull(model.getStatus()), Account::getStatus, model.getStatus())
			.eq(StringUtil.isNotBlank(model.getLoginName()), Account::getLoginName, model.getLoginName());
	}
}

