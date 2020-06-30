package github.sjroom.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdListVo<T> {
	@NotEmpty
	protected Set<T> idList;

	public static <R> IdListVo<R> of(Set<R> list) {
		return new IdListVo<>(list);
	}
}
