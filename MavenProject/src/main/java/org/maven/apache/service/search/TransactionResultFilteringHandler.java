package org.maven.apache.service.search;

import org.maven.apache.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("transactionResultFilter")
public class TransactionResultFilteringHandler implements SearchResultFilteringService<Transaction> {

    @Override
    public List<Transaction> doFilter(List<Transaction> sourceList, String inputText, SearchResultServiceHandler.ResultType resultType) {
        List<Transaction> resultList = new ArrayList<>();
        if (resultType == SearchResultServiceHandler.ResultType.STAFF) {
            sourceList.forEach(transaction -> {
                if (isContaining(transaction.getStaffName(), inputText)) {
                    resultList.add(transaction);
                }
            });
        } else if (resultType == SearchResultServiceHandler.ResultType.CARGO) {
            sourceList.forEach(transaction -> {
                if (isContaining(transaction.getItemName(), inputText)) {
                    resultList.add(transaction);
                }
            });
        }
        return resultList;
    }
}
