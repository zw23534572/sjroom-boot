package github.sjroom.core.mybatis.injector.methods;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 实体模型
 *
 * @author manson.zhou
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityItem implements Serializable {
	private String column;
	private Object field;
}
