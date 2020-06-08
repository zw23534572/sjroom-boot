package github.sjroom.example.service.impl;

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

}

