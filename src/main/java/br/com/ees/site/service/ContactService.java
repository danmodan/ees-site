package br.com.ees.site.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.ees.site.client.sendgrid.SendgridClient;
import br.com.ees.site.client.sendgrid.model.Attachment;
import br.com.ees.site.client.sendgrid.model.From;
import br.com.ees.site.client.sendgrid.model.Personalization;
import br.com.ees.site.client.sendgrid.model.To;
import br.com.ees.site.client.sendgrid.request.SendInboundMarketingRequest;
import br.com.ees.site.client.sendgrid.request.SendInboundMarketingRequestTemplateParams;
import br.com.ees.site.controller.v1.request.form.ContactForm;
import br.com.ees.site.model.Anexo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {

    private final SendgridClient sendgridClient;

    @Value("${SENDGRID_CONTATO_TEMPLATE_ID}")
    private String templateId;

    private final To[] tos;

    @Value("${EES_FROM_EMAIL_INBOUND}")
    private String fromEmail;

    @Async
    public void sendEmail(ContactForm contactForm, Anexo anexo) {
        try {
            Personalization<SendInboundMarketingRequestTemplateParams>[] personalizations = createPersonalization(contactForm);
            Attachment[] attachments = createAttachments(anexo);
            SendInboundMarketingRequest request = SendInboundMarketingRequest.builder()
                                                                             .from(new From(fromEmail, "Novo Contato"))
                                                                             .personalizations(personalizations)
                                                                             .templateId(templateId)
                                                                             .attachments(attachments)
                                                                             .build();
            sendgridClient.sendEmail(request);
        } catch (Exception e) {
            log.error(contactForm.toString(), e);
        }
    }

    private Attachment[] createAttachments(Anexo anexo) {
        if (anexo.isEmpty()) {
            return null;
        }
        return new Attachment[] { Attachment.builder()
                                            .filename(anexo.getFilename())
                                            .type(anexo.getContentType())
                                            .content(anexo.getContentBase64())
                                            .build() };
    }

    @SuppressWarnings(value = { "unchecked" })
    private Personalization<SendInboundMarketingRequestTemplateParams>[] createPersonalization(ContactForm contactForm) {
        SendInboundMarketingRequestTemplateParams templateParams = SendInboundMarketingRequestTemplateParams.builder()
                                                                                                            .nome(contactForm.getNome())
                                                                                                            .email(contactForm.getEmail())
                                                                                                            .mensagem(contactForm.getMensagem())
                                                                                                            .telefone(contactForm.getTelefone())
                                                                                                            .whatsapp(contactForm.isWhatsapp() ? "sim" : "n\u00E3o")
                                                                                                            .cep(contactForm.getCep())
                                                                                                            .logradouro(contactForm.getLogradouro())
                                                                                                            .numero(contactForm.getNumero())
                                                                                                            .bairro(contactForm.getBairro())
                                                                                                            .cidade(contactForm.getCidade())
                                                                                                            .uf(contactForm.getUf())
                                                                                                            .complemento(contactForm.getComplemento())
                                                                                                            .build();
        Personalization<SendInboundMarketingRequestTemplateParams> personalization = new Personalization<SendInboundMarketingRequestTemplateParams>();
        personalization.setDynamicTemplateData(templateParams);
        personalization.setTo(tos);
        personalization.setSubject("Novo Contato - " + contactForm.getNome());
        return new Personalization[] { personalization };
    }

}