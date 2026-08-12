package br.edu.sbornia.negocio.servico;

import br.edu.sbornia.negocio.imposto.CalculadoraImposto;
import br.edu.sbornia.negocio.imposto.ImpostoPorCategoria;
import br.edu.sbornia.negocio.imposto.PoliticaImposto;
import br.edu.sbornia.negocio.modelo.CategoriaProduto;
import br.edu.sbornia.negocio.modelo.Produto;
import br.edu.sbornia.negocio.modelo.ResultadoVenda;
import br.edu.sbornia.negocio.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicoVendaTest {
    private ServicoVenda servico;
    private final Clock clock = Clock.fixed(Instant.parse("2026-08-11T12:00:00Z"), ZoneOffset.UTC);

    @BeforeEach
    void preparar() {
        var politicas = Arrays.stream(CategoriaProduto.values())
                .<PoliticaImposto>map(c -> new ImpostoPorCategoria(c, switch (c) {
                    case ALIMENTICIO -> new BigDecimal("0.05");
                    case AUTOMOTIVO -> new BigDecimal("0.30");
                    case BEBIDA_ALCOOLICA -> new BigDecimal("1.00");
                    case OUTROS -> new BigDecimal("0.17");
                })).toList();
        servico = new ServicoVenda(new CalculadoraImposto(politicas, clock));
    }

    @Test
    void calculaTodasAsAliquotas() {
        Usuario usuario = usuarioComIdade(30, 0);
        assertVenda(CategoriaProduto.ALIMENTICIO, usuario, "10.00", "210.00");
        assertVenda(CategoriaProduto.AUTOMOTIVO, usuario, "60.00", "260.00");
        assertVenda(CategoriaProduto.BEBIDA_ALCOOLICA, usuario, "200.00", "400.00");
        assertVenda(CategoriaProduto.OUTROS, usuario, "34.00", "234.00");
    }

    @Test
    void maiorDeSessentaNaoPagaImposto() {
        assertVenda(CategoriaProduto.AUTOMOTIVO, usuarioComIdade(61, 0), "0.00", "200.00");
    }

    @Test
    void exatamenteSessentaAindaPagaImposto() {
        assertVenda(CategoriaProduto.AUTOMOTIVO, usuarioComIdade(60, 0), "60.00", "260.00");
    }

    @Test
    void maisDeTresDependentesRecebeMetadeDoImposto() {
        assertVenda(CategoriaProduto.OUTROS, usuarioComIdade(30, 4), "17.00", "217.00");
    }

    @Test
    void bebidaIgnoraBeneficiosDoUsuario() {
        assertVenda(CategoriaProduto.BEBIDA_ALCOOLICA, usuarioComIdade(70, 5), "200.00", "400.00");
    }

    @Test
    void rejeitaQuantidadeAcimaDoEstoque() {
        Produto produto = produto(CategoriaProduto.OUTROS);
        assertThrows(IllegalArgumentException.class,
                () -> servico.calcular(produto, usuarioComIdade(30, 0), 3));
    }

    private void assertVenda(CategoriaProduto categoria, Usuario usuario, String imposto, String total) {
        ResultadoVenda resultado = servico.calcular(produto(categoria), usuario, 2);
        assertEquals(new BigDecimal("200.00"), resultado.subtotal());
        assertEquals(new BigDecimal(imposto), resultado.imposto());
        assertEquals(new BigDecimal(total), resultado.valorFinal());
    }

    private Produto produto(CategoriaProduto categoria) {
        return new Produto("001", "Produto", 2, new BigDecimal("100.00"), categoria);
    }

    private Usuario usuarioComIdade(int idade, int dependentes) {
        return new Usuario("u1", "Usuário", LocalDate.now(clock).minusYears(idade), dependentes);
    }
}
