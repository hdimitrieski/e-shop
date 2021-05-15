package com.eshop.shared.outbox;

public enum EventState {
  NotPublished,
  InProgress,
  Published,
  PublishedFailed
}
