package br.com.fiap.unidades.dto.request;

public record UsuarioRequest(
        PessoaRequest pessoa,
        String username,
        String password
) {
}
