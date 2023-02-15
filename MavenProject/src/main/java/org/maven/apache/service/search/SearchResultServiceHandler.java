package org.maven.apache.service.search;

import jakarta.annotation.Resource;
import org.maven.apache.exception.UnsupportedPojoException;
import org.maven.apache.item.Item;
import org.maven.apache.staff.Staff;
import org.maven.apache.transaction.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("searchResultService")
public class SearchResultServiceHandler<R> implements SearchResultService<R> {

    @Resource
    @Qualifier("staffResultAdapter")
    private SearchResultAdapterService<Staff, R> staffResultAdapter;
    @Resource
    @Qualifier("transactionResultAdapter")
    private SearchResultAdapterService<Transaction, R> transactionResultAdapter;
    @Resource
    @Qualifier("itemResultAdapter")
    private SearchResultAdapterService<Item, R> itemResultAdapter;

    @Override
    public List<List<R>> getPagedResultList(List<List<R>> sourceList, String inputText, ResultType resultType) throws UnsupportedPojoException {
        return getPagedList(getResultList(sourceList, inputText, resultType), 7);
    }

    @Override
    public List<R> getResultList(List<List<R>> sourceList, String inputText, ResultType resultType) throws UnsupportedPojoException {
        if (sourceList.size() > 0) {
            if (isItem(sourceList)) {
                return itemResultAdapter.invoke(doConvert(sourceList), inputText, resultType);
            } else if (isStaff(sourceList)) {
                return staffResultAdapter.invoke(doConvert(sourceList), inputText, resultType);
            } else if (isTransaction(sourceList)) {
                return transactionResultAdapter.invoke(doConvert(sourceList), inputText, resultType);
            } else if (doConvert(sourceList).get(0) == null) {
                return new ArrayList<R>();
            } else {
                throw new UnsupportedPojoException("Classes other than transaction, staff and item are unsupported");
            }
        } else {
            return new ArrayList<>();
        }
    }

    private List<List<R>> getPagedList(List<R> resultList, int pageSize) {
        List<List<R>> pagedCachedList = new ArrayList<>();
        int pageNumber = resultList.size() / pageSize;
        for (int i = 0; i < pageNumber; i++) {
            List<R> rList = new ArrayList<R>(4);
            for (int j = 0; j < pageSize; j++) {
                rList.add(resultList.get((i * pageSize) + j));
            }
            pagedCachedList.add(i, rList);
        }
        if (resultList.size() % pageSize != 0) {
            List<R> rList = new ArrayList<R>();
            for (int i = 0; i < resultList.size() % pageSize; i++) {
                rList.add(i, resultList.get(resultList.size() - resultList.size() % pageSize + i));
            }
            pagedCachedList.add(rList);
        }
        return pagedCachedList;
    }

    private boolean isItem(List<List<R>> sourceList) {
        return doConvert(sourceList).get(0) instanceof Item;
    }

    private boolean isStaff(List<List<R>> sourceList) {
        return doConvert(sourceList).get(0) instanceof Staff;
    }

    private boolean isTransaction(List<List<R>> sourceList) {
        return doConvert(sourceList).get(0) instanceof Transaction;
    }

    public enum ResultType {
        STAFF, CARGO
    }

}
