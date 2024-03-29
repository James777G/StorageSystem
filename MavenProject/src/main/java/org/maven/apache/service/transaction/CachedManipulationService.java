package org.maven.apache.service.transaction;

import org.maven.apache.transaction.Transaction;

import java.util.List;

public interface CachedManipulationService {

    /**
     * This method is used to return a list sorted by Unit in Descending Order
     *
     * @param unsortedList the list to be sorted
     * @return List sorted by unit in descending order
     */
    List<Transaction> getUnitDescendingOrder(List<Transaction> unsortedList);

    /**
     * This method is used to return a list sorted by Unit in Ascending Order
     *
     * @param unsortedList the list to be sorted
     * @return List sorted by unit in ascending order
     */
    List<Transaction> getUnitAscendingOrder(List<Transaction> unsortedList);

    /**
     * This method is used to return a list that reverse the index
     *
     * @param unsortedList the list to be sorted
     * @return List has been reversed the index order
     */
    List<Transaction> getReversedList(List<Transaction> unsortedList);

    /**
     * This method is used to return a paged cached list
     *
     * @param cachedList the cachedList to be put in pages
     * @param pageSize   size of each page
     * @return a few transaction object lists(represents the transactions in each Page)
     * in the other list(represents the paged cache list - the index of the list is pageNumber - 1)
     */
    List<List<Transaction>> getPagedCacheList(List<Transaction> cachedList, int pageSize);

    /**
     * This method is used to return a list consist of restock transactions only from an all transaction list
     *
     * @param allList a list contains both restock and taken transactions
     * @return a list contains restock transactions only
     */
    List<Transaction> getRestockList(List<Transaction> allList);

    /**
     * This method is used to return a list consist of taken transactions only from an all transaction list
     *
     * @param allList a list contains both restock and taken transactions
     * @return a list contains taken transactions only
     */
    List<Transaction> getTakenList(List<Transaction> allList);
}
