package com.eshop.outbox;

public enum EventState {
  NotPublished,
  InProgress,
  Published,
  PublishedFailed
}
