package tech.willeei.admin.catalogo.domain.event;

@FunctionalInterface
public interface DomainEventPublisher {

    <T extends DomainEvent> void publishEvent(T event);
}
