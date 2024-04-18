package br.com.fiap.unidades.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        @NotNull(message = "Dados pessoais são obrigatórios")
        PessoaRequest pessoa,
        @NotNull(message = "Username é obrigatório")
        @Size(min = 3, max = 100)
        String username,
        @NotNull(message = "Senha é obrigatória")
        @Size(min = 6, max = 100)
        String password
) {
}
