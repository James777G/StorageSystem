package org.maven.apache.service.staff;

import javafx.application.Platform;
import org.maven.apache.controllers.AppPage2Controller;
import org.maven.apache.utils.DataUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service("appPageSynchronizingStaffStrategy")
public final class AppPageSynchronizingStrategy extends AbstractStaffStrategy {
    @Override
    public void doStrategy() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        invoke();
    }

    private void invoke() throws NoSuchMethodException {
        Class<AppPage2Controller> clazz = AppPage2Controller.class;
        Method setPromptTextForStaff = clazz.getDeclaredMethod("setPromptTextForStaff");
        setPromptTextForStaff.setAccessible(true);
        Platform.runLater(() -> {
            try {
                setPromptTextForStaff.invoke(DataUtils.appPage2Controller);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
