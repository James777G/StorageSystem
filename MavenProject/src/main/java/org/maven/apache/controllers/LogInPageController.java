package org.maven.apache.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.springframework.context.ApplicationContext;

public class LogInPageController {

    @FXML
    private ImageView exitButton;

    @FXML
    private AnchorPane signUpPane;

    @FXML
    private AnchorPane signInPane;

    @FXML
    private ImageView exitButton2;

    @FXML
    private void onExit(){
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void onExit2(){
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void onEnterExitButton(){
        exitButton.setScaleX(1.2);
        exitButton.setScaleY(1.2);
    }

    @FXML
    private void onEnterExitButton2(){
        exitButton2.setScaleX(1.2);
        exitButton2.setScaleY(1.2);
    }

    @FXML
    private void onLeaveExitButton2(){
        exitButton2.setScaleX(1);
        exitButton2.setScaleY(1);
    }

    @FXML
    private void onPressExitButton2(){
        exitButton2.setScaleX(0.8);
        exitButton2.setScaleY(0.8);
    }

    @FXML
    private void onReleaseExitButton2(){
        exitButton2.setScaleX(1);
        exitButton2.setScaleY(1);
    }

    @FXML
    private void onLeaveExitButton(){
        exitButton.setScaleX(1);
        exitButton.setScaleY(1);
    }

    @FXML
    private void onPressExitButton(){
        exitButton.setScaleX(0.8);
        exitButton.setScaleY(0.8);
    }

    @FXML
    private void onReleaseExitButton(){
        exitButton.setScaleX(1);
        exitButton.setScaleY(1);
    }

    @FXML
    private void onSignUp(){
        signInPane.setVisible(false);
        signUpPane.setVisible(true);
        signUpPane.setOpacity(1);
        signInPane.setPickOnBounds(false);
    }

}
