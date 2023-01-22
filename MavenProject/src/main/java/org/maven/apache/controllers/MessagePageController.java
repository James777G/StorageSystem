package org.maven.apache.controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.utils.TranslateUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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

    @FXML
    private  final AnchorPane editMessagePane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/editMessagePage.fxml")));

    private boolean isMovingLineOnMessage = true;

    private boolean isLineMoving = false;

    public MessagePageController() throws IOException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void enableNode(Node node){
        node.setOpacity(1);
        node.setVisible(true);
        node.setPickOnBounds(true);
    }

    @FXML
    private void disable(Node node){
        node.setOpacity(0);
        node.setVisible(false);
        node.setPickOnBounds(false);
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

    private void onClickNotePads(){

    }
}
