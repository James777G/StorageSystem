package org.maven.apache.mapper;

import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.transaction.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(propagation = Propagation.REQUIRED)
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
    @Transactional(propagation = Propagation.REQUIRED)
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
    @Transactional(propagation = Propagation.REQUIRED)
    void updateTransaction(Transaction transaction);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    Transaction selectById(int id);

    /**
     * if id is not continuous use this to fix
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    void IdGapInside();

    /**
     * would get all the information in the table
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<Transaction> selectAll();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<Transaction> orderByDateAsc();
}
