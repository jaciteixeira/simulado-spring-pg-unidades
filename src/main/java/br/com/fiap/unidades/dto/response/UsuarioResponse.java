package br.com.fiap.unidades.dto.response;

import lombok.Builder;

@Builder
public record UsuarioResponse(
        Long id,
        PessoaResponse pessoa,
        String username
) {
}
