package br.com.ees.site;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import br.com.ees.site.client.sendgrid.model.To;

@EnableAsync
@SpringBootApplication
public class EESSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(EESSiteApplication.class, args);
	}

	@Bean
	public To to(@Value("${EES_TO_EMAIL_INBOUND}") String email, @Value("${EES_TO_NAME_INBOUND}") String name) {
		return new To(email, name);
	}

}