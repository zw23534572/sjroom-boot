package ${currentPackage};

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.Date;
<% if (fileSuffix=='ReqVo') {%>import io.swagger.annotations.ApiModelProperty;<% } %>

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

<% if (fileSuffix=="ReqVo") {%>
    @ApiModelProperty("${item.comment}")
<% } else {%>
    /**
     * ${item.comment}
     */
<% } %>
    private ${item.propertyType} ${item.property};
<% } %>
}
