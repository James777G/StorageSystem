package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXPagination;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.MyLauncher;
import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.service.transaction.CachedTransactionService;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.ScaleUtils;
import org.maven.apache.utils.TransactionCachedUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class NewTransactionPageController implements Initializable {

    enum SortBy {
        DATEASCEND, DATEDESCEND, RESTOCKASCEND, RESTOCKDESCEND, TAKENASCEND, TAKENDDESCEND
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
    private JFXButton searchButton;

    @FXML
    private Label cargoLabel1, cargoLabel2, cargoLabel3, cargoLabel4, cargoLabel5, cargoLabel6, cargoLabel7;

    @FXML
    private Label idLabel1, idLabel2, idLabel3, idLabel4, idLabel5, idLabel6, idLabel7;

    @FXML
    private Label amountLabel1, amountLabel2, amountLabel3, amountLabel4, amountLabel5, amountLabel6, amountLabel7;

    @FXML
    private Label dateLabel1, dateLabel2, dateLabel3, dateLabel4, dateLabel5, dateLabel6, dateLabel7;

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
    private ImageView binImage1, binImage2, binImage3, binImage4, binImage5, binImage6, binImage7;

    @FXML
    private MFXGenericDialog deletionConfirmationDialog;

    @FXML
    private MFXTextField transactionSearchTextField;

    private Label[] cargoLabelArray = new Label[7];

    private Label[] idLabelArray = new Label[7];

    private Label[] amountLabelArray = new Label[7];

    private Label[] dateLabelArray = new Label[7];

    private final CachedTransactionService cachedTransactionService = MyLauncher.context.getBean("cachedTransactionService", CachedTransactionService.class);

    private List<List<Transaction>> sortedList;

    private List<Transaction> currentPageList;

    private SortBy sortBy = SortBy.DATEDESCEND;

    private boolean isAll = true;

    private boolean isRestock = false;

    private boolean isTaken = false;

    private boolean isDateAscend = false;

    private int currentPage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cachedTransactionService.updateAllCachedTransactionData();
        editCargoPane.getChildren().add(DataUtils.editCargoPane);
        DataUtils.editCargoPane.setVisible(false);
        initializeLabels();
        blockPane.setVisible(false);
        DataUtils.publicTransactionBlockPane = blockPane;
        deletionConfirmationDialog.setVisible(false);
        setPaginationPages(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.DATE_ASC_7));
        setSortCondition();
        onClickPagination();
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
    }

    /**
     * set how many pages do we need in total
     */
    private void setPaginationPages(List<List<Transaction>> list) {
        transactionPagination.setMaxPage(list.size());
    }


    /**
     * set the content of transaction list from current page when the pagination is clicked
     */
    @FXML
    private void onClickPagination() {
        currentPage = transactionPagination.getCurrentPage();
        currentPageList = sortedList.get(currentPage - 1);
        // set non-empty labels
        for (int i = 0; i < currentPageList.size(); i++) {
            cargoLabelArray[i].setText(currentPageList.get(i).getItemName());
            idLabelArray[i].setText(String.valueOf(currentPageList.get(i).getID()));
            amountLabelArray[i].setText(String.valueOf(currentPageList.get(i).getUnit()));
            dateLabelArray[i].setText(currentPageList.get(i).getTransactionTime());
        }
        // set empty labels
        if (currentPageList.size() != 7) {
            for (int j = 6; j >= currentPageList.size(); j--) {
                cargoLabelArray[j].setText("");
                idLabelArray[j].setText("");
                amountLabelArray[j].setText("");
                dateLabelArray[j].setText("");
            }
        }
        //setUnitStatus();
    }

    /**
     * get the transaction list by setting a sorting property
     */
    private void setSortCondition() {
        switch (sortBy) {
            case DATEASCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.DATE_ASC_7);
                break;
            case DATEDESCEND:
                sortedList = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.DATE_DESC_7);
                break;
