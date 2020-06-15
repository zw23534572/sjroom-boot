package ${currentPackage};

import github.sjroom.core.page.PageReqParam;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.Date;


/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@Data
public class ${upperModelName}${fileSuffix} extends PageReqParam {
<% for(var item in dbTableFieldInfoList) { %>
	/**
	 * ${item.comment}
	 */
	private ${item.propertyType} ${item.property};
<% } %>
}
