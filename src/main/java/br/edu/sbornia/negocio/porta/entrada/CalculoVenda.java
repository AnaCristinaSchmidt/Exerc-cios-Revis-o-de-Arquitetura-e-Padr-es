package br.edu.sbornia.negocio.porta.entrada;

import br.edu.sbornia.negocio.modelo.Produto;
import br.edu.sbornia.negocio.modelo.ResultadoVenda;
import br.edu.sbornia.negocio.modelo.Usuario;

/** Contrato oferecido pela camada de negócios às futuras interfaces de usuário ou REST. */
public interface CalculoVenda {
    ResultadoVenda calcular(Produto produto, Usuario usuario, int quantidade);
}
