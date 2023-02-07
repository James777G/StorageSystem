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
public class SearchResultServiceHandler<R> implements SearchResultService<R>{

    public enum ResultType{
        STAFF, CARGO
    }



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
    public List<R> getResultList(List<List<R>> sourceList, String inputText, ResultType resultType) throws UnsupportedPojoException {
        if(sourceList.size() > 0){
            if(isItem(sourceList)){
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

    private boolean isItem(List<List<R>> sourceList){
        return doConvert(sourceList).get(0) instanceof Item;
    }

    private boolean isStaff(List<List<R>> sourceList){
        return doConvert(sourceList).get(0) instanceof Staff;
    }

    private boolean isTransaction(List<List<R>> sourceList){
        return doConvert(sourceList).get(0) instanceof Transaction;
    }

}
