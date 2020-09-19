package br.com.ees.site.controller.v1;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ees.site.client.sendgrid.SendgridClient;
import br.com.ees.site.client.sendgrid.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HealthController implements InitializingBean {

    private final SendgridClient sendgridClient;

    @GetMapping(value = { "/health" })
    @ResponseStatus(code = HttpStatus.OK)
    public final void health() {
        try {
            User user = sendgridClient.getUserEmail();
            log.info(user.toString());
        } catch (Exception e) {
            log.error("health check error", e);
            throw e;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        health();
    }

}