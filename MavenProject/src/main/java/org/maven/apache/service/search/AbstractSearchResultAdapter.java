package org.maven.apache.service.search;

import java.util.List;

public abstract sealed class AbstractSearchResultAdapter<X, R> implements SearchResultAdapterService<X, R> permits ItemSearchResultAdapter, TransactionSearchResultAdapter, StaffSearchResultAdapter {

    public abstract List<X> doConvert(List<R> sourceList);

    public abstract List<R> postConvert(List<X> loadedList);

    public abstract List<R> invoke(List<R> sourceList, String inputText, SearchResultServiceHandler.ResultType resultType);

}
