package org.maven.apache.service.search;

import jakarta.annotation.Resource;
import org.maven.apache.item.Item;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("itemResultAdapter")
public final class ItemSearchResultAdapter<R> extends AbstractSearchResultAdapter<Item, R> {

    @Resource
    @Qualifier("itemResultFilter")
    private SearchResultFilteringService<Item> itemResultFilter;

    @Override
    public List<Item> doConvert(List<R> sourceList) {
        return sourceList.stream()
                .map(r -> (Item) r).toList();
    }

    @Override
    public List<R> postConvert(List<Item> loadedList) {
        return loadedList.stream()
                .map(item -> (R) item).toList();
    }

    @Override
    public List<R> invoke(List<R> sourceList, String inputText, SearchResultServiceHandler.ResultType resultType) {
        return postConvert(itemResultFilter.doFilter(doConvert(sourceList), inputText, resultType));
    }
}
