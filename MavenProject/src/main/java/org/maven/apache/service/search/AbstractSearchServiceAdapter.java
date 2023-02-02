package org.maven.apache.service.search;

import java.util.List;

public interface AbstractSearchServiceAdapter<S, R> extends SearchService{

    List<S> doConvert(List<R> sourceList);
}
