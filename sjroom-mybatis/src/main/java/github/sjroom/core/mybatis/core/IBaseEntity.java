package github.sjroom.core.mybatis.core;

import java.util.Date;

/**
 * @author Katrel.Zhou
 * @date 2019/5/31 11:27
 */
public interface IBaseEntity {

    /**
     * 主键id
     */
    Long getId();

    /**
     * 创建人
     */
    Long getCreatedBy();

    /**
     * 创建时间
     */
    Date getCreatedAt();

    /**
     * 更新人
     */
    Long getUpdatedBy();

    /**
     * 更新时间
     */
    Date getUpdatedAt();
}
