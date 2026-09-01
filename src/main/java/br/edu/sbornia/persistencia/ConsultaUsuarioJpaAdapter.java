package br.edu.sbornia.persistencia;

import br.edu.sbornia.negocio.modelo.Usuario;
import br.edu.sbornia.negocio.porta.saida.ConsultaUsuario;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Implementação real da porta de saída ConsultaUsuario, usando o banco relacional via JPA. */
@Component
public class ConsultaUsuarioJpaAdapter implements ConsultaUsuario {

    private final UsuarioRepository repository;

    public ConsultaUsuarioJpaAdapter(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        return repository.findById(id);
    }
}
