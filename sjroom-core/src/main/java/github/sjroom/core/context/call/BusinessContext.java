package github.sjroom.core.context.call;

import lombok.Data;

/**
 * 业务访问请求的上下文
 *
 * @Auther: manson.zhou
 * @Date: 2018/10/15 08:57
 * @Version 1.8
 * @Description:
 */
@Data
public class BusinessContext<T> {
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 用户当前角色id
	 */
	private Long roleId;
	/**
	 * 访问的菜单
	 */
	private Long menuId;
	/**
	 * 系统ID
	 */
	private Long systemId;
	/**
	 * 公司ID
	 */
	private Long companyId;
	/**
	 * 扩展数据，用于传输
	 */
	private T obj;

}
