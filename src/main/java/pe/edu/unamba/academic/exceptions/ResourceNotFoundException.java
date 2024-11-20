package pe.edu.unamba.academic.exceptions;

import java.text.MessageFormat;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }
}
