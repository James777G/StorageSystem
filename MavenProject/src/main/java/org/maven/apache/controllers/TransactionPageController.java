package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXPagination;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.MyLauncher;
import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.service.DateTransaction.DateTransactionService;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.ScaleUtils;
import org.maven.apache.utils.TransitionUtils;
import org.maven.apache.utils.TranslateUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class TransactionPageController implements Initializable {

    enum ButtonSelected {
        ALL,
        TAKEN,
        RESTOCK
    }

    private ButtonSelected buttonSelected = ButtonSelected.ALL;

    private boolean isMovingLineOnData = false;

    private boolean isRunning = false;

    private final DateTransactionService dateTransactionService = MyLauncher.context.getBean("dateTransactionService", DateTransactionService.class);

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
    private Label staffLabel1, staffLabel2, staffLabel3, staffLabel4;

    @FXML
    private Label orderLabel1, orderLabel2, orderLabel3, orderLabel4; //order ID

    @FXML
    private Label amountLabel1, amountLabel2, amountLabel3, amountLabel4;

    @FXML
    private Label dateLabel1, dateLabel2, dateLabel3, dateLabel4;

    @FXML
    private AnchorPane dataTransactionPage;

    @FXML
    private AnchorPane cargoPage;

    @FXML
    private MFXPagination transactionPagination;

    @FXML
    private ImageView sortByAmount;

    @FXML
    private ImageView sortByDate;

    private Label[] staffLabelArray = new Label[4];

    private Label[] orderLabelArray = new Label[4];
    ;

    private Label[] amountLabelArray = new Label[4];
    ;

    private Label[] dateLabelArray = new Label[4];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setCursor(Cursor.HAND);
        initializeLabels();
        setPaginationPages();
        setTransactionList(1);
        currentPage = cargoPage;
        dataTransactionPage.getChildren().add(DataUtils.publicDataPane);
        dataTransactionPage.setPickOnBounds(false);
        dataTransactionPage.setOpacity(0);
        dataTransactionPage.setVisible(false);

    }

    private Node currentPage;

    @FXML
    private void onMoveToData() {
        if (!isMovingLineOnData && !isRunning) {
            isRunning = true;
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionOnX(movingLinePane, 500, 105);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.setOnFinished(event -> {
                isRunning = false;
                isMovingLineOnData = true;
            });
            translateTransition.play();
            if (currentPage != dataTransactionPage) {
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
    private void onMoveToCargo() {
        if (isMovingLineOnData && !isRunning) {
            isRunning = true;
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionOnX(movingLinePane, 500, -105);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.setOnFinished(event -> {
                isRunning = false;
                isMovingLineOnData = false;
            });
            translateTransition.play();
            if (currentPage != cargoPage) {
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
    private void onClickAllSelectButton() {
        switch (buttonSelected) {
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
    private void onClickTakenSelectButton() {
        switch (buttonSelected) {
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
    private void onClickRestockSelectButton() {
        switch (buttonSelected) {
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
    private void onEnterAddButton() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionBy(addButton, 500, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitAddButton() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionBy(addButton, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

        /**
         * initialize the labels which can be stored in arrays
         */
        private void initializeLabels () {
            // initialize staff labels
            staffLabelArray[0] = staffLabel1;
            staffLabelArray[1] = staffLabel2;
            staffLabelArray[2] = staffLabel3;
            staffLabelArray[3] = staffLabel4;
            // initialize order id labels
            orderLabelArray[0] = orderLabel1;
            orderLabelArray[1] = orderLabel2;
            orderLabelArray[2] = orderLabel3;
            orderLabelArray[3] = orderLabel4;
            // initialize amount labels
            amountLabelArray[0] = amountLabel1;
            amountLabelArray[1] = amountLabel2;
            amountLabelArray[2] = amountLabel3;
            amountLabelArray[3] = amountLabel4;
            // initialize record time labels
            dateLabelArray[0] = dateLabel1;
            dateLabelArray[1] = dateLabel2;
            dateLabelArray[2] = dateLabel3;
            dateLabelArray[3] = dateLabel4;
        }

        /**
         * set how many pages do we need in total
         */
        private void setPaginationPages () {
            int numOfPages;
            List<DateTransaction> transactionList = dateTransactionService.selectAll();
            if ((transactionList.size() % 4) != 0) {
                // if the list does not contain exact number of pages
                numOfPages = (transactionList.size() / 4) + 1;
            } else {
                // if the list contains exact number of pages
                numOfPages = transactionList.size() / 4;
            }
            transactionPagination.setMaxPage(numOfPages);
        }

        /**
         * get the current page number when pagination is clicked
         */
        @FXML
        private void onClickPagination () {
            int currentPage = transactionPagination.getCurrentPage();
            ExecutorService threadPoolExecutor = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);
            threadPoolExecutor.execute(() -> setTransactionList(currentPage));
        }

        /**
         * set the transaction list with no order
         */
        private void setTransactionList ( int currentPage){
            List<DateTransaction> list = dateTransactionService.pageAskedNOOrder(currentPage, 4);
            Platform.runLater(() -> {
                for (int i = 0; i < list.size(); i++) {
                    staffLabelArray[i].setText(list.get(i).getStaffName());
                    orderLabelArray[i].setText(String.valueOf(list.get(i).getItemID()));
                    amountLabelArray[i].setText(String.valueOf(list.get(i).getCurrentUnit()));
                    dateLabelArray[i].setText(list.get(i).getRecordTime());
                }
            });
        }

        @FXML
        private void onEnterAmount () {
            sortByAmount.setScaleX(2);
            sortByAmount.setScaleY(2);
        }

        @FXML
        private void onExitAmount () {
            sortByAmount.setScaleX(1);
            sortByAmount.setScaleY(1);
        }

        @FXML
        private void onPressedAmount () {
            sortByAmount.setScaleX(1.5);
            sortByAmount.setScaleY(1.5);
        }

        @FXML
        private void onReleaseAmount () {
            sortByAmount.setScaleX(2);
            sortByAmount.setScaleY(2);
        }

        @FXML
        private void onEnterDate () {
            sortByDate.setScaleX(2);
            sortByDate.setScaleY(2);
        }

        @FXML
        private void onExitDate () {
            sortByDate.setScaleX(1);
            sortByDate.setScaleY(1);
        }

        @FXML
        private void onPressedDate () {
            sortByDate.setScaleX(1.5);
            sortByDate.setScaleY(1.5);
        }

        @FXML
        private void onReleaseDate () {
            sortByDate.setScaleX(2);
            sortByDate.setScaleY(2);
        }

}
