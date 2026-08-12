package br.edu.sbornia.negocio.imposto;

import br.edu.sbornia.negocio.modelo.CategoriaProduto;
import br.edu.sbornia.negocio.modelo.Produto;
import br.edu.sbornia.negocio.modelo.Usuario;
import java.math.BigDecimal;
import java.time.Clock;
import java.util.List;

public final class CalculadoraImposto {
    private static final BigDecimal METADE = new BigDecimal("0.50");
    private final List<PoliticaImposto> politicas;
    private final Clock clock;

    public CalculadoraImposto(List<PoliticaImposto> politicas, Clock clock) {
        this.politicas = List.copyOf(politicas);
        this.clock = clock;
    }

    public BigDecimal calcular(Produto produto, Usuario usuario, BigDecimal subtotal) {
        PoliticaImposto politica = politicas.stream()
                .filter(item -> item.aplicaA(produto))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Não há política de imposto para a categoria"));

        BigDecimal imposto = subtotal.multiply(politica.aliquota());
        if (produto.categoria() == CategoriaProduto.BEBIDA_ALCOOLICA) return imposto;
        if (usuario.idade(clock) > 60) return BigDecimal.ZERO;
        if (usuario.numeroDependentes() > 3) return imposto.multiply(METADE);
        return imposto;
    }
}
