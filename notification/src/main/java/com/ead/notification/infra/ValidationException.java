package com.ead.notification.infra;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Getter
public class ValidationException extends ResponseStatusException {

    private final List<ObjectError> errors;

    public ValidationException(final HttpStatusCode status, final List<ObjectError> errors) {
        super(status);
        this.errors = errors;
    }

}
