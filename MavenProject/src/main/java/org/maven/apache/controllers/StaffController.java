package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXPagination;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.MyLauncher;
import org.maven.apache.service.staff.CachedStaffService;
import org.maven.apache.staff.Staff;
import org.maven.apache.utils.ScaleUtils;
import org.maven.apache.utils.StaffCachedUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class StaffController implements Initializable {

    private enum Status{
        ACTIVE, ALL, INACTIVE
    }

    private final ExecutorService executorService = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

    private final CachedStaffService staffService = MyLauncher.context.getBean("staffService", CachedStaffService.class);

    @FXML
    private Label staffNameOne, staffNameTwo, staffNameThree, staffNameFour, staffNameFive, staffNameSix, staffNameSeven;

    @FXML
    private Label staffIdOne, staffIdTwo, staffIdThree, staffIdFour, staffIdFive, staffIdSix, staffIdSeven;

    @FXML
    private Label staffStatusOne, staffStatusTwo, staffStatusThree, staffStatusFour, staffStatusFive, staffStatusSix, staffStatusSeven;

    @FXML
    private JFXButton editOne, editTwo, editThree, editFour, editFive, editSix, editSeven;

    @FXML
    private MFXGenericDialog descriptionDialog;

    @FXML
    private MFXProgressSpinner loadSpinner;

    @FXML
    private MFXPagination pagination;

    @FXML
    private TextField staffNameInDetails;

    @FXML
    private MFXToggleButton staffStatusInDetails;

    @FXML
    private Label staffIdInDetails;

    @FXML
    private TextArea staffDescriptionInDetails;

    @FXML
    private AnchorPane addButton;

    @FXML
    private JFXButton applyButton;

    private final Label[] nameList = new Label[7];
    private final Label[] idList = new Label[7];
    private final Label[] statusList = new Label[7];
    private final JFXButton[] buttonList = new JFXButton[7];

    private List<Staff> currentStaffList;

    private List<Staff> currentActiveStaffList;

    private List<Staff> currentInactiveStaffList;

    private Status status = Status.ALL;

    private Staff selectedStaff;

    private int pageNumber;

    /**
     * Please check if text formatter has been applied
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staffDescriptionInDetails.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 100 ? change : null));
        staffNameInDetails.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 50 ? change : null));
        staffService.updateAllCachedStaffData();
        descriptionDialog.setVisible(false);
        loadSpinner.setVisible(false);
        getStaffList(pagination.getCurrentPage());
        calculatePageNumber();
        pagination.setMaxPage(pageNumber);
        pagination.currentPageProperty().addListener((observable, oldValue, newValue) -> {
            getStaffList(newValue);
            assignStaffValue();
        });
        initializeNameList();
        initializeIdList();
        initializeStatusList();
        initializeButtonList();
        assignStaffValue();
    }

    /**
     * This method updates the latest page number, thus should be executed every time when
     * there is an update in data or change in status
     */
    private void calculatePageNumber(){
        if(status == Status.ALL){
            pageNumber = StaffCachedUtils.getLists(StaffCachedUtils.listType.ALL).size();
        } else if(status == Status.INACTIVE){
            pageNumber = StaffCachedUtils.getLists(StaffCachedUtils.listType.INACTIVE).size();
        } else {
            pageNumber = StaffCachedUtils.getLists(StaffCachedUtils.listType.ACTIVE).size();
        }
    }

    private void getStaffList(Number newValue){
        if(newValue.intValue() - 1 < StaffCachedUtils.getLists(StaffCachedUtils.listType.ALL).size()){
            currentStaffList = StaffCachedUtils.getLists(StaffCachedUtils.listType.ALL).get(newValue.intValue() - 1);
        }
        if(newValue.intValue() - 1 < StaffCachedUtils.getLists(StaffCachedUtils.listType.ACTIVE).size()){
            currentActiveStaffList = StaffCachedUtils.getLists(StaffCachedUtils.listType.ACTIVE).get(newValue.intValue() - 1);
        }
        if(newValue.intValue() - 1 < StaffCachedUtils.getLists(StaffCachedUtils.listType.INACTIVE).size()){
            currentInactiveStaffList = StaffCachedUtils.getLists(StaffCachedUtils.listType.INACTIVE).get(newValue.intValue() - 1);
        }

    }

    /**
     * This method assigns the values based on the current selected status, and this must be called
     * everytime when there is a change in status
     */
    private void assignStaffValue(){
        if(Status.ALL == status){
            setTextWithCurrentList(currentStaffList);
        } else if(Status.ACTIVE == status){
            setTextWithCurrentList(currentActiveStaffList);
        } else{
            setTextWithCurrentList(currentInactiveStaffList);
        }
    }

    private void setTextWithCurrentList(List<Staff> currentStaffList){
        for(int i = 0; i < currentStaffList.size(); i++){
            buttonList[i].setDisable(false);
            nameList[i].setText(currentStaffList.get(i).getStaffName());
            idList[i].setText(Integer.valueOf(currentStaffList.get(i).getStaffID()).toString());
            statusList[i].setText(currentStaffList.get(i).getStatus());
        }
        for(int j = currentStaffList.size(); j < nameList.length; j++){
            nameList[j].setText("N/A");
            idList[j].setText("N/A");
            statusList[j].setText("N/A");
            buttonList[j].setDisable(true);
        }
    }

    private void initializeNameList(){
        nameList[0] = staffNameOne;
        nameList[1] = staffNameTwo;
        nameList[2] = staffNameThree;
        nameList[3] = staffNameFour;
        nameList[4] = staffNameFive;
        nameList[5] = staffNameSix;
        nameList[6] = staffNameSeven;
    }

    private void initializeIdList(){
        idList[0] = staffIdOne;
        idList[1] = staffIdTwo;
        idList[2] = staffIdThree;
        idList[3] = staffIdFour;
        idList[4] = staffIdFive;
        idList[5] = staffIdSix;
        idList[6] = staffIdSeven;
    }

    private void initializeStatusList(){
        statusList[0] = staffStatusOne;
        statusList[1] = staffStatusTwo;
        statusList[2] = staffStatusThree;
        statusList[3] = staffStatusFour;
        statusList[4] = staffStatusFive;
        statusList[5] = staffStatusSix;
        statusList[6] = staffStatusSeven;
    }

    private void initializeButtonList(){
        buttonList[0] = editOne;
        buttonList[1] = editTwo;
        buttonList[2] = editThree;
        buttonList[3] = editFour;
        buttonList[4] = editFive;
        buttonList[5] = editSix;
        buttonList[6] = editSeven;
    }
    private List<Staff> getCurrentList(){
        List<Staff> currentList = null;
        if(Status.ALL.equals(status)){
            currentList = currentStaffList;
        } else if(Status.ACTIVE.equals(status)){
            currentList = currentActiveStaffList;
        } else{
            currentList = currentInactiveStaffList;
        }
        return currentList;
    }

    private void assignStaffDetails(){
        staffNameInDetails.setText(selectedStaff.getStaffName());
        staffIdInDetails.setText(Integer.valueOf(selectedStaff.getStaffID()).toString());
        staffStatusInDetails.setSelected("ACTIVE".equals(selectedStaff.getStatus()));
        staffDescriptionInDetails.setText(selectedStaff.getOtherInfo());
    }
    @FXML
    private void onClickEditOne(){
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(0);
        descriptionDialog.setVisible(true);
        assignStaffDetails();
    }

    @FXML
    private void onClickEditTwo(){
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(1);
        descriptionDialog.setVisible(true);
        assignStaffDetails();
    }

    @FXML
    private void onClickEditThree(){
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(2);
        descriptionDialog.setVisible(true);
        assignStaffDetails();
    }

    @FXML
    private void onClickEditFour(){
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(3);
        descriptionDialog.setVisible(true);
        assignStaffDetails();
    }

    @FXML
    private void onClickEditFive(){
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(4);
        descriptionDialog.setVisible(true);
        assignStaffDetails();
    }

    @FXML
    private void onClickEditSix(){
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(5);
        descriptionDialog.setVisible(true);
        assignStaffDetails();
    }

    @FXML
    private void onClickEditSeven(){
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(6);
        descriptionDialog.setVisible(true);
        assignStaffDetails();
    }

    @FXML
    private void onCloseDescription(){
        descriptionDialog.setVisible(false);
        loadSpinner.setVisible(false);
    }

    @FXML
    private void onClickAddButton(){

    }

    @FXML
    private void onEnterAddButton(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(addButton, 500, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitAddButton(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(addButton, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onClickOkay(){
        onCloseDescription();
    }

    @FXML
    private void onClickApply(){
        applyButton.setVisible(false);
        loadSpinner.setVisible(true);
        executorService.execute(() -> {
            Staff staff = encapsulateCurrentStaffData();
            staffService.updateTransaction(staff);
            getStaffList(pagination.getCurrentPage());
            Platform.runLater(() -> {
                assignStaffValue();
                loadSpinner.setVisible(false);
                applyButton.setVisible(true);
            });
        });
    }

    private Staff encapsulateCurrentStaffData(){
        Staff staff = new Staff();
        staff.setStaffName(staffNameInDetails.getText());
        staff.setStaffID(Integer.parseInt(staffIdInDetails.getText()));
        staff.setOtherInfo(staffDescriptionInDetails.getText());
        staff.setStatus(staffStatusInDetails.isSelected() ? "ACTIVE" : "INACTIVE");
        return staff;
    }
}
