package br.com.fiap.unidades.dto.request;

public record UnidadeRequest(
        AbstractRequest macro,
        String sigla,
        String descricao,
        String nome
) {
}
