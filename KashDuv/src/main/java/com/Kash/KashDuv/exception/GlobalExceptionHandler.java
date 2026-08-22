package com.Kash.KashDuv.exception;

import com.Kash.KashDuv.dto.ErroRespostaDTO;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroRespostaDTO> tratarValidacao(MethodArgumentNotValidException exception) {
        var erro = exception.getBindingResult().getFieldError();
        String mensagem = erro == null ? "Dados inválidos" : erro.getDefaultMessage();
        String campo = erro == null ? null : erro.getField();
        return ResponseEntity.badRequest().body(new ErroRespostaDTO(mensagem, campo, OffsetDateTime.now()));
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroRespostaDTO> tratarNaoEncontrado(RecursoNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroRespostaDTO(exception.getMessage(), null, OffsetDateTime.now()));
    }
}
