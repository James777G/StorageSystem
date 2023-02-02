package org.maven.apache.service.search;

import org.maven.apache.item.Item;
import org.maven.apache.utils.CargoCachedUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class ItemAdapterConverterAdapter implements SearchAdapterService<Item> {

    private static final CargoCachedUtils.listType DEFAULT_SOURCE_TYPE = CargoCachedUtils.listType.ALL;

    @Override
    public List<String> doConvert(List<Item> sourceList) {
        return null;
    }

    @Override
    public List<String> doConvert() {
        List<List<Item>> lists = CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL);
        List<String> resultList = new ArrayList<>();
        lists.forEach(items -> items.forEach(item -> resultList.add(item.getItemName())));
        return resultList;
    }


}
