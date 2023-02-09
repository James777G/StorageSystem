package org.maven.apache.service.transaction;

import javafx.application.Platform;
import org.maven.apache.controllers.WarehouseController;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.utils.DataUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service("warehouseStrategy")
public final class WarehouseControllerSynchronizingStrategy extends AbstractTransactionStrategy {

    @Override
    public void doStrategy(ItemMapper itemMapper, TransactionMapper transactionMapper, Transaction transaction) {
        executeGenerateItemList();
        executeSetTableContents();
    }

    @Override
    public void doStrategy(ItemMapper itemMapper, TransactionMapper transactionMapper, int id) {
        executeGenerateItemList();
        executeSetTableContents();
    }

    private void executeGenerateItemList() {
        Class<WarehouseController> clazz = WarehouseController.class;
        Method generateItemList = null;
        try{
            generateItemList = clazz.getDeclaredMethod("generateItemList", int.class);
        }catch(Exception ignored){
        }
        assert generateItemList != null;
        generateItemList.setAccessible(true);
        Method finalGenerateItemList = generateItemList;
        Platform.runLater(() -> {
            try {
                finalGenerateItemList.invoke(DataUtils.warehouseController, DataUtils.pagination.getCurrentPageIndex());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void executeSetTableContents() {
        Class<WarehouseController> clazz = WarehouseController.class;
        Method setTableContents = null;
        try{
            setTableContents = clazz.getDeclaredMethod("setTableContents");
        }catch(Exception ignored){
        }
        assert setTableContents != null;
        setTableContents.setAccessible(true);
        Method finalSetTableContents = setTableContents;
        Platform.runLater(() -> {
            try {
                finalSetTableContents.invoke(DataUtils.warehouseController);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
