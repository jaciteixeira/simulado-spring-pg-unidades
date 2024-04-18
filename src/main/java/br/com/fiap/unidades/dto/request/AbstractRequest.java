package br.com.fiap.unidades.dto.request;

import jakarta.validation.constraints.NotNull;

public record AbstractRequest(
        @NotNull(message = "Id é obrigatório!")
        Long id
) {
}
