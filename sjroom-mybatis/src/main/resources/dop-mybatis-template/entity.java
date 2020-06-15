package ${currentPackage};

import com.baomidou.mybatisplus.annotation.TableName;
import github.sjroom.mybatis.core.${sys};
import github.sjroom.mybatis.annotation.TableBId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@TableName("${dbTableName}")
@Data
@EqualsAndHashCode(callSuper = true)
public class ${upperModelName} extends ${sys} {

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
    /**
     * ${item.comment}
     */
    <% if (item.related) {%>
    @TableBId
    <% } %>
    private ${item.propertyType} ${item.property};
<% }%>
}
