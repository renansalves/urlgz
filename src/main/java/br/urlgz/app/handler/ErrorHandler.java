
package br.urlgz.app.handler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    // Domain-specific mapping: turn "not found" message into 404
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
        log.error("RuntimeException: {}", ex.getMessage(), ex);

        String msg = ex.getMessage();
        if ("Não foi possivel consultar a url curta.".equals(msg)) {
            ApiError error = new ApiError(
                "Url não encontrada.",
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                msg
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        ApiError error = new ApiError(
            "Erro ao executar processo.",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            LocalDateTime.now(),
            msg
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // Repository delete: deleting a non-existent id
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiError> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        log.warn("EmptyResultDataAccessException: {}", ex.getMessage());
        ApiError error = new ApiError(
            "Url não encontrada.",
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now(),
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Validation errors: show field -> message map, respond 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "invalid",
                (a, b) -> a // keep first message if duplicates
            ));

        ApiError error = new ApiError(
            "Erro de validação.",
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now(),
            fieldErrors.toString()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Fallback: any other exception → 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        log.error("Unexpected exception: {}", ex.getMessage(), ex);
        ApiError error = new ApiError(
            "Ocorreu um erro inesperado. Contate o suporte.",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            LocalDateTime.now(),
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

