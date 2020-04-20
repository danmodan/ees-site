package br.com.ees.site.controller.v1;

import java.util.Base64;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ees.site.controller.v1.request.form.ContactForm;
import br.com.ees.site.model.Anexo;
import br.com.ees.site.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping(value = "/contact")
    public String postMethodName(@Valid ContactForm contactForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        log.info(contactForm.toString());

        redirectAttributes.addFlashAttribute("message", "Falhou! Por favor, entre em contato por nossos telefones.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getAllErrors().toString());
            return "redirect:/#message-link";
        } else {
            Anexo anexo = new Anexo();
            MultipartFile contaLuz = contactForm.getContaLuz();
            if (Objects.isNull(contaLuz)) {
                log.info("SEM ANEXO");
            } else {
                try {
                    String originalFilename = contaLuz.getOriginalFilename();
                    String contentType = contaLuz.getContentType();
                    String encodeToString = Base64.getEncoder().encodeToString(contaLuz.getBytes());
                    anexo = new Anexo(originalFilename, contentType, encodeToString, false);
                } catch (Exception e) {
                    log.error(contactForm.toString(), e);
                }
            }
            contactService.sendEmail(contactForm, anexo);
            redirectAttributes.addFlashAttribute("message", "Obrigada! Entraremos em contato.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            return "redirect:/#message-link";
        }
    }

}