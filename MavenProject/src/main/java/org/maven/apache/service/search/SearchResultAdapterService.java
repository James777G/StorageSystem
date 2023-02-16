package org.maven.apache.service.search;

import java.util.List;

public interface SearchResultAdapterService<X, R> extends SearchService {

    List<R> invoke(List<R> sourceList, String inputText, SearchResultServiceHandler.ResultType resultType);

}
