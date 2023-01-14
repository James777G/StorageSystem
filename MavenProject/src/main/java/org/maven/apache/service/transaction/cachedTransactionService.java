package org.maven.apache.service.transaction;

import org.maven.apache.transaction.Transaction;

public interface cachedTransactionService {

    /**
     * This method updates all the cached transaction data currently stored
     * <p>
     *     1. This method is highly time-consuming, thus should be delegated to loading animation.
     *     2. This method should be called whenever there is a change to database, thus should be
     *     included in add, delete and update methods.
     * </p>
     */
    void updateAllCachedTransactionData();

    /**
     * This method adds a new transaction to the database.
     * <p>
     *     1. This method will call updateAllCachedTransactionData to retrieve the latest data after
     *     the insertion of data is completed.
     *     2. This method is highly time-consuming, thus should be delegated to loading animation.
     *     3. This method might throw exceptions thus should be surrounded by try/catch for further
     *     operations.
     * </p>
     * @param transaction encapsulated transaction to be added
     */
    void addNewTransaction(Transaction transaction);
}
