package ${currentPackage};

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单分页请求模型
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ${upperModelName}${fileSuffix} extends PageReqParam {

    // 业务按需添加分页参数
}
