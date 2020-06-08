package github.sjroom.example.service.impl;

import github.sjroom.example.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DemoServiceImpl implements DemoService {
	@Override
	public void echo() {
		log.info("manson test");
	}
}
