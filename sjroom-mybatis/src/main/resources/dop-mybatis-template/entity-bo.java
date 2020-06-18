package ${currentPackage};

import lombok.Data;


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
