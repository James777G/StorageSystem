package org.maven.apache.service.item;

import jakarta.annotation.Resource;
import org.maven.apache.item.Item;
import org.maven.apache.service.transaction.CachedTransactionService;
import org.maven.apache.utils.CargoCachedUtils;
import org.springframework.stereotype.Service;

@Service("cachedItemService")
public class ControllerOrientedCachedItemHandler implements CachedItemService{
    @Resource
    private ItemService itemService;

    @Resource
    private ItemDataManipulationService itemDataManipulationService;

    @Resource
    private CachedTransactionService cachedTransactionService;

    @Override
    public void updateAllCachedItemData() {
        CargoCachedUtils.putLists(CargoCachedUtils.listType.ALL,
                itemDataManipulationService.getPagedCacheList(itemService.selectAll(), 7));
    }

    @Override
    public void addNewItem(Item item) {
        itemService.addNewItem(item);
        updateAllCachedItemData();
        cachedTransactionService.updateAllCachedTransactionData();
    }

    @Override
    public void deleteItemById(int id) {
        itemService.deleteById(id);
        updateAllCachedItemData();
        cachedTransactionService.updateAllCachedTransactionData();
    }

    private void invokeControllerToUpdate(){

    }

    @Override
    public void updateItem(Item item) {
        itemService.update(item);
        updateAllCachedItemData();
    }
}
