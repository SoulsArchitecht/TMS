package ru.sshibko.tms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an any requested entity is not found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor to create a new instance of {@link ResourceNotFoundException}
     * @param message the detail message that'll be displayed when this exception is thrown.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
