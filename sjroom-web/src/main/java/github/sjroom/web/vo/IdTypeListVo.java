package github.sjroom.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class IdTypeListVo<R, K> extends IdListVo {

	@NotNull
	private K type;

	public IdTypeListVo(Set<R> list, K type) {
		this.idList = list;
		this.type = type;
	}

	public <R> IdTypeListVo<R, K> of(Set<R> list, K type) {
		return new IdTypeListVo<R, K>(list, type);
	}
}
