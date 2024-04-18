package br.com.fiap.unidades.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ChefeRequest(
        @NotNull(message = "Data de início é obrigatória")
        LocalDateTime inicio,
        @NotNull(message = "Data de fim é obrigatória")
        LocalDateTime fim,
        @NotNull(message = "Usuário é obrigatório")
        AbstractRequest usuario,
        @NotNull(message = "Substituto é obrigatório")
        Boolean substituto,
        @NotNull(message = "Unidade é obrigatória")
        @Valid
        AbstractRequest unidade
) {
}
