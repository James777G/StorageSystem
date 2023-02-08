package org.maven.apache.service.transaction;

import jakarta.annotation.Resource;
import lombok.Data;
import org.maven.apache.exception.Warning;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.service.item.ControllerOrientedCachedItemHandler;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.utils.CargoCachedUtils;
import org.maven.apache.utils.TransactionCachedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service("cachedTransactionService")
@Data
public class CachedTransactionServiceProvider implements CachedTransactionService {

    @Autowired
    @SuppressWarnings("all")
    private TransactionMapper transactionMapper;

    @Resource
    @Qualifier("cachedManipulationService")
    private CachedManipulationService cachedManipulationService;

    @Resource
    private ItemMapper itemMapper;

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
        List<List<Transaction>> amountAsc7_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(cachedManipulationService.getUnitAscendingOrder(allTransaction)), 7);
        List<List<Transaction>> amountDesc7_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(cachedManipulationService.getUnitDescendingOrder(allTransaction)), 7);
        List<List<Transaction>> dateAsc7_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(dateTransactionASC),7);
        List<List<Transaction>> dateDesc7_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(cachedManipulationService.getReversedList(dateTransactionASC)),7);
        System.out.println("amountAsc4_Restock:");
        amountAsc4_Restock.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getUnit())));
        System.out.println("amountDesc4_Restock:");
        amountDesc4_Restock.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getUnit())));
        System.out.println("dateAsc4_Restock:");
        dateAsc4_Restock.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getTransactionTime())));
        System.out.println("dateDesc4_Restock:");
        dateDesc4_Restock.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getTransactionTime())));
        ////Taken
        List<List<Transaction>> amountAsc4_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getUnitAscendingOrder(allTransaction)), 4);
        List<List<Transaction>> amountDesc4_Taken= cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getUnitDescendingOrder(allTransaction)), 4);
        List<List<Transaction>> dateAsc4_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(dateTransactionASC),4);
        List<List<Transaction>> dateDesc4_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getReversedList(dateTransactionASC)),4);
        List<List<Transaction>> amountAsc7_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getUnitAscendingOrder(allTransaction)), 7);
        List<List<Transaction>> amountDesc7_Taken= cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getUnitDescendingOrder(allTransaction)), 7);
        List<List<Transaction>> dateAsc7_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(dateTransactionASC),7);
        List<List<Transaction>> dateDesc7_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getReversedList(dateTransactionASC)),7);
        System.out.println("amountAsc4_Taken:");
        amountAsc4_Taken.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getUnit())));
        System.out.println("amountDesc4_Taken:");
        amountDesc4_Taken.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getUnit())));
        System.out.println("dateAsc4_Taken :");
        dateAsc4_Taken .forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getTransactionTime())));
        System.out.println("dateDesc4_Taken:");
        dateDesc4_Taken.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getTransactionTime())));
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
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_AMOUNT_ASC_7,amountAsc7_Restock);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_AMOUNT_DESC_7,amountDesc7_Restock);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_DATE_ASC_7,dateAsc7_Restock);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_7,dateDesc7_Restock);
        ////Taken
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_AMOUNT_ASC_4,amountAsc4_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_AMOUNT_DESC_4,amountDesc4_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_DATE_ASC_4,dateAsc4_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4,dateDesc4_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_AMOUNT_ASC_7,amountAsc7_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_AMOUNT_DESC_7,amountDesc7_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_DATE_ASC_7,dateAsc7_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_7,dateDesc7_Taken);
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
    @Warning(Warning.WarningType.DEBUG)
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewTransaction(Transaction transaction) {
        transactionMapper.addNewTransaction(transaction);
        final Item[] item = {new Item()};
        CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL)
                .forEach(items -> items.forEach(item1 -> {
            if (Objects.equals(item1.getItemName(), transaction.getItemName())){
                item[0] = item1;
            }
        }));
        if (Objects.equals(transaction.getStatus(), "TAKEN")) {
            item[0].setUnit(item[0].getUnit() - transaction.getUnit());
        } else if (Objects.equals(transaction.getStatus(), "RESTOCK")) {
            item[0].setUnit(item[0].getUnit() + transaction.getUnit());
        }
        itemMapper.update(item[0]);
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
    @Warning(Warning.WarningType.DEBUG)
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTransactionById(int id) {
        transactionMapper.deleteTransactionById(id);
        final Item[] item = {new Item()};
        final Transaction[] transaction = {new Transaction()};
        TransactionCachedUtils.getLists(TransactionCachedUtils.listType.DATE_ASC_7).forEach(transactions -> transactions.forEach(transaction1 -> {
            if (transaction1.getID() == id){
                transaction[0] = transaction1;
            }
        }));
        CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL)
                .forEach(items -> items.forEach(item1 -> {
                    if (Objects.equals(item1.getItemName(), transaction[0].getItemName())){
                        item[0] = item1;
                    }
                }));
        if (Objects.equals(transaction[0].getStatus(), "TAKEN")) {
            item[0].setUnit(item[0].getUnit() + transaction[0].getUnit());
        } else if (Objects.equals(transaction[0].getStatus(), "RESTOCK")) {
            item[0].setUnit(item[0].getUnit() - transaction[0].getUnit());
        }
        itemMapper.update(item[0]);
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
        final Item[] item = {new Item()};
        CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL)
                .forEach(items -> items.forEach(item1 -> {
                    if (Objects.equals(item1.getItemName(), transaction.getItemName())){
                        item[0] = item1;
                    }
                }));
        final Transaction[] transaction_old = {new Transaction()};
        TransactionCachedUtils.getLists(TransactionCachedUtils.listType.DATE_ASC_7).forEach(transactions -> transactions.forEach(transaction1 -> {
            if (transaction1.getID() == transaction.getID()){
                transaction_old[0] = transaction1;
            }
        }));
        if (Objects.equals(transaction_old[0].getStatus(), "TAKEN")) {
            item[0].setUnit(item[0].getUnit() + transaction_old[0].getUnit());
        } else if (Objects.equals(transaction_old[0].getStatus(), "RESTOCK")) {
            item[0].setUnit(item[0].getUnit() - transaction_old[0].getUnit());
        }
        transactionMapper.updateTransaction(transaction);
        if (Objects.equals(transaction.getStatus(), "TAKEN")) {
            item[0].setUnit(item[0].getUnit() - transaction.getUnit());
        } else if (Objects.equals(transaction.getStatus(), "RESTOCK")) {
            item[0].setUnit(item[0].getUnit() + transaction.getUnit());
        }
        itemMapper.update(item[0]);
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
