package github.sjroom.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.core.page.PageReqParam;
import github.sjroom.example.bean.bo.AccountBo;
import github.sjroom.example.bean.entity.Account;
import github.sjroom.example.bean.vo.AccountPageReqVo;
import github.sjroom.mybatis.service.BaseService;
import org.apache.ibatis.annotations.Param;

/**
 * <B>说明：服务</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-12-16 14:14
 */
public interface IAccountService extends BaseService<Account>{

	/**
	 * 查询分页信息
	 *
	 * @param reqVo
	 * @return
	 */
	IPage<Account> findPage(AccountPageReqVo reqVo);
}
