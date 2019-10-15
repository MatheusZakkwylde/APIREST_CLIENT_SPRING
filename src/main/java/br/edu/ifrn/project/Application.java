package br.edu.ifrn.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"br.edu.ifrn.project.api.client.config",
		"br.edu.ifrn.project.web.controller", "br.edu.ifrn.project.api.client.resource"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
