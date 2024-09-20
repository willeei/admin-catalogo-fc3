package tech.willeei.admin.catalogo.domain.event;

import java.io.Serializable;
import java.time.Instant;

public interface DomainEvent extends Serializable {

    Instant occurredOn();
}
