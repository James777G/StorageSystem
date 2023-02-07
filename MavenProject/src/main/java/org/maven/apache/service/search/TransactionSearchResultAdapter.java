package org.maven.apache.service.search;

import jakarta.annotation.Resource;
import org.maven.apache.transaction.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service("transactionResultAdapter")
public final class TransactionSearchResultAdapter<R> extends AbstractSearchResultAdapter<Transaction, R> {

    @Resource
    @Qualifier("transactionResultFilter")
    private SearchResultFilteringService<Transaction> transactionResultFilter;

    @Override
    public List<Transaction> doConvert(List<R> sourceList) {
        return sourceList.stream()
                .map(r -> (Transaction) r).toList();
    }

    @Override
    @SuppressWarnings("all")
    public List<R> postConvert(List<Transaction> loadedList) {
        return loadedList.stream().map(transaction -> (R) transaction).toList();
    }


    @Override
    public List<R> invoke(List<R> sourceList, String inputText, SearchResultServiceHandler.ResultType resultType) {
        return postConvert(transactionResultFilter.doFilter(doConvert(sourceList),inputText,resultType));
    }
}
