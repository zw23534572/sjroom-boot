package github.sjroom.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.core.page.PageUtil;
import github.sjroom.example.bean.bo.AccountBo;
import github.sjroom.example.bean.vo.AccountPageReqVo;
import github.sjroom.example.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import github.sjroom.example.bean.entity.Account;
import github.sjroom.example.dao.IAccountDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import github.sjroom.mybatis.service.impl.BaseServiceImpl;

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
	private IAccountDao accountDao;

	@Override
	public IPage<Account> findPage(AccountPageReqVo reqVo) {
		reqVo.setAccountId(1206460712631992322l);
		return accountDao.findPage(PageUtil.toPage(reqVo), reqVo);
	}
}

