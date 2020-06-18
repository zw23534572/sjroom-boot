package ${currentPackage};

import ${config.boPackage}.${upperModelName}Bo;
{config.entityPackage}.${upperModelName};
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@Mapper
public interface I${upperModelName}${fileSuffix} extends IMapper<${upperModelName}> {

	/**
	 * 查询分页信息
	 *
	 * @param reqVo
	 * @return
	 */
	IPage<${upperModelName}> findPage(IPage page, @Param(value = "model") ${upperModelName}Bo reqVo);
}
