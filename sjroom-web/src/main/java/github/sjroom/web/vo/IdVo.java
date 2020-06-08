package github.sjroom.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdVo<T> {
	@NotNull
	protected T id;

	public static <T> IdVo<T> of(T id) {
		return new IdVo<>(id);
	}
}
