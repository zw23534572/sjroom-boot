package github.sjroom.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyDataVo<K, V> {
	private K key;
	private V value;

	public static <T, R> KeyDataVo<T, R> of(T key, R value) {
		return new KeyDataVo<>(key, value);
	}
}
