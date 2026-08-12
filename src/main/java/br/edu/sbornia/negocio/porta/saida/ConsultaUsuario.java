package br.edu.sbornia.negocio.porta.saida;

import br.edu.sbornia.negocio.modelo.Usuario;
import java.util.Optional;

/** Contrato esperado do sistema de usuários; não possui implementação neste exercício. */
public interface ConsultaUsuario {
    Optional<Usuario> buscarPorId(String id);
}
