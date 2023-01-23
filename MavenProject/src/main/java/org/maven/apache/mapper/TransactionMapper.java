package org.maven.apache.mapper;

import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.transaction.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface TransactionMapper {
    /**
     * This method adds a new transaction to the database.
     * <p>
     *     1. This method will call updateAllCachedTransactionData to retrieve the latest data after
     *     the insertion of data is completed.
     *     2. This method is highly time-consuming, thus should be delegated to loading animation.
     *     3. This method might throw exceptions thus should be surrounded by try/catch for further
     *     operations (Some fields are left blank accidentally).
     * </p>
     * @param transaction encapsulated transaction to be added
     */
    void addNewTransaction(Transaction transaction);


    /**
     * This method deletes an existing transaction from the database.
     * <p>
     *     1. This method will call updateAllCachedTransactionData to retrieve the latest data after
     *     the insertion of data is completed.
     *     2. This method is highly time-consuming, thus should be delegated to loading animation.
     * </p>
     * @param id Transaction ID which is unique
     */
    void deleteTransactionById(int id);

    /**
     * This method updates an existing transaction in the database.
     * <p>
     *     1. This method will call updateAllCachedTransactionData to retrieve the latest data after
     *     the insertion of data is completed.
     *     2. This method is highly time-consuming, thus should be delegated to loading animation.
     *     3. This method might throw exceptions thus should be surrounded by try/catch for further
     *     operations. (Some fields are left blank accidentally)
     * </p>
     * @param transaction encapsulated transaction object to be updated with desired attributes
     */
    void updateTransaction(Transaction transaction);

    /**
     * if id is not continuous use this to fix
     */
    void IdGapInside();

    /**
     * would get all the information in the table
     *
     * @return
     */
    List<Transaction> selectAll();

    List<Transaction> orderByDateAsc();
}
