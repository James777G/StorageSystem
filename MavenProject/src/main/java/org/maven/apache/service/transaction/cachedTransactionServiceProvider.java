package org.maven.apache.service.transaction;

import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.utils.TransactionCashedUtils;

import java.util.ArrayList;
import java.util.List;

public class cachedTransactionServiceProvider implements cachedTransactionService {
    private TransactionMapper transactionMapper;
    private cachedManipulationService cachedManipulationService;


    public TransactionMapper getTransactionMapper() {
        return transactionMapper;
    }

    public void setTransactionMapper(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    /**
     * This method updates all the cached transaction data currently stored
     * <p>
     * 1. This method is highly time-consuming, thus should be delegated to loading animation.
     * 2. This method should be called whenever there is a change to database, thus should be
     * included in add, delete and update methods.
     * </p>
     */
    @Override
    public void updateAllCachedTransactionData() {
    List<Transaction> allTransaction =  transactionMapper.selectAll();
    List<Transaction> dateTransactionASC = transactionMapper.orderByDateAsc();
    ////
    List<List<Transaction>> amountAsc4=  cachedManipulationService.getPagedCacheList(cachedManipulationService.getUnitAscendingOrder(allTransaction),4);
    List<List<Transaction>> amountAsc7=cachedManipulationService.getPagedCacheList(cachedManipulationService.getUnitAscendingOrder(allTransaction),7);
    List<List<Transaction>> amountDesc4 =cachedManipulationService.getPagedCacheList(cachedManipulationService.getUnitDescendingOrder(allTransaction),4);
    List<List<Transaction>> amountDesc7=cachedManipulationService.getPagedCacheList(cachedManipulationService.getUnitDescendingOrder(allTransaction),7);
    List<List<Transaction>> DateAsc4=   cachedManipulationService.getPagedCacheList(dateTransactionASC,4);
    List<List<Transaction>> DateAsc7=  cachedManipulationService.getPagedCacheList(dateTransactionASC,7);
    List<List<Transaction>> DateDesc4=  cachedManipulationService.getPagedCacheList(cachedManipulationService.getDateDescendingOrder(dateTransactionASC),4);
    List<List<Transaction>> DateDesc7= cachedManipulationService.getPagedCacheList(cachedManipulationService.getDateDescendingOrder(dateTransactionASC),7);

    TransactionCashedUtils.putLists(TransactionCashedUtils.listType.AMOUNT_ASC_4,amountAsc4);
    TransactionCashedUtils.putLists(TransactionCashedUtils.listType.AMOUNT_ASC_7,amountAsc7);
    TransactionCashedUtils.putLists(TransactionCashedUtils.listType.AMOUNT_DESC_4,amountDesc4);
    TransactionCashedUtils.putLists(TransactionCashedUtils.listType.AMOUNT_DESC_7,amountDesc7);
    TransactionCashedUtils.putLists(TransactionCashedUtils.listType.DATE_ASC_4,DateAsc4);
    TransactionCashedUtils.putLists(TransactionCashedUtils.listType.DATE_ASC_7,DateAsc7);
    TransactionCashedUtils.putLists(TransactionCashedUtils.listType.DATE_DESC_4,DateDesc4);
    TransactionCashedUtils.putLists(TransactionCashedUtils.listType.DATE_DESC_7,DateDesc7);
    }


    /**
     * This method adds a new transaction to the database.
     * <p>
     * 1. This method will call updateAllCachedTransactionData to retrieve the latest data after
     * the insertion of data is completed.
     * 2. This method is highly time-consuming, thus should be delegated to loading animation.
     * 3. This method might throw exceptions thus should be surrounded by try/catch for further
     * operations (Some fields are left blank accidentally).
     * </p>
     *
     * @param transaction encapsulated transaction to be added
     */
    @Override
    public void addNewTransaction(Transaction transaction) {
        transactionMapper.addNewTransaction(transaction);
        updateAllCachedTransactionData();
    }

    /**
     * This method deletes an existing transaction from the database.
     * <p>
     * 1. This method will call updateAllCachedTransactionData to retrieve the latest data after
     * the insertion of data is completed.
     * 2. This method is highly time-consuming, thus should be delegated to loading animation.
     * </p>
     *
     * @param id Transaction ID which is unique
     */
    @Override
    public void deleteTransactionById(int id) {
        transactionMapper.deleteTransactionById(id);
        updateAllCachedTransactionData();
    }

    /**
     * This method updates an existing transaction in the database.
     * <p>
     * 1. This method will call updateAllCachedTransactionData to retrieve the latest data after
     * the insertion of data is completed.
     * 2. This method is highly time-consuming, thus should be delegated to loading animation.
     * 3. This method might throw exceptions thus should be surrounded by try/catch for further
     * operations. (Some fields are left blank accidentally)
     * </p>
     *
     * @param transaction encapsulated transaction object to be updated with desired attributes
     */
    @Override
    public void updateTransaction(Transaction transaction) {
        transactionMapper.updateTransaction(transaction);
        updateAllCachedTransactionData();
    }



}
