package br.com.ees.function;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpRequest.HttpPart;
import com.google.cloud.functions.HttpResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.tuple.Triple;

import br.com.ees.client.sendgrid.SendGridClient;
import br.com.ees.client.sendgrid.model.Attachment;
import br.com.ees.client.sendgrid.model.Personalization;
import br.com.ees.client.sendgrid.request.SendInboundMarketingRequest;
import br.com.ees.client.sendgrid.request.SendInboundMarketingRequestTemplateParams;
import br.com.ees.dto.ContactForm;
import br.com.ees.dto.ContactForm.Anexo;

public class ContactFormFunction implements HttpFunction {

	@Override
	public void service(HttpRequest request, HttpResponse response) throws Exception {

		try {

			final var contactForm = getContactForm(request);

			sendEmail(contactForm);

		} catch (Exception e) {

			e.printStackTrace();

			response.setStatusCode(500);
		}

		enableCors(response);
	}

	private ContactForm getContactForm(final HttpRequest request) {

		final var parts = request.getParts();

		return ContactForm.builder()
			.nome(getStringValue(parts.get("nome")))
			.email(getStringValue(parts.get("email")))
			.mensagem(getStringValue(parts.get("mensagem")))
			.telefone(getStringValue(parts.get("telefone")))
			.whatsapp(BooleanUtils.toBoolean(getStringValue(parts.get("whatsapp"))))
			.cep(getStringValue(parts.get("cep")))
			.logradouro(getStringValue(parts.get("logradouro")))
			.numero(getStringValue(parts.get("numero")))
			.bairro(getStringValue(parts.get("bairro")))
			.cidade(getStringValue(parts.get("cidade")))
			.uf(getStringValue(parts.get("uf")))
			.complemento(getStringValue(parts.get("complemento")))
			.contaLuz(new Anexo(getMultipartValue(parts.get("contaLuz"))))
			.build();
	}

	private String getStringValue(HttpPart part) {

		try {

			return IOUtils.toString(part.getInputStream(), StandardCharsets.UTF_8.name());
		} catch (Exception e) {
			return null;
		}
	}

	private Triple<String, String, String> getMultipartValue(HttpPart part) {

		try {

			final var length = part.getContentLength();

			if(length > 11_000_000) {  // length > 10 MB
				throw new IllegalArgumentException("10 MB é o máximo.");
			}

			return Triple.of(
				part.getFileName().orElseThrow(),
				part.getContentType().orElseThrow(),
				Base64.getEncoder().encodeToString(part.getInputStream().readAllBytes()));
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			return null;
		}
	}

	private void enableCors(HttpResponse response) {

		response.appendHeader("Access-Control-Allow-Origin", "*");
		response.appendHeader("Access-Control-Allow-Methods", "POST");

	}

    public void sendEmail(ContactForm contactForm) {

		final var personalizations = createPersonalization(contactForm);
		final var request = SendInboundMarketingRequest.builder()
			.from(SendGridClient.EES_FROM)
			.personalizations(personalizations)
			.templateId(SendGridClient.SENDGRID_CONTATO_TEMPLATE_ID)
			.attachments(createAttachments(contactForm.getContaLuz()))
			.build();

		SendGridClient.sendEmail(request);
    }

    private Attachment[] createAttachments(Anexo anexo) {

        if (anexo.isEmpty()) {
            return null;
        }
        return new Attachment[] { new Attachment(anexo.getContent(), anexo.getContentType(), anexo.getFileName()) };
    }

    @SuppressWarnings(value = { "unchecked" })
    private Personalization<SendInboundMarketingRequestTemplateParams>[] createPersonalization(ContactForm contactForm) {

		final var templateParams = SendInboundMarketingRequestTemplateParams
			.builder()
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

        final var personalization = new Personalization<SendInboundMarketingRequestTemplateParams>();
        personalization.setDynamicTemplateData(templateParams);
        personalization.setTo(SendGridClient.EES_TOS);
        personalization.setSubject("Novo Contato - " + contactForm.getNome());
        return new Personalization[] { personalization };
    }

}