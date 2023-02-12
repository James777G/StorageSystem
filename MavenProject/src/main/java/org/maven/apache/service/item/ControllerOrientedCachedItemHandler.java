package org.maven.apache.service.item;

import jakarta.annotation.Resource;
import javafx.application.Platform;
import org.maven.apache.controllers.AppPage2Controller;
import org.maven.apache.controllers.NewTransactionPageController;
import org.maven.apache.item.Item;
import org.maven.apache.service.regulatory.RegulatoryService;
import org.maven.apache.service.transaction.CachedTransactionService;
import org.maven.apache.utils.CargoCachedUtils;
import org.maven.apache.utils.DataUtils;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
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

    @Resource
    private RegulatoryService regulatoryService;

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
            invokeAppPage2ControllerToUpdate();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteItemById(int id) {
        itemService.deleteById(id);
        updateAllCachedItemData();
        cachedTransactionService.updateAllCachedTransactionData();
        regulatoryService.updateAllRegulatoryData();
        try {
            invokeControllerToUpdate();
            invokeAppPage2ControllerToUpdate();
            invokeRegulatoryToUpdate();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void invokeControllerToUpdate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<NewTransactionPageController> clazz = NewTransactionPageController.class;
        Method refreshPage = clazz.getDeclaredMethod("refreshPage");
        refreshPage.setAccessible(true);
        Platform.runLater(() -> {
            try {
                refreshPage.invoke(DataUtils.transactionPageController);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void invokeRegulatoryToUpdate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<AppPage2Controller> clazz = AppPage2Controller.class;
        Method setCargoPageCount = clazz.getDeclaredMethod("setCargoPageCount");
        Method setCargoTable = clazz.getDeclaredMethod("setCargoTable", int.class);
        setCargoTable.setAccessible(true);
        setCargoPageCount.setAccessible(true);
        Platform.runLater(() -> {
            try {
                setCargoPageCount.invoke(DataUtils.appPage2Controller);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            try {
                setCargoTable.invoke(DataUtils.appPage2Controller, DataUtils.cargoPagination.getCurrentPageIndex());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void invokeAppPage2ControllerToUpdate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<AppPage2Controller> clazz = AppPage2Controller.class;
        Method fillCargoBoxesInformation = clazz.getDeclaredMethod("fillCargoBoxesInformation",
                AppPage2Controller.ButtonSelected.class);
        Method setLists = clazz.getDeclaredMethod("setLists");
        setLists.setAccessible(true);
        fillCargoBoxesInformation.setAccessible(true);
        setLists.invoke(DataUtils.appPage2Controller);
        Platform.runLater(() -> {
            try {
                fillCargoBoxesInformation.invoke(DataUtils.appPage2Controller, DataUtils.buttonSelected);
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
