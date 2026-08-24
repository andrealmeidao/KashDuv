package com.Kash.KashDuv.exception;

import java.time.OffsetDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.Kash.KashDuv.dto.ErroRespostaDTO;

import jakarta.validation.ConstraintViolationException;

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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroRespostaDTO> tratarConstraint(ConstraintViolationException exception) {
        return ResponseEntity.badRequest().body(new ErroRespostaDTO("Parâmetro inválido", null, OffsetDateTime.now()));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErroRespostaDTO> tratarValidacaoDeMetodo(HandlerMethodValidationException exception) {
        return ResponseEntity.badRequest().body(new ErroRespostaDTO("Parâmetro inválido", null, OffsetDateTime.now()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroRespostaDTO> tratarIntegridade(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroRespostaDTO("Dados duplicados ou inválidos", null, OffsetDateTime.now()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroRespostaDTO> tratarJson(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(new ErroRespostaDTO("JSON inválido ou data em formato incorreto", null, OffsetDateTime.now()));
    }
}
