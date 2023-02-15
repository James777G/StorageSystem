package org.maven.apache.service.staff;

import javafx.application.Platform;
import org.maven.apache.controllers.NewTransactionPageController;
import org.maven.apache.utils.DataUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service("transactionPageSynchronizingStaffStrategy")
public final class TransactionPageSynchronizingStrategy extends AbstractStaffStrategy {

    @Override
    public void doStrategy() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        invoke();
    }

    private void invoke() throws NoSuchMethodException {
        Class<NewTransactionPageController> clazz = NewTransactionPageController.class;
        Method setPromptTextForStaff = clazz.getDeclaredMethod("setPromptTextForStaff");
        setPromptTextForStaff.setAccessible(true);
        Platform.runLater( () -> {
            try {
                setPromptTextForStaff.invoke(DataUtils.transactionPageController);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
