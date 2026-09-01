package br.edu.sbornia.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/** Corpo esperado por POST /api/vendas. Um record pode ter anotações de Bean Validation normalmente. */
public record VendaRequest(
        @NotBlank(message = "Código do produto é obrigatório") String codigoProduto,
        @NotBlank(message = "Identificador do usuário é obrigatório") String idUsuario,
        @Min(value = 1, message = "Quantidade deve ser maior que zero") int quantidade) {
}
