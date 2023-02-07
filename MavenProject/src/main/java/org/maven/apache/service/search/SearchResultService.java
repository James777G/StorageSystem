package org.maven.apache.service.search;

import org.maven.apache.exception.UnsupportedPojoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface SearchResultService<R> extends SearchService{

    List<R> getResultList(List<List<R>> sourceList, String inputText, SearchResultServiceHandler.ResultType resultType) throws UnsupportedPojoException;

    default List<R> doConvert(List<List<R>> sourceList){
        List<R> resultList = new ArrayList<>();
        sourceList.forEach(resultList::addAll);
        return resultList;
    }

}
