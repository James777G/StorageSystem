package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import org.maven.apache.MyLauncher;
import org.maven.apache.service.DateTransaction.DateTransactionService;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.ScaleUtils;
import org.maven.apache.dateTransaction.DateTransaction;

import java.awt.event.KeyAdapter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private MFXTextField newItemTextField;

    @FXML
    private MFXTextField newStaffTextField;

    @FXML
    private MFXTextField newCurrentUnitTextField;

    @FXML
    private MFXTextField newTakenRestockUnitTextField;

    @FXML
    private MFXDatePicker datePicker;

    @FXML
    private Label notificationLabel;

    private String newItemName;

    private String newStaffName;

    private String transactionDate;

    private int newCurrentUnitAmount;

    private int newTakenRestockUnitAmount;

    private int newItemID;

    private boolean isAddingTaken = false;

    private final DateTransactionService newTransactionService = MyLauncher.context.getBean("dateTransactionService", DateTransactionService.class);

    private DateTransaction newTransaction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onClickTakenSelectButton();
        setInputValidation(newCurrentUnitTextField);
        setInputValidation(newTakenRestockUnitTextField);
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
            newItemID = newTransactionService.selectAll().size() + 1;
            newItemName = newItemTextField.getText();
            newStaffName = newStaffTextField.getText();
            newTakenRestockUnitAmount = Integer.valueOf(newTakenRestockUnitTextField.getText());
            newCurrentUnitAmount = Integer.valueOf(newCurrentUnitTextField.getText());
            if (datePicker.getText().equals("")){
                // return current date and time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                transactionDate = dtf.format(now);
            }else{
                // return chosen date from calendar
                transactionDate = datePicker.getText();
            }
            newTransaction = new DateTransaction();
            if (isAddingTaken) {
                // adding taken cargo
                addNewTransaction(newItemID, newItemName, newStaffName, newTakenRestockUnitAmount, 0, newCurrentUnitAmount, transactionDate, "**");
            } else {
                // adding restock cargo
                addNewTransaction(newItemID, newItemName, newStaffName, 0, newTakenRestockUnitAmount, newCurrentUnitAmount, transactionDate, "**");
            }
            newTransactionService.addTransaction(newTransaction);
            notificationLabel.setText("Transaction added successfully");
        }
    }

    /**
     * check if input information is good to go
     *
     * @return true or false
     */
    private boolean isValidated() {
        if (!newItemTextField.getText().equals("") && !newStaffTextField.getText().equals("") && !newCurrentUnitTextField.getText().equals("") && !newTakenRestockUnitTextField.getText().equals("")) {
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
     * @param itemID      new item ID (incremented by 1 pursuant to the amount of current transactions)
     * @param itemName    new cargo name that is transferred
     * @param staffName   staff name who controls this transaction
     * @param addUnit     restock amount
     * @param removeUnit  taken amount
     * @param currentUnit current amount
     * @param recordTime  time when this new transaction happens
     * @param purpose     ??
     */
    private void addNewTransaction(int itemID, String itemName, String staffName, int addUnit, int removeUnit, int currentUnit, String recordTime, String purpose) {
        newTransaction = new DateTransaction();
        newTransaction.setItemID(itemID);
        newTransaction.setItemName(itemName);
        newTransaction.setStaffName(staffName);
        newTransaction.setAddUnit(addUnit);
        newTransaction.setRemoveUnit(removeUnit);
        newTransaction.setCurrentUnit(currentUnit);
        newTransaction.setRecordTime(recordTime);
        newTransaction.setPurpose(purpose);
    }

    /**
     * add new taken amount
     */
    @FXML
    private void onClickTakenSelectButton() {
        enableNodes(onSelectTakenPane);
        disableNodes(onSelectRestockPane);
        newTakenRestockUnitTextField.setFloatingText("Taken Quantity");
        isAddingTaken = true;
    }

    /**
     * add new restock amount
     */
    @FXML
    private void onClickRestockSelectButton() {
        enableNodes(onSelectRestockPane);
        disableNodes(onSelectTakenPane);
        newTakenRestockUnitTextField.setFloatingText("Restock Quantity");
        isAddingTaken = false;
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
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(crossImage, 300, 1.5);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitCross() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(crossImage, 300, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onPressCross() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(crossImage, 300, 1.1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onReleaseCross() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(crossImage, 300, 1.5);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
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

    @FXML
    private void onClickDescription(){

    }

    @FXML
    private void onEnterDescription(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(descriptionImage, 300, 1.3);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDescription(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(descriptionImage, 300, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onPressDescription(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(descriptionImage, 300, 1.1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onReleaseDescription(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(descriptionImage, 300, 1.3);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

}
