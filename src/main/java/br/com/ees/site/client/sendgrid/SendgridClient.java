package br.com.ees.site.client.sendgrid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.ees.site.client.config.SendgridFeignClientConfig;
import br.com.ees.site.client.sendgrid.model.User;
import br.com.ees.site.client.sendgrid.request.SendInboundMarketingRequest;

@FeignClient(name = "sendgridClient",  
             configuration = { SendgridFeignClientConfig.class },
             url = "${SENDGRID_BASE_URL}")
public interface SendgridClient {

    @PostMapping(path = { "/v3/mail/send" })
    void sendEmail(@RequestBody SendInboundMarketingRequest request);

    @GetMapping(path = { "/v3/user/email" })
    User getUserEmail();

}