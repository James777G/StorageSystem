package org.maven.apache.service.search;

import java.util.List;

public interface SearchResultFilteringService<R> extends SearchService {

    List<R> doFilter(List<R> sourceList, String inputText, SearchResultServiceHandler.ResultType resultType);

    default boolean isContaining(String sourceValue, String inputText) {
        try {
            if (sourceValue.contains(inputText)) {
                return true;
            } else if (sourceValue.toLowerCase().contains(inputText.toLowerCase())) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
