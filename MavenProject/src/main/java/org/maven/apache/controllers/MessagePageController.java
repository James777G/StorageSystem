package org.maven.apache.controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.utils.TranslateUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class MessagePageController implements Initializable {

    @FXML
    private AnchorPane notePadPane1;

    @FXML
    private AnchorPane notePadPane2;

    @FXML
    private AnchorPane notePadPane3;

    @FXML
    private AnchorPane notePadPane4;

    @FXML
    private AnchorPane notePadPane5;

    @FXML
    private AnchorPane onMoveToMessagePane;

    @FXML
    private AnchorPane onMoveToStarredPane;

    @FXML
    private AnchorPane movingLinePane;

    private boolean isMovingLineOnMessage = true;

    private boolean isLineMoving = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onClickMessage(){
        if((!isMovingLineOnMessage) && (!isLineMoving)){
            isLineMoving = true;
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionByX(movingLinePane, 500, -120);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.setOnFinished(event -> {
                isLineMoving = false;
                isMovingLineOnMessage = true;
            });
            translateTransition.play();
        }
    }

    @FXML
    private void onClickStarred(){
        if((isMovingLineOnMessage) && (!isLineMoving)){
            isLineMoving = true;
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionByX(movingLinePane, 500, 120);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.setOnFinished(event -> {
                isLineMoving = false;
                isMovingLineOnMessage = false;
            });
            translateTransition.play();
        }
    }

}
