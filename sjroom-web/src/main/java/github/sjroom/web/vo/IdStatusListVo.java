package github.sjroom.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdStatusListVo<R, K> extends IdListVo {

	@NotNull
	private K status;

	public IdStatusListVo(Set<R> list, K status) {
		this.idList = list;
		this.status = status;
	}

	public <R> IdStatusListVo<R, K> of(Set<R> list, K type) {
		return new IdStatusListVo<R, K>(list, type);
	}
}
