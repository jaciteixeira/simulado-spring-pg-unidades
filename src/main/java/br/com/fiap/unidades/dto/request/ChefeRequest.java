package br.com.fiap.unidades.dto.request;

import java.time.LocalDate;

public record ChefeRequest(
        LocalDate fim,
        LocalDate inicio,
        AbstractRequest usuario,
        Boolean substituto,
        AbstractRequest unidade
) {
}
