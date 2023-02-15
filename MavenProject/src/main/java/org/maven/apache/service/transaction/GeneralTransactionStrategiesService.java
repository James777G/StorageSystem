package org.maven.apache.service.transaction;

import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.exception.NegativeDataException;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.transaction.Transaction;

public interface GeneralTransactionStrategiesService {

    /**
     * This method is responsible for handling all the strategies in the addNewTransaction
     *
     * @param transaction the transaction to be added
     */
    void doStrategies(ItemMapper itemMapper, TransactionMapper transactionMapper, Transaction transaction) throws DataNotFoundException, NegativeDataException;

    /**
     * This method is responsible for handling all the strategies related to delete operation
     *
     * @param id transaction to be deleted
     */
    void doStrategies(ItemMapper itemMapper, TransactionMapper transactionMapper, int id) throws NegativeDataException;

}
