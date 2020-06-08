package github.sjroom.example.bean.bo;

import github.sjroom.core.page.PageReqParam;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;


/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2019-12-16 14:14
 */
@Data
public class AccountBo extends PageReqParam {

	/**
	 * 业务主键
	 */
	private Long accountId;
	/**
	 * 业务主键，批次
	 */
	private Set<Long> accountIds;

	/**
	 * 账号类型（0.超级管理员，1.普通管理员，2.业务管理员，3.业务人员）
	 */
	private Integer accountType;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 登录用户名
	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 盐
	 */
	private String salt;
	/**
	 * 用户姓名
	 */
	private String realName;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 语言
	 */
	private Integer language;
	/**
	 * 长度单位
	 */
	private Integer lengthUnit;
	/**
	 * 重量单位
	 */
	private Integer volumeUnit;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 是否已分配公司（0.未分配 1.已分配）
	 */
	private Integer distribution;
	/**
	 * 是否首次登录
	 */
	private Integer landed;
	/**
	 * 登录状态
	 */
	private Integer loginStatus;
	/**
	 * 最近登录时间
	 */
	private Date lastLoginTime;
}
