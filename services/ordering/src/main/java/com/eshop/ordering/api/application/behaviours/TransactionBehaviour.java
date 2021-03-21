package com.eshop.ordering.api.application.behaviours;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

// TODO HD try to handle transactions with middleware

@RequiredArgsConstructor
@Order(1)
public class TransactionBehaviour { // implements middleware
  private final PlatformTransactionManager txManager;


  public <R, C extends Command<R>> R invoke(C command, Command.Middleware.Next<R> next) {
    var tx = new TransactionTemplate(txManager);
    tx.setName("Tx==" + UUID.randomUUID());
//    tx.setReadOnly(command instanceof ReadOnly);
    return tx.execute(status -> next.invoke());
  }
}
