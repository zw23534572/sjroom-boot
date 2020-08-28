package ${currentPackage};

import lombok.Data;
import java.util.Date;
import github.sjroom.core.mybatis.page.PageReqParam;
import lombok.EqualsAndHashCode;

/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ${upperModelName}${fileSuffix} extends PageReqParam {
<% for(var item in dbTableFieldInfoList) { %>
	/**
	 * ${item.comment}
	 */
	private ${item.propertyType} ${item.property};
<% } %>
}
