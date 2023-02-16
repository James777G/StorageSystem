package org.maven.apache.service.search;

import org.maven.apache.item.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("itemSearchServiceHandlerAdapter")
public class ItemSearchServiceHandlerAdapter<R> implements AbstractSearchServiceAdapter<Item, R> {
    @Override
    public List<Item> doConvert(List<R> sourceList) {
        List<Item> itemList = new ArrayList<>();
        sourceList.forEach(r -> itemList.add((Item) r));
        return itemList;
    }
}
