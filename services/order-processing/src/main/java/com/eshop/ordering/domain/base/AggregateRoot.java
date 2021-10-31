package com.eshop.ordering.domain.base;

import org.springframework.lang.NonNull;

import java.util.*;

/**
 * Convenience base class for aggregate roots that exposes a {@link #addDomainEvent(DomainEvent)} to capture domain
 * events and expose them via {@link #domainEvents()}.
 */
public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {

  private transient final List<DomainEvent> domainEvents = new ArrayList<>();

  /**
   * Registers the given event object for publication on a call to a Spring Data repository's save methods.
   *
   * @param event must not be {@literal null}.
   * @return the event that has been added.
   */
  protected <T extends DomainEvent> T addDomainEvent(@NonNull T event) {
    Objects.requireNonNull(event, "Domain event must not be null!");

    this.domainEvents.add(event);
    return event;
  }

  /**
   * Remove the given event object from the list of domain events currently held.
   *
   * @param event must not be {@literal null}.
   */
  public <T extends DomainEvent> void removeDomainEvent(@NonNull T event) {
    Objects.requireNonNull(event, "Domain event must not be null!");

    domainEvents.remove(event);
  }

  /**
   * Clears all domain events currently held. Usually invoked by the infrastructure in place in Spring Data
   * repositories.
   */
  public void clearDomainEvents() {
    this.domainEvents.clear();
  }

  /**
   * All domain events currently captured by the aggregate.
   */
  public Collection<DomainEvent> domainEvents() {
    return Collections.unmodifiableList(domainEvents);
  }
}
