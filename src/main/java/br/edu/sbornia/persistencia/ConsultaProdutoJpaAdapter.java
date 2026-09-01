package br.edu.sbornia.persistencia;

import br.edu.sbornia.negocio.modelo.Produto;
import br.edu.sbornia.negocio.porta.saida.ConsultaProduto;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Implementação real da porta de saída ConsultaProduto, usando o banco relacional via JPA. */
@Component
public class ConsultaProdutoJpaAdapter implements ConsultaProduto {

    private final ProdutoRepository repository;

    public ConsultaProdutoJpaAdapter(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Produto> buscarPorCodigo(String codigo) {
        return repository.findById(codigo);
    }
}
