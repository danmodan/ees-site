package br.com.ees.site.controller.v1.request.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactForm {

    private static final String CAMPO_NAO_PODE_EXCEDER_2_CARACTERES = "Campo n\u00E3o pode exceder 2 caracteres.";
    private static final String CAMPO_NAO_PODE_EXCEDER_12_CARACTERES = "Campo n\u00E3o pode exceder 12 caracteres.";
    private static final String CAMPO_NAO_PODE_EXCEDER_20_CARACTERES = "Campo n\u00E3o pode exceder 20 caracteres.";
    private static final String CAMPO_NAO_PODE_EXCEDER_100_CARACTERES = "Campo n\u00E3o pode exceder 100 caracteres.";
    private static final String CAMPO_NAO_PODE_EXCEDER_200_CARACTERES = "Campo n\u00E3o pode exceder 200 caracteres.";
    private static final String CAMPO_NAO_PODE_EXCEDER_400_CARACTERES = "Campo n\u00E3o pode exceder 400 caracteres.";
    private static final String CAMPO_NAO_PODE_EXCEDER_1000_CARACTERES = "Campo n\u00E3o pode exceder 1000 caracteres.";
    private static final String CAMPO_OBRIGATORIO = "Campo obrigat\u00F3rio.";

    @NotBlank(message = CAMPO_OBRIGATORIO)
    @Size(max = 100, message = CAMPO_NAO_PODE_EXCEDER_100_CARACTERES)
    private String nome;

    @NotBlank(message = CAMPO_OBRIGATORIO)
    @Size(max = 400, message = CAMPO_NAO_PODE_EXCEDER_400_CARACTERES)
    private String email;

    @NotBlank(message = CAMPO_OBRIGATORIO)
    @Size(max = 1000, message = CAMPO_NAO_PODE_EXCEDER_1000_CARACTERES)
    private String mensagem;

    @Size(max = 20, message = CAMPO_NAO_PODE_EXCEDER_20_CARACTERES)
    private String telefone;
    private boolean whatsapp;

    @Size(max = 12, message = CAMPO_NAO_PODE_EXCEDER_12_CARACTERES)
    private String cep;
    @Size(max = 200, message = CAMPO_NAO_PODE_EXCEDER_200_CARACTERES)
    private String logradouro;
    @Size(max = 100, message = CAMPO_NAO_PODE_EXCEDER_100_CARACTERES)
    private String numero;
    @Size(max = 200, message = CAMPO_NAO_PODE_EXCEDER_200_CARACTERES)
    private String bairro;
    @Size(max = 200, message = CAMPO_NAO_PODE_EXCEDER_200_CARACTERES)
    private String cidade;
    @Size(max = 5, message = CAMPO_NAO_PODE_EXCEDER_2_CARACTERES)
    private String uf;
    @Size(max = 100, message = CAMPO_NAO_PODE_EXCEDER_100_CARACTERES)
    private String complemento;

    private MultipartFile contaLuz;

}