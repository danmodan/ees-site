package br.com.ees.site.client.config;

import static feign.FeignException.errorStatus;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import feign.FeignException;
import feign.Request;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    private static final String LOG_MSG_RESPONSE_DATA = "\nMETHOD KEY   : {}" +
                                                        "\nSTATUS CODE  : {}" +
                                                        "\nHEADERS      : {}" +
                                                        "\nRESPONSE BODY: {}";

    private static final String LOG_MSG_REQUEST_DATA = "\nMETHOD KEY  : {}" +
                                                       "\nURL         : {}" +
                                                       "\nMETHOD      : {}" +
                                                       "\nHEADERS     : {}" +
                                                       "\nREQUEST BODY: {}";

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException e = errorStatus(methodKey, response);
        log.error("Something went wrong with the call to the service.", e);
        logCall(methodKey, response);
        return e;
    }

    private void logCall(String methodKey, Response response) {
        String requestBody;
        String responseBody;
        Request request = response.request();
        try {
            requestBody = new String(request.body(), "UTF-8");
            responseBody = IOUtils.toString(response.body().asInputStream(), "UTF-8");
        } catch (IOException e) {
            log.error("Error in parse request or response body from service.", e);
            requestBody = "";
            responseBody = "";
        }
        logRequest(methodKey, request, requestBody);
        logResponse(methodKey, response, responseBody);
    }

    private void logResponse(String methodKey, Response response, String responseBody) {
        log.error(LOG_MSG_RESPONSE_DATA,
                "RESPONSE FROM " + methodKey,
                response.status(),
                response.headers(),
                responseBody);
    }

    private void logRequest(String methodKey, Request request, String requestBody) {
        log.error(LOG_MSG_REQUEST_DATA,
                "REQUEST FROM " + methodKey,
                request.url(),
                request.httpMethod(),
                request.headers(),
                requestBody);
    }

}
