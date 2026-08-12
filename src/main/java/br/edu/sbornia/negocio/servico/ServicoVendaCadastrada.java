package br.edu.sbornia.negocio.servico;

import br.edu.sbornia.negocio.modelo.Produto;
import br.edu.sbornia.negocio.modelo.ResultadoVenda;
import br.edu.sbornia.negocio.modelo.Usuario;
import br.edu.sbornia.negocio.porta.entrada.CalculoVenda;
import br.edu.sbornia.negocio.porta.entrada.CalculoVendaCadastrada;
import br.edu.sbornia.negocio.porta.saida.ConsultaProduto;
import br.edu.sbornia.negocio.porta.saida.ConsultaUsuario;
import java.util.Objects;

public final class ServicoVendaCadastrada implements CalculoVendaCadastrada {
    private final ConsultaProduto consultaProduto;
    private final ConsultaUsuario consultaUsuario;
    private final CalculoVenda calculoVenda;

    public ServicoVendaCadastrada(ConsultaProduto consultaProduto,
                                  ConsultaUsuario consultaUsuario,
                                  CalculoVenda calculoVenda) {
        this.consultaProduto = Objects.requireNonNull(consultaProduto);
        this.consultaUsuario = Objects.requireNonNull(consultaUsuario);
        this.calculoVenda = Objects.requireNonNull(calculoVenda);
    }

    @Override
    public ResultadoVenda calcular(String codigoProduto, String idUsuario, int quantidade) {
        if (codigoProduto == null || codigoProduto.isBlank()) {
            throw new IllegalArgumentException("Código do produto é obrigatório");
        }
        if (idUsuario == null || idUsuario.isBlank()) {
            throw new IllegalArgumentException("Identificador do usuário é obrigatório");
        }

        Produto produto = consultaProduto.buscarPorCodigo(codigoProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + codigoProduto));
        Usuario usuario = consultaUsuario.buscarPorId(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + idUsuario));
        return calculoVenda.calcular(produto, usuario, quantidade);
    }
}
