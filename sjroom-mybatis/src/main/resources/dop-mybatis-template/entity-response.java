package ${currentPackage};

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
<% if (fileSuffix=='RespVo') {%>import io.swagger.annotations.ApiModelProperty;<% } %>


/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@Data
public class ${upperModelName}${fileSuffix}  {
<% for(var item in dbTableFieldInfoList) {
	var isIgore=false;
	for(var igoreitem in config.ignoreFieldArr){
		if(item.column==igoreitem){
		isIgore = true;
		break;
		}
	}
	if(isIgore) continue;
	%>

	<% if (fileSuffix=="RespVo") {%>
	@ApiModelProperty("${item.comment}")
	<% } else {%>
	/**
	 * ${item.comment}
	 */
	<% } %>
	<% if (item.propertyType=="Date") {%>
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	<% } %>
	private ${item.propertyType} ${item.property};
<% }%>
}
