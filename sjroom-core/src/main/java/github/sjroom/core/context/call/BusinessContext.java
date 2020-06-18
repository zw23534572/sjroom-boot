package github.sjroom.core.context.call;

import lombok.Data;

/**
 * 业务访问请求的上下文
 *
 * @Auther: smj
 * @Date: 2018/10/15 08:57
 * @Version 1.8
 * @Description:
 */
@Data
public class BusinessContext {
	private Long userId;//用户ID
    private Long roleId;//用户当前角色
    private Long systemId;
    private Long companyId; // 公司ID
    private Long menuId; // 访问的菜单

}
