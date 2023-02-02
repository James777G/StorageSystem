package org.maven.apache.service.search;

import org.maven.apache.item.Item;
import org.maven.apache.utils.CargoCachedUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemAdapterConverterAdapter implements SearchAdapterService<Item> {

    private static CargoCachedUtils.listType DEFAULT_SOURCE_TYPE = CargoCachedUtils.listType.ALL;

    private void customizeListType(CargoCachedUtils.listType listType){
        DEFAULT_SOURCE_TYPE = listType;
    }

    @Override
    public List<String> doConvert(List<Item> sourceList) {
        return null;
    }

    @Override
    public List<String> doConvert() {
        List<List<Item>> lists = CargoCachedUtils.getLists(DEFAULT_SOURCE_TYPE);
        List<String> resultList = new ArrayList<>();
        lists.forEach(items -> items.forEach(item -> resultList.add(item.getItemName())));
        return resultList;
    }


}
