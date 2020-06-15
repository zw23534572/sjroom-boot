package ${currentPackage};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${config.voPackage}.${upperModelName}PageReqVo;
import ${config.voPackage}.${upperModelName}ReqVo;
import ${config.voPackage}.${upperModelName}RespVo;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2019-12-16 14:14
 */
@Validated
public interface I${upperModelName}ServiceComp {
	/**
	 * 查看
	 */
	${upperModelName}RespVo find(IdVo<Long> idVo);

	/**
	 * 分页
	 */
	IPage page(${upperModelName}PageReqVo reqVo);

	/**
	 * 列表
	 */
	List<${upperModelName}RespVo> list(${upperModelName}ReqVo reqVo);

	/**
	 * 创建
	 */
	Long create(${upperModelName}ReqVo accountReqVo);

	/**
	 * 更新
	 */
	void update(${upperModelName}ReqVo accountReqVo);

	/**
	 * 批量更新
	 */
	void updateBatch(IdStatusListVo<Long,Integer> idStatusListVo);
}
