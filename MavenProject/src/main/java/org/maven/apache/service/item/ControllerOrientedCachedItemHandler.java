package org.maven.apache.service.item;

import jakarta.annotation.Resource;
import javafx.application.Platform;
import org.maven.apache.controllers.NewTransactionPageController;
import org.maven.apache.item.Item;
import org.maven.apache.service.transaction.CachedTransactionService;
import org.maven.apache.utils.CargoCachedUtils;
import org.maven.apache.utils.DataUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        try {
            invokeControllerToUpdate();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteItemById(int id) {
        itemService.deleteById(id);
        updateAllCachedItemData();
        cachedTransactionService.updateAllCachedTransactionData();
        try {
            invokeControllerToUpdate();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void invokeControllerToUpdate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<NewTransactionPageController> clazz = NewTransactionPageController.class;
        Method updatePagination = clazz.getDeclaredMethod("updatePagination", Number.class);
        Method refreshPage = clazz.getDeclaredMethod("refreshPage");
        refreshPage.setAccessible(true);
        updatePagination.setAccessible(true);
        Platform.runLater(() -> {
            try {
                updatePagination.invoke(DataUtils.transactionPageController, DataUtils.transactionPagination.getCurrentPageIndex());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            try {
                refreshPage.invoke(DataUtils.transactionPageController);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Override
    public void updateItem(Item item) {
        itemService.update(item);
        updateAllCachedItemData();
    }
}
