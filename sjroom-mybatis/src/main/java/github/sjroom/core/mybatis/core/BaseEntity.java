package github.sjroom.core.mybatis.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Katrel.Zhou
 * @date 2019/5/31 11:30
 */
@Getter
@Setter
public class BaseEntity implements IBaseEntity {

	/**
	 * 主键id
	 */
	@TableId
	private Long id;

	/**
	 * 创建人
	 */
	@TableField(value = "created_by", fill = FieldFill.INSERT)
	private Long createdBy;

	/**
	 * 创建时间
	 */
	@TableField(value = "created_at")
	private Date createdAt;

	/**
	 * 更新人
	 */
	@TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
	private Long updatedBy;

	/**
	 * 更新时间
	 */
	@TableField(value = "updated_at")
	private Date updatedAt;

}
