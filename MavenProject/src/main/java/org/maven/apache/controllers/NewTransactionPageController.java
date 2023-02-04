package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXPagination;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.MyLauncher;
import org.maven.apache.service.transaction.CachedTransactionService;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.ScaleUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class NewTransactionPageController implements Initializable {

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
    private Label statusLabel1, statusLabel2, statusLabel3, statusLabel4, statusLabel5, statusLabel6, statusLabel7;

    @FXML
    private Label idLabel1, idLabel2, idLabel3, idLabel4, idLabel5, idLabel6, idLabel7;

    @FXML
    private Label staffLabel1, staffLabel2, staffLabel3, staffLabel4, staffLabel5, staffLabel6, staffLabel7;

    @FXML
    private Label cargoLabel1, cargoLabel2, cargoLabel3, cargoLabel4, cargoLabel5, cargoLabel6, cargoLabel7;

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

    private Label[] statusLabelArray = new Label[7];

    private Label[] idLabelArray = new Label[7];

    private Label[] staffLabelArray = new Label[7];

    private Label[] cargoLabelArray = new Label[7];

    private Label[] amountLabelArray = new Label[7];

    private Label[] dateLabelArray = new Label[7];

    private final CachedTransactionService cachedTransactionService = MyLauncher.context.getBean("cachedTransactionService", CachedTransactionService.class);

    private List<List<Transaction>> dateAscendList, dateDescendList, amountAscendList, amountDescendList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cachedTransactionService.updateAllCachedTransactionData();
        editCargoPane.getChildren().add(DataUtils.editCargoPane);
        DataUtils.editCargoPane.setVisible(false);
        initializeLabels();
        blockPane.setVisible(false);
        DataUtils.publicTransactionBlockPane = blockPane;
        deletionConfirmationDialog.setVisible(false);
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
        statusLabelArray[4] = statusLabel5;
        statusLabelArray[5] = statusLabel6;
        statusLabelArray[6] = statusLabel7;
        // initialize transaction id labels
        idLabelArray[0] = idLabel1;
        idLabelArray[1] = idLabel2;
        idLabelArray[2] = idLabel3;
        idLabelArray[3] = idLabel4;
        idLabelArray[4] = idLabel5;
        idLabelArray[5] = idLabel6;
        idLabelArray[6] = idLabel7;
        // initialize staff labels
        staffLabelArray[0] = staffLabel1;
        staffLabelArray[1] = staffLabel2;
        staffLabelArray[2] = staffLabel3;
        staffLabelArray[3] = staffLabel4;
        staffLabelArray[4] = staffLabel5;
        staffLabelArray[5] = staffLabel6;
        staffLabelArray[6] = staffLabel7;
        // initailize cargo labels
        cargoLabelArray[0] = cargoLabel1;
        cargoLabelArray[1] = cargoLabel2;
        cargoLabelArray[2] = cargoLabel3;
        cargoLabelArray[3] = cargoLabel4;
        cargoLabelArray[4] = cargoLabel5;
        cargoLabelArray[5] = cargoLabel6;
        cargoLabelArray[6] = cargoLabel7;
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
     * sort the list by date
     */
    @FXML
    private void onClickDate() {

    }

    /**
     * sort the list by amount (all unit, restock unit, taken unit)
     */
    @FXML
    private void onClickAmount() {

    }

    /**
     * show the content of transaction list from current page when the pagination is clicked
     */
    @FXML
    private void onClickPagination() {
//        int currentPage = transactionPagination.getCurrentPage();
//        ExecutorService threadPoolExecutor = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);
//        threadPoolExecutor.execute(() -> setTransactionList(currentPage));
//        setUnitStatus();
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
