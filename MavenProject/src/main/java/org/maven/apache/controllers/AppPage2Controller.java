package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.maven.apache.MyLauncher;
import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.service.DateTransaction.DateTransactionService;
import org.maven.apache.service.excel.ExcelConverterService;
import org.maven.apache.service.user.UserService;
import org.maven.apache.user.User;
import org.maven.apache.utils.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AppPage2Controller implements Initializable {

    @FXML
    private ImageView refreshImage;

    @FXML
    private ImageView extendArrow;

    @FXML
    private VBox searchTable;

    @FXML
    private VBox passwordVBox;

    @FXML
    private VBox infoVBox;

    @FXML
    private JFXButton warehouseButton;

    @FXML
    private JFXButton messageButton;

    @FXML
    private JFXButton staffButton;

    @FXML
    private JFXButton transactionButton;

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
    private JFXButton updateUsernameButton;

    @FXML
    private JFXButton updateEmailButton;

    @FXML
    private JFXButton updatePasswordButton;

    @FXML
    private JFXButton confirmUpdateInfo;

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
    private AnchorPane cargoBox1BackPane;

    @FXML
    private AnchorPane cargoBox2BackPane;

    @FXML
    private AnchorPane cargoBox3BackPane;

    @FXML
    private AnchorPane cargoBox4BackPane;

    @FXML
    private AnchorPane[] cargoBoxBackPanes = new AnchorPane[4];

    @FXML
    private AnchorPane blockPane;

    @FXML
    private AnchorPane transitionSettingLine;

    @FXML
    private AnchorPane[] cargoBoxPanes = new AnchorPane[4];//{cargoBox1Pane,cargoBox2Pane,cargoBox3Pane,cargoBox4Pane};

    @FXML
    private StackPane stackPane;

    @FXML
    private StackPane stackPaneForWarehouse;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label cargoNameLabel01;

    @FXML
    private Label cargoNameLabel02;

    @FXML
    private Label cargoNameLabel03;

    @FXML
    private Label cargoNameLabel04;

    @FXML
    private Label[] cargoNameLabels = new Label[4];//{cargoNameLabel01,cargoNameLabel02,cargoNameLabel03,cargoNameLabel04};

    @FXML
    private Label cargoAmountLabel01;

    @FXML
    private Label cargoAmountLabel02;

    @FXML
    private Label cargoAmountLabel03;

    @FXML
    private Label cargoAmountLabel04;

    @FXML
    private Label[] cargoAmountLabels = new Label[4];//{cargoAmountLabel01,cargoAmountLabel02,cargoAmountLabel03,cargoAmountLabel04};

    @FXML
    private Label staffNameLabel01;

    @FXML
    private Label staffNameLabel02;

    @FXML
    private Label staffNameLabel03;

    @FXML
    private Label staffNameLabel04;

    @FXML
    private Label[] staffNameLabels = new Label[4];//{staffNameLabel01,staffNameLabel02,staffNameLabel03,staffNameLabel04}

    @FXML
    private Label redTakenLabel;

    @FXML
    private Label greenRestockLabel;

    @FXML
    private Label notificationLabel;

    @FXML
    private TextField searchField;

    @FXML
    private MFXTextField currentInfoTextField;

    @FXML
    private MFXTextField newInfoTextField;

    @FXML
    private MFXPasswordField currentPasswordField;

    @FXML
    private MFXPasswordField newPasswordField;

    @FXML
    protected MFXGenericDialog settingsDialog;

    //pass the user from login page
    private final User user = DataUtils.currentUser;

    private boolean isTriangleRotating = false;

    private boolean isRotating = false;

    private boolean isUpdatingUsername = false;

    private boolean isUpdatingEmail = false;

    private boolean isUpdatingPassword = false;

    private boolean isSearchTableMoving = false;

    private final JFXButton[] buttonList = new JFXButton[5];

    private final Timeline timeline = new Timeline();

    private Node currentPage;

    private final DateTransactionService dateTransactionService = MyLauncher.context.getBean("dateTransactionService", DateTransactionService.class);

    private final UserService userService = MyLauncher.context.getBean("userService", UserService.class);

    private int i = 0;

    private int j = 0;

    private int takenBoxNumber = 2;

    private int restockBoxNumber = 2;

    enum ButtonSelected {
        ALL,
        TAKEN,
        RESTOCK
    }

    private List<DateTransaction> dateTransactions_Restock = dateTransactionService.pageAskedDateAddUnitDescend();
    private List<DateTransaction> dateTransactions_Taken = dateTransactionService.pageAskedDateRemoveUnitDescend();
    private ButtonSelected buttonSelected = ButtonSelected.ALL;

    private boolean isSearchTableOut = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchField.deselect();
        currentPage = appPagePane;
        DataUtils.publicSettingsDialog = settingsDialog;
        settingsDialog.setVisible(false);
        searchTable.setVisible(false);
        usernameLabel.setText(user.getName());
        warehouseButton.setOpacity(0);
        staffButton.setOpacity(0);
        transactionButton.setOpacity(0);
        messageButton.setOpacity(0);
        searchTable.setPickOnBounds(false);
        searchTable.setOpacity(0);
        stackPaneForWarehouse.setOpacity(0);
        stackPaneForWarehouse.setPickOnBounds(false);
        stackPaneForWarehouse.setVisible(false);
        setButtonList();
        setTransactionPane();
        setWarehousePane();
        stackPane.setPickOnBounds(false);
        stackPane.setOpacity(0);
        stackPane.setVisible(false);
        // initialize search per sec when search field is chosen
        searchField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                searchOnBackgroundPerSec();
            } else {
                timeline.stop();
                isSearchTableOut = false;
            }
        });
        setDrawer();
        confirmUpdateInfo.setDisable(true);
        initializeLabels();
        System.out.println(dateTransactions_Taken);
        System.out.println(dateTransactions_Restock);
        System.out.println(dateTransactions_Taken.size());
        System.out.println(dateTransactions_Restock.size());
        fillCargoBoxesInformation(buttonSelected);
        blockPane.setVisible(false);
        DataUtils.publicSettingBlockPane = blockPane;
        onUpdateUsername();
    }

    private void enableNode(Node node){
        node.setOpacity(1);
        node.setVisible(true);
        node.setPickOnBounds(true);
    }

    private void disableNode(Node node){
        node.setOpacity(0);
        node.setVisible(false);
        node.setPickOnBounds(false);
    }

    private void initializeLabels() {
        cargoNameLabels[0] = cargoNameLabel01;
        cargoNameLabels[1] = cargoNameLabel02;
        cargoNameLabels[2] = cargoNameLabel03;
        cargoNameLabels[3] = cargoNameLabel04;
        cargoAmountLabels[0] = cargoAmountLabel01;
        cargoAmountLabels[1] = cargoAmountLabel02;
        cargoAmountLabels[2] = cargoAmountLabel03;
        cargoAmountLabels[3] = cargoAmountLabel04;
        staffNameLabels[0] = staffNameLabel01;
        staffNameLabels[1] = staffNameLabel02;
        staffNameLabels[2] = staffNameLabel03;
        staffNameLabels[3] = staffNameLabel04;
        cargoBoxPanes[0] = cargoBox1Pane;
        cargoBoxPanes[1] = cargoBox2Pane;
        cargoBoxPanes[2] = cargoBox3Pane;
        cargoBoxPanes[3] = cargoBox4Pane;
        cargoBoxBackPanes[0] = cargoBox1BackPane;
        cargoBoxBackPanes[1] = cargoBox2BackPane;
        cargoBoxBackPanes[2] = cargoBox3BackPane;
        cargoBoxBackPanes[3] = cargoBox4BackPane;
    }

    private void fillCargoBoxesInformation(ButtonSelected buttonSelected) {
        int boxNumber = 4;
        for (int index = 0; index < boxNumber; index++) {
            cargoBoxPanes[index].setOpacity(1);
            cargoBoxPanes[index].setVisible(true);
            cargoBoxPanes[index].setPickOnBounds(true);
        }
        switch (buttonSelected) {
            case ALL -> {
                redTakenLabel.setOpacity(1);
                redTakenLabel.setVisible(true);
                redTakenLabel.setPickOnBounds(true);
                greenRestockLabel.setOpacity(1);
                greenRestockLabel.setVisible(true);
                greenRestockLabel.setPickOnBounds(true);
                greenRestockLabel.setTranslateX(0);
                if (dateTransactions_Taken.size() < 2) {
                    takenBoxNumber = dateTransactions_Taken.size();
                    for (int hideAllTaken = 1; hideAllTaken >= dateTransactions_Taken.size(); hideAllTaken--) {
                        cargoBoxPanes[hideAllTaken].setOpacity(0);
                        cargoBoxPanes[hideAllTaken].setVisible(false);
                        cargoBoxPanes[hideAllTaken].setPickOnBounds(false);
                    }
                }
                if (dateTransactions_Restock.size() < 2) {
                    restockBoxNumber = dateTransactions_Restock.size();
                    for (int hideAllRestock = 3; hideAllRestock >= dateTransactions_Restock.size() + 2; hideAllRestock--) {
                        cargoBoxPanes[hideAllRestock].setOpacity(0);
                        cargoBoxPanes[hideAllRestock].setVisible(false);
                        cargoBoxPanes[hideAllRestock].setPickOnBounds(false);
                    }
                }
                for (int indexTaken = 0; indexTaken < takenBoxNumber; indexTaken++) {
                    cargoNameLabels[indexTaken].setText(dateTransactions_Taken.get(indexTaken).getItemName());
                    cargoAmountLabels[indexTaken].setText(dateTransactions_Taken.get(indexTaken).getRemoveUnit().toString());
                    staffNameLabels[indexTaken].setText(dateTransactions_Taken.get(indexTaken).getStaffName());
                }
                for (int indexRestock = 0; indexRestock < restockBoxNumber; indexRestock++) {
                    cargoNameLabels[indexRestock + 2].setText(dateTransactions_Restock.get(indexRestock).getItemName());
                    cargoAmountLabels[indexRestock + 2].setText(dateTransactions_Restock.get(indexRestock).getAddUnit().toString());
                    staffNameLabels[indexRestock + 2].setText(dateTransactions_Restock.get(indexRestock).getStaffName());
                }

            }
            case TAKEN -> {
                redTakenLabel.setOpacity(1);
                redTakenLabel.setVisible(true);
                redTakenLabel.setPickOnBounds(true);
                greenRestockLabel.setOpacity(0);
                greenRestockLabel.setVisible(false);
                greenRestockLabel.setPickOnBounds(false);
                if (dateTransactions_Taken.size() < 4) {
                    boxNumber = dateTransactions_Taken.size();
                    for (int hideTaken = 3; hideTaken > dateTransactions_Taken.size() - 1; hideTaken--) {
                        cargoBoxPanes[hideTaken].setOpacity(0);
                        cargoBoxPanes[hideTaken].setVisible(false);
                        cargoBoxPanes[hideTaken].setPickOnBounds(false);
                    }
                }
                for (int index = 0; index < boxNumber; index++) {
                    cargoNameLabels[index].setText(dateTransactions_Taken.get(index).getItemName());
                    cargoAmountLabels[index].setText(dateTransactions_Taken.get(index).getRemoveUnit().toString());
                    staffNameLabels[index].setText(dateTransactions_Taken.get(index).getStaffName());
                }

            }
            case RESTOCK -> {
                redTakenLabel.setOpacity(0);
                redTakenLabel.setVisible(false);
                redTakenLabel.setPickOnBounds(false);
                greenRestockLabel.setOpacity(1);
                greenRestockLabel.setVisible(true);
                greenRestockLabel.setPickOnBounds(true);
                greenRestockLabel.setTranslateX(-500);
                if (dateTransactions_Restock.size() < 4) {
                    boxNumber = dateTransactions_Restock.size();
                    for (int hideRestock = 3; hideRestock > dateTransactions_Restock.size() - 1; hideRestock--) {
                        cargoBoxPanes[hideRestock].setOpacity(0);
                        cargoBoxPanes[hideRestock].setVisible(false);
                        cargoBoxPanes[hideRestock].setPickOnBounds(false);
                    }
                }
                for (int index = 0; index < boxNumber; index++) {
                    cargoNameLabels[index].setText(dateTransactions_Restock.get(index).getItemName());
                    cargoAmountLabels[index].setText(dateTransactions_Restock.get(index).getAddUnit().toString());
                    staffNameLabels[index].setText(dateTransactions_Restock.get(index).getStaffName());
                }
            }
        }
    }

    @FXML
    @SuppressWarnings("all")
    private void onClickWarehouseButton() {
        if (currentPage != stackPaneForWarehouse) {
            if (currentPage == appPagePane) {
                appPagePane.setPickOnBounds(false);
                FadeTransition fadeTransition = TransitionUtils.getFadeTransition(appPagePane, 300, 1, 0);
                FadeTransition fadeTransition1 = TransitionUtils.getFadeTransition(stackPaneForWarehouse, 300, 0, 1);
                fadeTransition.setOnFinished(event -> {
                    fadeTransition1.play();
                    appPagePane.setVisible(false);
                    stackPaneForWarehouse.setPickOnBounds(true);
                    stackPaneForWarehouse.setVisible(true);
                });
                fadeTransition.play();
                currentPage = stackPaneForWarehouse;
            }
            if (currentPage == stackPane) {
                stackPane.setPickOnBounds(false);

                FadeTransition fadeTransition = TransitionUtils.getFadeTransition(stackPane, 300, 1, 0);
                FadeTransition fadeTransition1 = TransitionUtils.getFadeTransition(stackPaneForWarehouse, 300, 0, 1);
                fadeTransition.setOnFinished(event -> {
                    fadeTransition1.play();
                    stackPane.setVisible(false);
                    stackPaneForWarehouse.setPickOnBounds(true);
                    stackPaneForWarehouse.setVisible(true);
                });
                fadeTransition.play();
                currentPage = stackPaneForWarehouse;
            }
        }

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
        isSearchTableOut = true;
        searchOnBackgroundPerSec();
        searchTable.setOpacity(1);
        searchTable.setPickOnBounds(true);
        searchTable.setVisible(true);
    }

    private static void onClickSettingsTwo(MFXGenericDialog genericDialog) {
        genericDialog.setOpacity(1);
        genericDialog.setPickOnBounds(true);
        genericDialog.setVisible(true);
    }

    @FXML
    private void onClickSearchBar() {
        if (!isSearchTableMoving && !isSearchTableOut) {
            isSearchTableOut = true;
            isSearchTableMoving = true;
            searchOnBackgroundPerSec();
            searchTable.setOpacity(1);
            searchTable.setPickOnBounds(true);
            searchTable.setVisible(true);
            ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionFromToY(searchTable,500,0,1);
            scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
            scaleTransition.setOnFinished(event -> {
                isSearchTableMoving = false;
            });
            scaleTransition.play();
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(searchTable,500,-100,0);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.setOnFinished(event -> {
                isSearchTableMoving = false;
            });
            translateTransition.play();
        }
    }

    private void setTransactionPane() {
        try {
            DataUtils.publicDataPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/transactionPage_data.fxml")));
            DataUtils.editCargoPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/editCargoPane.fxml")));
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/transactionPage.fxml")));
            stackPane.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setWarehousePane() {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/warehousePage.fxml")));
            stackPaneForWarehouse.getChildren().add(pane);
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
    @SuppressWarnings("all")
    private void onEnterAppPage() {
        if (currentPage != appPagePane) {
            if (currentPage == stackPane) {
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
            if (currentPage == stackPaneForWarehouse) {
                assert appPagePane != null;
                appPagePane.setPickOnBounds(true);
                appPagePane.setVisible(true);
                stackPaneForWarehouse.setPickOnBounds(false);

                FadeTransition fadeTransition = TransitionUtils.getFadeTransition(appPagePane, 300, 0, 1);
                FadeTransition fadeTransition1 = TransitionUtils.getFadeTransition(stackPaneForWarehouse, 300, 1, 0);
                fadeTransition1.setOnFinished(event -> {
                    fadeTransition.play();
                    stackPaneForWarehouse.setVisible(false);
                });
                fadeTransition1.play();
                currentPage = appPagePane;
            }

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
        isSearchTableOut = false;
        isSearchTableMoving = false;
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
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(refreshImage, 500, 1.2);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void exitRefreshImage() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(refreshImage, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    @SuppressWarnings("all")
    private void onTransactionPage() {
        if (currentPage != stackPane) {
            if (currentPage == appPagePane) {
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
            if (currentPage == stackPaneForWarehouse) {
                stackPaneForWarehouse.setPickOnBounds(false);
                stackPane.setPickOnBounds(true);
                stackPane.setVisible(true);
                FadeTransition fadeTransition = TransitionUtils.getFadeTransition(stackPaneForWarehouse, 300, 1, 0);
                FadeTransition fadeTransition1 = TransitionUtils.getFadeTransition(stackPane, 300, 0, 1);
                fadeTransition.setOnFinished(event -> {
                    fadeTransition1.play();
                    stackPaneForWarehouse.setVisible(false);
                });
                fadeTransition.play();
                currentPage = stackPane;
            }
        }
    }

    /**
     * close and erase all operations in setting dialog
     */
    @FXML
    private void onCloseSettings() {
        settingsDialog.setVisible(false);
        infoVBox.setVisible(true);
        passwordVBox.setVisible(false);
        currentInfoTextField.clear();
        newInfoTextField.clear();
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmUpdateInfo.setDisable(true);
        isUpdatingUsername = false;
        isUpdatingEmail = false;
        isUpdatingPassword = false;
        notificationLabel.setText("");
        blockPane.setVisible(false);
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
        fillCargoBoxesInformation(buttonSelected);
    }

    @FXML
    private void onEnterCargoBox1(){
        ScaleTransition scaleTransition_enlarge = ScaleUtils.getScaleTransitionFromToXY(cargoBox1Pane,200,1,1.1);
        scaleTransition_enlarge = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition_enlarge);
        scaleTransition_enlarge.setOnFinished(closeFrontPane -> {
            ScaleTransition scaleTransition_closeFront = ScaleUtils.getScaleTransitionFromToX(cargoBox1Pane,300,1.1,0);
            scaleTransition_closeFront.setOnFinished(openBackPane -> {
                cargoBox1BackPane.setScaleX(0);
                enableNode(cargoBox1BackPane);
                disableNode(cargoBox1Pane);
                ScaleTransition scaleTransition_openBack = ScaleUtils.getScaleTransitionFromToX(cargoBox1BackPane,300,0,1.1);
                scaleTransition_openBack.play();
            });
            scaleTransition_closeFront.play();
        });
        scaleTransition_enlarge.play();
    }

    @FXML
    private void onExitCargoBox1(){
        ScaleTransition scaleTransition_closeBack = ScaleUtils.getScaleTransitionFromToX(cargoBox1BackPane,300,1.1,0);
        scaleTransition_closeBack.setOnFinished(closeBackPane -> {
            disableNode(cargoBox1BackPane);
            cargoBox1Pane.setScaleX(0);
            enableNode(cargoBox1Pane);
            ScaleTransition scaleTransition_openFront = ScaleUtils.getScaleTransitionFromToX(cargoBox1Pane,300, 0,1.1);
            scaleTransition_openFront.setOnFinished( toOriginalSize -> {
                ScaleTransition scaleTransition_ToOrigin = ScaleUtils.getScaleTransitionFromToXY(cargoBox1Pane,200,1.1,1);
                scaleTransition_ToOrigin = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition_ToOrigin);
                scaleTransition_ToOrigin.play();
            });
            scaleTransition_openFront.play();
        });
        scaleTransition_closeBack.play();
//        ScaleTransition scaleTransition_enlarge = ScaleUtils.getScaleTransitionFromToXY(cargoBox1Pane,200,1,1.1);
//        scaleTransition_enlarge.setOnFinished(closeFrontPane -> {
//            ScaleTransition scaleTransition_closeFront = ScaleUtils.getScaleTransitionFromToX(cargoBox1Pane,500,1.1,0);
//            scaleTransition_closeFront.setOnFinished(openBackPane -> {
//                cargoBox1BackPane.setScaleX(0);
//                enableNode(cargoBox1BackPane);
//                ScaleTransition scaleTransition_openBack = ScaleUtils.getScaleTransitionFromToX(cargoBox1BackPane,500,0,1.1);
//                scaleTransition_openBack.play();
//            });
//            scaleTransition_closeFront.play();
//        });
//        scaleTransition_enlarge.play();
    }
    @FXML
    private void onUpdateUsername() {
        updateSettingDialog("Username");
        setTransitionSettingLine();
    }

    @FXML
    private void onUpdateEmail() {
        updateSettingDialog("Email");
        setTransitionSettingLine();
    }

    @FXML
    private void onUpdatePassword() {
        updateSettingDialog("Password");
        setTransitionSettingLine();
    }

    /**
     * set the status of buttons and textfields pursuant to the property that is being updated
     *
     * @param infoType
     */
    private void updateSettingDialog(String infoType) {
        switch (infoType) {
            case "Username":
                isUpdatingUsername = true;
                isUpdatingEmail = false;
                isUpdatingPassword = false;
                infoVBox.setVisible(true);
                passwordVBox.setVisible(false);
                currentInfoTextField.setFloatingText(" Current " + infoType);
                newInfoTextField.setFloatingText(" New " + infoType);
                settingsDialog.setHeaderText("Update your Username");
                break;
            case "Email":
                isUpdatingUsername = false;
                isUpdatingEmail = true;
                isUpdatingPassword = false;
                infoVBox.setVisible(true);
                passwordVBox.setVisible(false);
                currentInfoTextField.setFloatingText(" Current " + infoType);
                newInfoTextField.setFloatingText(" New " + infoType);
                settingsDialog.setHeaderText("Update your Email");
                break;
            case "Password":
                isUpdatingUsername = false;
                isUpdatingEmail = false;
                isUpdatingPassword = true;
                infoVBox.setVisible(false);
                passwordVBox.setVisible(true);
                settingsDialog.setHeaderText("Update your Password");
                break;
        }
        confirmUpdateInfo.setDisable(false);
        currentInfoTextField.clear();
        newInfoTextField.clear();
        currentPasswordField.clear();
        newPasswordField.clear();
        notificationLabel.setText("");
    }

    /**
     * shift the line under the buttons pursuant to the info that is being updating
     */
    private void setTransitionSettingLine(){
        if (isUpdatingUsername && !isUpdatingEmail && !isUpdatingPassword){
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionToX(transitionSettingLine, 500, 0);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.play();
        }else if (!isUpdatingUsername && isUpdatingEmail && !isUpdatingPassword){
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionToX(transitionSettingLine, 500, 90);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.play();
        }else if (!isUpdatingUsername && !isUpdatingEmail && isUpdatingPassword){
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionToX(transitionSettingLine, 500, 180);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.play();
        }
    }

    /**
     * update any account info for current logged in user
     */
    @FXML
    private void onConfirmUpdateInfo() {
        User currentUser = DataUtils.currentUser;
        String currentUsername = currentUser.getUsername();
        String currentUserEmail = currentUser.getEmailAddress();
        String currentUserPassword = currentUser.getPassword();
        String currentInfo = currentInfoTextField.getText();
        // get current username and email
        String newInfo = newInfoTextField.getText();
        // get current password
        String currentPassword = currentPasswordField.getText();
        //get new passwrod
        String newPassword = newPasswordField.getText();
        if (isUpdatingUsername && !isUpdatingEmail && !isUpdatingPassword) {
            // updating username
            if (currentInfo.equals(currentUsername) && newInfo.length() > 1) {
                // old username is matched with database and new username has length of at least 2
                currentUser.setUsername(newInfo);
                userService.update(currentUser);
                notificationLabel.setText("Username updated");
            } else {
                notificationLabel.setText("Invalid old or new Username");
            }
        } else if (!isUpdatingUsername && isUpdatingEmail && !isUpdatingPassword) {
            // updating email
            if (currentInfo.equals(currentUserEmail) && newInfo.length() > 5 && newInfo.contains("@") && newInfo.contains(".com")) {
                // old email is matched with database and
                // new email contains characters "@" and string ".com"
                // and has length of at least 6
                currentUser.setEmailAddress(newInfo);
                userService.update(currentUser);
                notificationLabel.setText("Email updated");
            } else {
                notificationLabel.setText("Invalid old or new Email");
            }
        } else if (!isUpdatingUsername && !isUpdatingEmail && isUpdatingPassword) {
            // updating password
            if (currentPassword.equals(currentUserPassword) && newPassword.length() > 5) {
                // old password is matched and new password has length of at least 6
                currentUser.setPassword(newPassword);
                userService.update(currentUser);
                notificationLabel.setText("Password updated");
            } else {
                notificationLabel.setText("Invalid old or new Password");
            }
        }
    }
}
