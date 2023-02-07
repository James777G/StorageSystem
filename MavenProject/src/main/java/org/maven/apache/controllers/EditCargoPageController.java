package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import org.maven.apache.MyLauncher;
import org.maven.apache.service.DateTransaction.DateTransactionService;
import org.maven.apache.service.transaction.CachedTransactionService;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.ScaleUtils;
import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.utils.TransactionCachedUtils;

import java.awt.event.KeyAdapter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class EditCargoPageController implements Initializable {

    @FXML
    private ImageView crossImage;

    @FXML
    private ImageView descriptionImage;

    @FXML
    private AnchorPane onSelectRestockPane;

    @FXML
    private AnchorPane onSelectTakenPane;

    @FXML
    private AnchorPane descriptionBlockPane;

    @FXML
    private MFXTextField newItemTextField;

    @FXML
    private MFXTextField newStaffTextField;

    @FXML
    private MFXTextField newUnitTextField;

    @FXML
    private MFXDatePicker datePicker = new MFXDatePicker(Locale.ENGLISH);

    @FXML
    private MFXGenericDialog descriptionDialog;

    @FXML
    private Label notificationLabel;

    @FXML
    private TextArea descriptionTextArea;

    private String newItemName;

    private String newStaffName;

    private String transactionDate;

    private String transactionDescription = "";

    private int newUnitAmount;

    private int newTransactionID;

    private int numOfTransaction;

    private boolean isStatusTaken = false;

   // private final DateTransactionService newTransactionService = MyLauncher.context.getBean("dateTransactionService", DateTransactionService.class);

    private final CachedTransactionService newCachedTransactionService = MyLauncher.context.getBean("cachedTransactionService", CachedTransactionService.class);

    private Transaction newTransaction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newCachedTransactionService.updateAllCachedTransactionData();
        onClickTakenSelectButton();
        setInputValidation(newUnitTextField);
        descriptionDialog.setVisible(false);
        descriptionBlockPane.setVisible(false);
    }

    /**
     * get the total amount of transaction to increment id
     *
     * @return amount of transaction
     */
    private int getNumOfTransaction(){
        int count = 0;
        for (int i = 0; i < TransactionCachedUtils.getLists(TransactionCachedUtils.listType.DATE_DESC_7).size(); i++){
            for (int j = 0; j < TransactionCachedUtils.getLists(TransactionCachedUtils.listType.DATE_DESC_7).get(i).size(); j++){
                count++;
            }
        }
        return count;
    }


    /**
     * add this new transaction record to the database
     */
    @FXML
    private void onPostNewTransaction() {
        // check validation
        if (!isValidated()) {
            notificationLabel.setText("Empty fields");
        } else {
            newTransactionID = getNumOfTransaction() + 1;
            newItemName = newItemTextField.getText();
            newStaffName = newStaffTextField.getText();
            newUnitAmount = Integer.valueOf(newUnitTextField.getText());
            if (datePicker.getText().equals("")){
                // return current date and time
                LocalDate dateTime = LocalDate.now();
                transactionDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }else{
                // return chosen date from calendar
                LocalDate dateTime = datePicker.getValue();
                transactionDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            newTransaction = new Transaction();
            if (isStatusTaken) {
                // adding taken cargo
                addNewTransaction("TAKEN", newTransactionID, newItemName, newStaffName, newUnitAmount, transactionDate, transactionDescription);
            } else {
                // adding restock cargo
                addNewTransaction("RESTOCK", newTransactionID, newItemName, newStaffName, newUnitAmount, transactionDate, transactionDescription);
            }
            newCachedTransactionService.addNewTransaction(newTransaction);
            notificationLabel.setText("Transaction added successfully");
        }
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
    private void setInputValidation(MFXTextField textField) {
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
     * @param TransactionID      new transaction ID (incremented by 1 pursuant to the amount of current transactions)
     * @param itemName    new cargo name that is transferred
     * @param staffName   staff name who controls this transaction
     * @param unit     restock/taken amount
     * @param date  date of making this new transaction
     * @param purpose     transaction details
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
     * add new taken amount
     */
    @FXML
    private void onClickTakenSelectButton() {
        enableNodes(onSelectTakenPane);
        disableNodes(onSelectRestockPane);
        newUnitTextField.setFloatingText("Taken Quantity");
        isStatusTaken = true;
    }

    /**
     * add new restock amount
     */
    @FXML
    private void onClickRestockSelectButton() {
        enableNodes(onSelectRestockPane);
        disableNodes(onSelectTakenPane);
        newUnitTextField.setFloatingText("Restock Quantity");
        isStatusTaken = false;
    }

    /**
     * close the pane of adding new transaction
     */
    @FXML
    private void onClickCross() {
        DataUtils.editCargoPane.setVisible(false);
        DataUtils.publicTransactionBlockPane.setVisible(false);
        notificationLabel.setText("");
    }

    @FXML
    private void onEnterCross() {
        setScaleTransition(crossImage, 300, 1.5);
    }

    @FXML
    private void onExitCross() {
        setScaleTransition(crossImage, 300, 1);
    }

    @FXML
    private void onPressCross() {
        setScaleTransition(crossImage, 300, 1.1);
    }

    @FXML
    private void onReleaseCross() {
        setScaleTransition(crossImage, 300, 1.5);
    }

    private void disableNodes(@NotNull Node node) {
        node.setOpacity(0);
        node.setVisible(false);
        node.setPickOnBounds(false);
    }

    private void enableNodes(@NotNull Node node) {
        node.setOpacity(1);
        node.setVisible(true);
        node.setPickOnBounds(true);
    }

    /**
     * show the page of adding new description
     */
    @FXML
    private void onClickDescription(){
        descriptionDialog.setVisible(true);
        descriptionBlockPane.setVisible(true);
    }

    @FXML
    private void onEnterDescription(){
        setScaleTransition(descriptionImage, 300, 1.3);
    }

    @FXML
    private void onExitDescription(){
        setScaleTransition(descriptionImage, 300, 1);
    }

    @FXML
    private void onPressDescription(){
        setScaleTransition(descriptionImage, 300, 1.1);
    }

    @FXML
    private void onReleaseDescription(){
        setScaleTransition(descriptionImage, 300, 1.3);
    }

    /**
     * perform scale transition for a specific image view
     *
     * @param imageView image needs to be scaled
     * @param duration time needed for transition to be done
     * @param size transition scale size
     */
    private void setScaleTransition(ImageView imageView, int duration, double size){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(imageView, duration, size);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    /**
     * parse description to the new transaction and close the pane
     */
    @FXML
    private void onSaveDescription(){
        transactionDescription = descriptionTextArea.getText();
        descriptionDialog.setVisible(false);
        descriptionBlockPane.setVisible(false);
    }

}
