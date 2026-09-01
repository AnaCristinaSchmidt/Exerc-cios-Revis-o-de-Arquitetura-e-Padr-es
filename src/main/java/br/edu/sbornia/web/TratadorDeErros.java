package br.edu.sbornia.web;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Converte as exceções lançadas pelos controllers/serviços em respostas HTTP padronizadas. */
@RestControllerAdvice
public class TratadorDeErros {

    // Disparado automaticamente pelo Spring Validation quando @Valid encontra campos inválidos.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(erro -> campos.put(erro.getField(), erro.getDefaultMessage()));
        return ResponseEntity.badRequest().body(corpoDeErro(HttpStatus.BAD_REQUEST, "Dados inválidos", campos));
    }

    // Regras de negócio quebradas (estoque insuficiente, quantidade inválida, etc.)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> tratarRegraDeNegocio(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(corpoDeErro(HttpStatus.BAD_REQUEST, ex.getMessage(), null));
    }

    // Produto/usuário não encontrado ao buscar por id/código.
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> tratarNaoEncontrado(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(corpoDeErro(HttpStatus.NOT_FOUND, ex.getMessage(), null));
    }

    private Map<String, Object> corpoDeErro(HttpStatus status, String mensagem, Map<String, String> campos) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("timestamp", Instant.now().toString());
        corpo.put("status", status.value());
        corpo.put("mensagem", mensagem);
        if (campos != null && !campos.isEmpty()) {
            corpo.put("campos", campos);
        }
        return corpo;
    }
}
