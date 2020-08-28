package github.sjroom.example.dao;

import github.sjroom.example.bean.bo.AccountBo;
import github.sjroom.example.bean.entity.Account;
import github.sjroom.core.mybatis.mapper.IMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <B>说明：平台账号表</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-28 14:49
 */
@Mapper
public interface IAccountDao extends IMapper<Account> {

}
