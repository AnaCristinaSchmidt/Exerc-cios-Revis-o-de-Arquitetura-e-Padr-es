package br.edu.sbornia.negocio.servico;

import br.edu.sbornia.negocio.modelo.CategoriaProduto;
import br.edu.sbornia.negocio.modelo.Produto;
import br.edu.sbornia.negocio.modelo.ResultadoVenda;
import br.edu.sbornia.negocio.modelo.Usuario;
import br.edu.sbornia.negocio.porta.entrada.CalculoVenda;
import br.edu.sbornia.negocio.porta.saida.ConsultaProduto;
import br.edu.sbornia.negocio.porta.saida.ConsultaUsuario;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicoVendaCadastradaTest {
    private final Produto produto = new Produto("P1", "Alimento", 10,
            new BigDecimal("10.00"), CategoriaProduto.ALIMENTICIO);
    private final Usuario usuario = new Usuario("U1", "Maria", LocalDate.of(1990, 1, 1), 0);
    private final ResultadoVenda resultado = new ResultadoVenda(
            new BigDecimal("20.00"), new BigDecimal("1.00"), new BigDecimal("21.00"));

    @Test
    void consultaCadastrosEDelegaCalculo() {
        ConsultaProduto produtos = codigo -> Optional.of(produto);
        ConsultaUsuario usuarios = id -> Optional.of(usuario);
        CalculoVenda calculo = (p, u, quantidade) -> resultado;
        var servico = new ServicoVendaCadastrada(produtos, usuarios, calculo);

        assertEquals(resultado, servico.calcular("P1", "U1", 2));
    }

    @Test
    void informaProdutoNaoEncontrado() {
        ConsultaProduto produtos = codigo -> Optional.empty();
        ConsultaUsuario usuarios = id -> Optional.of(usuario);
        CalculoVenda calculo = (p, u, quantidade) -> resultado;
        var servico = new ServicoVendaCadastrada(produtos, usuarios, calculo);

        var erro = assertThrows(IllegalArgumentException.class,
                () -> servico.calcular("P9", "U1", 2));
        assertEquals("Produto não encontrado: P9", erro.getMessage());
    }

    @Test
    void informaUsuarioNaoEncontrado() {
        ConsultaProduto produtos = codigo -> Optional.of(produto);
        ConsultaUsuario usuarios = id -> Optional.empty();
        CalculoVenda calculo = (p, u, quantidade) -> resultado;
        var servico = new ServicoVendaCadastrada(produtos, usuarios, calculo);

        var erro = assertThrows(IllegalArgumentException.class,
                () -> servico.calcular("P1", "U9", 2));
        assertEquals("Usuário não encontrado: U9", erro.getMessage());
    }
}
