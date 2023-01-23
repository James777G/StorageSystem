package org.maven.apache.service.transaction;

import org.maven.apache.transaction.Transaction;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class cachedManipulationServiceProvider {

   // @Override
    @SuppressWarnings("all")
    public List<Transaction> getUnitDescendingOrder(List<Transaction> unsortedList) {
        return unsortedList.stream()
                .sorted((o1, o2) -> o1.getUnit() >= o2.getUnit() ? -1 : 1).toList();
    }

  //  @Override
    @SuppressWarnings("all")
    public List<Transaction> getUnitAscendingOrder(List<Transaction> unsortedList) {
        return unsortedList.stream()
                .sorted((o1, o2) -> o1.getUnit() <= o2.getUnit() ? -1 : 1).toList();
    }


  //  @Override

    public List<Transaction> getReversedList(List<Transaction> unsortedList) {

        for(int i=0; i < unsortedList.size() / 2;i++){
            Transaction tempTransaction = unsortedList.get(i);
            unsortedList.set(i,unsortedList.get(unsortedList.size() - 1 - i));
            unsortedList.set(unsortedList.size() - 1 - i,tempTransaction);
        }
        return unsortedList;
    }

  //  @Override
    public List<List<Transaction>> getPagedCacheList(List<Transaction> cachedList, int pageSize) {
        List<List<Transaction>> pagedCachedList = new ArrayList<>();
        int pageNumber = cachedList.size() / pageSize  ;
        int lastPageSize = cachedList.size() % pageSize;
        for(int i = 0; i < pageNumber ; i++){
            List<Transaction> transactionList = new ArrayList<Transaction>(4);
            for(int j = 0; j < pageSize; j++){
                System.out.println(cachedList.get((i * pageSize) + j));
                System.out.println(i * pageSize + j);
                transactionList.add(cachedList.get((i * pageSize) + j));
//                transactionList.add(j,cachedList.get((i * pageSize) + j));
//                System.out.println(transactionList.size());
            }
            pagedCachedList.add(i,transactionList);
//            pagedCachedList.add(i,transactionList);
        }
        if(cachedList.size() % pageSize != 0 ) {
            List<Transaction> transactionList = new ArrayList<Transaction>();
            for (int i = 0; i < cachedList.size() % pageSize; i++) {
                System.out.println(cachedList.size() - cachedList.size() % pageSize + i);
                System.out.println(cachedList.get(cachedList.size() - cachedList.size() % pageSize + i));
                transactionList.add(i, cachedList.get(cachedList.size() - cachedList.size() % pageSize + i));
//            pagedCachedList.add( cachedList.size() / pageSize,transactionList);
            }
            pagedCachedList.add(transactionList);
        }
        return pagedCachedList;
    }


//    @Test
//    public void t(){
//        List<Transaction> transactionList = new ArrayList<Transaction>();
//        Transaction transaction = new Transaction();
//        transaction.setUnit(20);
//        transactionList.add(0,transaction);
//        Transaction transaction1 = new Transaction();
//        transaction1.setUnit(10);
//        transactionList.add(1,transaction1);
//        Transaction transaction2 = new Transaction();
//        transaction2.setUnit(30);
//        transactionList.add(2,transaction2);
//        Transaction transaction3 = new Transaction();
//        transaction3.setUnit(2);
//        transactionList.add(3,transaction3);
//        Transaction transaction4 = new Transaction();
//        transaction4.setUnit(100);
//        transactionList.add(4,transaction4);
//        Transaction transaction5 = new Transaction();
//        transaction5.setUnit(60);
//        transactionList.add(5,transaction5);
//        Transaction transaction6 = new Transaction();
//        transaction6.setUnit(50);
//        transactionList.add(6,transaction6);
//        //transactionList = getUnitDescendingOrder(transactionList);
//        transactionList = getUnitDescendingOrder(transactionList);
//        for (Transaction transaction7 : transactionList) {
//            System.out.println(transaction7.getUnit());
//        }
//
//        List<List<Transaction>> pagedList = getPagedCacheList(transactionList,2);
//
//
//        for (List<Transaction> CachedListPage : pagedList){
//            System.out.println(CachedListPage);
//        }
//    }
}
