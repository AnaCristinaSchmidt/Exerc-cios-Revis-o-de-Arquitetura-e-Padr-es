package br.edu.sbornia.negocio.modelo;

import java.math.BigDecimal;
import java.util.Objects;

public record Produto(String codigo, String descricao, int quantidadeEmEstoque,
                      BigDecimal precoUnitario, CategoriaProduto categoria) {

    public Produto {
        if (codigo == null || codigo.isBlank()) throw new IllegalArgumentException("Código é obrigatório");
        if (descricao == null || descricao.isBlank()) throw new IllegalArgumentException("Descrição é obrigatória");
        if (quantidadeEmEstoque < 0) throw new IllegalArgumentException("Estoque não pode ser negativo");
        Objects.requireNonNull(precoUnitario, "Preço unitário é obrigatório");
        Objects.requireNonNull(categoria, "Categoria é obrigatória");
        if (precoUnitario.signum() < 0) throw new IllegalArgumentException("Preço não pode ser negativo");
    }
}
