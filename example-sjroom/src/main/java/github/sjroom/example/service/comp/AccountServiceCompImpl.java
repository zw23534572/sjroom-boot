package github.sjroom.example.service.comp;

import github.sjroom.example.service.IAccountServiceComp;
import github.sjroom.example.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2019-12-16 14:14
 */
@Slf4j
@Service
@Validated
public class AccountServiceCompImpl implements IAccountServiceComp {
    @Autowired
    private IAccountService accountService;
}
