package ru.practicum.shareit.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.exception.DuplicateException;
import ru.practicum.shareit.exception.NotAccessException;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorMessage> duplicateException(DuplicateException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage(), exception.getStackTrace()[0].getFileName()));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorMessage> duplicateEmailException(DuplicateEmailException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(exception.getMessage(), exception.getStackTrace()[0].getFileName()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(NotFoundException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage(), exception.getStackTrace()[0].getFileName()));
    }

    @ExceptionHandler(NotAccessException.class)
    public ResponseEntity<ErrorMessage> notAccessException(NotAccessException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorMessage(exception.getMessage(), exception.getStackTrace()[0].getFileName()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> notValidException(MethodArgumentNotValidException exception) {
        log.warn(exception.getMessage());

        final List<ErrorMessage> fieldErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(s -> new ErrorMessage(s.getDefaultMessage(), s.getObjectName()))
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ValidationErrorResponse(fieldErrors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> internalServerException(Exception exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(exception.getMessage(), exception.getStackTrace()[0].getFileName()));
    }
}