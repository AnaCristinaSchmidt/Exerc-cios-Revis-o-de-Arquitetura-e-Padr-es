package br.edu.sbornia.configuracao;

import br.edu.sbornia.negocio.imposto.CalculadoraImposto;
import br.edu.sbornia.negocio.imposto.ImpostoPorCategoria;
import br.edu.sbornia.negocio.imposto.PoliticaImposto;
import br.edu.sbornia.negocio.modelo.CategoriaProduto;
import br.edu.sbornia.negocio.porta.entrada.CalculoVenda;
import br.edu.sbornia.negocio.porta.saida.ConsultaProduto;
import br.edu.sbornia.negocio.porta.saida.ConsultaUsuario;
import br.edu.sbornia.negocio.servico.ServicoVenda;
import br.edu.sbornia.negocio.servico.ServicoVendaCadastrada;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.time.Clock;
import java.util.List;

@Configuration
public class ConfiguracaoNegocio {
    @Bean
    Clock clock() { return Clock.systemDefaultZone(); }

    @Bean
    PoliticaImposto impostoAlimenticio() {
        return new ImpostoPorCategoria(CategoriaProduto.ALIMENTICIO, new BigDecimal("0.05"));
    }

    @Bean
    PoliticaImposto impostoAutomotivo() {
        return new ImpostoPorCategoria(CategoriaProduto.AUTOMOTIVO, new BigDecimal("0.30"));
    }

    @Bean
    PoliticaImposto impostoBebidaAlcoolica() {
        return new ImpostoPorCategoria(CategoriaProduto.BEBIDA_ALCOOLICA, new BigDecimal("1.00"));
    }

    @Bean
    PoliticaImposto impostoOutros() {
        return new ImpostoPorCategoria(CategoriaProduto.OUTROS, new BigDecimal("0.17"));
    }

    @Bean
    CalculadoraImposto calculadoraImposto(List<PoliticaImposto> politicas, Clock clock) {
        return new CalculadoraImposto(politicas, clock);
    }

    @Bean
    ServicoVenda servicoVenda(CalculadoraImposto calculadora) {
        return new ServicoVenda(calculadora);
    }

    // ConsultaProduto e ConsultaUsuario são implementadas pelos adapters JPA
    // (pacote persistencia), que o Spring encontra automaticamente via @Component.
    @Bean
    ServicoVendaCadastrada servicoVendaCadastrada(ConsultaProduto consultaProduto,
                                                   ConsultaUsuario consultaUsuario,
                                                   CalculoVenda calculoVenda) {
        return new ServicoVendaCadastrada(consultaProduto, consultaUsuario, calculoVenda);
    }
}
