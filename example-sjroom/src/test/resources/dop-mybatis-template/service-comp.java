package ${currentPackage};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${config.voPackage}.${upperModelName}PageReqVo;
import ${config.voPackage}.${upperModelName}ReqVo;
import ${config.voPackage}.${upperModelName}RespVo;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import org.springframework.validation.annotation.Validated;
import github.sjroom.web.vo.IdListVo;
import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
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
	IPage<${upperModelName}RespVo> page(${upperModelName}PageReqVo reqVo);

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

	/**
	 * 批量移除
	 */
	void removeBatch(IdListVo<Long> idListVo);
}
