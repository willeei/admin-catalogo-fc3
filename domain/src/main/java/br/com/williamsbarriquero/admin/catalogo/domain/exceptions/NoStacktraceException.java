package br.com.williamsbarriquero.admin.catalogo.domain.exceptions;

import java.io.Serial;

public class NoStacktraceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6836572721387793183L;

    public NoStacktraceException(final String message) {
        this(message, null);
    }

    public NoStacktraceException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }

}
