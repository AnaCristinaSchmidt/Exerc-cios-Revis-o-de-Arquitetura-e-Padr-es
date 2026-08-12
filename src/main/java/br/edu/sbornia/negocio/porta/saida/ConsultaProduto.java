package br.edu.sbornia.negocio.porta.saida;

import br.edu.sbornia.negocio.modelo.Produto;
import java.util.Optional;

/** Contrato esperado do sistema de estoque; não possui implementação neste exercício. */
public interface ConsultaProduto {
    Optional<Produto> buscarPorCodigo(String codigo);
}
