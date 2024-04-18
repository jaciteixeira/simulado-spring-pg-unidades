package br.com.fiap.unidades.dto.request;

import br.com.fiap.unidades.entity.Tipo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PessoaRequest(
        @Size(min = 3, max = 100)
        @NotNull(message = "Nome é obrigatório")
        String nome,
        @Size(min = 3, max = 100)
        @NotNull(message = "Sobrenome é obrigatório")
        String sobrenome,
        @Email(message = "Email inválido")
        @NotNull(message = "Email é obrigatório")
        String email,
        @NotNull(message = "Tipo é obrigatório")
        Tipo tipo,
        @NotNull(message = "Data de nascimento é obrigatória")
        LocalDate nascimento
) {

}
