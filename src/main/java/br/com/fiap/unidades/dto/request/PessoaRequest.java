package br.com.fiap.unidades.dto.request;

import br.com.fiap.unidades.entity.Tipo;

import java.time.LocalDate;

public record PessoaRequest(
        String nome,
        String sobrenome,
        String email,
        Tipo tipo,
        LocalDate nascimento
) {

}
