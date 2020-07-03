package github.sjroom.core;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import github.sjroom.core.code.BaseErrorCode;
import github.sjroom.core.code.I18nUtil;
import github.sjroom.core.utils.ObjectUtil;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T> 泛型标记
 * @author L.cm
 */
@Getter
@Setter
@ToString
@ApiModel(description = "返回信息")
@Slf4j
public class RespVo<T> implements Serializable {
	/**
	 * 返回代码
	 */
	private String stateCode;
	/**
	 * 返回消息
	 */
	private String stateMsg;
	/**
	 * 返回消息动态参数
	 */
	private Object[] stateMsgArgs;
	/**
	 * 返回数据
	 */
	private T data;


	public RespVo() {
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateMsg() {
//		if (ObjectUtil.isNotNull(stateCode)) {
//			try {
//				stateMsg = I18nUtil.getMessage(stateCode, stateMsgArgs);
//			} catch (Exception ex) {
//				log.warn("getStateMsg ex:{}", ex.getMessage());
//			}
//		}
		return stateMsg;
	}

	public void setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setStateMsgArgs(Object[] stateMsgArgs) {
		this.stateMsgArgs = stateMsgArgs;
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public Object[] getStateMsgArgs() {
		return stateMsgArgs;
	}

	/**
	 * 是否成功
	 *
	 * @return true or false
	 */
	@JsonIgnore
	@JSONField(serialize = false)
	public boolean isSuccess() {
		return BaseErrorCode.SUCCESS.equals(this.stateCode);
	}

	/**
	 * 是否失败
	 *
	 * @return true or false
	 */
	@JsonIgnore
	@JSONField(serialize = false)
	public boolean isFailure() {
		return !this.isSuccess();
	}

	/**
	 * 成功
	 *
	 * @return RespVo
	 */
	public static RespVo success() {
		RespVo respVo = new RespVo();
		respVo.setStateCode(BaseErrorCode.SUCCESS);
		return respVo;
	}

	/**
	 * 成功
	 *
	 * @return RespVo<T>
	 */
	public static <T> RespVo<T> success(T data) {
		RespVo<T> respVo = new RespVo<>();
		respVo.setStateCode(BaseErrorCode.SUCCESS);
		respVo.setData(data);
		return respVo;
	}

	/**
	 * 失败
	 *
	 * @return RespVo
	 */
	public static RespVo ok(String stateCode, String msg) {
		RespVo respVo = new RespVo();
		respVo.setStateCode(stateCode);
		respVo.setStateMsg(msg);
		return respVo;
	}
}
