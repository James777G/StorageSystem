package org.maven.apache.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import org.maven.apache.MyLauncher;
import org.maven.apache.service.DateTransaction.DateTransactionService;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.ScaleUtils;
import org.maven.apache.dateTransaction.DateTransaction;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCargoPageController implements Initializable {

    @FXML
    private ImageView crossImage;

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
    private Label takenRestockUnitLabel;

    private String newItemName;

    private String newStaffName;

    private int newCurrentUnitAmount;

    private int newTakenRestockUnitAmount;

    private int newItemID;

    private boolean isAddingTaken = false;

    private final DateTransactionService newTransactionService = MyLauncher.context.getBean("dateTransactionService", DateTransactionService.class);

    private DateTransaction newTransaction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onClickTakenSelectButton();
    }

    /**
     * add this new transaction record to the database
     */
    @FXML
    private void onPostNewTransaction(){
        newItemID = newTransactionService.selectAll().size() + 1;
        newItemName = newItemTextField.getText();
        newStaffName = newStaffTextField.getText();
        newTakenRestockUnitAmount = Integer.valueOf(newTakenRestockUnitTextField.getText());
        newCurrentUnitAmount = Integer.valueOf(newCurrentUnitTextField.getText());
        newTransaction = new DateTransaction();
        if (isAddingTaken){
            // adding taken cargo
            addNewTransaction(newItemID, newItemName, newStaffName, newTakenRestockUnitAmount, 0, newCurrentUnitAmount, "1942", "**");
        }else{
            // adding restock cargo
            addNewTransaction(newItemID, newItemName, newStaffName, 0, newTakenRestockUnitAmount, newCurrentUnitAmount, "1942", "**");
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Item ID: " + newTransaction.getItemID());
        System.out.println("Item name: " + newTransaction.getItemName());
        System.out.println("Staff name: " + newTransaction.getStaffName());
        System.out.println("Add unit: " + newTransaction.getAddUnit());
        System.out.println("Removed unit: " + newTransaction.getRemoveUnit());
        System.out.println("Current uni: " + newTransaction.getCurrentUnit());
        System.out.println("Record time: " + newTransaction.getRecordTime());
        System.out.println("Purpose: " + newTransaction.getPurpose());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        newTransactionService.addTransaction(newTransaction);
    }

    /**
     * pass field properties to the new transaction
     *
     * @param itemID new item ID (incremented by 1 pursuant to the amount of current transactions)
     * @param itemName new cargo name that is transferred
     * @param staffName staff name who controls this transaction
     * @param addUnit restock amount
     * @param removeUnit taken amount
     * @param currentUnit current amount
     * @param recordTime time when this new transaction happens
     * @param purpose ??
     */
    private void addNewTransaction(int itemID, String itemName, String staffName, int addUnit, int removeUnit, int currentUnit, String recordTime, String purpose){
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
    private void onClickTakenSelectButton(){
        enableNodes(onSelectTakenPane);
        disableNodes(onSelectRestockPane);
        takenRestockUnitLabel.setText("Taken Quantity");
        isAddingTaken = true;
    }

    /**
     * add new restock amount
     */
    @FXML
    private void onClickRestockSelectButton(){
        enableNodes(onSelectRestockPane);
        disableNodes(onSelectTakenPane);
        takenRestockUnitLabel.setText("Restock Quantity");
        isAddingTaken = false;
    }

    /**
     * close the pane of adding new transaction
     */
    @FXML
    private void onClickCross(){
        DataUtils.editCargoPane.setVisible(false);
        DataUtils.publicTransactionBlockPane.setVisible(false);
    }

    @FXML
    private void onEnterCross(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(crossImage, 500, 1.5);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitCross(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(crossImage, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onPressCross(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(crossImage, 500, 1.3);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onReleaseCross(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(crossImage, 500, 1.5);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    private void disableNodes(@NotNull Node node){
        node.setOpacity(0);
        node.setVisible(false);
        node.setPickOnBounds(false);
    }

    private void enableNodes(@NotNull Node node){
        node.setOpacity(1);
        node.setVisible(true);
        node.setPickOnBounds(true);
    }

}
