package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.MyLauncher;
import org.maven.apache.service.transaction.CachedTransactionService;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.ScaleUtils;
import org.maven.apache.utils.TransactionCachedUtils;
import org.maven.apache.utils.TranslateUtils;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class NewTransactionPageController implements Initializable {

    enum SortBy {
        ALLDATEASCEND, ALLDATEDESCEND, RESTOCKDATEASCEND, RESTOCKDATEDESCEND, TAKENDATEASCEND, TAKENDATEDESCEND, ALLAMOUNTASCEND, ALLAMOUNTDESCEND,
        RESTOCKAMOUNTASCEND, RESTOCKAMOUNTDESCEND, TAKENAMOUNTASCEND, TAKENAMOUNTDESCEND
    }

    enum ButtonSelected {
        ALL, TAKEN, RESTOCK
    }

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
    private AnchorPane cargoPage;

    @FXML
    private AnchorPane blockPane;

    @FXML
    private AnchorPane staffSearchPane;

    @FXML
    private AnchorPane cargoSearchPane;

    @FXML
    private AnchorPane searchSwitchingBlockPane;

    @FXML
    private AnchorPane addTransactionPane;

    @FXML
    private AnchorPane transactionPane1, transactionPane2, transactionPane3, transactionPane4, transactionPane5, transactionPane6, transactionPane7;

    @FXML
    private JFXButton allSelectButton;

    @FXML
    private JFXButton takenSelectButton;

    @FXML
    private JFXButton restockSelectButton;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXButton applyButtonInAdd;

    @FXML
    private Label cargoLabel1, cargoLabel2, cargoLabel3, cargoLabel4, cargoLabel5, cargoLabel6, cargoLabel7;

    @FXML
    private Label idLabel1, idLabel2, idLabel3, idLabel4, idLabel5, idLabel6, idLabel7;

    @FXML
    private Label amountLabel1, amountLabel2, amountLabel3, amountLabel4, amountLabel5, amountLabel6, amountLabel7;

    @FXML
    private Label dateLabel1, dateLabel2, dateLabel3, dateLabel4, dateLabel5, dateLabel6, dateLabel7;

    @FXML
    private Label statusLabel1, statusLabel2, statusLabel3, statusLabel4, statusLabel5, statusLabel6, statusLabel7;

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
    private Label notificationLabel;

    @FXML
    private Pagination transactionPagination;

    @FXML
    private ImageView sortByAmount;

    @FXML
    private ImageView sortByDate;

    @FXML
    private ImageView binImage1, binImage2, binImage3, binImage4, binImage5, binImage6, binImage7;

    @FXML
    private MFXGenericDialog deletionConfirmationDialog;

    @FXML
    private MFXGenericDialog descriptionDialog;

    @FXML
    private TextField staffField;

    @FXML
    private TextField newItemTextField;

    @FXML
    private TextField newUnitTextField;

    @FXML
    private TextField newStaffTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextArea descriptionField;

    @FXML
    private MFXProgressSpinner loadSpinnerInAdd;

    @FXML
    private MFXDatePicker datePicker = new MFXDatePicker(Locale.ENGLISH);

    @FXML
    private MFXToggleButton statusToggleButton;

    private Label[] cargoLabelArray = new Label[7];

    private Label[] idLabelArray = new Label[7];

    private Label[] amountLabelArray = new Label[7];

    private Label[] dateLabelArray = new Label[7];

    private Label[] statusLabelArray = new Label[7];

    private AnchorPane[] transactionPaneArray = new AnchorPane[7];

    private final CachedTransactionService cachedTransactionService = MyLauncher.context.getBean("cachedTransactionService", CachedTransactionService.class);

    private List<List<Transaction>> sortedList;

    private List<Transaction> currentPageList;

    private SortBy sortBy = SortBy.ALLDATEDESCEND;

    private ButtonSelected buttonSelected = ButtonSelected.ALL;

    private boolean isAll = true;

    private boolean isRestock = false;

    private boolean isTaken = false;

    private boolean isDateAscend = false;

    private boolean isAmountAscend = false;

    private int currentPage;

    private int newUnitAmount;

    private int newTransactionID;

    private int numOfTransaction;

    private String newItemName;

    private String newStaffName;

    private String transactionDate;

    private String transactionDescription = "";

    private Transaction newTransaction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cachedTransactionService.updateAllCachedTransactionData();
        initializeLabels();
        blockPane.setVisible(false);
        DataUtils.publicTransactionBlockPane = blockPane;
        deletionConfirmationDialog.setVisible(false);
        descriptionDialog.setVisible(false);
        addTransactionPane.setVisible(false);
        setPaginationPages(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.DATE_ASC_7));
        refreshPage();
        // perform the action of loading current page content when pagination is clicked
        transactionPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            updatePagination(newValue);
        });
        setInputValidation(newUnitTextField);
    }

    /**
     * initialize the labels which can be stored in arrays
     */
    private void initializeLabels() {
        // initailize cargo labels
        cargoLabelArray[0] = cargoLabel1;
        cargoLabelArray[1] = cargoLabel2;
        cargoLabelArray[2] = cargoLabel3;
        cargoLabelArray[3] = cargoLabel4;
        cargoLabelArray[4] = cargoLabel5;
        cargoLabelArray[5] = cargoLabel6;
        cargoLabelArray[6] = cargoLabel7;
        // initialize transaction id labels
        idLabelArray[0] = idLabel1;
        idLabelArray[1] = idLabel2;
        idLabelArray[2] = idLabel3;
        idLabelArray[3] = idLabel4;
        idLabelArray[4] = idLabel5;
        idLabelArray[5] = idLabel6;
        idLabelArray[6] = idLabel7;
        // initialize amount labels
        amountLabelArray[0] = amountLabel1;
        amountLabelArray[1] = amountLabel2;
        amountLabelArray[2] = amountLabel3;
        amountLabelArray[3] = amountLabel4;
        amountLabelArray[4] = amountLabel5;
        amountLabelArray[5] = amountLabel6;
        amountLabelArray[6] = amountLabel7;
        // initialize record time labels
        dateLabelArray[0] = dateLabel1;
        dateLabelArray[1] = dateLabel2;
        dateLabelArray[2] = dateLabel3;
        dateLabelArray[3] = dateLabel4;
        dateLabelArray[4] = dateLabel5;
        dateLabelArray[5] = dateLabel6;
        dateLabelArray[6] = dateLabel7;
        // initialize status labels
        statusLabelArray[0] = statusLabel1;
        statusLabelArray[1] = statusLabel2;
        statusLabelArray[2] = statusLabel3;
        statusLabelArray[3] = statusLabel4;
        statusLabelArray[4] = statusLabel5;
        statusLabelArray[5] = statusLabel6;
        statusLabelArray[6] = statusLabel7;
        // initialize transaction anchor pane
        transactionPaneArray[0] = transactionPane1;
        transactionPaneArray[1] = transactionPane2;
        transactionPaneArray[2] = transactionPane3;
        transactionPaneArray[3] = transactionPane4;
        transactionPaneArray[4] = transactionPane5;
        transactionPaneArray[5] = transactionPane6;
        transactionPaneArray[6] = transactionPane7;
    }

    /**
     * set how many pages do we need in total
     */
    private void setPaginationPages(List<List<Transaction>> list) {
        transactionPagination.setPageCount(list.size());
    }


    /**
     * set the content of transaction list from current page when the pagination is clicked
     */
    private void updatePagination(Number currentPage) {
        currentPageList = sortedList.get(currentPage.intValue());
        // set non-empty labels
        for (int i = 0; i < currentPageList.size(); i++) {
            cargoLabelArray[i].setText(currentPageList.get(i).getItemName());
            idLabelArray[i].setText(String.valueOf(currentPageList.get(i).getID()));
            amountLabelArray[i].setText(String.valueOf(currentPageList.get(i).getUnit()));
            dateLabelArray[i].setText(currentPageList.get(i).getTransactionTime());
            transactionPaneArray[i].setVisible(true);
        }
        // set empty labels
        if (currentPageList.size() != 7) {
            for (int j = 6; j >= currentPageList.size(); j--) {
                transactionPaneArray[j].setVisible(false);
            }
        }
        setUnitStatus();
    }

    /**
     * set the cargo status (restock unit, taken unit) labels
     */
    private void setUnitStatus() {
        if (isAll && !isRestock && !isTaken) {
            for (int i = 0; i < currentPageList.size(); i++) {
                if (currentPageList.get(i).getStatus().equals("RESTOCK")) {
                    statusLabelArray[i].setText(" Restock");
                    statusLabelArray[i].setStyle("-fx-background-color: #ddeab1#c7ddb5; -fx-text-fill: #759751; -fx-background-radius: 5");
                    statusLabelArray[i].setPrefWidth(56);
                } else {
                    statusLabelArray[i].setText(" Taken");
                    statusLabelArray[i].setStyle("-fx-background-color: #feccc9; -fx-text-fill: #ff4137; -fx-background-radius: 5");
                    statusLabelArray[i].setPrefWidth(44);
                }
            }
        } else if (!isAll && isRestock && !isTaken) {
            for (int i = 0; i < currentPageList.size(); i++) {
                statusLabelArray[i].setText(" Restock");
                statusLabelArray[i].setStyle("-fx-background-color: #ddeab1#c7ddb5; -fx-text-fill: #759751; -fx-background-radius: 5");
                statusLabelArray[i].setPrefWidth(56);
            }
        } else if (!isAll && !isRestock && isTaken) {
            for (int i = 0; i < currentPageList.size(); i++) {
                statusLabelArray[i].setText(" Taken");
                statusLabelArray[i].setStyle("-fx-background-color: #feccc9; -fx-text-fill: #ff4137; -fx-background-radius: 5");
                statusLabelArray[i].setPrefWidth(44);
            }
        }
    }

    /**
     * get a particular transaction list by setting a sorting property
     */
    private void setSortCondition() {
        switch (sortBy) {
            case ALLDATEASCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.DATE_ASC_7);
                break;
            case ALLDATEDESCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.DATE_DESC_7);
                break;
            case RESTOCKDATEASCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_ASC_7);
                break;
            case RESTOCKDATEDESCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_7);
                break;
            case TAKENDATEASCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_ASC_7);
                break;
            case TAKENDATEDESCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_7);
                break;
            case ALLAMOUNTASCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.AMOUNT_ASC_7);
                break;
            case ALLAMOUNTDESCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.AMOUNT_DESC_7);
                break;
            case RESTOCKAMOUNTASCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_AMOUNT_ASC_7);
                break;
            case RESTOCKAMOUNTDESCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_AMOUNT_DESC_7);
                break;
            case TAKENAMOUNTASCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_AMOUNT_ASC_7);
                break;
            case TAKENAMOUNTDESCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_AMOUNT_DESC_7);
                break;
        }
    }

    /**
     * Clicked the button labeled with "ALL" and returns an ascend list ordered by date including both status
     */
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
        if (isDateAscend) {
            sortBy = SortBy.ALLDATEASCEND;
        } else {
            sortBy = SortBy.ALLDATEDESCEND;
        }
        refreshPage();

    }

    /**
     * Clicked the button labeled with "RESTOCK" and returns an ascend list ordered by date including restock status only
     */
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
        if (isDateAscend) {
            sortBy = SortBy.RESTOCKDATEASCEND;
        } else {
            sortBy = SortBy.RESTOCKDATEDESCEND;
        }
        refreshPage();
    }

    /**
     * Clicked the button labeled with "TAKEN" and returns an ascend list ordered by date including taken status only
     */
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
        if (isDateAscend) {
            sortBy = SortBy.TAKENDATEASCEND;
        } else {
            sortBy = SortBy.TAKENDATEDESCEND;
        }
        refreshPage();
    }

    /**
     * sort the list by date
     */
    @FXML
    private void onClickDate() {
        if (isAll && !isRestock && !isTaken) {
            if (isDateAscend) {
                sortBy = SortBy.ALLDATEDESCEND;
                isDateAscend = false;
            } else {
                sortBy = SortBy.ALLDATEASCEND;
                isDateAscend = true;
            }
        } else if (!isAll && isRestock && !isTaken) {
            if (isDateAscend) {
                sortBy = SortBy.RESTOCKDATEDESCEND;
                isDateAscend = false;
            } else {
                sortBy = SortBy.RESTOCKDATEASCEND;
                isDateAscend = true;
            }
        } else if (!isAll && !isRestock && isTaken) {
            if (isDateAscend) {
                sortBy = SortBy.TAKENDATEDESCEND;
                isDateAscend = false;
            } else {
                sortBy = SortBy.TAKENDATEASCEND;
                isDateAscend = true;
            }
        }
        refreshPage();
    }

    /**
     * sort the list by amount (all unit, restock unit, taken unit)
     */
    @FXML
    private void onClickAmount() {
        if (isAll && !isRestock && !isTaken) {
            if (isAmountAscend) {
                sortBy = SortBy.ALLAMOUNTDESCEND;
                isAmountAscend = false;
            } else {
                sortBy = SortBy.ALLAMOUNTASCEND;
                isAmountAscend = true;
            }
        } else if (!isAll && isRestock && !isTaken) {
            if (isAmountAscend) {
                sortBy = SortBy.RESTOCKAMOUNTDESCEND;
                isAmountAscend = false;
            } else {
                sortBy = SortBy.RESTOCKAMOUNTASCEND;
                isAmountAscend = true;
            }
        } else if (!isAll && !isRestock && isTaken) {
            if (isAmountAscend) {
                sortBy = SortBy.TAKENAMOUNTDESCEND;
                isAmountAscend = false;
            } else {
                sortBy = SortBy.TAKENAMOUNTASCEND;
                isAmountAscend = true;
            }
        }
        refreshPage();
    }

    /**
     * reload the content of current page
     */
    private void refreshPage() {
        setSortCondition();
        updatePagination(0);
        setPaginationPages(sortedList);
    }

    /**
     * show the pane of adding new transaction
     */
    @FXML
    private void onClickAddButton() {
        addTransactionPane.setVisible(true);
        blockPane.setVisible(true);
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

    @FXML
    private void onEnterAmount() {
        setScaleTransition(sortByAmount, 100, 1.3);
    }

    @FXML
    private void onExitAmount() {
        setScaleTransition(sortByAmount, 100, 1);
    }

    @FXML
    private void onPressedAmount() {
        setScaleTransition(sortByAmount, 100, 1.1);
    }

    @FXML
    private void onReleaseAmount() {
        setScaleTransition(sortByAmount, 100, 1.3);
    }

    @FXML
    private void onEnterDate() {
        setScaleTransition(sortByDate, 100, 1.3);
    }

    @FXML
    private void onExitDate() {
        setScaleTransition(sortByDate, 100, 1);
    }

    @FXML
    private void onPressedDate() {
        setScaleTransition(sortByDate, 100, 1.1);
    }

    @FXML
    private void onReleaseDate() {
        setScaleTransition(sortByDate, 100, 1.3);
    }

    @FXML
    private void onCloseDeletionConfirmation() {
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
        setDeletionPanes(0);
    }

    /**
     * delete the 2nd transaction
     */
    @FXML
    private void onClickBin2() {
        setDeletionPanes(1);
    }

    /**
     * delete the 3rd transaction
     */
    @FXML
    private void onClickBin3() {
        setDeletionPanes(2);
    }

    /**
     * delete the 4th transaction
     */
    @FXML
    private void onClickBin4() {
        setDeletionPanes(3);
    }

    /**
     * delete the 5th transaction
     */
    @FXML
    private void onClickBin5() {
        setDeletionPanes(4);
    }

    /**
     * delete the 6th transaction
     */
    @FXML
    private void onClickBin6() {
        setDeletionPanes(5);
    }

    /**
     * delete the 7th transaction
     */
    @FXML
    private void onClickBin7() {
        setDeletionPanes(6);
    }

    private void setDeletionPanes(int row) {
        deletionConfirmationDialog.setVisible(true);
        blockPane.setVisible(true);
        setRemovalConfirmation(row);
    }

    /**
     * set confirmation details before performing deletion
     *
     * @param row which row of transition list needs to be removed
     */
    private void setRemovalConfirmation(int row) {
        confirmStatusLabel.setText("Status: " + currentPageList.get(row).getStatus());
        confirmIdLabel.setText("Transaction ID: " + String.valueOf(currentPageList.get(row).getID()));
        confirmStaffNameLabel.setText("Staff issued: " + currentPageList.get(row).getStaffName());
        confirmCargoNameLabel.setText("Cargo: " + currentPageList.get(row).getItemName());
        confirmCargoUnitLabel.setText("Cargo unit: " + String.valueOf(currentPageList.get(row).getUnit()));
        confirmDateLabel.setText("Transaction date: " + currentPageList.get(row).getTransactionTime());
    }

    /**
     * user confirms to perform deletion
     */
    @FXML
    private void onConfirmDeletion() {
        cachedTransactionService.deleteTransactionById(Integer.parseInt(confirmIdLabel.getText().split(":")[1].strip()));
        confirmButton.setDisable(true);
        deletionNotificationLabel.setText("Removal accomplished");
        refreshPage();
    }

    @FXML
    private void onRefreshTransitionList() {
        refreshPage();
    }

    @FXML
    private void onEnterBin1() {
        setScaleTransition(binImage1, 100, 1.3);
    }

    @FXML
    private void onEnterBin2() {
        setScaleTransition(binImage2, 100, 1.3);
    }

    @FXML
    private void onEnterBin3() {
        setScaleTransition(binImage3, 100, 1.3);
    }

    @FXML
    private void onEnterBin4() {
        setScaleTransition(binImage4, 100, 1.3);
    }

    @FXML
    private void onEnterBin5() {
        setScaleTransition(binImage5, 100, 1.3);
    }

    @FXML
    private void onEnterBin6() {
        setScaleTransition(binImage6, 100, 1.3);
    }

    @FXML
    private void onEnterBin7() {
        setScaleTransition(binImage7, 100, 1.3);
    }

    @FXML
    private void onExitBin1() {
        setScaleTransition(binImage1, 100, 1);
    }

    @FXML
    private void onExitBin2() {
        setScaleTransition(binImage2, 100, 1);
    }

    @FXML
    private void onExitBin3() {
        setScaleTransition(binImage3, 100, 1);
    }

    @FXML
    private void onExitBin4() {
        setScaleTransition(binImage4, 100, 1);
    }

    @FXML
    private void onExitBin5() {
        setScaleTransition(binImage5, 100, 1);
    }

    @FXML
    private void onExitBin6() {
        setScaleTransition(binImage6, 100, 1);
    }

    @FXML
    private void onExitBin7() {
        setScaleTransition(binImage7, 100, 1);
    }

    @FXML
    private void onPressBin1() {
        setScaleTransition(binImage1, 100, 1.1);
    }

    @FXML
    private void onPressBin2() {
        setScaleTransition(binImage2, 100, 1.1);
    }

    @FXML
    private void onPressBin3() {
        setScaleTransition(binImage3, 100, 1.1);
    }

    @FXML
    private void onPressBin4() {
        setScaleTransition(binImage4, 100, 1.1);
    }

    @FXML
    private void onPressBin5() {
        setScaleTransition(binImage5, 100, 1.1);
    }

    @FXML
    private void onPressBin6() {
        setScaleTransition(binImage6, 100, 1.1);
    }

    @FXML
    private void onPressBin7() {
        setScaleTransition(binImage7, 100, 1.1);
    }

    @FXML
    private void onReleaseBin1() {
        setScaleTransition(binImage1, 100, 1.3);
    }

    @FXML
    private void onReleaseBin2() {
        setScaleTransition(binImage2, 100, 1.3);
    }

    @FXML
    private void onReleaseBin3() {
        setScaleTransition(binImage3, 100, 1.3);
    }

    @FXML
    private void onReleaseBin4() {
        setScaleTransition(binImage4, 100, 1.3);
    }

    @FXML
    private void onReleaseBin5() {
        setScaleTransition(binImage5, 100, 1.3);
    }

    @FXML
    private void onReleaseBin6() {
        setScaleTransition(binImage6, 100, 1.3);
    }

    @FXML
    private void onReleaseBin7() {
        setScaleTransition(binImage7, 100, 1.3);
    }

    private void setScaleTransition(ImageView imageView, int duration, double size) {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(imageView, duration, size);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onCheckDetails1() {
        setTransactionDetails(0);
    }

    @FXML
    private void onCheckDetails2() {
        setTransactionDetails(1);
    }

    @FXML
    private void onCheckDetails3() {
        setTransactionDetails(2);
    }

    @FXML
    private void onCheckDetails4() {
        setTransactionDetails(3);
    }

    @FXML
    private void onCheckDetails5() {
        setTransactionDetails(4);
    }

    @FXML
    private void onCheckDetails6() {
        setTransactionDetails(5);
    }

    @FXML
    private void onCheckDetails7() {
        setTransactionDetails(6);
    }

    private void setTransactionDetails(int row) {
        descriptionDialog.setVisible(true);
        staffField.setText(currentPageList.get(row).getStaffName());
        descriptionField.setText(currentPageList.get(row).getPurpose());
    }

    @FXML
    private void onCloseDescriptionDialog() {
        descriptionDialog.setVisible(false);
    }

    /**
     * close the pane of adding new transaction
     */
    @FXML
    private void onClickOkayInAdd() {
        addTransactionPane.setVisible(false);
        blockPane.setVisible(false);
    }

    /**
     * saves the properties of the added new transaction
     */
    @FXML
    private void onClickApplyInAdd() {
        if (!isValidated()) {
            notificationLabel.setText("Empty fields");
        } else {
            newTransactionID = getNumOfTransaction() + 1;
            newItemName = newItemTextField.getText();
            newStaffName = newStaffTextField.getText();
            newUnitAmount = Integer.valueOf(newUnitTextField.getText());
            if (datePicker.getText().equals("")) {
                // return current date and time
                LocalDate dateTime = LocalDate.now();
                transactionDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } else {
                // return chosen date from calendar
                LocalDate dateTime = datePicker.getValue();
                transactionDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            newTransaction = new Transaction();
            if (statusToggleButton.isSelected()) {
                // adding taken cargo
                addNewTransaction("TAKEN", newTransactionID, newItemName, newStaffName, newUnitAmount, transactionDate, transactionDescription);
            } else {
                // adding restock cargo
                addNewTransaction("RESTOCK", newTransactionID, newItemName, newStaffName, newUnitAmount, transactionDate, transactionDescription);
            }
            cachedTransactionService.addNewTransaction(newTransaction);
            notificationLabel.setText("Transaction added successfully");
        }
    }

    /**
     * set current transaction status
     */
    @FXML
    private void onToggle(){
        if (statusToggleButton.isSelected()){
            // convert status from RESTOCK to TAKEN
            statusToggleButton.setText("TAKEN");
        }else{
            // convert status from TAKEN to RESTOCK
            statusToggleButton.setText("RESTOCK");
        }
    }

    /**
     * get the total amount of transaction to increment id
     *
     * @return amount of transaction
     */
    private int getNumOfTransaction() {
        int count = 0;
        for (int i = 0; i < TransactionCachedUtils.getLists(TransactionCachedUtils.listType.DATE_DESC_7).size(); i++) {
            for (int j = 0; j < TransactionCachedUtils.getLists(TransactionCachedUtils.listType.DATE_DESC_7).get(i).size(); j++) {
                count++;
            }
        }
        return count;
    }

    /**
     * check if input information is good to go
     *
     * @return true or false
     */
    private boolean isValidated() {
        if (!newItemTextField.getText().equals("") && !newStaffTextField.getText().equals("") && !newUnitTextField.getText().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * force the text field to be numeric only
     *
     * @param textField
     */
    private void setInputValidation(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[0-9]*")) {
                    Platform.runLater(() -> {
                        textField.setText(newValue.replaceAll("[^\\d]", ""));
                    });
                }
            }
        });
    }

    /**
     * pass field properties to the new transaction
     *
     * @param TransactionID new transaction ID (incremented by 1 pursuant to the amount of current transactions)
     * @param itemName      new cargo name that is transferred
     * @param staffName     staff name who controls this transaction
     * @param unit          restock/taken amount
     * @param date          date of making this new transaction
     * @param purpose       transaction details
     */
    private void addNewTransaction(String status, int TransactionID, String itemName, String staffName, int unit, String date, String purpose) {
        newTransaction.setStatus(status);
        newTransaction.setID(TransactionID);
        newTransaction.setItemName(itemName);
        newTransaction.setStaffName(staffName);
        newTransaction.setUnit(unit);
        newTransaction.setTransactionTime(date);
        newTransaction.setPurpose(purpose);
    }

    @FXML
    private void onClickStaffSearch() {
        searchSwitchingBlockPane.toFront();
        cargoSearchPane.setVisible(true);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(staffSearchPane, 300, 0, -15);
        TranslateTransition translateTransition1 = TranslateUtils.getTranslateTransitionFromToY(cargoSearchPane, 300, 15, 0);
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionFromToY(staffSearchPane, 300, 1, 0);
        scaleTransition.setOnFinished(event -> {
            searchSwitchingBlockPane.toBack();
            staffSearchPane.setVisible(false);
        });
        ScaleTransition scaleTransition1 = ScaleUtils.getScaleTransitionFromToY(cargoSearchPane, 300, 0, 1);
        translateTransition.play();
        translateTransition1.play();
        scaleTransition.play();
        scaleTransition1.play();
    }

    @FXML
    private void onClickCargoSearch() {
        searchSwitchingBlockPane.toFront();
        staffSearchPane.setVisible(true);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(staffSearchPane, 300, -15, 0);
        TranslateTransition translateTransition1 = TranslateUtils.getTranslateTransitionFromToY(cargoSearchPane, 300, 0, 15);
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionFromToY(staffSearchPane, 300, 0, 1);
        ScaleTransition scaleTransition1 = ScaleUtils.getScaleTransitionFromToY(cargoSearchPane, 300, 1, 0);
        scaleTransition1.setOnFinished(event -> {
            searchSwitchingBlockPane.toBack();
            cargoSearchPane.setVisible(false);
        });
        translateTransition.play();
        translateTransition1.play();
        scaleTransition.play();
        scaleTransition1.play();
    }
}