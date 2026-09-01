package br.edu.sbornia.web;

import br.edu.sbornia.negocio.modelo.ResultadoVenda;
import br.edu.sbornia.negocio.porta.entrada.CalculoVendaCadastrada;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendaController {

    private final CalculoVendaCadastrada calculoVendaCadastrada;

    public VendaController(CalculoVendaCadastrada calculoVendaCadastrada) {
        this.calculoVendaCadastrada = calculoVendaCadastrada;
    }

    /**
     * Realiza uma venda a partir do código do produto e do id do usuário já cadastrados.
     * @Valid dispara o Spring Validation sobre o VendaRequest antes de o método ser executado.
     */
    @PostMapping("/api/vendas")
    public ResultadoVenda vender(@Valid @RequestBody VendaRequest request) {
        return calculoVendaCadastrada.calcular(request.codigoProduto(), request.idUsuario(), request.quantidade());
    }
}
