package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javafx.scene.control.Label;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.ScaleUtils;
import org.maven.apache.utils.TransitionUtils;
import org.maven.apache.utils.TranslateUtils;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TransactionPageController implements Initializable {

    enum ButtonSelected {
        ALL,
        TAKEN,
        RESTOCK
    }

    private ButtonSelected buttonSelected = ButtonSelected.ALL;
    
    private boolean isMovingLineOnData = false;

//    private final DateTransactionService dateTransactionService = MyLauncher.context.getBean("dateTransactionService", DateTransactionService.class);
//
//    private List<DateTransaction> transactionList = dateTransactionService.selectAll();

    @FXML
    private AnchorPane movingLinePane;

    @FXML
    private AnchorPane cargoPane;

    @FXML
    private AnchorPane dataPane;

    @FXML
    private AnchorPane addButton;

    @FXML
    private AnchorPane onAllSelectPane;

    @FXML
    private AnchorPane onTakenSelectPane;

    @FXML
    private AnchorPane onRestockSelectPane;


    @FXML
    private JFXButton allSelectButton;

    @FXML
    private JFXButton takenSelectButton;

    @FXML
    private JFXButton restockSelectButton;

    @FXML
    private Label orderLabel1, orderLabel2, orderLabel3, orderLabel4;

    @FXML
    private Label amountLabel1, amountLabel2, amountLabel3, amountLabel4;

    @FXML
    private Label dateLabel1, dateLabel2, dateLabel3, dateLabel4;

    @FXML
    private AnchorPane dataTransactionPage;

    @FXML
    private AnchorPane cargoPage;

    @FXML
    private Label[] orderLabelArray = {orderLabel1, orderLabel2, orderLabel3, orderLabel4};

    @FXML
    private Label[] amountLabelArray = {amountLabel1, amountLabel2, amountLabel3, amountLabel4};

    @FXML
    private Label[] dateLabelArray = {dateLabel1, dateLabel2, dateLabel3, dateLabel4};

    private boolean isRunning = false;

    private Node currentPage;

    @FXML
    private void onMoveToData(){
        if (!isMovingLineOnData && !isRunning){
            isRunning = true;
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionOnX(movingLinePane,500,105);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.setOnFinished(event -> {
                isRunning = false;
                isMovingLineOnData = true;
            });
            translateTransition.play();
            if(currentPage != dataTransactionPage){
                currentPage = dataTransactionPage;
                FadeTransition fadeTransitionForCargo = TransitionUtils.getFadeTransition(cargoPage, 100, 1, 0);
                FadeTransition fadeTransitionForData = TransitionUtils.getFadeTransition(dataTransactionPage, 100, 0, 1);
                fadeTransitionForCargo.setOnFinished(event -> {
                    cargoPage.setVisible(false);
                    cargoPage.setPickOnBounds(false);
                    dataTransactionPage.setVisible(true);
                    dataTransactionPage.setPickOnBounds(true);
                    fadeTransitionForData.play();
                });
                fadeTransitionForCargo.play();

            }
        }

    }

    @FXML
    private void onMoveToCargo(){
        if(isMovingLineOnData && !isRunning ){
            isRunning = true;
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionOnX(movingLinePane,500,-105);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.setOnFinished(event -> {
                isRunning = false;
                isMovingLineOnData = false;
            });
            translateTransition.play();
            if(currentPage != cargoPage){
                currentPage = cargoPage;
                FadeTransition fadeTransitionForData = TransitionUtils.getFadeTransition(dataTransactionPage, 100, 1, 0);
                FadeTransition fadeTransitionForCargo = TransitionUtils.getFadeTransition(cargoPage, 100, 0, 1);
                fadeTransitionForData.setOnFinished(event -> {
                    cargoPage.setPickOnBounds(true);
                    cargoPage.setVisible(true);
                    dataTransactionPage.setVisible(false);
                    dataTransactionPage.setPickOnBounds(false);
                    fadeTransitionForCargo.play();
                });
                fadeTransitionForData.play();
            }
        }
    }

    @FXML
    private void onClickAllSelectButton(){
        switch (buttonSelected){
            case ALL:
                break;
            case TAKEN:
                onTakenSelectPane.setVisible(false);
                onAllSelectPane.setVisible(true);
                buttonSelected = ButtonSelected.ALL;
                break;
            case RESTOCK:
                onRestockSelectPane.setVisible(false);
                onAllSelectPane.setVisible(true);
                buttonSelected = ButtonSelected.ALL;
                break;
        }
    }

    @FXML
    private void onClickTakenSelectButton(){
        switch (buttonSelected){
            case ALL:
                onAllSelectPane.setVisible(false);
                onTakenSelectPane.setVisible(true);
                buttonSelected = ButtonSelected.TAKEN;
                break;
            case TAKEN:
                break;
            case RESTOCK:
                onRestockSelectPane.setVisible(false);
                onTakenSelectPane.setVisible(true);
                buttonSelected = ButtonSelected.TAKEN;
                break;
        }
    }

    @FXML
    private void onClickRestockSelectButton(){
        switch (buttonSelected){
            case ALL:
                onAllSelectPane.setVisible(false);
                onRestockSelectPane.setVisible(true);
                buttonSelected = ButtonSelected.RESTOCK;
                break;
            case TAKEN:
                onTakenSelectPane.setVisible(false);
                onRestockSelectPane.setVisible(true);
                buttonSelected = ButtonSelected.RESTOCK;
                break;
            case RESTOCK:
                break;
        }
    }

    @FXML
    private void onEnterAddButton(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionBy(addButton,500,1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitAddButton(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionBy(addButton,500,1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentPage = cargoPage;
        dataTransactionPage.getChildren().add(DataUtils.publicDataPane);
        addButton.setCursor(Cursor.HAND);
        dataTransactionPage.setPickOnBounds(false);
        dataTransactionPage.setOpacity(0);
        dataTransactionPage.setVisible(false);
    }
}