//            case RESTOCKASCEND:
//                sortedList = dateTransactionService.pageAskedAddUnitAscend(currentPage, 4);
//                break;
//            case RESTOCKDESCEND:
//                sortedList = dateTransactionService.pageAskedAddUnitDescend(currentPage, 4);
//                break;
//            case TAKENASCEND:
//                sortedList = dateTransactionService.pageAskedRemoveUnitAscend(currentPage, 4);
//                break;
//            case TAKENDDESCEND:
//                sortedList = dateTransactionService.pageAskedRemoveUnitDescend(currentPage, 4);
//                break;
        }
    }

    /**
     * set the status (current unit, restock unit, taken unit) labels
     */
    private void setUnitStatus() {
//        if (isAll && !isRestock && !isTaken) {
//            for (int i = 0; i < 7; i++) {
//                statusLabelArray[i].setText(" Current Unit");
//                statusLabelArray[i].setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-background-radius: 5");
//                statusLabelArray[i].setPrefWidth(82);
//            }
//        } else if (!isAll && isRestock && !isTaken) {
//            for (int i = 0; i < 7; i++) {
//                statusLabelArray[i].setText(" Restock");
//                statusLabelArray[i].setStyle("-fx-background-color: #ddeab1#c7ddb5; -fx-text-fill: #759751; -fx-background-radius: 5");
//                statusLabelArray[i].setPrefWidth(56);
//            }
//        } else if (!isAll && !isRestock && isTaken) {
//            for (int i = 0; i < 7; i++) {
//                statusLabelArray[i].setText(" Taken");
//                statusLabelArray[i].setStyle("-fx-background-color: #feccc9; -fx-text-fill: #ff4137; -fx-background-radius: 5");
//                statusLabelArray[i].setPrefWidth(44);
//            }
//        }
    }

    /**
     * sort the list by date
     */
    @FXML
    private void onClickDate() {
        if (isDateAscend) {
            sortBy = SortBy.DATEDESCEND;
            isDateAscend = false;
        } else {
            sortBy = SortBy.DATEASCEND;
            isDateAscend = true;
        }
        setSortCondition();
        onClickPagination();
    }

    /**
     * sort the list by amount (all unit, restock unit, taken unit)
     */
    @FXML
    private void onClickAmount() {

    }
















    @FXML
    private void onClickAddButton() {
        DataUtils.editCargoPane.setVisible(true);
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
    private void onEnterAmount() {setScaleTransition(sortByAmount, 100, 1.3);}

    @FXML
    private void onExitAmount() {setScaleTransition(sortByAmount, 100, 1);}

    @FXML
    private void onPressedAmount() {setScaleTransition(sortByAmount, 100, 1.1);}

    @FXML
    private void onReleaseAmount() {setScaleTransition(sortByAmount, 100, 1.3);}

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
     * delete the 5th transaction
     */
    @FXML
    private void onClickBin5() {
        if (!idLabelArray[4].getText().equals("")){
            deletionConfirmationDialog.setVisible(true);
            blockPane.setVisible(true);
            setRemovalConfirmation(4);
        }
    }

    /**
     * delete the 6th transaction
     */
    @FXML
    private void onClickBin6() {
        if (!idLabelArray[5].getText().equals("")){
            deletionConfirmationDialog.setVisible(true);
            blockPane.setVisible(true);
            setRemovalConfirmation(5);
        }
    }

    /**
     * delete the 7th transaction
     */
    @FXML
    private void onClickBin7() {
        if (!idLabelArray[6].getText().equals("")){
            deletionConfirmationDialog.setVisible(true);
            blockPane.setVisible(true);
            setRemovalConfirmation(6);
        }
    }

    /**
     * set confirmation details before performing deletion
     *
     * @param row which row of transition list needs to be removed
     */
    private void setRemovalConfirmation(int row){
//        confirmStatusLabel.setText("Transaction status: " + statusLabelArray[row].getText());
//        confirmIdLabel.setText("Transaction id: " + idLabelArray[row].getText());
//        confirmStaffNameLabel.setText("Staff name: " + staffLabelArray[row].getText());
//        confirmCargoNameLabel.setText("Cargo name: " + cargoLabelArray[row].getText());
//        confirmCargoUnitLabel.setText(statusLabelArray[row].getText() + ": " + amountLabelArray[row].getText());
//        confirmDateLabel.setText("Date of completion: " + dateLabelArray[row].getText());
    }

    /**
     * user confirms to perform deletion
     */
    @FXML
    private void onConfirmDeletion(){
//        cachedTransactionService.deleteTransactionById();
//        confirmButton.setDisable(true);
//        deletionNotificationLabel.setText("Removal completed");
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
    private void onEnterBin5() {setScaleTransition(binImage5, 100, 1.3);}

    @FXML
    private void onEnterBin6() {setScaleTransition(binImage6, 100, 1.3);}

    @FXML
    private void onEnterBin7() {setScaleTransition(binImage7, 100, 1.3);}

    @FXML
    private void onExitBin1() {setScaleTransition(binImage1, 100, 1);}

    @FXML
    private void onExitBin2() {setScaleTransition(binImage2, 100, 1);}

    @FXML
    private void onExitBin3() {setScaleTransition(binImage3, 100, 1);}

    @FXML
    private void onExitBin4() {setScaleTransition(binImage4, 100, 1);}

    @FXML
    private void onExitBin5() {setScaleTransition(binImage5, 100, 1);}

    @FXML
    private void onExitBin6() {setScaleTransition(binImage6, 100, 1);}

    @FXML
    private void onExitBin7() {setScaleTransition(binImage7, 100, 1);}

    @FXML
    private void onPressBin1() {setScaleTransition(binImage1, 100, 1.1);}

    @FXML
    private void onPressBin2() {setScaleTransition(binImage2, 100, 1.1);}

    @FXML
    private void onPressBin3() {setScaleTransition(binImage3, 100, 1.1);}

    @FXML
    private void onPressBin4() {setScaleTransition(binImage4, 100, 1.1);}

    @FXML
    private void onPressBin5() {setScaleTransition(binImage5, 100, 1.1);}

    @FXML
    private void onPressBin6() {setScaleTransition(binImage6, 100, 1.1);}

    @FXML
    private void onPressBin7() {setScaleTransition(binImage7, 100, 1.1);}

    @FXML
    private void onReleaseBin1() {setScaleTransition(binImage1, 100, 1.3);}

    @FXML
    private void onReleaseBin2() {setScaleTransition(binImage2, 100, 1.3);}

    @FXML
    private void onReleaseBin3() {setScaleTransition(binImage3, 100, 1.3);}

    @FXML
    private void onReleaseBin4() {setScaleTransition(binImage4, 100, 1.3);}

    @FXML
    private void onReleaseBin5() {setScaleTransition(binImage5, 100, 1.3);}

    @FXML
    private void onReleaseBin6() {setScaleTransition(binImage6, 100, 1.3);}

    @FXML
    private void onReleaseBin7() {setScaleTransition(binImage7, 100, 1.3);}

    private void setScaleTransition(ImageView imageView, int duration, double size) {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(imageView, duration, size);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }


}
