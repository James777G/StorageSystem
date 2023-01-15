package org.maven.apache.service.transaction;

import org.maven.apache.transaction.Transaction;

import java.util.List;

public interface cachedManipulationService {

    /**
     * This method is used to return a list sorted by Unit in Descending Order
     *
     * @param unsortedList the list to be sorted
     * @return List sorted by unit in descending order
     */
    List<Transaction> getUnitDescendingOrder (List<Transaction> unsortedList);

    /**
     * This method is used to return a list sorted by Unit in Ascending Order
     *
     * @param unsortedList the list to be sorted
     * @return List sorted by unit in ascending order
     */
    List<Transaction> getUnitAscendingOrder(List<Transaction> unsortedList);

    /**
     * This method is used to return a list sorted by date in descending order with an ascending
     *
     * @param unsortedList the list to be sorted
     * @return  List sorted by date in descending order
     */
    List<Transaction> getDateDescendingOrder(List<Transaction> unsortedList);
}
