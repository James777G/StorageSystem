package org.maven.apache.service.item;

import org.maven.apache.item.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("itemDataManipulationService")
public class CachedItemDataManipulationHandler implements ItemDataManipulationService{

    @Override
    public List<List<Item>> getPagedCacheList(List<Item> cachedList, int pageSize) {
        List<List<Item>> pagedCachedList = new ArrayList<>();
        int pageNumber = cachedList.size() / pageSize;
        for (int i = 0; i < pageNumber; i++) {
            List<Item> itemList = new ArrayList<>(4);
            for (int j = 0; j < pageSize; j++) {
                itemList.add(cachedList.get((i * pageSize) + j));
            }
            pagedCachedList.add(i, itemList);
        }
        if (cachedList.size() % pageSize != 0) {
            List<Item> itemList = new ArrayList<>();
            for (int i = 0; i < cachedList.size() % pageSize; i++) {
                itemList.add(i, cachedList.get(cachedList.size() - cachedList.size() % pageSize + i));
            }
            pagedCachedList.add(itemList);
        }
        return pagedCachedList;
    }
}
