package com.murong.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class WorkflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkflowApplication.class, args);
	}
}
