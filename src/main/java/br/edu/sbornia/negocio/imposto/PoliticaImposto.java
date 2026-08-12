package br.edu.sbornia.negocio.imposto;

import br.edu.sbornia.negocio.modelo.Produto;
import java.math.BigDecimal;

public interface PoliticaImposto {
    boolean aplicaA(Produto produto);
    BigDecimal aliquota();
}
