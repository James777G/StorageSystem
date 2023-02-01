package org.maven.apache.service.transaction;

import jakarta.annotation.Resource;
import lombok.Data;
import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.utils.TransactionCachedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("cachedTransactionService")
@Data
public class CachedTransactionServiceProvider implements CachedTransactionService {

    @Autowired
    @SuppressWarnings("all")
    private TransactionMapper transactionMapper;

    @Resource
    @Qualifier("cachedManipulationService")
    private CachedManipulationService cachedManipulationService;


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
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void updateAllCachedTransactionData() {
        List<Transaction> allTransaction = transactionMapper.selectAll();
        List<Transaction> dateTransactionASC = transactionMapper.orderByDateAsc();
        ////Restock
        List<List<Transaction>> amountAsc4_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(cachedManipulationService.getUnitAscendingOrder(allTransaction)), 4);
        List<List<Transaction>> amountDesc4_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(cachedManipulationService.getUnitDescendingOrder(allTransaction)), 4);
        List<List<Transaction>> dateAsc4_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(dateTransactionASC),4);
        List<List<Transaction>> dateDesc4_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(cachedManipulationService.getReversedList(dateTransactionASC)),4);
        ////Taken
        List<List<Transaction>> amountAsc4_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getUnitAscendingOrder(allTransaction)), 4);
        List<List<Transaction>> amountDesc4_Taken= cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getUnitDescendingOrder(allTransaction)), 4);
        List<List<Transaction>> dateAsc4_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(dateTransactionASC),4);
        List<List<Transaction>> dateDesc4_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getReversedList(dateTransactionASC)),4);
        ////All
        List<List<Transaction>> amountAsc4 = cachedManipulationService.getPagedCacheList(cachedManipulationService.getUnitAscendingOrder(allTransaction), 4);
        List<List<Transaction>> amountAsc7 = cachedManipulationService.getPagedCacheList(cachedManipulationService.getUnitAscendingOrder(allTransaction), 7);
        List<List<Transaction>> amountDesc4 = cachedManipulationService.getPagedCacheList(cachedManipulationService.getUnitDescendingOrder(allTransaction), 4);
        List<List<Transaction>> amountDesc7 = cachedManipulationService.getPagedCacheList(cachedManipulationService.getUnitDescendingOrder(allTransaction), 7);
        List<List<Transaction>> DateAsc4 = cachedManipulationService.getPagedCacheList(dateTransactionASC, 4);
        List<List<Transaction>> DateAsc7 = cachedManipulationService.getPagedCacheList(dateTransactionASC, 7);
        List<List<Transaction>> DateDesc4 = cachedManipulationService.getPagedCacheList(cachedManipulationService.getReversedList(dateTransactionASC), 4);
        List<List<Transaction>> DateDesc7 = cachedManipulationService.getPagedCacheList(cachedManipulationService.getReversedList(dateTransactionASC), 7);

        ////Restock
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_AMOUNT_ASC_4,amountAsc4_Restock);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_AMOUNT_DESC_4,amountDesc4_Restock);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_DATE_ASC_4,dateAsc4_Restock);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4,dateDesc4_Restock);
        ////Taken
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_AMOUNT_ASC_4,amountAsc4_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_AMOUNT_DESC_4,amountDesc4_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_DATE_ASC_4,dateAsc4_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4,dateDesc4_Taken);
        ////All
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.AMOUNT_ASC_4, amountAsc4);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.AMOUNT_ASC_7, amountAsc7);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.AMOUNT_DESC_4, amountDesc4);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.AMOUNT_DESC_7, amountDesc7);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.DATE_ASC_4, DateAsc4);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.DATE_ASC_7, DateAsc7);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.DATE_DESC_4, DateDesc4);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.DATE_DESC_7, DateDesc7);
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
    @Transactional(propagation = Propagation.REQUIRED)
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
    @Transactional(propagation = Propagation.REQUIRED)
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
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void updateTransaction(Transaction transaction) {
        transactionMapper.updateTransaction(transaction);
        updateAllCachedTransactionData();
    }

    /**
     * this method should be use after using delete by id
     */
    @Override
    public void IdGapInside() {
        transactionMapper.IdGapInside();
    }


}
