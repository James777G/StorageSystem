package org.maven.apache.service.transaction;

import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.utils.TransactionCachedUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cachedTransactionListService")
public class CachedListObserver implements CachedTransactionDataListService {

    @Override
    public void updateAllLists(TransactionMapper transactionMapper, CachedManipulationService cachedManipulationService) {
        update(transactionMapper, cachedManipulationService);
    }

    private void update(TransactionMapper transactionMapper, CachedManipulationService cachedManipulationService) {
        List<Transaction> allTransaction = transactionMapper.selectAll();
        List<Transaction> dateTransactionASC = transactionMapper.orderByDateAsc();
        ////Restock
        List<List<Transaction>> amountAsc4_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(cachedManipulationService.getUnitAscendingOrder(allTransaction)), 4);
        List<List<Transaction>> amountDesc4_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(cachedManipulationService.getUnitDescendingOrder(allTransaction)), 4);
        List<List<Transaction>> dateAsc4_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(dateTransactionASC), 4);
        List<List<Transaction>> dateDesc4_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(cachedManipulationService.getReversedList(dateTransactionASC)), 4);
        List<List<Transaction>> amountAsc7_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(cachedManipulationService.getUnitAscendingOrder(allTransaction)), 7);
        List<List<Transaction>> amountDesc7_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(cachedManipulationService.getUnitDescendingOrder(allTransaction)), 7);
        List<List<Transaction>> dateAsc7_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(dateTransactionASC), 7);
        List<List<Transaction>> dateDesc7_Restock = cachedManipulationService.getPagedCacheList(cachedManipulationService.getRestockList(cachedManipulationService.getReversedList(dateTransactionASC)), 7);
//        System.out.println("amountAsc4_Restock:");
//        amountAsc4_Restock.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getUnit())));
//        System.out.println("amountDesc4_Restock:");
//        amountDesc4_Restock.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getUnit())));
//        System.out.println("dateAsc4_Restock:");
//        dateAsc4_Restock.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getTransactionTime())));
//        System.out.println("dateDesc4_Restock:");
//        dateDesc4_Restock.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getTransactionTime())));
        ////Taken
        List<List<Transaction>> amountAsc4_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getUnitAscendingOrder(allTransaction)), 4);
        List<List<Transaction>> amountDesc4_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getUnitDescendingOrder(allTransaction)), 4);
        List<List<Transaction>> dateAsc4_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(dateTransactionASC), 4);
        List<List<Transaction>> dateDesc4_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getReversedList(dateTransactionASC)), 4);
        List<List<Transaction>> amountAsc7_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getUnitAscendingOrder(allTransaction)), 7);
        List<List<Transaction>> amountDesc7_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getUnitDescendingOrder(allTransaction)), 7);
        List<List<Transaction>> dateAsc7_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(dateTransactionASC), 7);
        List<List<Transaction>> dateDesc7_Taken = cachedManipulationService.getPagedCacheList(cachedManipulationService.getTakenList(cachedManipulationService.getReversedList(dateTransactionASC)), 7);
//        System.out.println("amountAsc4_Taken:");
//        amountAsc4_Taken.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getUnit())));
//        System.out.println("amountDesc4_Taken:");
//        amountDesc4_Taken.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getUnit())));
//        System.out.println("dateAsc4_Taken :");
//        dateAsc4_Taken.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getTransactionTime())));
//        System.out.println("dateDesc4_Taken:");
//        dateDesc4_Taken.forEach(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getTransactionTime())));
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
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_AMOUNT_ASC_4, amountAsc4_Restock);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_AMOUNT_DESC_4, amountDesc4_Restock);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_DATE_ASC_4, dateAsc4_Restock);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4, dateDesc4_Restock);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_AMOUNT_ASC_7, amountAsc7_Restock);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_AMOUNT_DESC_7, amountDesc7_Restock);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_DATE_ASC_7, dateAsc7_Restock);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_7, dateDesc7_Restock);
        ////Taken
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_AMOUNT_ASC_4, amountAsc4_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_AMOUNT_DESC_4, amountDesc4_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_DATE_ASC_4, dateAsc4_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4, dateDesc4_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_AMOUNT_ASC_7, amountAsc7_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_AMOUNT_DESC_7, amountDesc7_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_DATE_ASC_7, dateAsc7_Taken);
        TransactionCachedUtils.putLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_7, dateDesc7_Taken);
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

}
