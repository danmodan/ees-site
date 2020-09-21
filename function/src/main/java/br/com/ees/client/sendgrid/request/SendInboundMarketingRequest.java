package br.com.ees.client.sendgrid.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import br.com.ees.client.sendgrid.model.Attachment;
import br.com.ees.client.sendgrid.model.From;
import br.com.ees.client.sendgrid.model.Personalization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(value = SnakeCaseStrategy.class)
@JsonInclude(content = Include.NON_NULL)
public class SendInboundMarketingRequest {
    private From from;
    private Personalization<SendInboundMarketingRequestTemplateParams>[] personalizations;
    private String templateId;
    private Attachment[] attachments;
}