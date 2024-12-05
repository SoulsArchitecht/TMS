package ru.sshibko.tms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception throws when access to a resource or operation with resource was denied
 * due to insufficient permissions
 */
@ResponseStatus(value = HttpStatus.LOCKED)
public class AccessDeniedException extends RuntimeException {

    /**
     * Constructs a new {@link AccessDeniedException} with the specified detail message.
     * @param message the detail message to be displayed when the exception is thrown.
     */
    public AccessDeniedException(String message) {
        super(message);
    }
}
