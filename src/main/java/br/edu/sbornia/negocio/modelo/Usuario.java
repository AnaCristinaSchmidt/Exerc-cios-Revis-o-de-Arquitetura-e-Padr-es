package br.edu.sbornia.negocio.modelo;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public record Usuario(String id, String nome, LocalDate dataNascimento, int numeroDependentes) {
    public Usuario {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Identificador é obrigatório");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório");
        Objects.requireNonNull(dataNascimento, "Data de nascimento é obrigatória");
        if (numeroDependentes < 0) throw new IllegalArgumentException("Dependentes não podem ser negativos");
    }

    public int idade(Clock clock) {
        LocalDate hoje = LocalDate.now(clock);
        if (dataNascimento.isAfter(hoje)) throw new IllegalStateException("Data de nascimento está no futuro");
        return Period.between(dataNascimento, hoje).getYears();
    }
}
