package br.com.ees.site.controller.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.ees.site.controller.v1.request.form.ContactForm;

@Controller
public class WelcomeController {

    @GetMapping(value = { "/", "/index", "/welcome", "/home" })
	public String index(ContactForm contactForm) {
		return "welcome";
	}

}