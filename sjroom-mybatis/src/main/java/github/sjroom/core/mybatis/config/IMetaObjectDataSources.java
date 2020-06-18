package github.sjroom.core.mybatis.config;

import github.sjroom.core.context.call.BusinessContextHolders;
import github.sjroom.core.mybatis.util.IdUtil;

/**
 * MetaObject 数据来源
 *
 * @author manson.zhou
 */
public interface IMetaObjectDataSources {

	/**
	 * 公司id
	 *
	 * @return 公司id
	 */
	default Object getXCompanyId() {
		return BusinessContextHolders.getXCompanyId();
	}

	/**
	 * 系统id
	 *
	 * @return 系统id
	 */
	default Object getXSystemId() {
		return BusinessContextHolders.getXSystemId();
	}

	/**
	 * 操作者
	 *
	 * @return 账号id
	 */
	default Object getOperator() {
		return BusinessContextHolders.getXSystemId();
	}

	/**
	 * 获取授权后透传的角色id
	 *
	 * @return 角色id
	 */
	default Object getOwner() {
		return BusinessContextHolders.getXUserId();
	}

	/**
	 * 获取业务id
	 *
	 * @return 业务id
	 */
	default Object getBizId() {
		return IdUtil.getBId();
	}

}
