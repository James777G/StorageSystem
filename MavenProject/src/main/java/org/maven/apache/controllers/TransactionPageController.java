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
        ALL, TAKEN, RESTOCK
    }

    enum SortBy {
        ALL, DATEASCEND, DATEDESCEND, ALLASCEND, ALLDESCEND, RESTOCKASCEND, RESTOCKDESCEND, TAKENASCEND, TAKENDDESCEND
    }

    private Node currentPage;

    private ButtonSelected buttonSelected = ButtonSelected.ALL;

    private SortBy sortBy = SortBy.ALL;

    private boolean isMovingLineOnData = false;

    private boolean isRunning = false;

    private boolean isDateAscend = false;

    private boolean isAmountAscend = false;

    private boolean isAll = true;

    private boolean isRestock = false;

    private boolean isTaken = false;

    private boolean isRestockAscend = false;

    private boolean isTakenAscend = false;

    private final DateTransactionService dateTransactionService = MyLauncher.context.getBean("dateTransactionService", DateTransactionService.class);

    private List<DateTransaction> sortedList;

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
    private Label idLabel1, idLabel2, idLabel3, idLabel4; //order ID

    @FXML
    private Label amountLabel1, amountLabel2, amountLabel3, amountLabel4;

    @FXML
    private Label dateLabel1, dateLabel2, dateLabel3, dateLabel4;

    @FXML
    private Label statusLabel1, statusLabel2, statusLabel3, statusLabel4;

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

    @FXML
    private AnchorPane editCargoPane;

    private Label[] staffLabelArray = new Label[4];

    private Label[] idLabelArray = new Label[4];

    private Label[] amountLabelArray = new Label[4];

    private Label[] dateLabelArray = new Label[4];

    private Label[] statusLabelArray = new Label[4];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editCargoPane.getChildren().add(DataUtils.editCargoPane);
        editCargoPane.setOpacity(0);
        editCargoPane.setPickOnBounds(false);
        editCargoPane.setVisible(false);
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
    private void onClickAddButton() {
        editCargoPane.setOpacity(1);
        editCargoPane.setPickOnBounds(true);
        editCargoPane.setVisible(true);
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
        isAll = true;
        isRestock = false;
        isTaken = false;
        onClickPagination();
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
        isAll = false;
        isRestock = false;
        isTaken = true;
        onClickPagination();
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
        isAll = false;
        isRestock = true;
        isTaken = false;
        onClickPagination();
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
    private void initializeLabels() {
        // initialize staff labels
        staffLabelArray[0] = staffLabel1;
        staffLabelArray[1] = staffLabel2;
        staffLabelArray[2] = staffLabel3;
        staffLabelArray[3] = staffLabel4;
        // initialize transaction id labels
        idLabelArray[0] = idLabel1;
        idLabelArray[1] = idLabel2;
        idLabelArray[2] = idLabel3;
        idLabelArray[3] = idLabel4;
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
        // initialize status labels
        statusLabelArray[0] = statusLabel1;
        statusLabelArray[1] = statusLabel2;
        statusLabelArray[2] = statusLabel3;
        statusLabelArray[3] = statusLabel4;
    }

    /**
     * set how many pages do we need in total
     */
    private void setPaginationPages() {
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
     * set the sorting property
     */
    private void setSortCondition(int currentPage){
        switch (sortBy) {
            case ALL:
                sortedList = dateTransactionService.pageAskedNOOrder(currentPage, 4);
                break;
            case DATEASCEND:
                sortedList = dateTransactionService.pageAskedDateAscend(currentPage, 4);
                break;
            case DATEDESCEND:
                sortedList = dateTransactionService.pageAskedDateDescend(currentPage, 4);
                break;
            case ALLASCEND:
                sortedList = dateTransactionService.pageAskedCurrentUnitAscend(currentPage, 4);
                break;
            case ALLDESCEND:
                sortedList = dateTransactionService.pageAskedCurrentUnitDescend(currentPage, 4);
                break;
            case RESTOCKASCEND:
                sortedList = dateTransactionService.pageAskedAddUnitAscend(currentPage, 4);
                break;
            case RESTOCKDESCEND:
                sortedList = dateTransactionService.pageAskedAddUnitDescend(currentPage, 4);
                break;
            case TAKENASCEND:
                sortedList = dateTransactionService.pageAskedRemoveUnitAscend(currentPage, 4);
                break;
            case TAKENDDESCEND:
                sortedList = dateTransactionService.pageAskedRemoveUnitDescend(currentPage, 4);
                break;
        }
    }

    /**
     * set the transaction list by a specific property
     */
    private void setTransactionList(int currentPage) {
        setSortCondition(currentPage);
        Platform.runLater(() -> {
            // set non-empty labels
            for (int i = 0; i < sortedList.size(); i++) {
                staffLabelArray[i].setText(sortedList.get(i).getStaffName());
                idLabelArray[i].setText(String.valueOf(sortedList.get(i).getItemID()));
                if (isAll && !isRestock && !isTaken){
                    amountLabelArray[i].setText(String.valueOf(sortedList.get(i).getCurrentUnit()));
                }else if (!isAll && isRestock && !isTaken){
                    amountLabelArray[i].setText(String.valueOf(sortedList.get(i).getAddUnit()));
                }else if (!isAll && !isRestock && isTaken){
                    amountLabelArray[i].setText(String.valueOf(sortedList.get(i).getRemoveUnit()));
                }
                dateLabelArray[i].setText(sortedList.get(i).getRecordTime());
            }
            // set empty labels
            if (sortedList.size() != 4) {
                for (int j = 3; j >= sortedList.size(); j--) {
                    staffLabelArray[j].setText("");
                    idLabelArray[j].setText("");
                    amountLabelArray[j].setText("");
                    dateLabelArray[j].setText("");
                }
            }
        });
    }

    /**
     * set the status (current unit, restock unit, taken unit) labels
     */
    private void setUnitStatus(){
        if (isAll && !isRestock && !isTaken){
            for (int i = 0; i < 4; i++){
                statusLabelArray[i].setText(" Current Unit");
                statusLabelArray[i].setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-background-radius: 5");
                statusLabelArray[i].setPrefWidth(82);
            }
        }else if (!isAll && isRestock && !isTaken){
            for (int i = 0; i < 4; i++){
                statusLabelArray[i].setText(" Restock");
                statusLabelArray[i].setStyle("-fx-background-color: #ddeab1#c7ddb5; -fx-text-fill: #759751; -fx-background-radius: 5");
                statusLabelArray[i].setPrefWidth(56);
            }
        }else if (!isAll && !isRestock && isTaken){
            for (int i = 0; i < 4; i++){
                statusLabelArray[i].setText(" Taken");
                statusLabelArray[i].setStyle("-fx-background-color: #feccc9; -fx-text-fill: #ff4137; -fx-background-radius: 5");
                statusLabelArray[i].setPrefWidth(44);
            }
        }
    }

    /**
     * show the content of transaction list from current page when the pagination is clicked
     */
    @FXML
    private void onClickPagination() {
        int currentPage = transactionPagination.getCurrentPage();
        ExecutorService threadPoolExecutor = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);
        threadPoolExecutor.execute(() -> setTransactionList(currentPage));
        setUnitStatus();
    }

    /**
     * sort the list by amount (all unit, restock unit, taken unit)
     */
    @FXML
    private void onClickAmount() {
        if (isAll && !isRestock && !isTaken){
            // sort by all unit
            if (isAmountAscend) {
                sortBy = SortBy.ALLASCEND;
                isAmountAscend = false;
                onClickPagination();
            } else {
                sortBy = SortBy.ALLDESCEND;
                isAmountAscend = true;
                onClickPagination();
            }
        }else if (!isAll && isRestock && !isTaken){
            // sort by restock unit
            if (isRestockAscend){
                sortBy = SortBy.RESTOCKASCEND;
                isRestockAscend = false;
                onClickPagination();
            }else{
                sortBy = SortBy.RESTOCKDESCEND;
                isRestockAscend = true;
                onClickPagination();
            }
        }else if (!isAll && !isRestock && isTaken){
            // sort by removed unit
            if (isTakenAscend) {
                sortBy = SortBy.TAKENASCEND;
                isTakenAscend = false;
                onClickPagination();
            }else{
                sortBy = SortBy.TAKENDDESCEND;
                isTakenAscend = true;
                onClickPagination();
            }
        }
    }

    @FXML
    private void onEnterAmount() {
        sortByAmount.setScaleX(2);
        sortByAmount.setScaleY(2);
    }

    @FXML
    private void onExitAmount() {
        sortByAmount.setScaleX(1);
        sortByAmount.setScaleY(1);
    }

    @FXML
    private void onPressedAmount() {
        sortByAmount.setScaleX(1.5);
        sortByAmount.setScaleY(1.5);
    }

    @FXML
    private void onReleaseAmount() {
        sortByAmount.setScaleX(2);
        sortByAmount.setScaleY(2);
    }

    /**
     * sort the list by date
     */
    @FXML
    private void onClickDate() {
        if (isDateAscend) {
            sortBy = SortBy.DATEASCEND;
            isDateAscend = false;
            onClickPagination();
        } else {
            sortBy = SortBy.DATEDESCEND;
            isDateAscend = true;
            onClickPagination();
        }
    }

    @FXML
    private void onEnterDate() {
        sortByDate.setScaleX(2);
        sortByDate.setScaleY(2);
    }

    @FXML
    private void onExitDate() {
        sortByDate.setScaleX(1);
        sortByDate.setScaleY(1);
    }

    @FXML
    private void onPressedDate() {
        sortByDate.setScaleX(1.5);
        sortByDate.setScaleY(1.5);
    }

    @FXML
    private void onReleaseDate() {
        sortByDate.setScaleX(2);
        sortByDate.setScaleY(2);
    }

}
