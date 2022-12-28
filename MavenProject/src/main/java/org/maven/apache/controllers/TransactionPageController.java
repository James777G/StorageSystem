package org.maven.apache.controllers;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javafx.scene.control.Label;
import org.maven.apache.utils.TranslateUtils;

public class TransactionPageController {

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
    private Label orderLabel1, orderLabel2, orderLabel3, orderLabel4;

    @FXML
    private Label amountLabel1, amountLabel2, amountLabel3, amountLabel4;

    @FXML
    private Label dateLabel1, dateLabel2, dateLabel3, dateLabel4;

    @FXML
    private Label[] orderLabelArray = {orderLabel1, orderLabel2, orderLabel3, orderLabel4};

    @FXML
    private Label[] amountLabelArray = {amountLabel1, amountLabel2, amountLabel3, amountLabel4};

    @FXML
    private Label[] dateLabelArray = {dateLabel1, dateLabel2, dateLabel3, dateLabel4};

    private boolean isRunning = false;

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

        }
    }


}
