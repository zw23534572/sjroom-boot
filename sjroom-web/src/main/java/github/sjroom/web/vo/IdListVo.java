package github.sjroom.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdListVo<T> {
	@NotEmpty
	protected List<T> idList;

	public static <R> IdListVo<R> of(List<R> list) {
		return new IdListVo<>(list);
	}
}
