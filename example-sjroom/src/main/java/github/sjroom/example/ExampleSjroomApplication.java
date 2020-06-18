package github.sjroom.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author {author}
 */
@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"github.sjroom"})
public class ExampleSjroomApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExampleSjroomApplication.class, args);
	}
}
