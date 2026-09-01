package br.edu.sbornia.persistencia;

import br.edu.sbornia.negocio.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositório Spring Data JPA: o Spring gera a implementação automaticamente. */
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
