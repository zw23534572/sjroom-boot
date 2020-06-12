package github.sjroom.example.service;

import github.sjroom.example.bean.entity.Dict;

import java.util.Map;
import java.util.Set;

public interface IDictService {
	void echo();

	Map<Integer, Dict> findMap(Set<Long> ids) throws Exception;
}

