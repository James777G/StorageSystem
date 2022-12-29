package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javafx.scene.control.Label;
import org.maven.apache.utils.TranslateUtils;

import java.net.URL;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setCursor(Cursor.HAND);
    }
}
