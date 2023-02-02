package org.maven.apache.service.search;

import java.util.List;

public interface SearchAdapterService<R> extends SearchService{

    List<String> doConvert(List<R> sourceList);
}
