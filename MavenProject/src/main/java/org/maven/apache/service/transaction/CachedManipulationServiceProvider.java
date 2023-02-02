package org.maven.apache.service.transaction;

import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service("cachedManipulationService")
public class CachedManipulationServiceProvider implements CachedManipulationService {

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
    public List<Transaction> getReversedList(List<Transaction> unsortedList) {
        List<Transaction> reversedList = new ArrayList<Transaction>();
        for(int i = 0; i < unsortedList.size(); i++){
            reversedList.add(unsortedList.get(unsortedList.size()-1 - i));
        }
            return reversedList;
    }

    @Override
    public List<List<Transaction>> getPagedCacheList(List<Transaction> cachedList, int pageSize) {
        List<List<Transaction>> pagedCachedList = new ArrayList<>();
        int pageNumber = cachedList.size() / pageSize;
        for (int i = 0; i < pageNumber; i++) {
            List<Transaction> transactionList = new ArrayList<Transaction>(4);
            for (int j = 0; j < pageSize; j++) {
                transactionList.add(cachedList.get((i * pageSize) + j));
            }
            pagedCachedList.add(i, transactionList);
        }
        if (cachedList.size() % pageSize != 0) {
            List<Transaction> transactionList = new ArrayList<Transaction>();
            for (int i = 0; i < cachedList.size() % pageSize; i++) {
                transactionList.add(i, cachedList.get(cachedList.size() - cachedList.size() % pageSize + i));
            }
            pagedCachedList.add(transactionList);
        }
        return pagedCachedList;
    }

    @Override
    public List<Transaction> getRestockList(List<Transaction> allList) {
        List<Transaction> restockList = new ArrayList<>();
        allList
                .forEach(transaction -> {
                    if(Objects.equals(transaction.getStatus(), "RESTOCK")) { restockList.add(transaction);} ;
                });
        return restockList;
    }

    @Override
    public List<Transaction> getTakenList(List<Transaction> allList) {
        List<Transaction> takenList = new ArrayList<>();
        allList
                .forEach(transaction -> {
                    if(Objects.equals(transaction.getStatus(), "TAKEN")) { takenList.add(transaction);} ;
                });
        return takenList;
    }

    @Test
    public void t(){
        List<Transaction> transactionList = new ArrayList<Transaction>();
        Transaction transaction = new Transaction();
        transaction.setStatus("TAKEN");
        transaction.setUnit(20);
        transactionList.add(0,transaction);
        Transaction transaction1 = new Transaction();
        transaction1.setStatus("TAKEN");
        transaction1.setUnit(10);
        transactionList.add(1,transaction1);
        Transaction transaction2 = new Transaction();
        transaction2.setStatus("TAKEN");
        transaction2.setUnit(30);
        transactionList.add(2,transaction2);
        Transaction transaction3 = new Transaction();
        transaction3.setStatus("RESTOCK");
        transaction3.setUnit(2);
        transactionList.add(3,transaction3);
        Transaction transaction4 = new Transaction();
        transaction4.setUnit(100);
        transaction4.setStatus("TAKEN");
        transactionList.add(4,transaction4);
        Transaction transaction5 = new Transaction();
        transaction5.setStatus("RESTOCK");
        transaction5.setUnit(60);
        transactionList.add(5,transaction5);
        Transaction transaction6 = new Transaction();
        transaction6.setStatus("TAKEN");
        transaction6.setUnit(50);
        transactionList.add(6,transaction6);
        //transactionList = getUnitDescendingOrder(transactionList);
        transactionList = getUnitDescendingOrder(transactionList);
        transactionList = getReversedList(transactionList);
//        transactionList = getTakenList(transactionList);
        for (Transaction transaction7 : transactionList) {
            System.out.println(transaction7.getStatus() + ": " + transaction7.getUnit());
        }

        List<List<Transaction>> pagedList = getPagedCacheList(transactionList,4);


        for (List<Transaction> CachedListPage : pagedList){
            System.out.println(CachedListPage);
        }
    }
}
