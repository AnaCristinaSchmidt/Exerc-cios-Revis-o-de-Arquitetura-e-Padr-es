package br.edu.sbornia;

import br.edu.sbornia.negocio.servico.ServicoVenda;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SistemaVendasApplicationTest {
    @Autowired
    private ServicoVenda servicoVenda;

    @Test
    void carregaContextoEConfiguraCamadaDeNegocio() {
        assertNotNull(servicoVenda);
    }
}
