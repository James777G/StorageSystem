package org.maven.apache.service.transaction;

import javafx.application.Platform;
import org.maven.apache.controllers.NewTransactionPageController;
import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.exception.NegativeDataException;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.utils.DataUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service("transactionPageControllerSynchronizer")
public final class TransactionPageControllerSynchronizingStrategy extends AbstractTransactionStrategy {

    @Override
    public void doStrategy(ItemMapper itemMapper, TransactionMapper transactionMapper, Transaction transaction) throws DataNotFoundException, NegativeDataException {
        invokeTransactionControllerToUpdate();
    }

    @Override
    public void doStrategy(ItemMapper itemMapper, TransactionMapper transactionMapper, int id) throws NegativeDataException {
        invokeTransactionControllerToUpdate();
    }

    private void invokeTransactionControllerToUpdate(){
        Class<NewTransactionPageController> newTransactionPageControllerClass = NewTransactionPageController.class;
        Method refreshPage = null;
        try {
            refreshPage = newTransactionPageControllerClass.getDeclaredMethod("refreshPage");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        refreshPage.setAccessible(true);

        Method finalRefreshPage = refreshPage;
        Platform.runLater(()->{
            try {
                finalRefreshPage.invoke(DataUtils.transactionPageController);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

