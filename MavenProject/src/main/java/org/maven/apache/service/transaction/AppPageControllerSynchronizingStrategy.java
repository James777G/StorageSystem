package org.maven.apache.service.transaction;

import javafx.application.Platform;
import org.maven.apache.controllers.AppPage2Controller;
import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.exception.NegativeDataException;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.utils.DataUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service("appPageControllerSynchronizer")
public final class AppPageControllerSynchronizingStrategy extends AbstractTransactionStrategy {

    @Override
    public void doStrategy(ItemMapper itemMapper, TransactionMapper transactionMapper, Transaction transaction){
        invokeAppPageControllerToUpdateCargo();
    }

    @Override
    public void doStrategy(ItemMapper itemMapper, TransactionMapper transactionMapper, int id){
        invokeAppPageControllerToUpdateCargo();
    }

    private void invokeAppPageControllerToUpdateCargo(){
        Class<AppPage2Controller> appPage2ControllerClass = AppPage2Controller.class;
        Method setLists = null;
        Method fillCargoBoxesInformation = null;
        try {
            setLists = appPage2ControllerClass.getDeclaredMethod("setLists");
            fillCargoBoxesInformation = appPage2ControllerClass.getDeclaredMethod("fillCargoBoxesInformation", AppPage2Controller.ButtonSelected.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }


        setLists.setAccessible(true);
        fillCargoBoxesInformation.setAccessible(true);

        Method finalSetLists = setLists;
        Method finalFillCargoBoxesInformation = fillCargoBoxesInformation;
        Platform.runLater(()->{
            try {
                finalSetLists.invoke(DataUtils.appPage2Controller);
                finalFillCargoBoxesInformation.invoke(DataUtils.appPage2Controller, DataUtils.buttonSelected);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });



    }
}
