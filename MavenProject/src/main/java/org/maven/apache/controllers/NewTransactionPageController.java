package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.MyLauncher;
import org.maven.apache.exception.*;
import org.maven.apache.item.Item;
import org.maven.apache.service.search.SearchResultService;
import org.maven.apache.service.search.SearchResultServiceHandler;
import org.maven.apache.service.transaction.CachedTransactionService;
import org.maven.apache.staff.Staff;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.utils.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class NewTransactionPageController implements Initializable {

    enum SortBy {
        ALLDATEASCEND, ALLDATEDESCEND, RESTOCKDATEASCEND, RESTOCKDATEDESCEND, TAKENDATEASCEND, TAKENDATEDESCEND,
        ALLAMOUNTASCEND, ALLAMOUNTDESCEND, RESTOCKAMOUNTASCEND, RESTOCKAMOUNTDESCEND, TAKENAMOUNTASCEND, TAKENAMOUNTDESCEND
    }

    enum ButtonSelected {
        ALL, TAKEN, RESTOCK
    }

    enum ArrowStatus {
        HORIZONTAL, UP, DOWN
    }

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
    private AnchorPane deleteItemPane;

    @FXML
    private AnchorPane dateArrowBlockPane;

    @FXML
    private AnchorPane amountArrowBlockPane;

    @FXML
    private AnchorPane transactionPane1, transactionPane2, transactionPane3, transactionPane4, transactionPane5, transactionPane6, transactionPane7;

    @FXML
    private JFXButton allSelectButton;

    @FXML
    private JFXButton takenSelectButton;

    @FXML
    private JFXButton restockSelectButton;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXButton applyButtonInAdd;

    @FXML
    private JFXButton okayButton;

    @FXML
    private JFXButton clearButton;

    @FXML
    private JFXButton cargoDialogApplyButton;

    @FXML
    private Label cargoLabel1, cargoLabel2, cargoLabel3, cargoLabel4, cargoLabel5, cargoLabel6, cargoLabel7;

    @FXML
    private Label staffLabel1, staffLabel2, staffLabel3, staffLabel4, staffLabel5, staffLabel6, staffLabel7;

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
    private Label warnMessageInAdd;

    @FXML
    private Label transactionIdInDetails;

    @FXML
    private Label transactionStatusInDetails;

    @FXML
    private Label transactionNameInDetails;

    @FXML
    private Label transactionDateInDetails;

    @FXML
    private Label transactionAmountInDetails;

    @FXML
    private Label warnMessageInDetails;

    @FXML
    private Pagination transactionPagination;

    @FXML
    private Image arrowImage = new Image(Objects.requireNonNull(NewTransactionPageController.class.getResourceAsStream("/image/icons8-sort-down-100.png")));

    @FXML
    private Image horizontalLineImage = new Image(Objects.requireNonNull(NewTransactionPageController.class.getResourceAsStream("/image/icons8-horizontal-line-96.png")));

    @FXML
    private ImageView sortByAmount;

    @FXML
    private ImageView sortByDate;

    @FXML
    private ImageView binImage1, binImage2, binImage3, binImage4, binImage5, binImage6, binImage7;

    @FXML
    private ImageView[] binImages = new ImageView[7];

    @FXML
    private ImageView deletionCross;

    @FXML
    private ImageView deletionTick;

    @FXML
    private MFXGenericDialog descriptionDialog;

    @FXML
    private TextField newUnitTextField;

    @FXML
    private MFXFilterComboBox staffNameInDetails;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextArea purposeTextInDetails;

    @FXML
    private MFXProgressSpinner loadSpinnerInAdd;

    @FXML
    private MFXProgressSpinner loadSpinnerOnDeletePane;

    @FXML
    private MFXProgressSpinner loadSpinnerInDetails;

    @FXML
    private MFXDatePicker datePicker = new MFXDatePicker(Locale.ENGLISH);

    @FXML
    private MFXTextField searchField;

    @FXML
    private MFXFilterComboBox newItemFilterComboBox;

    @FXML
    private MFXFilterComboBox newStaffFilterComboBox;

    @FXML
    private Label warnMessageInDelete;

    @FXML
    private MFXComboBox newStatusComboBox;

    private Label[] cargoLabelArray = new Label[7];

    private Label[] staffLabelArray = new Label[7];

    private Label[] amountLabelArray = new Label[7];

    private Label[] dateLabelArray = new Label[7];

    private Label[] statusLabelArray = new Label[7];

    private AnchorPane[] transactionPaneArray = new AnchorPane[7];

    private final CachedTransactionService cachedTransactionService = MyLauncher.context.getBean("cachedTransactionService", CachedTransactionService.class);

    private final ExecutorService executorService = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

    private final SearchResultService<Transaction> searchResultService = MyLauncher.context.getBean("searchResultService", SearchResultService.class);

    private List<List<Transaction>> sortedList;

    private List<Transaction> currentPageList;

    private SortBy sortBy = SortBy.ALLDATEDESCEND;

    private ButtonSelected buttonSelected = ButtonSelected.ALL;

    private ArrowStatus amountArrow = ArrowStatus.HORIZONTAL;

    private ArrowStatus dateArrow = ArrowStatus.DOWN;

    private boolean isArrowRotating = false;

    private boolean isAll = true;

    private boolean isRestock = false;

    private boolean isTaken = false;

    private boolean isDateAscend = false;

    private boolean isAmountAscend = false;

    private boolean isAdditionSucceed;

    private boolean isBlockPaneOpen = false;

    public static boolean isSearchingStaff = true;

    private int newUnitAmount;

    private int newTransactionID;

    private String newItemName;

    private String newStaffName;

    private String transactionDate;

    private String newTransactionStatus;

    private String transactionDescription = "";

    private Transaction newTransaction;

    private Transaction originalTransaciton = new Transaction();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cachedTransactionService.updateAllCachedTransactionData();
        initializeLabels();
        blockPane.setVisible(false);
        DataUtils.publicTransactionBlockPane = blockPane;
        DataUtils.transactionPagination = transactionPagination;
        DataUtils.transactionPageController = this;
        deleteItemPane.setVisible(false);
        descriptionDialog.setVisible(false);
        addTransactionPane.setVisible(false);
        warnMessageInAdd.setVisible(false);
        loadSpinnerInAdd.setVisible(false);
        setBinsImages();
        amountArrowBlockPane.setVisible(false);
        dateArrowBlockPane.setVisible(false);
        List<String> status = new ArrayList<>();
        status.add("TAKEN");
        status.add("RESTOCK");
        newStatusComboBox.setItems(FXCollections.observableList(status));
        setPaginationPages(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.DATE_ASC_7));
        try {
            refreshPage();
        } catch (UnsupportedPojoException e) {
            throw new RuntimeException(e);
        }
        // perform the action of loading current page content when pagination is clicked
        transactionPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            updatePagination(newValue);
        });
        setInputValidation(newUnitTextField);
        getNumOfTransaction();
        warnMessageInDetails.setVisible(false);
        setPromptTextForStaff();
        setPromptTextForRegulatory();
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
        staffLabelArray[0] = staffLabel1;
        staffLabelArray[1] = staffLabel2;
        staffLabelArray[2] = staffLabel3;
        staffLabelArray[3] = staffLabel4;
        staffLabelArray[4] = staffLabel5;
        staffLabelArray[5] = staffLabel6;
        staffLabelArray[6] = staffLabel7;
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
        // initialize delete bin imageview
        binImages[0] = binImage1;
        binImages[1] = binImage2;
        binImages[2] = binImage3;
        binImages[3] = binImage4;
        binImages[4] = binImage5;
        binImages[5] = binImage6;
        binImages[6] = binImage7;
    }

    private void resetArrows() {
        sortByAmount.setRotate(0);
        sortByAmount.setImage(horizontalLineImage);
        sortByDate.setRotate(0);
        sortByDate.setImage(arrowImage);
        amountArrow = ArrowStatus.HORIZONTAL;
        dateArrow = ArrowStatus.DOWN;
    }

    private void setBinsImages() {
        Arrays.stream(binImages).forEach(imageView -> imageView.setVisible(false));
    }

    /**
     * set how many pages do we need in total
     */
    private void setPaginationPages(List<List<Transaction>> list) {
        transactionPagination.setPageCount(list.size());
    }


    /**
     * update the content of which page is going to be indicated
     */
    private void updatePagination(Number currentPage) {
        if (sortedList.size() == 0) {
            for (int k = 0; k < 7; k++) {
                transactionPaneArray[k].setVisible(false);
            }
        } else {
            currentPageList = sortedList.get(currentPage.intValue());
            // set non-empty labels
            for (int i = 0; i < currentPageList.size(); i++) {
                cargoLabelArray[i].setText(currentPageList.get(i).getItemName());
                staffLabelArray[i].setText(String.valueOf(currentPageList.get(i).getStaffName()));
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
    private void onClickAllSelectButton() throws UnsupportedPojoException {
        switch (buttonSelected) {
            case ALL -> {
                return;
            }
            case TAKEN -> {
                onTakenSelectPane.setVisible(false);
                onAllSelectPane.setVisible(true);
                buttonSelected = ButtonSelected.ALL;
            }
            case RESTOCK -> {
                onRestockSelectPane.setVisible(false);
                onAllSelectPane.setVisible(true);
                buttonSelected = ButtonSelected.ALL;
            }
        }
        isAll = true;
        isRestock = false;
        isTaken = false;
        resetArrows();
        sortBy = SortBy.ALLDATEDESCEND;
        refreshPage();
    }

    /**
     * Clicked the button labeled with "RESTOCK" and returns an ascend list ordered by date including restock status only
     */
    @FXML
    private void onClickRestockSelectButton() throws UnsupportedPojoException {
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
        resetArrows();
        sortBy = SortBy.RESTOCKDATEDESCEND;
        refreshPage();
    }

    /**
     * Clicked the button labeled with "TAKEN" and returns an ascend list ordered by date including taken status only
     */
    @FXML
    private void onClickTakenSelectButton() throws UnsupportedPojoException {
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
        resetArrows();
        sortBy = SortBy.TAKENDATEDESCEND;
        refreshPage();
    }

    /**
     * sort the list by date
     */
    @FXML
    private void onClickDate() throws UnsupportedPojoException {
        setPressScaleTransition(false, dateArrow);

    }

    /**
     * sort the list by amount (all unit, restock unit, taken unit)
     */
    @FXML
    private void onClickAmount() throws UnsupportedPojoException {
        setPressScaleTransition(true, amountArrow);
    }

    /**
     * recalculate the amount of pages by specifying a particular transaction list and jump into its initial page
     */
    private void refreshPage() throws UnsupportedPojoException {
        setSortCondition();
        if (!searchField.getText().isBlank()) {
            try {
                if (isSearchingStaff) {
                    sortedList = searchResultService.getPagedResultList(sortedList, searchField.getText(), SearchResultServiceHandler.ResultType.STAFF);
                } else {
                    sortedList = searchResultService.getPagedResultList(sortedList, searchField.getText(), SearchResultServiceHandler.ResultType.CARGO);
                }
            } catch (Exception e) {
                sortedList.clear();
            }
        }
        updatePagination(0);
        transactionPagination.setCurrentPageIndex(0);
        setPaginationPages(sortedList);
    }

    /**
     * show the pane of adding new transaction
     */
    @FXML
    private void onClickAddButton() {
        addTransactionPane.setOpacity(0);
        addTransactionPane.setVisible(true);
        blockPane.setVisible(true);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(addTransactionPane, 300, 0, 1);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(addTransactionPane, 300, -170, 0);
        translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
        fadeTransition.play();
        translateTransition.play();
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
        setEnterScaleTransition(sortByAmount, 100, 1.1);
    }

    @FXML
    private void onExitAmount() {
        setExitScaleTransition(sortByAmount, 100, 1);
    }

    @FXML
    private void onPressedAmount() {
    }

    @FXML
    private void onReleaseAmount() {
        setEnterScaleTransition(sortByAmount, 100, 1.1);
    }

    @FXML
    private void onEnterDate() {
        setEnterScaleTransition(sortByDate, 100, 1.1);
    }

    @FXML
    private void onExitDate() {
        setExitScaleTransition(sortByDate, 100, 1);
    }

    @FXML
    private void onPressedDate() {
    }

    @FXML
    private void onReleaseDate() {
        setEnterScaleTransition(sortByDate, 100, 1.1);
    }

    @FXML
    private void onCloseDeletionConfirmation() {
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteItemPane, 300, 1, 0);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteItemPane, 300, 0, -110);
        translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
        translateTransition.setOnFinished(event -> {
            deleteItemPane.setVisible(false);
            isBlockPaneOpen = false;
            setBinsImages();
            blockPane.setVisible(false);
        });
        fadeTransition.play();
        translateTransition.play();
        warnMessageInDelete.setVisible(false);
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
        isBlockPaneOpen = true;
        blockPane.setVisible(true);
        deleteItemPane.setOpacity(0);
        deleteItemPane.setVisible(true);
        setRemovalConfirmation(row);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteItemPane, 300, 0, 1);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteItemPane, 300, -110, 0);
        translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
        fadeTransition.play();
        translateTransition.play();
    }

    @FXML
    private void onEnterCross() {
        setEnterScaleTransition(deletionCross, 250, 1.1);
    }

    @FXML
    private void onExitCross() {
        setExitScaleTransition(deletionCross, 250, 1);
    }

    @FXML
    private void onEnterTick() {
        setEnterScaleTransition(deletionTick, 250, 1.1);
    }

    @FXML
    private void onExitTick() {
        setExitScaleTransition(deletionTick, 250, 1);
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
    @Warning(Warning.WarningType.IMPROVEMENT)
    private void onConfirmDeletion() {
        deletionCross.setDisable(true);
        deletionTick.setVisible(false);
        loadSpinnerOnDeletePane.setVisible(true);
        executorService.execute(() -> {
            try {
                cachedTransactionService.deleteTransactionById(Integer.parseInt(confirmIdLabel.getText().split(":")[1].strip()));
                Platform.runLater(() -> {
                    try {
                        refreshPage();
                        warnMessageInDelete.setVisible(false);
                    } catch (UnsupportedPojoException e) {
                        throw new RuntimeException(e);
                    }
                    // restore nodes after a succeesful deletion
                    deletionCross.setDisable(false);
                    deletionTick.setVisible(true);
                    loadSpinnerOnDeletePane.setVisible(false);
                    onCloseDeletionConfirmation();
                });
            } catch (NegativeDataException e) {
                warnMessageInDelete.setVisible(true);
                deletionCross.setDisable(false);
                deletionTick.setVisible(true);
                loadSpinnerOnDeletePane.setVisible(false);
            }
        });
    }

    @FXML
    private void onEnterBin1() {
        setEnterScaleTransition(binImages[0], 100, 1.1);
    }

    @FXML
    private void onEnterBin2() {
        setEnterScaleTransition(binImages[1], 100, 1.1);
    }

    @FXML
    private void onEnterBin3() {
        setEnterScaleTransition(binImages[2], 100, 1.1);
    }

    @FXML
    private void onEnterBin4() {
        setEnterScaleTransition(binImages[3], 100, 1.1);
    }

    @FXML
    private void onEnterBin5() {
        setEnterScaleTransition(binImages[4], 100, 1.1);
    }

    @FXML
    private void onEnterBin6() {
        setEnterScaleTransition(binImages[5], 100, 1.1);
    }

    @FXML
    private void onEnterBin7() {
        setEnterScaleTransition(binImages[6], 100, 1.1);
    }

    @FXML
    private void onExitBin1() {
        setExitScaleTransition(binImages[0], 100, 1);
    }

    @FXML
    private void onExitBin2() {
        setExitScaleTransition(binImages[1], 100, 1);
    }

    @FXML
    private void onExitBin3() {
        setExitScaleTransition(binImages[2], 100, 1);
    }

    @FXML
    private void onExitBin4() {
        setExitScaleTransition(binImages[3], 100, 1);
    }

    @FXML
    private void onExitBin5() {
        setExitScaleTransition(binImages[4], 100, 1);
    }

    @FXML
    private void onExitBin6() {
        setExitScaleTransition(binImages[5], 100, 1);
    }

    @FXML
    private void onExitBin7() {
        setExitScaleTransition(binImages[6], 100, 1);
    }

    @FXML
    private void onPressBin1() {
        setExitScaleTransition(binImage1, 100, 0.9);
    }

    @FXML
    private void onPressBin2() {
        setExitScaleTransition(binImage2, 100, 0.9);
    }

    @FXML
    private void onPressBin3() {
        setExitScaleTransition(binImage3, 100, 0.9);
    }

    @FXML
    private void onPressBin4() {
        setExitScaleTransition(binImage4, 100, 0.9);
    }

    @FXML
    private void onPressBin5() {
        setExitScaleTransition(binImage5, 100, 0.9);
    }

    @FXML
    private void onPressBin6() {
        setExitScaleTransition(binImage6, 100, 0.9);
    }

    @FXML
    private void onPressBin7() {
        setExitScaleTransition(binImage7, 100, 0.9);
    }

    @FXML
    private void onReleaseBin1() {
        setEnterScaleTransition(binImage1, 100, 1.1);
    }

    @FXML
    private void onReleaseBin2() {
        setEnterScaleTransition(binImage2, 100, 1.1);
    }

    @FXML
    private void onReleaseBin3() {
        setEnterScaleTransition(binImage3, 100, 1.1);
    }

    @FXML
    private void onReleaseBin4() {
        setEnterScaleTransition(binImage4, 100, 1.3);
    }

    @FXML
    private void onReleaseBin5() {
        setEnterScaleTransition(binImage5, 100, 1.1);
    }

    @FXML
    private void onReleaseBin6() {
        setEnterScaleTransition(binImage6, 100, 1.1);
    }

    @FXML
    private void onReleaseBin7() {
        setEnterScaleTransition(binImage7, 100, 1.1);
    }

    private void setEnterScaleTransition(ImageView imageView, int duration, double size) {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(imageView, duration, size);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    private void setExitScaleTransition(ImageView imageView, int duration, double size) {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(imageView, duration, size);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    private void setPressScaleTransition(boolean isPressAmount, ArrowStatus currentStatus) throws UnsupportedPojoException {
        if (!isArrowRotating) {
            if (isPressAmount) {
                switch (currentStatus) {
                    case HORIZONTAL -> {
                        isAmountAscend = false;
                        if (isAll && !isRestock && !isTaken) {
                            sortBy = SortBy.ALLAMOUNTDESCEND;
                        } else if (!isAll && isRestock && !isTaken) {
                            sortBy = SortBy.RESTOCKAMOUNTDESCEND;
                        } else if (!isAll && !isRestock && isTaken) {
                            sortBy = SortBy.TAKENAMOUNTDESCEND;
                        }
                        sortByAmount.setRotate(0);
                        sortByAmount.setImage(arrowImage);
                        sortByDate.setImage(horizontalLineImage);
                        amountArrow = ArrowStatus.DOWN;
                        dateArrow = ArrowStatus.HORIZONTAL;
                        refreshPage();
                    }
                    case DOWN -> {
                        isArrowRotating = true;
                        RotateTransition rotateTransition = RotationUtils.getRotationTransitionFromTo(sortByAmount, 200, 0, -180);
                        rotateTransition.setOnFinished(event -> {
                            amountArrow = ArrowStatus.UP;
                            isAmountAscend = true;
                            if (isAll && !isRestock && !isTaken) {
                                sortBy = SortBy.ALLAMOUNTASCEND;
                            } else if (!isAll && isRestock && !isTaken) {
                                sortBy = SortBy.RESTOCKAMOUNTASCEND;
                            } else if (!isAll && !isRestock && isTaken) {
                                sortBy = SortBy.TAKENAMOUNTASCEND;
                            }
                            try {
                                refreshPage();
                            } catch (UnsupportedPojoException e) {
                                throw new RuntimeException(e);
                            }
                            isArrowRotating = false;
                        });
                        rotateTransition.play();
                    }
                    case UP -> {
                        RotateTransition rotateTransition = RotationUtils.getRotationTransitionFromTo(sortByAmount, 200, -180, 0);
                        rotateTransition.setOnFinished(event -> {
                            amountArrow = ArrowStatus.DOWN;
                            isAmountAscend = false;
                            if (isAll && !isRestock && !isTaken) {
                                sortBy = SortBy.ALLAMOUNTDESCEND;
                            } else if (!isAll && isRestock && !isTaken) {
                                sortBy = SortBy.RESTOCKAMOUNTDESCEND;
                            } else if (!isAll && !isRestock && isTaken) {
                                sortBy = SortBy.TAKENAMOUNTDESCEND;
                            }
                            try {
                                refreshPage();
                            } catch (UnsupportedPojoException e) {
                                throw new RuntimeException(e);
                            }
                            isArrowRotating = false;
                        });
                        rotateTransition.play();
                    }
                }
            } else {
                switch (currentStatus) {
                    case HORIZONTAL -> {
                        isDateAscend = false;
                        if (isAll && !isRestock && !isTaken) {
                            sortBy = SortBy.ALLDATEDESCEND;
                        } else if (!isAll && isRestock && !isTaken) {
                            sortBy = SortBy.RESTOCKDATEDESCEND;
                        } else if (!isAll && !isRestock && isTaken) {
                            sortBy = SortBy.TAKENDATEDESCEND;
                        }
                        sortByAmount.setImage(horizontalLineImage);
                        sortByDate.setRotate(0);
                        sortByDate.setImage(arrowImage);
                        amountArrow = ArrowStatus.HORIZONTAL;
                        dateArrow = ArrowStatus.DOWN;
                        refreshPage();
                    }
                    case DOWN -> {
                        isArrowRotating = true;
                        RotateTransition rotateTransition = RotationUtils.getRotationTransitionFromTo(sortByDate, 200, 0, -180);
                        rotateTransition.setOnFinished(event -> {
                            dateArrow = ArrowStatus.UP;
                            isDateAscend = true;
                            if (isAll && !isRestock && !isTaken) {
                                sortBy = SortBy.ALLDATEASCEND;
                            } else if (!isAll && isRestock && !isTaken) {
                                sortBy = SortBy.RESTOCKDATEASCEND;
                            } else if (!isAll && !isRestock && isTaken) {
                                sortBy = SortBy.TAKENDATEASCEND;
                            }
                            try {
                                refreshPage();
                            } catch (UnsupportedPojoException e) {
                                throw new RuntimeException(e);
                            }
                            isArrowRotating = false;
                        });
                        rotateTransition.play();
                    }
                    case UP -> {
                        RotateTransition rotateTransition = RotationUtils.getRotationTransitionFromTo(sortByDate, 200, -180, 0);
                        rotateTransition.setOnFinished(event -> {
                            dateArrow = ArrowStatus.DOWN;
                            isDateAscend = false;
                            if (isAll && !isRestock && !isTaken) {
                                sortBy = SortBy.ALLDATEDESCEND;
                            } else if (!isAll && isRestock && !isTaken) {
                                sortBy = SortBy.RESTOCKDATEDESCEND;
                            } else if (!isAll && !isRestock && isTaken) {
                                sortBy = SortBy.TAKENDATEDESCEND;
                            }
                            try {
                                refreshPage();
                            } catch (UnsupportedPojoException e) {
                                throw new RuntimeException(e);
                            }
                            isArrowRotating = false;
                        });
                        rotateTransition.play();
                    }
                }
            }
        }
    }

    @FXML
    private void onEdit1() {
        setTransactionDetails(0);
    }

    @FXML
    private void onEdit2() {
        setTransactionDetails(1);
    }

    @FXML
    private void onEdit3() {
        setTransactionDetails(2);
    }

    @FXML
    private void onEdit4() {
        setTransactionDetails(3);
    }

    @FXML
    private void onEdit5() {
        setTransactionDetails(4);
    }

    @FXML
    private void onEdit6() {
        setTransactionDetails(5);
    }

    @FXML
    private void onEdit7() {
        setTransactionDetails(6);
    }

    /**
     * set transaction attributes
     *
     * @param row transaction entity at which row needs to be modified
     */
    private void setTransactionDetails(int row) {
        originalTransaciton = currentPageList.get(row); // pass a temporary transaction instance
        if (currentPageList.get(row).getStatus().equals("RESTOCK")) {
            transactionStatusInDetails.setText("RESTOCK");
        } else {
            transactionStatusInDetails.setText("TAKEN");
        }
        transactionIdInDetails.setText(String.valueOf(currentPageList.get(row).getID()));
        transactionNameInDetails.setText(currentPageList.get(row).getItemName());
        transactionDateInDetails.setText(currentPageList.get(row).getTransactionTime());
        staffNameInDetails.setText(currentPageList.get(row).getStaffName());
        transactionAmountInDetails.setText(String.valueOf(currentPageList.get(row).getUnit()));
        purposeTextInDetails.setText(currentPageList.get(row).getPurpose());
        descriptionDialog.setOpacity(0);
        descriptionDialog.setVisible(true);
        blockPane.setVisible(true);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(descriptionDialog, 300, 0, 1);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(descriptionDialog, 300, -200, 0);
        translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
        fadeTransition.play();
        translateTransition.play();
    }

    /**
     * close transaction modification page
     */
    @FXML
    private void onCloseDescriptionDialog() {
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(descriptionDialog, 300, 1, 0);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(descriptionDialog, 300, 0, -200);
        translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
        translateTransition.setOnFinished(event -> {
            descriptionDialog.setVisible(false);
            blockPane.setVisible(false);
        });
        fadeTransition.play();
        translateTransition.play();
        warnMessageInDetails.setVisible(false);
    }

    /**
     * modify and overwrite new staff or description fields to the specified transaction
     */
    @FXML
    private void onClickApplyInDetails() {
        cargoDialogApplyButton.setVisible(false);
        loadSpinnerInDetails.setVisible(true);
        executorService.execute(() -> {
            Transaction transaction;
            try {
                transaction = encapsulateCurrentStaffData();
                cachedTransactionService.updateTransaction(transaction);
            } catch (Exception e) {
                warnMessageInDetails.setVisible(true);
            } finally {
                Platform.runLater(() -> {
                    loadSpinnerInDetails.setVisible(false);
                    cargoDialogApplyButton.setVisible(true);
                    try {
                        refreshPage();
                    } catch (UnsupportedPojoException e) {
                        throw new RuntimeException(e);
                    }
                });

            }
        });
    }

    /**
     * encapsulate a new transaction which is going to be overwritten
     *
     * @return overrided transaction
     * @throws EmptyValueException some not-null values are left empty
     */
    private Transaction encapsulateCurrentStaffData() throws EmptyValueException {
        Transaction transaction = new Transaction();
        transaction = originalTransaciton;
        if (staffNameInDetails.getText().isBlank()) {
            throw new EmptyValueException("Empty input values in staff name section");
        }
        transaction.setStaffName(staffNameInDetails.getText());
        transaction.setPurpose(purposeTextInDetails.getText());
        return transaction;
    }

    /**
     * clear the properties of the new transaction from all text fields
     */
    @FXML
    private void onClickClearInAdd() {
        newItemFilterComboBox.clear();
        newUnitTextField.clear();
        newStaffFilterComboBox.clear();
        datePicker.clear();
        descriptionTextArea.clear();
        warnMessageInAdd.setVisible(false);
    }

    /**
     * close the pane of adding new transaction
     */
    @FXML
    private void onClickOkayInAdd() {
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(addTransactionPane, 300, 1, 0);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(addTransactionPane, 300, 0, -170);
        translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
        translateTransition.setOnFinished(event -> {
            addTransactionPane.setVisible(false);
            blockPane.setVisible(false);
        });
        fadeTransition.play();
        translateTransition.play();
    }

    @FXML
    private void onScrolled(ScrollEvent event) {
        if (event.getDeltaY() < 0) {
            transactionPagination.setCurrentPageIndex(transactionPagination.getCurrentPageIndex() + 1);
        }
        if (event.getDeltaY() > 0) {
            transactionPagination.setCurrentPageIndex(transactionPagination.getCurrentPageIndex() - 1);
        }
    }

    @FXML
    private void onEnterTransactionPane1() {
        binImages[0].setVisible(true);
    }

    @FXML
    private void onEnterTransactionPane2() {
        binImages[1].setVisible(true);
    }

    @FXML
    private void onEnterTransactionPane3() {
        binImages[2].setVisible(true);
    }

    @FXML
    private void onEnterTransactionPane4() {
        binImages[3].setVisible(true);
    }

    @FXML
    private void onEnterTransactionPane5() {
        binImages[4].setVisible(true);
    }

    @FXML
    private void onEnterTransactionPane6() {
        binImages[5].setVisible(true);
    }

    @FXML
    private void onEnterTransactionPane7() {
        binImages[6].setVisible(true);
    }

    @FXML
    private void onExitTransactionPane1() {
        if (!isBlockPaneOpen) {
            binImages[0].setVisible(false);
        }
    }

    @FXML
    private void onExitTransactionPane2() {
        if (!isBlockPaneOpen) {
            binImages[1].setVisible(false);
        }
    }

    @FXML
    private void onExitTransactionPane3() {
        if (!isBlockPaneOpen) {
            binImages[2].setVisible(false);
        }
    }

    @FXML
    private void onExitTransactionPane4() {
        if (!isBlockPaneOpen) {
            binImages[3].setVisible(false);
        }
    }

    @FXML
    private void onExitTransactionPane5() {
        if (!isBlockPaneOpen) {
            binImages[4].setVisible(false);
        }
    }

    @FXML
    private void onExitTransactionPane6() {
        if (!isBlockPaneOpen) {
            binImages[5].setVisible(false);
        }
    }

    @FXML
    private void onExitTransactionPane7() {
        if (!isBlockPaneOpen) {
            binImages[6].setVisible(false);
        }
    }

    /**
     * add a new transaction to database
     */
    @FXML
    private void onClickApplyInAdd() {
        if (!isValidated()) {
            warnMessageInAdd.setText("Insertion Failed. \r\nPlease check your data are valid.");
            warnMessageInAdd.setVisible(true);
        } else {
            isAdditionSucceed = true;
            applyButtonInAdd.setVisible(false);
            loadSpinnerInAdd.setVisible(true);
            okayButton.setDisable(true);
            clearButton.setDisable(true);
            newTransactionID = getNumOfTransaction() + 1;
            newItemName = newItemFilterComboBox.getText();
            newStaffName = newStaffFilterComboBox.getText();
            newUnitAmount = Integer.valueOf(newUnitTextField.getText());
            newTransactionStatus = newStatusComboBox.getText();
            transactionDescription = descriptionTextArea.getText();
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
            executorService.execute(() -> {
                try {
                    addNewTransaction(newTransactionStatus, newTransactionID, newItemName, newStaffName, newUnitAmount, transactionDate, transactionDescription);
                    try{
                        cachedTransactionService.addNewTransaction(newTransaction);
                    }catch (DataNotFoundException dataNotFoundException){
                        Platform.runLater(() -> {
                            warnMessageInAdd.setText("Insertion Failed. \r\nThis item does not exit in our warehouse!");
                            warnMessageInAdd.setVisible(true);
                        });
                        isAdditionSucceed = false;
                    }catch (NegativeDataException negativeDataException){
                        Platform.runLater(() -> {
                            warnMessageInAdd.setText("Insertion Failed. \r\nInsufficient item amount left");
                            warnMessageInAdd.setVisible(true);
                        });
                        isAdditionSucceed = false;
                    }
                    Platform.runLater(() -> {
                        try {
                            refreshPage();
                        } catch (UnsupportedPojoException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (Exception e) {
                    Platform.runLater(() -> {
                        warnMessageInAdd.setText("Insertion Failed. \r\nPlease check your inserted data are indeed valid.");
                        warnMessageInAdd.setVisible(true);
                    });
                    isAdditionSucceed = false;
                } finally {
                    // restore nodes after a succeesful addition
                    applyButtonInAdd.setVisible(true);
                    loadSpinnerInAdd.setVisible(false);
                    if (isAdditionSucceed) {
                        warnMessageInAdd.setVisible(false);
                    }
                    okayButton.setDisable(false);
                    clearButton.setDisable(false);
                }
            });
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
        if (!newItemFilterComboBox.getText().equals("") && !newStaffFilterComboBox.getText().equals("") && !newUnitTextField.getText().equals("") && !newStatusComboBox.getText().equals("")) {
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

    /**
     * search an indicated staff name
     */
    @FXML
    private void onClickStaffSearch() {
        setSearchProperty(true);
        DataUtils.appPage2Controller.setSearchProperty(true);
    }

    /**
     * search an indicated cargo name
     */
    @FXML
    private void onClickCargoSearch() {
        setSearchProperty(false);
        DataUtils.appPage2Controller.setSearchProperty(false);
    }

    /**
     * indicates if the keyword is related to cargo or staff
     *
     * @param isStaff searching staff if true
     */
    public void setSearchProperty(boolean isCargo) {
        if (isCargo) {
            // convert item to staff icon
            searchSwitchingBlockPane.toFront();
            cargoSearchPane.setVisible(true);
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(staffSearchPane, 300, 0, -15);
            TranslateTransition translateTransition1 = TranslateUtils.getTranslateTransitionFromToY(cargoSearchPane, 300, 15, 0);
            ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionFromToY(staffSearchPane, 300, 1, 0);
            ScaleTransition scaleTransition1 = ScaleUtils.getScaleTransitionFromToY(cargoSearchPane, 300, 0, 1);
            scaleTransition.setOnFinished(event -> {
                searchSwitchingBlockPane.toBack();
                staffSearchPane.setVisible(false);
            });
            translateTransition.play();
            translateTransition1.play();
            scaleTransition.play();
            scaleTransition1.play();
            isSearchingStaff = false;
        } else {
            // convert staff to item icon
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
            isSearchingStaff = true;
        }
    }

    /**
     * set a keyword into the input field
     *
     * @param keyword searched keyword
     */
    public void setKeyword(String keyword) {
        searchField.setText(keyword);
    }

    /**
     * search and return a list corresponds to the input item or staff keyword
     */
    @FXML
    public void onClickSearch() throws UnsupportedPojoException {
        refreshPage();
    }

    /**
     * set all prompt staff names from database
     */
    private void setPromptTextForStaff() {
        List<List<Staff>> staffList = StaffCachedUtils.getLists(StaffCachedUtils.listType.ALL);
        List<String> resultList = new ArrayList<>();
        staffList.forEach(new Consumer<List<Staff>>() {
            @Override
            public void accept(List<Staff> staffList) {
                staffList.forEach(new Consumer<Staff>() {
                    @Override
                    public void accept(Staff staff) {
                        resultList.add(staff.getStaffName());
                    }
                });
            }
        });
        newStaffFilterComboBox.setItems(FXCollections.observableList(resultList));
        staffNameInDetails.setItems(FXCollections.observableList(resultList));
    }

    /**
     * set all prompt item names from database
     */
    private void setPromptTextForRegulatory() {
        List<List<Item>> itemList = CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL);
        List<String> resultList = new ArrayList<>();
        itemList.forEach(new Consumer<List<Item>>() {
            @Override
            public void accept(List<Item> items) {
                items.forEach(new Consumer<Item>() {
                    @Override
                    public void accept(Item item) {
                        resultList.add(item.getItemName());
                    }
                });
            }
        });
        newItemFilterComboBox.setItems(FXCollections.observableList(resultList));
    }

    /**
     * reload cache from database
     */
    @FXML
    public void onRefresh() throws UnsupportedPojoException {
        cachedTransactionService.updateAllCachedTransactionData();
        refreshPage();
    }
}