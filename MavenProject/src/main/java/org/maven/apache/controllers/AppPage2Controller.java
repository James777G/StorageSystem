package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.maven.apache.MyLauncher;
import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.service.DateTransaction.DateTransactionService;
import org.maven.apache.service.excel.ExcelConverterService;
import org.maven.apache.service.user.UserService;
import org.maven.apache.spring.ExcelConverterConfiguration;
import org.maven.apache.user.User;
import org.maven.apache.utils.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AppPage2Controller implements Initializable {

    @FXML
    private JFXButton warehouseButton;

    @FXML
    private JFXButton messageButton;

    @FXML
    private JFXButton staffButton;

    @FXML
    private JFXButton transactionButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView refreshImage;

    @FXML
    private ImageView extendArrow;

    @FXML
    private MFXTextField searchField;

    @FXML
    private VBox searchTable;

    @FXML
    private JFXButton buttonOne;

    @FXML
    private JFXButton buttonTwo;

    @FXML
    private JFXButton buttonThree;

    @FXML
    private JFXButton buttonFour;

    @FXML
    private JFXButton buttonFive;

    @FXML
    private JFXButton statusAllButton;

    @FXML
    private JFXButton statusTakenButton;

    @FXML
    private JFXButton statusRestockButton;

    @FXML
    private JFXDrawer VBoxDrawer;

    @FXML
    private AnchorPane appPagePane;

    @FXML
    private AnchorPane allStatusPane;

    @FXML
    private AnchorPane takenStatusPane;

    @FXML
    private AnchorPane restockStatusPane;

    @FXML
    private AnchorPane cargoBox1Pane;

    @FXML
    private AnchorPane cargoBox2Pane;

    @FXML
    private AnchorPane cargoBox3Pane;

    @FXML
    private AnchorPane cargoBox4Pane;

    @FXML
    private AnchorPane[] cargoBoxPanes = {cargoBox1Pane,cargoBox2Pane,cargoBox3Pane,cargoBox4Pane};

    @FXML
    private Label cargoNameLabel01;

    @FXML
    private Label cargoNameLabel02;

    @FXML
    private Label cargoNameLabel03;

    @FXML
    private Label cargoNameLabel04;

    @FXML
    private Label[] cargoNameLabels = {cargoNameLabel01,cargoNameLabel02,cargoNameLabel03,cargoNameLabel04};

    @FXML
    private Label cargoAmountLabel01;

    @FXML
    private Label cargoAmountLabel02;

    @FXML
    private Label cargoAmountLabel03;

    @FXML
    private Label cargoAmountLabel04;

    @FXML
    private Label[] cargoAmountLabels = {cargoAmountLabel01,cargoAmountLabel02,cargoAmountLabel03,cargoAmountLabel04};

    @FXML
    private Label staffNameLabel01;

    @FXML
    private Label staffNameLabel02;

    @FXML
    private Label staffNameLabel03;

    @FXML
    private Label staffNameLabel04;

    @FXML
    private Label[] staffNameLabels = {staffNameLabel01,staffNameLabel02,staffNameLabel03,staffNameLabel04};

    @FXML
    private Label redTakenLabel;

    @FXML
    private Label greenRestockLabel;

    @FXML
    private StackPane stackPane;

    @FXML
    protected MFXGenericDialog settingsDialog;


    //pass the user from login page
    private final User user = DataUtils.currentUser;

    private boolean isTriangleRotating = false;

    private final JFXButton[] buttonList = new JFXButton[5];

    private boolean isRotating = false;

    private final Timeline timeline = new Timeline();

    private Node currentPage;

    private final DateTransactionService dateTransactionService = MyLauncher.context.getBean("dateTransactionService", DateTransactionService.class);

    private int i = 0;
    private int j = 0;
    enum ButtonSelected {
        ALL,
        TAKEN,
        RESTOCK
    }

    private int takenBoxNumber = 2;
    private int restockBoxNumber = 2;

    /**
     * These lists are for testing purpose only
     */
    private List<DateTransaction> dateTransactions = dateTransactionService.selectAll();
    private List<DateTransaction> dateTransactions_Date = dateTransactionService.pageAskedDateDescend(1,dateTransactions.size());
    private List<DateTransaction> dateTransactions_Taken = new ArrayList<DateTransaction>();
    private List<DateTransaction> dateTransactions_Restock = new ArrayList<DateTransaction>();
    /* testing ends*/

    private ButtonSelected buttonSelected = ButtonSelected.ALL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentPage = appPagePane;
        DataUtils.publicSettingsDialog = settingsDialog;
        settingsDialog.setPickOnBounds(false);
        settingsDialog.setOpacity(0);
        settingsDialog.setVisible(false);
        usernameLabel.setText(user.getName());
        warehouseButton.setOpacity(0);
        staffButton.setOpacity(0);
        transactionButton.setOpacity(0);
        messageButton.setOpacity(0);
        searchTable.setPickOnBounds(false);
        searchTable.setOpacity(0);
        setButtonList();
        setTransactionPane();
        stackPane.setPickOnBounds(false);
        stackPane.setOpacity(0);
        stackPane.setVisible(false);
        // initialize search per sec when search field is chosen
        searchField.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                searchOnBackgroundPerSec();
                searchTable.setOpacity(1);
                searchTable.setPickOnBounds(true);
                searchTable.setVisible(true);
            } else {
                timeline.stop();
                searchTable.setOpacity(0);
                searchTable.setPickOnBounds(false);
                searchTable.setVisible(false);
            }
        });
        // load the menu VBox to drawer
        setDrawer();
