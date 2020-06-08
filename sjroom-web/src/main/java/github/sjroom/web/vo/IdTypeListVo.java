package github.sjroom.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class IdTypeListVo<R, K> extends IdListVo {

	@NotNull
	private K type;

	public IdTypeListVo(List<R> list, K type) {
		this.idList = list;
		this.type = type;
	}

	public <R> IdTypeListVo<R, K> of(List<R> list, K type) {
		return new IdTypeListVo<R, K>(list, type);
	}
}
