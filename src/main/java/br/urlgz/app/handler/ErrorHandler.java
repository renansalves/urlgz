package br.urlgz.app.handler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApiError> UrlRuntimeExceptionHandler(6){
    ApiError error = new ApiError(
        "Não foi possivel salvar a url.",
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> UrlNotFoundHandler(NotFoundException ex){
      ApiError error = new ApiError(
          ex.getMessage(),
          HttpStatus.NOT_FOUND.value(),
          LocalDateTime.now());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ResponseEntity<ApiError> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex){
    ApiError error = new ApiError(
        "Url não encontrada.",
        HttpStatus.NOT_FOUND.value(),
        LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex){
    String errors = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map( fieldError-> fieldError.getField() + ": "+ fieldError.getDefaultMessage())
      .collect(Collectors.joining(", "));
      
    ApiError error = new ApiError(
        "Erro de validação: " + errors,
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        LocalDateTime.now()
        );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGenericException(Exception ex){
    ApiError error = new ApiError(
        "Ocorreu um erro inesperado. Contate o suporte.",
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        LocalDateTime.now()
        );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
 

