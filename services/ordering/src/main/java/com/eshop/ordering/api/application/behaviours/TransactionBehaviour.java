package com.eshop.ordering.api.application.behaviours;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

// TODO HD try something with middleware (like it is in in eShopOnContainers)

@RequiredArgsConstructor
public class TransactionBehaviour { // implements middleware
  private final PlatformTransactionManager txManager;

//  @Override
  public <R, C extends Command<R>> R invoke(C command, Command.Middleware.Next<R> next) {
    var tx = new TransactionTemplate(txManager);
    tx.setName("Tx for " + command.getClass().getSimpleName());
//    tx.setReadOnly(command instanceof ReadOnly);
    return tx.execute(status -> next.invoke());
  }
}
