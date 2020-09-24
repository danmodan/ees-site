package br.com.ees.client.sendgrid;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

import br.com.ees.client.HttpClient;
import br.com.ees.client.sendgrid.model.From;
import br.com.ees.client.sendgrid.model.To;
import br.com.ees.client.sendgrid.request.SendInboundMarketingRequest;
import br.com.ees.utils.ObjectMapperUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SendGridClient {

    public static final String SENDGRID_CONTATO_TEMPLATE_ID = System.getenv("SENDGRID_CONTATO_TEMPLATE_ID");
    public static final String BASE_URL = System.getenv("SENDGRID_BASE_URL");
    public static final String API_KEY = "Bearer " + System.getenv("SENDGRID_API_KEY");
    public static final String EES_TO_EMAIL_INBOUND = System.getenv("EES_TO_EMAIL_INBOUND");
    public static final String EES_TO_NAME_INBOUND = System.getenv("EES_TO_NAME_INBOUND");
    public static final String EES_FROM_EMAIL_INBOUND = System.getenv("EES_FROM_EMAIL_INBOUND");

    public static final From EES_FROM = new From(EES_FROM_EMAIL_INBOUND, "Novo Contato");
    public static final To[] EES_TOS = new To[] { new To(EES_TO_EMAIL_INBOUND, EES_TO_NAME_INBOUND) };

    public static final Builder SEND_EMAIL_REQUEST = HttpRequest
        .newBuilder(URI.create(BASE_URL + "/v3/mail/send"))
        .headers("Accept", "application/json",
                 "Content-Type", "application/json",
                 "Authorization", API_KEY);

    public static void sendEmail(SendInboundMarketingRequest request) {

        final var requestBody = ObjectMapperUtils.name(request);

        final var httpReq = SEND_EMAIL_REQUEST.POST(BodyPublishers.ofString(requestBody)).build();

        HttpClient.CLIENT
            .sendAsync(httpReq, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(httpResp -> System.out.println(String.format("req = %s resp = %s", requestBody, httpResp)))
            .join();

    }

}
