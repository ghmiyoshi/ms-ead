package com.ead.course.infra;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handleResponseStatusException(final ResponseStatusException exception) {
        return buildProblemDetail(exception);
    }

    @ExceptionHandler(ValidationException.class)
    public ProblemDetail handleValidationException(final ValidationException exception) {
        List<Map<String, String>> apiErrorFields = mapErrorsToApiFields(exception.getErrors());
        return buildProblemDetailWithFieldErrors(exception, apiErrorFields);
    }

    private List<Map<String, String>> mapErrorsToApiFields(final List<ObjectError> errors) {
        return errors.stream()
                .map(error -> {
                    Map<String, String> errorField = new HashMap<>();
                    errorField.put("field", ((FieldError) error).getField());
                    errorField.put("message", error.getDefaultMessage());
                    return errorField;
                }).toList();
    }

    private ProblemDetail buildProblemDetail(final ResponseStatusException exception) {
        var problemDetail = ProblemDetail.forStatusAndDetail(exception.getStatusCode(),
                                                             exception.getReason());
        problemDetail.setType(URI.create(createUrl(exception.getStatusCode())));
        return problemDetail;
    }

    private ProblemDetail buildProblemDetailWithFieldErrors(final Exception exception,
                                                            final List<Map<String, String>> errorFields) {
        var problemDetail = buildProblemDetail(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                                           exception.getMessage()));
        if (nonNull(errorFields)) {
            problemDetail.setProperty("errors", errorFields);
        }
        return problemDetail;
    }

    private String createUrl(final HttpStatusCode httpStatusCode) {
        return switch (httpStatusCode.value()) {
            case 400 -> "https://api.course.com/errors/bad-request";
            case 404 -> "https://api.course.com/errors/not-found";
            case 500 -> "https://api.course.com/errors/internal-server-error";
            default -> "https://api.course.com/errors";
        };
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        List<Map<String, String>> apiErrorFields = mapErrorsToApiFields(exception.getAllErrors());
        return buildProblemDetailWithFieldErrors(exception, apiErrorFields);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ProblemDetail handleHttpClientErrorException(final HttpClientErrorException exception) {
        return exception.getResponseBodyAs(ProblemDetail.class);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) {
        var messageError = "Invalid JSON body";
        if (exception.getCause() instanceof UnrecognizedPropertyException unrecognizedPropertyException) {
            final var property = unrecognizedPropertyException.getPropertyName();
            messageError = String.format("%s, property: %s", messageError, property);
        }

        return buildProblemDetail(new ResponseStatusException(HttpStatus.BAD_REQUEST, messageError));
    }

}
