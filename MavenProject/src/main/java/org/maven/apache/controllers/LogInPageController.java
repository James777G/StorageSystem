package org.maven.apache.controllers;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import org.maven.apache.utils.TransitionUtils;
import org.springframework.context.ApplicationContext;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInPageController implements Initializable {

    @FXML
    private ImageView exitButton;

    @FXML
    private AnchorPane signUpPane;

    @FXML
    private AnchorPane signInPane;

    @FXML
    private ImageView exitButton2;

    @FXML
    private Label labelOnSignUp;

    @FXML
    private Label labelOnSignIn;

    @FXML
    private Line lineOnSignIn;

    @FXML
    private Line lineOnSignUp;

    @FXML
    private ImageView imageOnStorage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signInPane.setOpacity(1);
        signUpPane.setOpacity(0);
        signUpPane.setVisible(false);
        signUpPane.setPickOnBounds(false);
        labelOnSignUp.setCursor(Cursor.HAND);
        labelOnSignIn.setCursor(Cursor.HAND);
        lineOnSignIn.setVisible(false);
        lineOnSignUp.setVisible(false);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(imageOnStorage, 3000, 0, 1);
        fadeTransition.play();

    }

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
        signUpPane.setVisible(true);
        signUpPane.setPickOnBounds(true);
        signInPane.setPickOnBounds(false);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(signInPane, 300, 1, 0);
        FadeTransition fadeTransition1 = TransitionUtils.getFadeTransition(signUpPane, 300, 0, 1);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            fadeTransition1.play();
            signInPane.setVisible(false);
        });
    }

    @FXML
    private void onSignIn(){
        signInPane.setVisible(true);
        signInPane.setPickOnBounds(true);
        signUpPane.setPickOnBounds(false);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(signUpPane, 300, 1, 0);
        FadeTransition fadeTransition1 = TransitionUtils.getFadeTransition(signInPane, 300, 0, 1);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            fadeTransition1.play();
            signUpPane.setVisible(false);
        });

    }

    @FXML
    private void onEnterLabelSignIn(){
        lineOnSignIn.setVisible(true);
    }

    @FXML
    private void onExitLabelSignIn(){
        lineOnSignIn.setVisible(false);
    }

    @FXML
    private void onEnterLabelSignUp(){
        lineOnSignUp.setVisible(true);
    }

    @FXML
    private void onExitLabelSignUp(){
        lineOnSignUp.setVisible(false);
    }

    @FXML
    private void onEnterImage(){
        Bloom bloom = new Bloom(0.2);
        imageOnStorage.setEffect(bloom);
    }

    @FXML
    private void onExitImage(){
        imageOnStorage.setEffect(null);
    }




}
