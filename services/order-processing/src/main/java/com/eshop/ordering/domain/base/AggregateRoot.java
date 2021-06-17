package com.eshop.ordering.domain.base;

import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * Convenience base class for aggregate roots that exposes a {@link #addDomainEvent(Object)} to capture domain events and
 * expose them via {@link #domainEvents()}. The implementation is using the general event publication mechanism implied
 * by {@link DomainEvents} and {@link AfterDomainEventPublication}. If in doubt or need to customize anything here,
 * rather build your own base class and use the annotations directly.
 */
public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {

  private transient final List<Object> domainEvents = new ArrayList<>();

  /**
   * Registers the given event object for publication on a call to a Spring Data repository's save methods.
   *
   * @param event must not be {@literal null}.
   * @return the event that has been added.
   */
  protected <T> T addDomainEvent(@NonNull T event) {
    Objects.requireNonNull(event, "Domain event must not be null!");

    this.domainEvents.add(event);
    return event;
  }

  /**
   * Remove the given event object from the list of domain events currently held.
   *
   * @param event must not be {@literal null}.
   */
  public <T> void removeDomainEvent(@NonNull T event) {
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
  public Collection<Object> domainEvents() {
    return Collections.unmodifiableList(domainEvents);
  }
}
