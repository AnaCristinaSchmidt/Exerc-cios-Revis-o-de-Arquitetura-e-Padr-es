package br.edu.sbornia.negocio.imposto;

import br.edu.sbornia.negocio.modelo.CategoriaProduto;
import br.edu.sbornia.negocio.modelo.Produto;
import java.math.BigDecimal;
import java.util.Objects;

public final class ImpostoPorCategoria implements PoliticaImposto {
    private final CategoriaProduto categoria;
    private final BigDecimal aliquota;

    public ImpostoPorCategoria(CategoriaProduto categoria, BigDecimal aliquota) {
        this.categoria = Objects.requireNonNull(categoria);
        this.aliquota = Objects.requireNonNull(aliquota);
        if (aliquota.signum() < 0) throw new IllegalArgumentException("Alíquota não pode ser negativa");
    }

    @Override public boolean aplicaA(Produto produto) { return produto.categoria() == categoria; }
    @Override public BigDecimal aliquota() { return aliquota; }
}
