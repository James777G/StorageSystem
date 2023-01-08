package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.maven.apache.App;
import org.maven.apache.utils.DataUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuPageController{

    @FXML
    private JFXButton signOffButton;

    @FXML
    private void onSignOff() throws IOException {
        Stage stage = (Stage) signOffButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/logInPage.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onClickSettingsButton() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<AppPage2Controller> clazz = AppPage2Controller.class;
        Method onClickSettingsTwo = clazz.getDeclaredMethod("onClickSettingsTwo", MFXGenericDialog.class);
        onClickSettingsTwo.setAccessible(true);
        onClickSettingsTwo.invoke(clazz, DataUtils.publicSettingsDialog);
        DataUtils.publicSettingBlockPane.setVisible(true);
    }
}

