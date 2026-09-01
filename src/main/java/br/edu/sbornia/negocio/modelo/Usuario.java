package br.edu.sbornia.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class Usuario {

    @Id
    @NotBlank(message = "Identificador é obrigatório")
    private String id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve estar no passado")
    private LocalDate dataNascimento;

    @Min(value = 0, message = "Dependentes não podem ser negativos")
    private int numeroDependentes;

    /** Construtor exigido pelo JPA. */
    protected Usuario() {
    }

    public Usuario(String id, String nome, LocalDate dataNascimento, int numeroDependentes) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.numeroDependentes = numeroDependentes;
    }

    public int idade(Clock clock) {
        LocalDate hoje = LocalDate.now(clock);
        if (dataNascimento.isAfter(hoje)) throw new IllegalStateException("Data de nascimento está no futuro");
        return Period.between(dataNascimento, hoje).getYears();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getNumeroDependentes() {
        return numeroDependentes;
    }

    public void setNumeroDependentes(int numeroDependentes) {
        this.numeroDependentes = numeroDependentes;
    }
}
