package github.sjroom.example.dao.comp;

import github.sjroom.example.dao.IAccountDaoComp;
import github.sjroom.example.dao.IAccountDao;
import github.sjroom.example.bean.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-06-15 16:23
 */
@Service
@Slf4j
public class AccountDaoCompImpl implements IAccountDaoComp {

    @Autowired
    private IAccountDao accountDao;

}
