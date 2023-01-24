package org.maven.apache.service.transaction;

import org.maven.apache.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        for (int i = 0; i < unsortedList.size() / 2; i++) {
            Transaction tempTransaction = unsortedList.get(i);
            unsortedList.set(i, unsortedList.get(unsortedList.size() - 1 - i));
            unsortedList.set(unsortedList.size() - 1 - i, tempTransaction);
        }
        return unsortedList;
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


}
