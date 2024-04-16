package br.com.fiap.unidades.dto.response;

public record UnidadeResponse(
        Long id,
        String nome,
        String sigla,
        String descricao,
        UnidadeResponse macro

) {
}
