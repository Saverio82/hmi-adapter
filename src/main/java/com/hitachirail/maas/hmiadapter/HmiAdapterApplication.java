package com.hitachirail.maas.hmiadapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.UUID;

@SpringBootApplication
@EnableSwagger2
public class HmiAdapterApplication {

	public static void main(String[] args) {
		System.setProperty("application.instance.id", UUID.randomUUID().toString());
		SpringApplication.run(HmiAdapterApplication.class, args);
	}

}