//        List<DateTransaction> dateTransactions = dateTransactionService.selectAll();
//        List<DateTransaction> dateTransactions_Date = dateTransactionService.pageAskedDateDescend(1,dateTransactions.size());
//        List<DateTransaction> dateTransactions_Taken = new ArrayList<DateTransaction>();
//        List<DateTransaction> dateTransactions_Restock = new ArrayList<DateTransaction>();
//        for (DateTransaction dateTransaction : dateTransactions_Date) {
//            if (dateTransaction.getAddUnit() == 0) {
//                dateTransactions_Taken.add(i, dateTransaction);
//                if (i < 3) {
//                    i++;
//                }
//            } else if (dateTransaction.getRemoveUnit() == 0) {
//                dateTransactions_Restock.add(j, dateTransaction);
//                if (j < 3) {
//                    j++;
//                }
//            }
//            if(i+j == 6){
//                break;
//            }
//        }
        createRestockAndTakenLists();
        System.out.println(dateTransactions_Taken);
        System.out.println(dateTransactions_Restock);
        System.out.println(dateTransactions_Taken.size());
        System.out.println(dateTransactions_Restock.size());
        fillCargoBoxesInformation(buttonSelected);
//        cargoNameLabel01.setText(dateTransactions_Taken.get(0).getItemName());
//        cargoNameLabel02.setText(dateTransactions_Taken.get(1).getItemName());
//        cargoNameLabel03.setText(dateTransactions_Restock.get(0).getItemName());
//        cargoNameLabel04.setText(dateTransactions_Restock.get(1).getItemName());
//        cargoAmountLabel01.setText(dateTransactions_Taken.get(0).getRemoveUnit().toString());
//        cargoAmountLabel02.setText(dateTransactions_Taken.get(1).getRemoveUnit().toString());
//        cargoAmountLabel03.setText(dateTransactions_Restock.get(0).getAddUnit().toString());
//        cargoAmountLabel04.setText(dateTransactions_Restock.get(1).getAddUnit().toString());
//        staffNameLabel01.setText(dateTransactions_Taken.get(0).getStaffName());
//        staffNameLabel02.setText(dateTransactions_Taken.get(1).getStaffName());
//        staffNameLabel03.setText(dateTransactions_Restock.get(0).getStaffName());
//        staffNameLabel04.setText(dateTransactions_Restock.get(1).getStaffName());
    }

    /**
     * This method is for testing only
     *
     */
    private void createRestockAndTakenLists(){
        for (DateTransaction dateTransaction : dateTransactions_Date) {
            if (dateTransaction.getAddUnit() == 0) {
                if (i < 4) {
                    dateTransactions_Taken.add(i, dateTransaction);
                    i++;
                }
            } else if (dateTransaction.getRemoveUnit() == 0) {
                if (j < 4) {
                    dateTransactions_Restock.add(j, dateTransaction);
                    j++;
                }
            }
            if(i+j == 8){
                break;
            }
        }
    }
    /*
       Test ends.
     */

    private void fillCargoBoxesInformation(ButtonSelected buttonSelected){
        int boxNumber = 4;
        System.out.println(Arrays.stream(cargoBoxPanes).toList());
        for(int index = 0; index < boxNumber; index++){
            Arrays.stream(cargoBoxPanes).toList().get(index).setOpacity(0);
            cargoBoxPanes[index].setVisible(false);
            cargoBoxPanes[index].setPickOnBounds(false);
        }
//        cargoNameLabel01.setText(cargoBox1.getItemName());
//        cargoNameLabel02.setText(cargoBox2.getItemName());
//        cargoNameLabel03.setText(cargoBox3.getItemName());
//        cargoNameLabel04.setText(cargoBox4.getItemName());
        System.out.println(buttonSelected);
        switch (buttonSelected) {
            case ALL -> {
//                cargoAmountLabel01.setText(dateTransactions_Taken.get(0).getRemoveUnit().toString());
//                cargoAmountLabel02.setText(dateTransactions_Taken.get(1).getRemoveUnit().toString());
//                cargoAmountLabel03.setText(dateTransactions_Restock.get(0).getAddUnit().toString());
//                cargoAmountLabel04.setText(dateTransactions_Restock.get(1).getAddUnit().toString());
                if(dateTransactions_Taken.size() < 2) {
                    takenBoxNumber = dateTransactions_Taken.size();
                } else if (dateTransactions_Restock.size() < 2) {
                    restockBoxNumber = dateTransactions_Restock.size();
                }
                for(int indexTaken = 0; indexTaken < takenBoxNumber; indexTaken++){
                    cargoBoxPanes[indexTaken].setOpacity(1);
                    cargoBoxPanes[indexTaken].setVisible(true);
                    cargoBoxPanes[indexTaken].setPickOnBounds(true);
                    cargoAmountLabels[indexTaken].setText(dateTransactions_Taken.get(indexTaken).getRemoveUnit().toString());
                }
                for(int indexRestock = 0; indexRestock < restockBoxNumber; indexRestock++){
                    cargoBoxPanes[indexRestock+takenBoxNumber].setOpacity(1);
                    cargoBoxPanes[indexRestock+takenBoxNumber].setVisible(true);
                    cargoBoxPanes[indexRestock+takenBoxNumber].setPickOnBounds(true);
                    cargoAmountLabels[indexRestock+takenBoxNumber].setText(dateTransactions_Taken.get(indexRestock+takenBoxNumber).getRemoveUnit().toString());
                }

            }
            case TAKEN -> {
                if(dateTransactions_Taken.size() < 4){
                    boxNumber = dateTransactions_Taken.size();
                }
                for(int index = 0; index < boxNumber; index++) {
                    cargoBoxPanes[index].setOpacity(1);
                    cargoBoxPanes[index].setVisible(true);
                    cargoBoxPanes[index].setPickOnBounds(true);
                    cargoNameLabels[index].setText(dateTransactions_Taken.get(index).getItemName());
                    cargoAmountLabels[index].setText(dateTransactions_Taken.get(index).getRemoveUnit().toString());
                    staffNameLabels[index].setText(dateTransactions_Taken.get(index).getStaffName());
//                    cargoAmountLabel02.setText(cargoBox2.getRemoveUnit().toString());
//                    cargoAmountLabel03.setText(cargoBox3.getRemoveUnit().toString());
//                    cargoAmountLabel04.setText(cargoBox4.getRemoveUnit().toString());
                }

            }
            case RESTOCK -> {
                if(dateTransactions_Restock.size() < 4){
                    boxNumber = dateTransactions_Restock.size();
                }
                for(int index = 0; index < boxNumber; index++) {
                    cargoBoxPanes[index].setOpacity(1);
                    cargoBoxPanes[index].setVisible(true);
                    cargoBoxPanes[index].setPickOnBounds(true);
                    cargoNameLabels[index].setText(dateTransactions_Restock.get(index).getItemName());
                    cargoAmountLabels[index].setText(dateTransactions_Restock.get(index).getAddUnit().toString());
                    staffNameLabels[index].setText(dateTransactions_Restock.get(index).getStaffName());
//                    cargoAmountLabel02.setText(cargoBox2.getAddUnit().toString());
//                    cargoAmountLabel03.setText(cargoBox3.getAddUnit().toString());
//                    cargoAmountLabel04.setText(cargoBox4.getAddUnit().toString());
                }
            }
        }

//        staffNameLabel01.setText(cargoBox1.getStaffName());
//        staffNameLabel02.setText(cargoBox2.getStaffName());
//        staffNameLabel03.setText(cargoBox3.getStaffName());
//        staffNameLabel04.setText(cargoBox4.getStaffName());
    }

    @FXML
    private void onClickStuff() throws IOException {
        FileChooser fc = new FileChooser();
        Stage stage = new Stage();
        File imageToClassify = fc.showSaveDialog(stage);
        ExcelConverterService excelConverterService = MyLauncher.context.getBean("excelConverterService", ExcelConverterService.class);
        excelConverterService.convertToExcel(imageToClassify);

    }

    @FXML
    private void onClickSearch() {
        searchOnBackgroundPerSec();
        searchTable.setOpacity(1);
        searchTable.setPickOnBounds(true);
        searchTable.setVisible(true);
    }

    private void onClickSettingsTwo(MFXGenericDialog genericDialog) {
        genericDialog.setOpacity(1);
        genericDialog.setPickOnBounds(true);
        genericDialog.setVisible(true);
    }

    @FXML
    private void onClickSearchBar() {
        searchOnBackgroundPerSec();
        searchTable.setOpacity(1);
        searchTable.setPickOnBounds(true);
        searchTable.setVisible(true);
    }

    private void setTransactionPane() {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/transactionPage.fxml")));
            stackPane.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDrawer() {
        try {
            VBox vbox = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/menuPage.fxml")));
            vbox.setOnMouseExited(event -> {
                RotateTransition rotate = RotationUtils.getRotationTransitionFromTo(extendArrow, 300, -90, 0);
                rotate.setOnFinished(event1 -> isTriangleRotating = false);
                if (!isTriangleRotating) {
                    isTriangleRotating = true;
                    rotate.play();
                }
                VBoxDrawer.close();
            });
            VBoxDrawer.setSidePane(vbox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onEnterAppPage() {
        if (currentPage != appPagePane) {
            appPagePane.setPickOnBounds(true);
            appPagePane.setVisible(true);
            stackPane.setPickOnBounds(false);

            FadeTransition fadeTransition = TransitionUtils.getFadeTransition(appPagePane, 300, 0, 1);
            FadeTransition fadeTransition1 = TransitionUtils.getFadeTransition(stackPane, 300, 1, 0);
            fadeTransition1.setOnFinished(event -> {
                fadeTransition.play();
                stackPane.setVisible(false);
            });
            fadeTransition1.play();
            currentPage = appPagePane;
        }
    }

    private void setButtonList() {
        buttonOne.setAlignment(Pos.CENTER_LEFT);
        buttonTwo.setAlignment(Pos.CENTER_LEFT);
        buttonThree.setAlignment(Pos.CENTER_LEFT);
        buttonFour.setAlignment(Pos.CENTER_LEFT);
        buttonFive.setAlignment(Pos.CENTER_LEFT);
        buttonList[0] = buttonOne;
        buttonList[1] = buttonTwo;
        buttonList[2] = buttonThree;
        buttonList[3] = buttonFour;
        buttonList[4] = buttonFive;
    }

    @FXML
    private void onClickExtend() {
        RotateTransition rotate;
        if (VBoxDrawer.isOpened()) {
            rotate = RotationUtils.getRotationTransitionFromTo(extendArrow, 300, -90, 0);
        } else {
            rotate = RotationUtils.getRotationTransitionFromTo(extendArrow, 300, 0, -90);
        }
        rotate.setOnFinished(event -> isTriangleRotating = false);
        if (!isTriangleRotating) {
            isTriangleRotating = true;
            rotate.play();
        }
        if (VBoxDrawer.isOpened()) {
            VBoxDrawer.close();
        } else {
            VBoxDrawer.open();
        }
    }

    @FXML
    private void onClickAppPagePane() {
        timeline.stop();
        searchTable.setOpacity(0);
        searchTable.setPickOnBounds(false);
        searchTable.setVisible(false);
    }

    @FXML
    private void onEnterExtend() {
        if (VBoxDrawer.isClosed()) {
            VBoxDrawer.open();
            RotateTransition rotate = RotationUtils.getRotationTransitionFromTo(extendArrow, 300, 0, -90);
            rotate.setOnFinished(event -> isTriangleRotating = false);
            if (!isTriangleRotating) {
                isTriangleRotating = true;
                rotate.play();
            }

        }
    }

    /**
     * perform fuzzy search and show the list of relevant cargos in background per
     * sec
     */
    private void searchOnBackgroundPerSec() {
        KeyFrame keyFrame = ThreadUtils.generateSearchKeyFrame(buttonList, searchField);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        timeline.playFromStart();
    }

    @FXML
    private void enterWarehouseButton() {
        warehouseButton.setOpacity(1);
    }

    @FXML
    private void exitWarehouseButton() {
        warehouseButton.setOpacity(0);
    }

    @FXML
    private void enterStaffButton() {
        staffButton.setOpacity(1);
    }

    @FXML
    private void exitStaffButton() {
        staffButton.setOpacity(0);
    }

    @FXML
    private void enterTransactionButton() {
        transactionButton.setOpacity(1);
    }

    @FXML
    private void exitTransactionButton() {
        transactionButton.setOpacity(0);
    }

    @FXML
    private void enterMessageButton() {
        messageButton.setOpacity(1);
    }

    @FXML
    private void exitMessageButton() {
        messageButton.setOpacity(0);
    }

    @FXML
    private void refreshPage() {
        RotateTransition rotate = RotationUtils.getRotationTransitionFromBy(refreshImage, 1500, 0,
                RotationUtils.Direction.COUNTERCLOCKWISE, 360);
        rotate = RotationUtils.addEaseOutTranslateInterpolator(rotate);
        rotate.setOnFinished(event -> isRotating = false);
        if (!isRotating) {
            isRotating = true;
            rotate.play();
        }
    }

    @FXML
    private void enterRefreshImage() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionBy(refreshImage, 500, 1.2);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void exitRefreshImage() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionBy(refreshImage, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onTransactionPage() {
        if (currentPage != stackPane) {
            appPagePane.setPickOnBounds(false);
            stackPane.setPickOnBounds(true);
            stackPane.setVisible(true);
            FadeTransition fadeTransition = TransitionUtils.getFadeTransition(appPagePane, 300, 1, 0);
            FadeTransition fadeTransition1 = TransitionUtils.getFadeTransition(stackPane, 300, 0, 1);
            fadeTransition.setOnFinished(event -> {
                fadeTransition1.play();
                appPagePane.setVisible(false);
            });
            fadeTransition.play();
            currentPage = stackPane;
        }
    }

    @FXML
    private void onCloseSettings() {
        settingsDialog.setOpacity(0);
        settingsDialog.setPickOnBounds(false);
        settingsDialog.setVisible(false);
    }

    @FXML
    private void onClickAll() {
        switch (buttonSelected) {
            case ALL:
                break;
            case TAKEN:
                allStatusPane.setVisible(true);
                takenStatusPane.setVisible(false);
                buttonSelected = ButtonSelected.ALL;
                break;
            case RESTOCK:
                allStatusPane.setVisible(true);
                restockStatusPane.setVisible(false);
                buttonSelected = ButtonSelected.ALL;
                break;
        }
        redTakenLabel.setOpacity(1);
        redTakenLabel.setVisible(true);
        redTakenLabel.setPickOnBounds(true);
        greenRestockLabel.setOpacity(1);
        greenRestockLabel.setVisible(true);
        greenRestockLabel.setPickOnBounds(true);
        greenRestockLabel.setTranslateX(0);
        fillCargoBoxesInformation(buttonSelected);
    }

    @FXML
    private void onClickTaken() {
        switch (buttonSelected) {
            case ALL:
                takenStatusPane.setVisible(true);
                allStatusPane.setVisible(false);
                buttonSelected = ButtonSelected.TAKEN;
                break;
            case TAKEN:
                break;
            case RESTOCK:
                takenStatusPane.setVisible(true);
                restockStatusPane.setVisible(false);
                buttonSelected = ButtonSelected.TAKEN;
                break;
        }
        redTakenLabel.setOpacity(1);
        redTakenLabel.setVisible(true);
        redTakenLabel.setPickOnBounds(true);
        greenRestockLabel.setOpacity(0);
        greenRestockLabel.setVisible(false);
        greenRestockLabel.setPickOnBounds(false);
        greenRestockLabel.setTranslateX(0);
        fillCargoBoxesInformation(buttonSelected);
    }

    @FXML
    private void onClickRestock() {
        switch (buttonSelected) {
            case ALL:
                restockStatusPane.setVisible(true);
                allStatusPane.setVisible(false);
                buttonSelected = ButtonSelected.RESTOCK;
                break;
            case TAKEN:
                restockStatusPane.setVisible(true);
                takenStatusPane.setVisible(false);
                buttonSelected = ButtonSelected.RESTOCK;
                break;
            case RESTOCK:
                break;
        }
        redTakenLabel.setOpacity(0);
        redTakenLabel.setVisible(false);
        redTakenLabel.setPickOnBounds(false);
        greenRestockLabel.setOpacity(1);
        greenRestockLabel.setVisible(true);
        greenRestockLabel.setPickOnBounds(true);
        greenRestockLabel.setTranslateX(-500);
        fillCargoBoxesInformation(buttonSelected);

    }

}
