package github.sjroom.core.mybatis.page;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 分页查询模型，用于业务继承
 *
 * @author manson.zhou
 */
@Data
@ApiModel(description = "分页查询模型")
public class PageParam {

    @ApiModelProperty("页码")
    private Long pageNum = 1L;
    @ApiModelProperty("分页大小")
    private Long pageSize = 10L;

    /**
     * 开始返回数据的下标，用于limit 第一个参数
     *
     * @return
     */
    public Long startOffset() {
        return NumberUtils.max(0L, (pageNum - 1) * pageSize);
    }
}
