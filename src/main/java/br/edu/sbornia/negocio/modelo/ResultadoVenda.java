package br.edu.sbornia.negocio.modelo;

import java.math.BigDecimal;

public record ResultadoVenda(BigDecimal subtotal, BigDecimal imposto, BigDecimal valorFinal) {
}
