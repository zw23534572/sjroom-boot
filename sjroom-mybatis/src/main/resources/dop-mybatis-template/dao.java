package ${currentPackage};

import org.apache.ibatis.annotations.Mapper;
import ${config.entityPackage}.${upperModelName};
import ${config.voPackage}.${upperModelName}ReqVo;
import ${config.voPackage}.${upperModelName}RespVo;
import java.util.List;

/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@Mapper
public interface I${upperModelName}${fileSuffix} extends IMapper<${upperModelName}> {
	List<${upperModelName}Response> selectPage(${upperModelName}Request ${lowerModelName}Request);
}
