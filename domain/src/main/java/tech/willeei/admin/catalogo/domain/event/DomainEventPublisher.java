package tech.willeei.admin.catalogo.domain.event;

@FunctionalInterface
public interface DomainEventPublisher {

    void publishEvent(DomainEvent event);
}
