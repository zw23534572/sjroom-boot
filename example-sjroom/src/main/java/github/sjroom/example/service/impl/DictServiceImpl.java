package github.sjroom.example.service.impl;

import github.sjroom.example.bean.entity.Dict;
import github.sjroom.example.service.IDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class DictServiceImpl implements IDictService {
	@Override
	public void echo() {
		log.info("manson test");
	}

	@Override
	public Map<Integer, Dict> findMap(Set<Long> ids) {
		log.info("DemoServiceImpl findMap ids:{}", ids);
		Dict dict = new Dict();
		dict.setDictId(1);
		dict.setDictName("test");

		Map<Integer, Dict> mapStatus = new HashMap<>();
		mapStatus.put(1, dict);
		mapStatus.put(2, dict);
		mapStatus.put(3, dict);
		return mapStatus;
	}
}
