package github.sjroom.example.bean.vo;

import github.sjroom.core.page.PageReqParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单分页请求模型
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2019-12-16 14:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountPageReqVo extends PageReqParam {

    // 业务按需添加分页参数
}
