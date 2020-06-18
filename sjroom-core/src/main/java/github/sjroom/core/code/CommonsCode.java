package github.sjroom.core.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务通用的 code
 *
 * @author manson.zhou
 */
@Getter
@AllArgsConstructor
public enum CommonsCode implements IResultCode {
	/**
	 * 公共的 code
	 */
	DATA_VALIDATION_FAILED("COM00101", "数据验证一场:{0}"),
	DATA_ENABLE_REMOVE_FAILED("COM00102", "仅待启用以及非初始化状态下的数据可进行删除"),
	DATA_ENABLE_OPERATE("COM00103", "仅启用或已禁用的状态方可操作"),
	DATA_ENABLE_OPERATE_FAILED("COM00104", "仅待启用或已禁用的数据方可启用，请确认选择启用的数据状态"),
	DATA_UN_ENABLE_OPERATE_FAILED("COM00105", "仅已启用及非初始化的数据方可禁用，请确认选择禁用的数据状态"),
	// 批量
	DATA_BATCH_LIMIT_IN_200("COM00106", "批量操作不能超过200条"),
	// 第三方 异常 code
	THIRD_PARTY_NETWORK_ERROR("COM00201", "调用第三方接口网络异常"),
	;

	private String code;
	private String msg;
}
