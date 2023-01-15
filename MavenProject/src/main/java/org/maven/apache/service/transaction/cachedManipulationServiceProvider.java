package org.maven.apache.service.transaction;

import org.maven.apache.transaction.Transaction;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class cachedManipulationServiceProvider implements cachedManipulationService{

    @Override
    @SuppressWarnings("all")
    public List<Transaction> getUnitDescendingOrder(List<Transaction> unsortedList) {
        return unsortedList.stream()
                .sorted((o1, o2) -> o1.getUnit() >= o2.getUnit() ? -1 : 1).toList();
    }

    @Override
    @SuppressWarnings("all")
    public List<Transaction> getUnitAscendingOrder(List<Transaction> unsortedList) {
        return unsortedList.stream()
                .sorted((o1, o2) -> o1.getUnit() <= o2.getUnit() ? -1 : 1).toList();
    }

    @Override
    public List<Transaction> getDateDescendingOrder(List<Transaction> unsortedList) {
         Collections.reverse(unsortedList);
         return unsortedList;
    }


    @Test
    public void t(){
        List<Transaction> transactionList = new ArrayList<Transaction>();
        Transaction transaction = new Transaction();
        transaction.setUnit(20);
        transactionList.add(0,transaction);
        Transaction transaction1 = new Transaction();
        transaction1.setUnit(10);
        transactionList.add(1,transaction1);
        Transaction transaction2 = new Transaction();
        transaction2.setUnit(30);
        transactionList.add(2,transaction2);
        Transaction transaction3 = new Transaction();
        transaction3.setUnit(2);
        transactionList.add(3,transaction3);
        Transaction transaction4 = new Transaction();
        transaction4.setUnit(100);
        transactionList.add(4,transaction4);
        //transactionList = getUnitDescendingOrder(transactionList);
        transactionList = getDateDescendingOrder(transactionList);
        transactionList
                .forEach(transaction5 -> System.out.println(transaction5.getUnit()));
    }
}
