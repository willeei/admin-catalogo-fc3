package br.com.williamsbarriquero.admin.catalogo.domain.exceptions;

import br.com.williamsbarriquero.admin.catalogo.domain.validation.handler.Notification;

import java.io.Serial;

public class NotificationException extends DomainException {

    @Serial
    private static final long serialVersionUID = 2290132700855462795L;

    public NotificationException(final String aMessage, final Notification notification) {
        super(aMessage, notification.getErrors());
    }
}
