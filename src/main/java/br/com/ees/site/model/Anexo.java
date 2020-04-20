package br.com.ees.site.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Anexo {
    private String filename;
    private String contentType;
    private String contentBase64;
    private boolean empty = true;
}