package br.edu.sbornia.negocio.porta.entrada;

import br.edu.sbornia.negocio.modelo.ResultadoVenda;

/** Caso de uso que calcula uma venda usando os identificadores recebidos do terminal. */
public interface CalculoVendaCadastrada {
    ResultadoVenda calcular(String codigoProduto, String idUsuario, int quantidade);
}
