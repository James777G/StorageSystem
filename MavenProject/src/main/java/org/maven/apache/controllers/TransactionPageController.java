package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPagination;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private AnchorPane dataTransactionPage;

    @FXML
    private AnchorPane cargoPage;

    @FXML
    private AnchorPane editCargoPane;

    @FXML
    private AnchorPane blockPane;

    @FXML
    private JFXButton allSelectButton;

    @FXML
    private JFXButton takenSelectButton;

    @FXML
    private JFXButton restockSelectButton;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private Label statusLabel1, statusLabel2, statusLabel3, statusLabel4;

    @FXML
    private Label idLabel1, idLabel2, idLabel3, idLabel4;

    @FXML
    private Label staffLabel1, staffLabel2, staffLabel3, staffLabel4;

    @FXML
    private Label cargoLabel1, cargoLabel2, cargoLabel3, cargoLabel4;

    @FXML
    private Label amountLabel1, amountLabel2, amountLabel3, amountLabel4;

    @FXML
    private Label dateLabel1, dateLabel2, dateLabel3, dateLabel4;

    @FXML
    private Label confirmStatusLabel;

    @FXML
    private Label confirmIdLabel;

    @FXML
    private Label confirmStaffNameLabel;

    @FXML
    private Label confirmCargoNameLabel;

    @FXML
    private Label confirmCargoUnitLabel;

    @FXML
    private Label confirmDateLabel;

    @FXML
    private Label deletionNotificationLabel;

    @FXML
    private MFXPagination transactionPagination;

    @FXML
    private ImageView sortByAmount;

    @FXML
    private ImageView sortByDate;

    @FXML
    private ImageView binImage1, binImage2, binImage3, binImage4;

    @FXML
    private MFXGenericDialog deletionConfirmationDialog;

    private Label[] statusLabelArray = new Label[4];

    private Label[] idLabelArray = new Label[4];

    private Label[] staffLabelArray = new Label[4];

    private Label[] cargoLabelArray = new Label[4];

    private Label[] amountLabelArray = new Label[4];

    private Label[] dateLabelArray = new Label[4];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editCargoPane.getChildren().add(DataUtils.editCargoPane);
        DataUtils.editCargoPane.setVisible(false);
        initializeLabels();
        setPaginationPages();
        setTransactionList(1);
        currentPage = cargoPage;
        dataTransactionPage.getChildren().add(DataUtils.publicDataPane);
        dataTransactionPage.setPickOnBounds(false);
        dataTransactionPage.setOpacity(0);
        dataTransactionPage.setVisible(false);
        blockPane.setVisible(false);
        DataUtils.publicTransactionBlockPane = blockPane;
        deletionConfirmationDialog.setVisible(false);
    }

    @FXML
    private void onMoveToData() {
        if (!isMovingLineOnData && !isRunning) {
            isRunning = true;
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionByX(movingLinePane, 500, 105);
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
        DataUtils.editCargoPane.setVisible(true);
        blockPane.setVisible(true);
    }

    @FXML
    private void onMoveToCargo() {
        if (isMovingLineOnData && !isRunning) {
            isRunning = true;
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionByX(movingLinePane, 500, -105);
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
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(addButton, 500, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitAddButton() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(addButton, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    /**
     * initialize the labels which can be stored in arrays
     */
    private void initializeLabels() {
        // initialize status labels
        statusLabelArray[0] = statusLabel1;
        statusLabelArray[1] = statusLabel2;
        statusLabelArray[2] = statusLabel3;
        statusLabelArray[3] = statusLabel4;
        // initialize transaction id labels
        idLabelArray[0] = idLabel1;
        idLabelArray[1] = idLabel2;
        idLabelArray[2] = idLabel3;
        idLabelArray[3] = idLabel4;
        // initialize staff labels
        staffLabelArray[0] = staffLabel1;
        staffLabelArray[1] = staffLabel2;
        staffLabelArray[2] = staffLabel3;
        staffLabelArray[3] = staffLabel4;
        // initailize cargo labels
        cargoLabelArray[0] = cargoLabel1;
        cargoLabelArray[1] = cargoLabel2;
        cargoLabelArray[2] = cargoLabel3;
        cargoLabelArray[3] = cargoLabel4;
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
    private void setSortCondition(int currentPage) {
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
                idLabelArray[i].setText(String.valueOf(sortedList.get(i).getItemID()));
                staffLabelArray[i].setText(sortedList.get(i).getStaffName());
                cargoLabelArray[i].setText(sortedList.get(i).getItemName());
                if (isAll && !isRestock && !isTaken) {
                    amountLabelArray[i].setText(String.valueOf(sortedList.get(i).getCurrentUnit()));
                } else if (!isAll && isRestock && !isTaken) {
                    amountLabelArray[i].setText(String.valueOf(sortedList.get(i).getAddUnit()));
                } else if (!isAll && !isRestock && isTaken) {
                    amountLabelArray[i].setText(String.valueOf(sortedList.get(i).getRemoveUnit()));
                }
                dateLabelArray[i].setText(sortedList.get(i).getRecordTime());
            }
            // set empty labels
            if (sortedList.size() != 4) {
                for (int j = 3; j >= sortedList.size(); j--) {
                    idLabelArray[j].setText("");
                    staffLabelArray[j].setText("");
                    cargoLabelArray[j].setText("");
                    amountLabelArray[j].setText("");
                    dateLabelArray[j].setText("");
                }
            }
        });
    }

    /**
     * set the status (current unit, restock unit, taken unit) labels
     */
    private void setUnitStatus() {
        if (isAll && !isRestock && !isTaken) {
            for (int i = 0; i < 4; i++) {
                statusLabelArray[i].setText(" Current Unit");
                statusLabelArray[i].setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-background-radius: 5");
                statusLabelArray[i].setPrefWidth(82);
            }
        } else if (!isAll && isRestock && !isTaken) {
            for (int i = 0; i < 4; i++) {
                statusLabelArray[i].setText(" Restock");
                statusLabelArray[i].setStyle("-fx-background-color: #ddeab1#c7ddb5; -fx-text-fill: #759751; -fx-background-radius: 5");
                statusLabelArray[i].setPrefWidth(56);
            }
        } else if (!isAll && !isRestock && isTaken) {
            for (int i = 0; i < 4; i++) {
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
        if (isAll && !isRestock && !isTaken) {
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
        } else if (!isAll && isRestock && !isTaken) {
            // sort by restock unit
            if (isRestockAscend) {
                sortBy = SortBy.RESTOCKASCEND;
                isRestockAscend = false;
                onClickPagination();
            } else {
                sortBy = SortBy.RESTOCKDESCEND;
                isRestockAscend = true;
                onClickPagination();
            }
        } else if (!isAll && !isRestock && isTaken) {
            // sort by removed unit
            if (isTakenAscend) {
                sortBy = SortBy.TAKENASCEND;
                isTakenAscend = false;
                onClickPagination();
            } else {
                sortBy = SortBy.TAKENDDESCEND;
                isTakenAscend = true;
                onClickPagination();
            }
        }
    }

    @FXML
    private void onEnterAmount() {setScaleTransition(sortByAmount, 100, 1.3);}

    @FXML
    private void onExitAmount() {setScaleTransition(sortByAmount, 100, 1);}

    @FXML
    private void onPressedAmount() {setScaleTransition(sortByAmount, 100, 1.1);}

    @FXML
    private void onReleaseAmount() {setScaleTransition(sortByAmount, 100, 1.3);}

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
    private void onEnterDate() {setScaleTransition(sortByDate, 100, 1.3);}

    @FXML
    private void onExitDate() {setScaleTransition(sortByDate, 100, 1);}

    @FXML
    private void onPressedDate() {setScaleTransition(sortByDate, 100, 1.1);}

    @FXML
    private void onReleaseDate() {setScaleTransition(sortByDate, 100, 1.3);}

    @FXML
    private void onCloseDeletionConfirmation(){
        deletionConfirmationDialog.setVisible(false);
        blockPane.setVisible(false);
        confirmButton.setDisable(false);
        deletionNotificationLabel.setText("");
    }

    /**
     * delete the 1st transaction
     */
    @FXML
    private void onClickBin1() {
        if (!idLabelArray[0].getText().equals("")){
            deletionConfirmationDialog.setVisible(true);
            blockPane.setVisible(true);
            setRemovalConfirmation(0);
        }
    }

    /**
     * delete the 2nd transaction
     */
    @FXML
    private void onClickBin2() {
        if (!idLabelArray[1].getText().equals("")){
            deletionConfirmationDialog.setVisible(true);
            blockPane.setVisible(true);
            setRemovalConfirmation(1);
        }
    }

    /**
     * delete the 3rd transaction
     */
    @FXML
    private void onClickBin3() {
        if (!idLabelArray[2].getText().equals("")){
            deletionConfirmationDialog.setVisible(true);
            blockPane.setVisible(true);
            setRemovalConfirmation(2);
        }
    }

    /**
     * delete the 4th transaction
     */
    @FXML
    private void onClickBin4() {
        if (!idLabelArray[3].getText().equals("")){
            deletionConfirmationDialog.setVisible(true);
            blockPane.setVisible(true);
            setRemovalConfirmation(3);
        }
    }

    /**
     * set confirmation details before performing deletion
     *
     * @param row which row of transition list needs to be removed
     */
    private void setRemovalConfirmation(int row){
        confirmStatusLabel.setText("Transaction status: " + statusLabelArray[row].getText());
        confirmIdLabel.setText("Transaction id: " + idLabelArray[row].getText());
        confirmStaffNameLabel.setText("Staff name: " + staffLabelArray[row].getText());
        confirmCargoNameLabel.setText("Cargo name: " + cargoLabelArray[row].getText());
        confirmCargoUnitLabel.setText(statusLabelArray[row].getText() + ": " + amountLabelArray[row].getText());
        confirmDateLabel.setText("Date of completion: " + dateLabelArray[row].getText());
    }

    /**
     * user confirms to perform deletion
     */
    @FXML
    private void onConfirmDeletion(){
        dateTransactionService.deleteById(Integer.valueOf(idLabel1.getText()));
        confirmButton.setDisable(true);
        deletionNotificationLabel.setText("Removal completed");
    }

    @FXML
    private void onEnterBin1() {setScaleTransition(binImage1, 100, 1.3);}

    @FXML
    private void onEnterBin2() {setScaleTransition(binImage2, 100, 1.3);}

    @FXML
    private void onEnterBin3() {setScaleTransition(binImage3, 100, 1.3);}

    @FXML
    private void onEnterBin4() {setScaleTransition(binImage4, 100, 1.3);}

    @FXML
    private void onExitBin1() {setScaleTransition(binImage1, 100, 1);}

    @FXML
    private void onExitBin2() {setScaleTransition(binImage2, 100, 1);}

    @FXML
    private void onExitBin3() {setScaleTransition(binImage3, 100, 1);}

    @FXML
    private void onExitBin4() {setScaleTransition(binImage4, 100, 1);}

    @FXML
    private void onPressBin1() {setScaleTransition(binImage1, 100, 1.1);}

    @FXML
    private void onPressBin2() {setScaleTransition(binImage2, 100, 1.1);}

    @FXML
    private void onPressBin3() {setScaleTransition(binImage3, 100, 1.1);}

    @FXML
    private void onPressBin4() {setScaleTransition(binImage4, 100, 1.1);}

    @FXML
    private void onReleaseBin1() {setScaleTransition(binImage1, 100, 1.3);}

    @FXML
    private void onReleaseBin2() {setScaleTransition(binImage2, 100, 1.3);}

    @FXML
    private void onReleaseBin3() {setScaleTransition(binImage3, 100, 1.3);}

    @FXML
    private void onReleaseBin4() {setScaleTransition(binImage4, 100, 1.3);}

    private void setScaleTransition(ImageView imageView, int duration, double size) {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(imageView, duration, size);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    /**
     *  refresh current transition page
     */
    @FXML
    private void onRefreshTransitionList(){
        setPaginationPages();
        setTransactionList(transactionPagination.getCurrentPage());
    }

}