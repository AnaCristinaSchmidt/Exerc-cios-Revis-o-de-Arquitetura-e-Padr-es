package br.edu.sbornia.negocio.servico;

import br.edu.sbornia.negocio.imposto.CalculadoraImposto;
import br.edu.sbornia.negocio.modelo.Produto;
import br.edu.sbornia.negocio.modelo.ResultadoVenda;
import br.edu.sbornia.negocio.modelo.Usuario;
import br.edu.sbornia.negocio.porta.entrada.CalculoVenda;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ServicoVenda implements CalculoVenda {
    private final CalculadoraImposto calculadoraImposto;

    public ServicoVenda(CalculadoraImposto calculadoraImposto) {
        this.calculadoraImposto = calculadoraImposto;
    }

    @Override
    public ResultadoVenda calcular(Produto produto, Usuario usuario, int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        if (quantidade > produto.quantidadeEmEstoque()) throw new IllegalArgumentException("Estoque insuficiente");

        BigDecimal subtotal = produto.precoUnitario().multiply(BigDecimal.valueOf(quantidade));
        BigDecimal imposto = calculadoraImposto.calcular(produto, usuario, subtotal);
        return new ResultadoVenda(moeda(subtotal), moeda(imposto), moeda(subtotal.add(imposto)));
    }

    private BigDecimal moeda(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }
}
