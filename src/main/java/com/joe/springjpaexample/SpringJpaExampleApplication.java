package com.joe.springjpaexample;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.util.Optional;

@SpringBootApplication
@Slf4j
public class SpringJpaExampleApplication {

	@SneakyThrows
	public static void main(String[] args) {
		final ConfigurableApplicationContext application = SpringApplication.run(SpringJpaExampleApplication.class, args);

		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = Optional.ofNullable(env.getProperty("server.port")).orElse("8080");
		String path = Optional.ofNullable(env.getProperty("server.servlet.context-path")).orElse("");
		String serviceName = Optional.ofNullable(env.getProperty("spring.application.name")).orElse("Spring Boot Application");
		log.info("\n----------------------------------------------------------\n\t" +
				"Service:" + serviceName + " is running! Access URLs:\n\t" +
				"Local: \t\thttp://localhost:" + port + path + "/\n\t" +
				"External: \thttp://" + ip + ":" + port + path + "/\n\t" +
				"swagger-ui: http://" + ip + ":" + port + path + "/swagger-ui/index.html\n" +
				"----------------------------------------------------------");
	}

}
