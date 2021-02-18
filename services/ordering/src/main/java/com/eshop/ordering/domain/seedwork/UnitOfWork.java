package com.eshop.ordering.domain.seedwork;

public interface UnitOfWork {
    int saveChanges();
    boolean saveEntities();
}
