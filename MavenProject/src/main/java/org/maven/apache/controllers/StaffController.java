package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.maven.apache.MyLauncher;
import org.maven.apache.service.staff.CachedStaffService;

import java.net.URL;
import java.util.ResourceBundle;

public class StaffController implements Initializable {

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

    private final Label[] nameList = new Label[7];
    private final Label[] idList = new Label[7];
    private final Label[] statusList = new Label[7];
    private final JFXButton[] buttonList = new JFXButton[7];


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staffService.updateAllCachedStaffData();
        loadSpinner.setVisible(false);
        initializeNameList();
        initializeIdList();
        initializeStatusList();
        initializeButtonList();
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

    @FXML
    private void onCloseDescription(){
        descriptionDialog.setVisible(false);
        loadSpinner.setVisible(false);
    }
}
