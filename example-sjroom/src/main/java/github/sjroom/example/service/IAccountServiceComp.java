package github.sjroom.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.example.bean.vo.AccountPageReqVo;
import github.sjroom.example.bean.vo.AccountReqVo;
import github.sjroom.example.bean.vo.AccountRespVo;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import org.springframework.validation.annotation.Validated;
import github.sjroom.web.vo.IdListVo;
import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-28 14:49
 */
@Validated
public interface IAccountServiceComp {
	/**
	 * 查看
	 */
	AccountRespVo find(IdVo<Long> idVo);

	/**
	 * 分页
	 */
	IPage<AccountRespVo> page(AccountPageReqVo reqVo);

	/**
	 * 列表
	 */
	List<AccountRespVo> list(AccountReqVo reqVo);

	/**
	 * 创建
	 */
	Long create(AccountReqVo accountReqVo);

	/**
	 * 更新
	 */
	void update(AccountReqVo accountReqVo);

	/**
	 * 批量更新
	 */
	void updateBatch(IdStatusListVo<Long,Integer> idStatusListVo);

	/**
	 * 批量移除
	 */
	void removeBatch(IdListVo<Long> idListVo);
}
