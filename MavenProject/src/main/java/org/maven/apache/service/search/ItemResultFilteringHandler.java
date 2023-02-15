package org.maven.apache.service.search;

import org.maven.apache.item.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("itemResultFilter")
public class ItemResultFilteringHandler implements SearchResultFilteringService<Item> {


    @Override
    public List<Item> doFilter(List<Item> sourceList, String inputText, SearchResultServiceHandler.ResultType resultType) {
        List<Item> resultList = new ArrayList<>();
        sourceList.forEach(item -> {
            if (isContaining(item.getItemName(), inputText)) {
                resultList.add(item);
            }
        });
        return resultList;
    }
}
