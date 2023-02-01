package org.maven.apache.service.item;

import jakarta.annotation.Resource;
import org.maven.apache.item.Item;
import org.maven.apache.utils.CargoCachedUtils;
import org.springframework.stereotype.Service;

@Service("cachedItemService")
public class ControllerOrientedCachedItemHandler implements CachedItemService{
    @Resource
    private ItemService itemService;

    @Resource
    private ItemDataManipulationService itemDataManipulationService;

    @Override
    public void updateAllCachedItemData() {
        CargoCachedUtils.putLists(CargoCachedUtils.listType.All,
                itemDataManipulationService.getPagedCacheList(itemService.selectAll(), 7));
    }

    @Override
    public void addNewItem(Item item) {
        itemService.addNewItem(item);
        updateAllCachedItemData();
    }

    @Override
    public void deleteItemById(int id) {
        itemService.deleteById(id);
        updateAllCachedItemData();
    }

    @Override
    public void updateItem(Item item) {
        itemService.update(item);
        updateAllCachedItemData();
    }
}
