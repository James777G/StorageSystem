package org.maven.apache.service.transaction;

import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.exception.NegativeDataException;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.transaction.Transaction;

import java.lang.reflect.InvocationTargetException;

public abstract sealed class AbstractTransactionStrategy permits WarehouseControllerSynchronizingStrategy, CargoDataSynchronizingStrategy, AlertSystemSynchronizingStrategy, AppPageControllerSynchronizingStrategy, TransactionPageControllerSynchronizingStrategy {


    public abstract void doStrategy(ItemMapper itemMapper, TransactionMapper transactionMapper, Transaction transaction) throws DataNotFoundException, NegativeDataException;

    public abstract void doStrategy(ItemMapper itemMapper, TransactionMapper transactionMapper, int id) throws NegativeDataException;
}
