package br.com.fiap.unidades.dto.response;

public record UsuarioResponse(
        Long id,
        PessoaResponse pessoa,
        String username
) {
}
