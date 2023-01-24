package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.maven.apache.App;
import org.maven.apache.utils.DataUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuPageController implements Initializable{

    @FXML
    private JFXButton signOffButton;

    @FXML
    private JFXButton settingButton;

    @FXML
    private ImageView settingImageView;

    @FXML
    private ImageView logOutImageView;

    @FXML
    private final Image onEnterLogOutImage = new Image(Objects.requireNonNull(AppPage2Controller.class.getResourceAsStream("/image/icons8-logout-64 (1).png")));

    @FXML
    private final Image onEnterSettingImage = new Image(Objects.requireNonNull(AppPage2Controller.class.getResourceAsStream("/image/icons8-settings-192.png")));

    @FXML
    private final Image onExitLogOutImage = new Image(Objects.requireNonNull(AppPage2Controller.class.getResourceAsStream("/image/icons8-logout-64.png")));

    @FXML
    private final Image onExitSettingImage = new Image(Objects.requireNonNull(AppPage2Controller.class.getResourceAsStream("/image/icons8-settings-384.png")));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingImageView.setImage(onExitSettingImage);
        logOutImageView.setImage(onExitLogOutImage);
    }

    @FXML
    private void onEnterSetting(){
        settingImageView.setImage(onEnterSettingImage);
    }

    @FXML
    private void onExitSetting(){
        settingImageView.setImage(onExitSettingImage);
    }

    @FXML
    private void onEnterLogOut(){
        logOutImageView.setImage(onEnterLogOutImage);
    }

    @FXML
    private void onExitLogOut(){
        logOutImageView.setImage(onExitLogOutImage);
    }


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

