package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.maven.apache.App;
import org.maven.apache.MyLauncher;
import org.maven.apache.email.Email;
import org.maven.apache.exception.*;
import org.maven.apache.item.Item;
import org.maven.apache.regulatory.Regulatory;
import org.maven.apache.service.email.EmailService;
import org.maven.apache.service.excel.ExcelConverterService;
import org.maven.apache.service.mail.MailNotifyService;
import org.maven.apache.service.regulatory.RegulatoryService;
import org.maven.apache.service.search.PromptSearchBarServiceHandler;
import org.maven.apache.service.search.SearchBarService;
import org.maven.apache.service.transaction.CachedTransactionService;
import org.maven.apache.service.user.UserService;
import org.maven.apache.staff.Staff;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.user.User;
import org.maven.apache.utils.*;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class AppPage2Controller implements Initializable {

    public enum ButtonSelected {
        ALL,
        TAKEN,
        RESTOCK
    }

    enum CargoBoxNumber {
        ONE,
        TWO,
        THREE,
        FOUR
    }

    enum CurrentPaneStatus {
        HOMEPAGE,
        WAREHOUSE,
        TRANSACTION,
        STAFF,
        MESSAGE
    }

    @FXML
    private ImageView appPageImageView;

    @FXML
    private ImageView warehouseImageView;

    @FXML
    private ImageView transactionImageView;

    @FXML
    private ImageView staffImageView;

    @FXML
    private ImageView messageImageView;

    @FXML
    private ImageView refreshImage;

    @FXML
    private ImageView regulatoryDeleteOne, regulatoryDeleteTwo, regulatoryDeleteThree;

    @FXML
    private ImageView emailDeleteOne, emailDeleteTwo, emailDeleteThree;

    @FXML
    private ImageView extendArrow;

    @FXML
    private ImageView notificationCross;

    @FXML
    private TextArea purposeTextInDetails;

    @FXML
    private VBox searchTable;

    @FXML
    private VBox passwordVBox;

    @FXML
    private VBox infoVBox;

    @FXML
    private VBox vbox;

    @FXML
    private VBox drawerVBox = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/menuPage.fxml")));

    @FXML
    private JFXButton appPageButton;

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
    private JFXButton cargoDialogApplyButton;

    @FXML
    private JFXButton emailApplyButton, cargoApplyButton;

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
    private AnchorPane cargoBox1FunctionalityPane, cargoBox2FunctionalityPane, cargoBox3FunctionalityPane, cargoBox4FunctionalityPane;

    @FXML
    private AnchorPane blockPane;

    @FXML
    private AnchorPane transitionSettingLine;

    @FXML
    private AnchorPane homeButtonBlockPane;

    @FXML
    private AnchorPane warehouseButtonBlockPane;

    @FXML
    private AnchorPane transactionButtonBlockPane;

    @FXML
    private AnchorPane staffButtonBlockPane;

    @FXML
    private AnchorPane messageButtonBlockPane;

    @FXML
    private AnchorPane transactionDialogTakenActionPane;

    @FXML
    private AnchorPane transactionDialogRestockActionPane;

    @FXML
    private AnchorPane cargoSearchPane;

    @FXML
    private AnchorPane staffSearchPane;

    @FXML
    private AnchorPane searchSwitchingBlockPane;

    @FXML
    private AnchorPane appPageBlockPane;

    @FXML
    private AnchorPane notificationPane;

    @FXML
    private AnchorPane emailSpaceOne, emailSpaceTwo, emailSpaceThree;

    @FXML
    private AnchorPane regulatorySpaceOne, regulatorySpaceTwo, regulatorySpaceThree;

    @FXML
    private StackPane stackPane;

    @FXML
    private StackPane stackPaneForWarehouse;

    @FXML
    private StackPane staffPane;

    @FXML
    private StackPane messagePane;

    @FXML
    private MFXCircleToggleNode notifyButton;

    @FXML
    private Label transactionAmountInDetails;

    @FXML
    private Label transactionStatusInDetails;

    @FXML
    private Label transactionDateInDetails;

    @FXML
    private Label warehouseLabel;

    @FXML
    private Label transactionLabel;

    @FXML
    private Label staffLabel;

    @FXML
    private Label messageLabel;

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
    private Label cargoAmountLabel01;

    @FXML
    private Label cargoAmountLabel02;

    @FXML
    private Label cargoAmountLabel03;

    @FXML
    private Label cargoAmountLabel04;

    @FXML
    private Label emailWarnMessage;

    @FXML
    private Label staffNameLabel01;

    @FXML
    private Label staffNameLabel02;

    @FXML
    private Label staffNameLabel03;

    @FXML
    private Label staffNameLabel04;

    @FXML
    private Label redTakenLabel;

    @FXML
    private Label greenRestockLabel;

    @FXML
    private Label transactionIdLabel;

    @FXML
    private Label warnMessageInAdd;

    @FXML
    private Label transactionNameInDetails;

    @FXML
    private Label notificationLabel;

    @FXML
    private Label emailOne, emailTwo, emailThree;

    @FXML
    private Label regulatoryNameOne, regulatoryNameTwo, regulatoryNameThree, regulatoryAmountOne, regulatoryAmountTwo, regulatoryAmountThree;

    @FXML
    private Label regulatoryWarnMessage;

    @FXML
    private TextField searchField;

    @FXML
    private MFXTextField currentInfoTextField;

    @FXML
    private MFXTextField newInfoTextField;

    @FXML
    private MFXTextField emailTextField, cargoAmountTextField;

    @FXML
    private MFXPasswordField currentPasswordField;

    @FXML
    private MFXPasswordField newPasswordField;

    @FXML
    protected MFXGenericDialog settingsDialog;

    @FXML
    private MFXGenericDialog transactionDialog;

    @FXML
    private MFXProgressSpinner loadSpinnerInAdd;

    @FXML
    private MFXProgressSpinner emailSpinner, cargoSpinner;

    @FXML
    private MFXProgressSpinner emailDeleteSpinnerOne, emailDeleteSpinnerTwo, emailDeleteSpinnerThree;

    @FXML
    private MFXProgressSpinner regulatoryDeleteSpinnerOne, regulatoryDeleteSpinnerTwo, regulatoryDeleteSpinnerThree;

    @FXML
    private MFXProgressSpinner refreshSpinner;

    @FXML
    private MFXFilterComboBox staffNameInDetails;

    @FXML
    private MFXFilterComboBox cargoNameTextField;

    @FXML
    private Pagination emailPagination, cargoPagination;

    private URL url1 = App.class.getResource("/image/icons8-warehouse-100.png");

    private URL url2 = MyLauncher.class.getResource("/image/icons8-warehouse-50.png");

    private URL url3 = MyLauncher.class.getResource("/image/icons8-cardboard-box-100 (3).png");

    private URL url4 = MyLauncher.class.getResource("/image/icons8-cardboard-box-100 (2).png");

    private URL url5 = MyLauncher.class.getResource("/image/icons8-up-down-arrow-96 (2).png");

    private URL url6 = MyLauncher.class.getResource("/image/icons8-up-down-arrow-96 (1).png");

    private URL url7 = MyLauncher.class.getResource("/image/icons8-account-96 (1).png");

    private URL url8 = MyLauncher.class.getResource("/image/icons8-account-96 (2).png");

    private URL url9 = MyLauncher.class.getResource("/image/icons8-envelope-96 (1).png");

    private URL url10 = MyLauncher.class.getResource("/image/icons8-envelope-96.png");

    private final Image onAppPageHomeImage = new Image(url1.toExternalForm());

    private final Image offAppPageHomeImage = new Image(url2.toExternalForm());

    private final Image onWarehousePageCardBoardImage = new Image(url3.toExternalForm());

    private final Image offWarehousePageCardBoardImage = new Image(url4.toExternalForm());

    private final Image onTransactionPageArrowUpDownImage = new Image(url5.toExternalForm());

    private final Image offTransactionPageArrowUpDownImage = new Image(url6.toExternalForm());

    private final Image onStaffPageUserImage = new Image(url7.toExternalForm());

    private final Image offStaffPageUserImage = new Image(url8.toExternalForm());

    private final Image onMessagePageEnvelopeImage = new Image(url9.toExternalForm());

    private final Image offMessagePageEnvelopeImage = new Image(url10.toExternalForm());

    private final Paint appPageHoverTheme = Paint.valueOf("#37a592");

    private final Paint appPageTheme = Paint.valueOf("#223c40");

    //pass the user from login page
    private final User user = DataUtils.currentUser;

    private boolean isAppPageBlockPaneOpen = false;

    private boolean isRotating = false;

    private boolean isUpdatingUsername = false;

    private boolean isUpdatingEmail = false;

    private boolean isUpdatingPassword = false;

    private boolean isChangingSide_CargoBox1 = false;

    private boolean isChangingSide_CargoBox2 = false;

    private boolean isChangingSide_CargoBox3 = false;

    private boolean isChangingSide_CargoBox4 = false;

    private boolean changeToBack_CargoBox1 = false;

    private boolean changeToBack_CargoBox2 = false;

    private boolean changeToBack_CargoBox3 = false;

    private boolean changeToBack_CargoBox4 = false;

    private boolean isSearchTableOut = false;

    private boolean isMouseExitInformationPage = true;

    private boolean isVBoxOpened = false;

    private boolean isVBoxOnOpenAnimation = false;

    private boolean isVBoxOnCloseAnimation = false;

    private boolean[] changeToBack = new boolean[4];

    private boolean[] isChangingSide = new boolean[4];

    private int takenBoxNumber = 2;

    private int restockBoxNumber = 2;

    private Node currentPage;

    private final ExecutorService executorService = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

    private final CachedTransactionService cachedTransactionService = MyLauncher.context.getBean("cachedTransactionService", CachedTransactionService.class);

    private final UserService userService = MyLauncher.context.getBean("userService", UserService.class);

    private final MailNotifyService mailNotifyService = MyLauncher.context.getBean("mailNotifyService", MailNotifyService.class);

    private final SearchBarService searchBarService = MyLauncher.context.getBean("searchBarService", SearchBarService.class);

    private final EmailService emailService = MyLauncher.context.getBean("emailService", EmailService.class);

    private final RegulatoryService regulatoryService = MyLauncher.context.getBean("regulatoryService", RegulatoryService.class);

    private Transaction transaction;

    private Transaction encapsulatedTransaction = new Transaction();

    private Transaction[] dateTransactionListInAppPage = new Transaction[4];

    private Label[] staffNameLabels = new Label[4];//{staffNameLabel01,staffNameLabel02,staffNameLabel03,staffNameLabel04}

    private Label[] cargoAmountLabels = new Label[4];//{cargoAmountLabel01,cargoAmountLabel02,cargoAmountLabel03,cargoAmountLabel04};

    private Label[] cargoNameLabels = new Label[4];//{cargoNameLabel01,cargoNameLabel02,cargoNameLabel03,cargoNameLabel04};

    private AnchorPane[] cargoBoxBackPanes = new AnchorPane[4];

    private AnchorPane[] cargoBoxFunctionalityPanes = new AnchorPane[4];

    private AnchorPane[] cargoBoxPanes = new AnchorPane[4];//{cargoBox1Pane,cargoBox2Pane,cargoBox3Pane,cargoBox4Pane};

    private ButtonSelected buttonSelected = ButtonSelected.ALL;

    private CargoBoxNumber cargoBoxNumber;

    private TranslateTransition translateTransition_openMenu = new TranslateTransition();

    private TranslateTransition translateTransition_closeMenu = new TranslateTransition();

    private TranslateTransition translateTransition_openCargoBox1 = new TranslateTransition();

    private TranslateTransition translateTransition_openCargoBox2 = new TranslateTransition();

    private TranslateTransition translateTransition_openCargoBox3 = new TranslateTransition();

    private TranslateTransition translateTransition_openCargoBox4 = new TranslateTransition();

    private TranslateTransition translateTransition_closeCargoBox1 = new TranslateTransition();

    private TranslateTransition translateTransition_closeCargoBox2 = new TranslateTransition();

    private TranslateTransition translateTransition_closeCargoBox3 = new TranslateTransition();

    private TranslateTransition translateTransition_closeCargoBox4 = new TranslateTransition();

    private TranslateTransition[] translateTransition_openCargoBox = new TranslateTransition[4];

    private TranslateTransition[] translateTransition_closeCargoBox = new TranslateTransition[4];

    private RotateTransition rotateTransition_openMenu = new RotateTransition();

    private RotateTransition rotateTransition_closeMenu = new RotateTransition();

    private Timeline timeline_menuDelay = new Timeline();

    private List<Label> emailList = new ArrayList<>();

    private List<Label> regulatoryNameList = new ArrayList<>();

    private List<Label> regulatoryAmountList = new ArrayList<>();

    private List<AnchorPane> emailSpaceList = new ArrayList<>();

    private List<AnchorPane> regulatorySpaceList = new ArrayList<>();

    private List<Transaction> dateTransactions_Restock;

    private List<Transaction> dateTransactions_Taken;

    private final List<JFXButton> buttonList = new ArrayList<>();

    CurrentPaneStatus currentPaneStatus = CurrentPaneStatus.HOMEPAGE;

    public AppPage2Controller() throws IOException {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cachedTransactionService.updateAllCachedTransactionData();
        regulatoryService.updateAllRegulatoryData();
        emailService.updateCachedEmailData();
        dateTransactions_Restock = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0);
        dateTransactions_Taken = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0);
        transactionDialog.setVisible(false);
        searchField.deselect();
        initializeEmailSpaceList();
        initializeEmails();
        regulatoryWarnMessage.setVisible(false);
        setEmailTable(emailPagination.getCurrentPageIndex());
        emailPagination.setPageCount(EmailCachedUtils.getLists(EmailCachedUtils.listType.ALL).size());
        emailPagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setEmailTable(newValue.intValue());
            }
        });
        currentPage = appPagePane;
        DataUtils.publicSettingsDialog = settingsDialog;
        DataUtils.appPage2Controller = this;
        DataUtils.buttonSelected = this.buttonSelected;
        settingsDialog.setVisible(false);
        searchTable.setVisible(false);
        usernameLabel.setText(user.getName());
        DataUtils.cargoPagination = cargoPagination;
        initializeRegulatoryAmountList();
        initializeRegulatoryNameList();
        initializeRegulatorySpaceList();
        setCargoPageCount();
        setCargoTable(cargoPagination.getCurrentPageIndex());
        cargoPagination.setPageCount(RegulatoryCachedUtils.getLists(RegulatoryCachedUtils.listType.ALL).size());
        cargoPagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setCargoTable(newValue.intValue());
            }
        });
        stackPaneForWarehouse.setVisible(false);
        setButtonList();
        setWarehousePane();
        setStaffPane();
        setMessagePane();
        setTransactionPane();
        staffPane.setVisible(false);
        stackPane.setVisible(false);
        emailSpinner.setVisible(false);
        cargoSpinner.setVisible(false);
        refreshSpinner.setVisible(false);
        setPromptTextForRegulatory();
        setPromptTextForStaff();
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (NewTransactionPageController.isSearchingStaff) {
                    searchBarService.setSearchPrompts(buttonList, searchField.getText(), PromptSearchBarServiceHandler.ResultType.STAFF);
                } else {
                    searchBarService.setSearchPrompts(buttonList, searchField.getText(), PromptSearchBarServiceHandler.ResultType.CARGO);
                }
            }
        });
        setDrawer();
        initializeLabels();
        fillCargoBoxesInformation(buttonSelected);
        blockPane.setVisible(false);
        DataUtils.publicSettingBlockPane = blockPane;
        onUpdateUsername();
    }

    private void setLists() {
        dateTransactions_Restock = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0);
        dateTransactions_Taken = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0);
    }

    private void initializeRegulatoryNameList() {
        regulatoryNameList.add(regulatoryNameOne);
        regulatoryNameList.add(regulatoryNameTwo);
        regulatoryNameList.add(regulatoryNameThree);
    }

    private void initializeRegulatoryAmountList() {
        regulatoryAmountList.add(regulatoryAmountOne);
        regulatoryAmountList.add(regulatoryAmountTwo);
        regulatoryAmountList.add(regulatoryAmountThree);
    }

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
        cargoNameTextField.setItems(FXCollections.observableList(resultList));
    }

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
        staffNameInDetails.setItems(FXCollections.observableList(resultList));
    }

    private void initializeRegulatorySpaceList() {
        regulatorySpaceList.add(regulatorySpaceOne);
        regulatorySpaceList.add(regulatorySpaceTwo);
        regulatorySpaceList.add(regulatorySpaceThree);
    }

    private void setCargoTable(int pageNumber) {
        List<Regulatory> regulatories = null;
        try {
            regulatories = RegulatoryCachedUtils.getLists(RegulatoryCachedUtils.listType.ALL)
                    .get(pageNumber);
        } catch (Exception e) {
            for (int i = 0; i < regulatorySpaceList.size(); i++) {
                regulatorySpaceList.get(i).setVisible(true);
                regulatoryAmountList.get(i).setText("N/A");
                regulatoryNameList.get(i).setText("N/A");
            }
            return;
        }
        for (int i = 0; i < regulatories.size(); i++) {
            regulatorySpaceList.get(i).setVisible(true);
            regulatoryNameList.get(i).setText(regulatories.get(i).getItemName());
            regulatoryAmountList.get(i).setText(String.valueOf(regulatories.get(i).getItemAmount()));
        }
        for (int i = regulatories.size(); i < regulatoryAmountList.size(); i++) {
            regulatorySpaceList.get(i).setVisible(false);
        }
    }

    private void initializeEmails() {
        emailList.add(emailOne);
        emailList.add(emailTwo);
        emailList.add(emailThree);
    }

    @FXML
    private void onClearEmail() {
        emailTextField.clear();
    }

    @FXML
    private void onClickApplyInCargo() {
        Regulatory regulatory = null;
        try {
            regulatory = encapsulateRegulatoryData();
        } catch (EmptyValueException e) {
            regulatoryWarnMessage.setVisible(true);
            return;
        }

        Regulatory finalRegulatory = regulatory;
        executorService.execute(() -> {
            Platform.runLater(() -> {
                cargoApplyButton.setVisible(false);
                cargoSpinner.setVisible(true);
            });
            try {
                regulatoryService.addNewRegulatory(finalRegulatory);
                Platform.runLater(() -> regulatoryWarnMessage.setVisible(false));
            } catch (Exception e) {
                Platform.runLater(() -> {
                    regulatoryWarnMessage.setVisible(true);
                });
            } finally {
                regulatoryService.updateAllRegulatoryData();
                Platform.runLater(() -> {
                    setCargoPageCount();
                    setCargoTable(cargoPagination.getCurrentPageIndex());
                    cargoApplyButton.setVisible(true);
                    cargoSpinner.setVisible(false);
                });
            }
        });
    }

    @FXML
    private void onClearRegulatory() {
        cargoAmountTextField.clear();
        cargoNameTextField.clear();
    }

    private Regulatory encapsulateRegulatoryData() throws EmptyValueException {
        Regulatory regulatory = new Regulatory();
        if (cargoNameTextField.getText().isBlank() || cargoAmountTextField.getText().isBlank()) {
            throw new EmptyValueException("Regulatory Necessary Data Left Unhandled");
        }
        try {
            regulatory.setItemName(cargoNameTextField.getText());
            regulatory.setItemAmount(Integer.parseInt(cargoAmountTextField.getText()));
        } catch (Exception e) {
            throw new EmptyValueException("wrong data format in regulatory");
        }

        return regulatory;
    }

    @FXML
    @Warning(Warning.WarningType.IMPROVEMENT)
    private void onClickAlert() {
        notificationPane.setVisible(true);
        appPageBlockPane.setVisible(true);
    }

    @FXML
    private void onEnterNotify() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(notifyButton, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitNotify() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(notifyButton, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterRegulatoryDeleteOne() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(regulatoryDeleteOne, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterRegulatoryDeleteTwo() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(regulatoryDeleteTwo, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterRegulatoryDeleteThree() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(regulatoryDeleteThree, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitRegulatoryDeleteOne() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(regulatoryDeleteOne, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitRegulatoryDeleteTwo() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(regulatoryDeleteTwo, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitRegulatoryDeleteThree() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(regulatoryDeleteThree, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    @Warning(Warning.WarningType.IMPROVEMENT)
    private void onClickCloseNotification() {
        notificationPane.setVisible(false);
        appPageBlockPane.setVisible(false);
    }

    @FXML
    private void onClickDeleteEmailOne() {
        emailDeleteSpinnerOne.setVisible(true);
        emailDeleteOne.setVisible(false);
        executorService.execute(() -> {
            try {
                emailService.deleteEmailByName(emailOne.getText());
            } catch (Exception e) {
                emailService.updateCachedEmailData();
            } finally {
                Platform.runLater(() -> {
                    setEmailTable(emailPagination.getCurrentPageIndex());
                    setEmailPageCount();
                    emailDeleteSpinnerOne.setVisible(false);
                    emailDeleteOne.setVisible(true);
                });
            }

        });
    }

    @FXML
    private void onClickDeleteEmailTwo() {
        emailDeleteSpinnerTwo.setVisible(true);
        emailDeleteTwo.setVisible(false);
        executorService.execute(() -> {
            try {
                emailService.deleteEmailByName(emailTwo.getText());
            } catch (Exception e) {
                emailService.updateCachedEmailData();
            } finally {
                Platform.runLater(() -> {
                    setEmailTable(emailPagination.getCurrentPageIndex());
                    setEmailPageCount();
                    emailDeleteSpinnerTwo.setVisible(false);
                    emailDeleteTwo.setVisible(true);
                });
            }

        });
    }

    @FXML
    private void onClickDeleteEmailThree() {
        emailDeleteSpinnerThree.setVisible(true);
        emailDeleteThree.setVisible(false);
        executorService.execute(() -> {
            try {
                emailService.deleteEmailByName(emailThree.getText());
            } catch (Exception e) {
                emailService.updateCachedEmailData();
            } finally {
                Platform.runLater(() -> {
                    setEmailTable(emailPagination.getCurrentPageIndex());
                    setEmailPageCount();
                    emailDeleteSpinnerThree.setVisible(false);
                    emailDeleteThree.setVisible(true);
                });
            }
        });
    }

    @FXML
    private void onClickApplyInEmail() {
        Email email = null;
        try {
            email = encapsulateEmailData();
        } catch (InvalidEmailFormatException e) {
            emailWarnMessage.setText("Invalid Email Format");
            emailWarnMessage.setVisible(true);
            return;
        }
        Email finalEmail = email;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        emailSpinner.setVisible(true);
                        emailApplyButton.setVisible(false);
                    }
                });
                try {
                    emailService.addNewEmail(finalEmail);
                    Platform.runLater(() -> emailWarnMessage.setVisible(false));

                } catch (Exception e) {
                    emailService.updateCachedEmailData();
                    Platform.runLater(() -> {
                        emailWarnMessage.setVisible(true);
                        emailWarnMessage.setText("Insertion Failed, Please Check Data");
                    });
                } finally {
                    Platform.runLater(() -> {
                        setEmailTable(emailPagination.getCurrentPageIndex());
                        setEmailPageCount();
                        emailSpinner.setVisible(false);
                        emailApplyButton.setVisible(true);
                    });

                }

            }
        });
    }

    @FXML
    private void onDeleteRegulatoryOne() {
        regulatoryDeleteSpinnerOne.setVisible(true);
        regulatoryDeleteOne.setVisible(false);
        executorService.execute(() -> {
            try {
                regulatoryService.deleteRegulatory(regulatoryNameOne.getText());
            } catch (Exception e) {
                regulatoryService.updateAllRegulatoryData();
            } finally {
                Platform.runLater(() -> {
                    setCargoTable(cargoPagination.getCurrentPageIndex());
                    setCargoPageCount();
                    regulatoryDeleteOne.setVisible(true);
                    regulatoryDeleteSpinnerOne.setVisible(false);
                });
            }
        });
    }

    @FXML
    private void onDeleteRegulatoryTwo() {
        regulatoryDeleteSpinnerTwo.setVisible(true);
        regulatoryDeleteTwo.setVisible(false);
        executorService.execute(() -> {
            try {
                regulatoryService.deleteRegulatory(regulatoryNameTwo.getText());
            } catch (Exception e) {
                regulatoryService.updateAllRegulatoryData();
            } finally {
                Platform.runLater(() -> {
                    setCargoTable(cargoPagination.getCurrentPageIndex());
                    setCargoPageCount();
                    regulatoryDeleteTwo.setVisible(true);
                    regulatoryDeleteSpinnerTwo.setVisible(false);
                });
            }
        });
    }

    @FXML
    private void onDeleteRegulatoryThree() {
        regulatoryDeleteSpinnerThree.setVisible(true);
        regulatoryDeleteThree.setVisible(false);
        executorService.execute(() -> {
            try {
                regulatoryService.deleteRegulatory(regulatoryNameThree.getText());
            } catch (Exception e) {
                regulatoryService.updateAllRegulatoryData();
            } finally {
                Platform.runLater(() -> {
                    setCargoTable(cargoPagination.getCurrentPageIndex());
                    setCargoPageCount();
                    regulatoryDeleteThree.setVisible(true);
                    regulatoryDeleteSpinnerThree.setVisible(false);
                });
            }
        });
    }

    private void setEmailPageCount() {
        if (EmailCachedUtils.getLists(EmailCachedUtils.listType.ALL).size() != 0) {
            emailPagination.setPageCount(EmailCachedUtils.getLists(EmailCachedUtils.listType.ALL).size());
        } else {
            emailPagination.setPageCount(1);
        }
    }

    private void setCargoPageCount() {
        if (RegulatoryCachedUtils.getLists(RegulatoryCachedUtils.listType.ALL).size() != 0) {
            cargoPagination.setPageCount(RegulatoryCachedUtils.getLists(RegulatoryCachedUtils.listType.ALL).size());
        } else {
            cargoPagination.setPageCount(1);
        }
    }

    private Email encapsulateEmailData() throws InvalidEmailFormatException {
        if (RegexUtils.isEmail(emailTextField.getText())) {
            Email email = new Email();
            email.setEmailAddress(emailTextField.getText());
            return email;
        } else {
            throw new InvalidEmailFormatException("Email Address is not valid");
        }
    }

    private void initializeEmailSpaceList() {
        emailSpaceList.add(emailSpaceOne);
        emailSpaceList.add(emailSpaceTwo);
        emailSpaceList.add(emailSpaceThree);
    }

    private void setEmailTable(int pageNumber) {
        List<Email> emails = null;
        try {
            emails = EmailCachedUtils.getLists(EmailCachedUtils.listType.ALL)
                    .get(pageNumber);
        } catch (Exception e) {
            for (int i = 0; i < emailList.size(); i++) {
                emailSpaceList.get(i).setVisible(true);
                emailList.get(i).setText("N/A");
            }
            return;
        }
        for (int i = 0; i < emails.size(); i++) {
            emailSpaceList.get(i).setVisible(true);
            emailList.get(i).setText(emails.get(i).getEmailAddress());
        }
        for (int i = emails.size(); i < emailList.size(); i++) {
            emailSpaceList.get(i).setVisible(false);
        }
    }

    @FXML
    private void onEnterCloseNotification() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(notificationCross, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitCloseNotification() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(notificationCross, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteOne() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(emailDeleteOne, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteTwo() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(emailDeleteTwo, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteThree() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(emailDeleteThree, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteOne() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(emailDeleteOne, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteTwo() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(emailDeleteTwo, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteThree() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(emailDeleteThree, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onClickSearchButtonOne() {
        searchTable.setVisible(true);
        searchField.setText(buttonOne.getText());
    }

    @FXML
    private void onClickSearchButtonTwo() {
        searchTable.setVisible(true);
        searchField.setText(buttonTwo.getText());
    }

    @FXML
    private void onClickSearchButtonThree() {
        searchTable.setVisible(true);
        searchField.setText(buttonThree.getText());
    }

    @FXML
    private void onClickSearchButtonFour() {
        searchTable.setVisible(true);
        searchField.setText(buttonFour.getText());
    }

    @FXML
    private void onClickSearchButtonFive() {
        searchTable.setVisible(true);
        searchField.setText(buttonFive.getText());
    }

    private void enableNode(Node node) {
        node.setOpacity(1);
        node.setVisible(true);
        node.setPickOnBounds(true);
    }

    private void disableNode(Node node) {
        node.setOpacity(0);
        node.setVisible(false);
        node.setPickOnBounds(false);
    }

    @SuppressWarnings("all")
    private void changeButtonColorOn(CurrentPaneStatus currentPaneStatus) {
        switch (currentPaneStatus) {
            case HOMEPAGE -> {
                appPageImageView.setImage(onAppPageHomeImage);
            }
            case WAREHOUSE -> {
                warehouseLabel.setTextFill(appPageHoverTheme);
                warehouseImageView.setImage(onWarehousePageCardBoardImage);
            }
            case TRANSACTION -> {
                transactionLabel.setTextFill(appPageHoverTheme);
                transactionImageView.setImage(onTransactionPageArrowUpDownImage);
            }
            case STAFF -> {
                staffLabel.setTextFill(appPageHoverTheme);
                staffImageView.setImage(onStaffPageUserImage);
            }
            case MESSAGE -> {
                messageLabel.setTextFill(appPageHoverTheme);
                messageImageView.setImage(onMessagePageEnvelopeImage);
            }
        }
    }

    private void changeButtonColorOff(CurrentPaneStatus currentPaneStatus) {
        switch (currentPaneStatus) {
            case HOMEPAGE -> {
                appPageImageView.setImage(offAppPageHomeImage);
            }
            case WAREHOUSE -> {
                warehouseLabel.setTextFill(appPageTheme);
                warehouseImageView.setImage(offWarehousePageCardBoardImage);
            }
            case TRANSACTION -> {
                transactionLabel.setTextFill(appPageTheme);
                transactionImageView.setImage(offTransactionPageArrowUpDownImage);
            }
            case STAFF -> {
                staffLabel.setTextFill(appPageTheme);
                staffImageView.setImage(offStaffPageUserImage);
            }
            case MESSAGE -> {
                messageLabel.setTextFill(appPageTheme);
                messageImageView.setImage(offMessagePageEnvelopeImage);
            }
        }
    }

    private void blockAllSwitchPaneButton() {
        homeButtonBlockPane.toFront();
        warehouseButtonBlockPane.toFront();
        transactionButtonBlockPane.toFront();
        staffButtonBlockPane.toFront();
        messageButtonBlockPane.toFront();
    }

    private void enableAllSwitchPaneButton() {
        homeButtonBlockPane.toBack();
        warehouseButtonBlockPane.toBack();
        transactionButtonBlockPane.toBack();
        staffButtonBlockPane.toBack();
        messageButtonBlockPane.toBack();
    }

    @SuppressWarnings("all")
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
        isChangingSide[0] = isChangingSide_CargoBox1;
        isChangingSide[1] = isChangingSide_CargoBox2;
        isChangingSide[2] = isChangingSide_CargoBox3;
        isChangingSide[3] = isChangingSide_CargoBox4;
        changeToBack[0] = changeToBack_CargoBox1;
        changeToBack[1] = changeToBack_CargoBox2;
        changeToBack[2] = changeToBack_CargoBox3;
        changeToBack[3] = changeToBack_CargoBox4;
        cargoBoxFunctionalityPanes[0] = cargoBox1FunctionalityPane;
        cargoBoxFunctionalityPanes[1] = cargoBox2FunctionalityPane;
        cargoBoxFunctionalityPanes[2] = cargoBox3FunctionalityPane;
        cargoBoxFunctionalityPanes[3] = cargoBox4FunctionalityPane;
        translateTransition_openCargoBox[0] = translateTransition_openCargoBox1;
        translateTransition_openCargoBox[1] = translateTransition_openCargoBox2;
        translateTransition_openCargoBox[2] = translateTransition_openCargoBox3;
        translateTransition_openCargoBox[3] = translateTransition_openCargoBox4;
        translateTransition_closeCargoBox[0] = translateTransition_closeCargoBox1;
        translateTransition_closeCargoBox[1] = translateTransition_closeCargoBox2;
        translateTransition_closeCargoBox[2] = translateTransition_closeCargoBox3;
        translateTransition_closeCargoBox[3] = translateTransition_closeCargoBox4;
    }

    @SuppressWarnings("all")
    private void changePaneAnimation(CurrentPaneStatus currentPaneStatus, CurrentPaneStatus switchedPaneStatus) {
        blockAllSwitchPaneButton();
        FadeTransition openingPaneFadeTransition = new FadeTransition();
        switch (switchedPaneStatus) {
            case HOMEPAGE -> {
                openingPaneFadeTransition = TransitionUtils.getFadeTransition(appPagePane, 300, 0, 1);
            }
            case WAREHOUSE -> {
                openingPaneFadeTransition = TransitionUtils.getFadeTransition(stackPaneForWarehouse, 300, 0, 1);
            }
            case TRANSACTION -> {
                openingPaneFadeTransition = TransitionUtils.getFadeTransition(stackPane, 300, 0, 1);
            }
            case STAFF -> {
                openingPaneFadeTransition = TransitionUtils.getFadeTransition(staffPane, 300, 0, 1);
            }
            case MESSAGE -> {
                openingPaneFadeTransition = TransitionUtils.getFadeTransition(messagePane, 300, 0, 1);
            }
        }
        openingPaneFadeTransition.setOnFinished(event -> {
            switch (switchedPaneStatus) {
                case HOMEPAGE -> {
                    enableNode(appPagePane);
                }
                case WAREHOUSE -> {
                    enableNode(stackPaneForWarehouse);
                }
                case TRANSACTION -> {
                    enableNode(stackPane);
                }
                case STAFF -> {
                    enableNode(staffPane);
                }
                case MESSAGE -> {
                    enableNode(messagePane);
                }
            }
            enableAllSwitchPaneButton();
            switch (switchedPaneStatus) {
                case HOMEPAGE -> {
                    homeButtonBlockPane.toFront();
                }
                case WAREHOUSE -> {
                    warehouseButtonBlockPane.toFront();
                }
                case TRANSACTION -> {
                    transactionButtonBlockPane.toFront();
                }
                case STAFF -> {
                    staffButtonBlockPane.toFront();
                }
                case MESSAGE -> {
                    messageButtonBlockPane.toFront();
                }
            }
        });
        FadeTransition closingPaneFadeTransition = new FadeTransition();
        switch (currentPaneStatus) {
            case HOMEPAGE -> {
                closingPaneFadeTransition = TransitionUtils.getFadeTransition(appPagePane, 300, 1, 0);
            }
            case WAREHOUSE -> {
                closingPaneFadeTransition = TransitionUtils.getFadeTransition(stackPaneForWarehouse, 300, 1, 0);
            }
            case TRANSACTION -> {
                closingPaneFadeTransition = TransitionUtils.getFadeTransition(stackPane, 300, 1, 0);
            }
            case STAFF -> {
                closingPaneFadeTransition = TransitionUtils.getFadeTransition(staffPane, 300, 1, 0);
            }
            case MESSAGE -> {
                closingPaneFadeTransition = TransitionUtils.getFadeTransition(messagePane, 300, 1, 0);
            }
        }
        FadeTransition finalFadeTransition = openingPaneFadeTransition;
        closingPaneFadeTransition.setOnFinished(event -> {
            switch (currentPaneStatus) {
                case HOMEPAGE -> {
                    disableNode(appPagePane);
                }
                case WAREHOUSE -> {
                    disableNode(stackPaneForWarehouse);
                }
                case TRANSACTION -> {
                    disableNode(stackPane);
                }
                case STAFF -> {
                    disableNode(staffPane);
                }
                case MESSAGE -> {
                    disableNode(messagePane);
                }
            }
            switch (switchedPaneStatus) {
                case HOMEPAGE -> {
                    appPagePane.setVisible(true);
                }
                case WAREHOUSE -> {
                    stackPaneForWarehouse.setVisible(true);
                }
                case TRANSACTION -> {
                    stackPane.setVisible(true);
                }
                case STAFF -> {
                    staffPane.setVisible(true);
                }
                case MESSAGE -> {
                    messagePane.setVisible(true);
                }
            }
            finalFadeTransition.play();
        });
        closingPaneFadeTransition.play();
    }

    private void fillCargoBoxesInformation(ButtonSelected buttonSelected) {
        if((TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4)!=null)&&(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4)==null)){
            for(int i = 0 ; i < 4 ; i++) {
                disableNode(cargoBoxBackPanes[i]);
                disableNode(cargoBoxPanes[i]);
                disableNode(cargoBoxFunctionalityPanes[i]);
                System.out.println("Enter disable for loop ");
                System.out.println();
                System.out.println();
                return;
            }
        }
        int boxNumber = 4;
        takenBoxNumber = 2;
        restockBoxNumber = 2;
        for (int index = 0; index < boxNumber; index++) {
            enableNode(cargoBoxBackPanes[index]);
            enableNode(cargoBoxPanes[index]);
            enableNode(cargoBoxFunctionalityPanes[index]);
        }
        switch (buttonSelected) {
            case ALL -> {
                enableNode(redTakenLabel);
                enableNode(greenRestockLabel);
                greenRestockLabel.setTranslateX(0);
                if (TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4) != null) {
                    if (TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).size() < 2) {
                        takenBoxNumber = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).size();
                        for (int hideAllTaken = 1; hideAllTaken >= TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).size(); hideAllTaken--) {
                            disableNode(cargoBoxPanes[hideAllTaken]);
                            disableNode(cargoBoxBackPanes[hideAllTaken]);
                            disableNode(cargoBoxFunctionalityPanes[hideAllTaken]);
                        }
                    }

                    for (int indexTaken = 0; indexTaken < takenBoxNumber; indexTaken++) {
                        dateTransactionListInAppPage[indexTaken] = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).get(indexTaken);
                        cargoNameLabels[indexTaken].setText(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).get(indexTaken).getItemName());
                        cargoAmountLabels[indexTaken].setText(String.valueOf(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).get(indexTaken).getUnit()));
                        staffNameLabels[indexTaken].setText(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).get(indexTaken).getStaffName());
                    }
                }
                System.out.println("TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4)");
                System.out.println(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4));
                System.out.println();
                System.out.println();
                if (TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4) != null) {
                    if (TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).size() < 2) {
                        restockBoxNumber = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).size();
                        for (int hideAllRestock = 3; hideAllRestock >= TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).size() + 2; hideAllRestock--) {
                            disableNode(cargoBoxPanes[hideAllRestock]);
                            disableNode(cargoBoxBackPanes[hideAllRestock]);
                            disableNode(cargoBoxFunctionalityPanes[hideAllRestock]);
                        }
                    }

                    for (int indexRestock = 0; indexRestock < restockBoxNumber; indexRestock++) {
                        dateTransactionListInAppPage[indexRestock + 2] = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).get(indexRestock);
                        cargoNameLabels[indexRestock + 2].setText(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).get(indexRestock).getItemName());
                        cargoAmountLabels[indexRestock + 2].setText(String.valueOf(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).get(indexRestock).getUnit()));
                        staffNameLabels[indexRestock + 2].setText(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).get(indexRestock).getStaffName());
                    }
                }
            }
            case TAKEN -> {
                enableNode(redTakenLabel);
                disableNode(greenRestockLabel);
                if (TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4) != null){
                    boxNumber = 0;
                    for (int hideTaken = 0; hideTaken < 4; hideTaken++) {
                        disableNode(cargoBoxPanes[hideTaken]);
                        disableNode(cargoBoxBackPanes[hideTaken]);
                        disableNode(cargoBoxFunctionalityPanes[hideTaken]);
                    }
                    return;
                }
                if (TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).size() < 4) {
                    boxNumber = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).size();
                    for (int hideTaken = 3; hideTaken > TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).size() - 1; hideTaken--) {
                        disableNode(cargoBoxPanes[hideTaken]);
                        disableNode(cargoBoxBackPanes[hideTaken]);
                        disableNode(cargoBoxFunctionalityPanes[hideTaken]);
                    }
                }
                for (int index = 0; index < boxNumber; index++) {
                    dateTransactionListInAppPage[index] = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).get(index);
                    cargoNameLabels[index].setText(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).get(index).getItemName());
                    cargoAmountLabels[index].setText(String.valueOf(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).get(index).getUnit()));
                    staffNameLabels[index].setText(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.TAKEN_DATE_DESC_4).get(0).get(index).getStaffName());
                }

            }
            case RESTOCK -> {
                disableNode(redTakenLabel);
                enableNode(greenRestockLabel);
                greenRestockLabel.setTranslateX(-500);
                if (TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4) != null) {
                    boxNumber = 0;
                    for (int hideRestock = 0; hideRestock < 4; hideRestock++) {
                        disableNode(cargoBoxPanes[hideRestock]);
                        disableNode(cargoBoxBackPanes[hideRestock]);
                        disableNode(cargoBoxFunctionalityPanes[hideRestock]);
                    }
                    return;
                }
                if (TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).size() < 4) {
                    boxNumber = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).size();
                    for (int hideRestock = 3; hideRestock > TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).size() - 1; hideRestock--) {
                        disableNode(cargoBoxPanes[hideRestock]);
                        disableNode(cargoBoxBackPanes[hideRestock]);
                        disableNode(cargoBoxFunctionalityPanes[hideRestock]);
                    }
                }
                for (int index = 0; index < boxNumber; index++) {
                    dateTransactionListInAppPage[index] = TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).get(index);
                    cargoNameLabels[index].setText(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).get(index).getItemName());
                    cargoAmountLabels[index].setText(String.valueOf(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).get(index).getUnit()));
                    staffNameLabels[index].setText(TransactionCachedUtils.getLists(TransactionCachedUtils.listType.RESTOCK_DATE_DESC_4).get(0).get(index).getStaffName());
                }
            }
        }
    }

    private void enterCargoBoxAnimation(CargoBoxNumber cargoBoxNumber) {
        int index = 0;
        switch (cargoBoxNumber) {
            case ONE -> {
                index = 0;
            }
            case TWO -> {
                index = 1;
            }
            case THREE -> {
                index = 2;
            }
            case FOUR -> {
                index = 3;
            }
        }
        translateTransition_closeCargoBox[index].stop();
        cargoBoxFunctionalityPanes[index].setDisable(false);
        translateTransition_openCargoBox[index] = TranslateUtils.getTranslateTransitionFromToY(cargoBoxPanes[index], 5 * (20 - cargoBoxPanes[index].getTranslateY()), cargoBoxPanes[index].getTranslateY(), 20);
        translateTransition_openCargoBox[index].play();
    }

    private void exitCargoBoxAnimation(CargoBoxNumber cargoBoxNumber) {
        int index = 0;
        switch (cargoBoxNumber) {
            case ONE -> {
                index = 0;
            }
            case TWO -> {
                index = 1;
            }
            case THREE -> {
                index = 2;
            }
            case FOUR -> {
                index = 3;
            }
        }
        translateTransition_openCargoBox[index].stop();
        translateTransition_closeCargoBox[index] = TranslateUtils.getTranslateTransitionFromToY(cargoBoxPanes[index], 5 * cargoBoxPanes[index].getTranslateY(), cargoBoxPanes[index].getTranslateY(), 0);
        int finalIndex = index;
        translateTransition_closeCargoBox[index].setOnFinished(event -> {
            cargoBoxFunctionalityPanes[finalIndex].setDisable(true);
        });
        translateTransition_closeCargoBox[index].play();
    }

    @FXML
    private void onCloseTransactionDialog() {
        transaction = null;
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(transactionDialog, 300, 1, 0);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(transactionDialog, 300, 0, -200);
        translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
        translateTransition.setOnFinished(event -> {
            transactionDialog.setVisible(false);
            isAppPageBlockPaneOpen = false;
            appPageBlockPane.setVisible(false);
            exitCargoBoxAnimation(cargoBoxNumber);
        });
        fadeTransition.play();
        translateTransition.play();
    }

    @FXML
    @SuppressWarnings("all")
    private void onClickWarehouseButton() {
        if (currentPaneStatus != CurrentPaneStatus.WAREHOUSE) {
            changePaneAnimation(currentPaneStatus, CurrentPaneStatus.WAREHOUSE);
            changeButtonColorOff(currentPaneStatus);
            currentPaneStatus = CurrentPaneStatus.WAREHOUSE;
            changeButtonColorOn(currentPaneStatus);
        }
    }

    @FXML
    private void bringToNote() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<MessagePageController> messagePageControllerClass = MessagePageController.class;
        Method onClickAddButtonForStaff = messagePageControllerClass.getDeclaredMethod("onClickAddButtonForTransaction", String.class, String.class);
        onClickAddButtonForStaff.setAccessible(true);
        onClickMessage();
        onClickAddButtonForStaff.invoke(DataUtils.messageController, transactionIdLabel.getText(), transactionNameInDetails.getText());
    }

    @FXML
    private void onClickStaff() {
        if (currentPaneStatus != CurrentPaneStatus.STAFF) {
            changePaneAnimation(currentPaneStatus, CurrentPaneStatus.STAFF);
            changeButtonColorOff(currentPaneStatus);
            currentPaneStatus = CurrentPaneStatus.STAFF;
            changeButtonColorOn(currentPaneStatus);
        }
    }

    @FXML
    private void onClickExcel() throws IOException {
        FileChooser fc = new FileChooser();
        Stage stage = new Stage();
        File imageToClassify = fc.showSaveDialog(stage);
        executorService.execute(() -> {
            ExcelConverterService excelConverterService = MyLauncher.context.getBean("excelConverterService", ExcelConverterService.class);
            try {
                excelConverterService.convertToExcel(imageToClassify);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * bring the searched keyword to transaction page
     */
    @FXML
    private void onClickSearch() throws UnsupportedPojoException, IOException {
        isSearchTableOut = true;
        searchTable.setVisible(true);
        DataUtils.transactionPageController.setKeyword(searchField.getText());
        DataUtils.transactionPageController.onClickSearch();
        onClickTransaction();
    }

    private static void onClickSettingsTwo(MFXGenericDialog genericDialog) {
//        genericDialog.setOpacity(0);
//        genericDialog.setPickOnBounds(true);
        genericDialog.setVisible(true);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(genericDialog, 300, 0, 1);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(genericDialog, 300, -150, 0);
        translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
        translateTransition.play();
        fadeTransition.play();
    }

    @FXML
    private void onClickSearchBar() {
        if (NewTransactionPageController.isSearchingStaff) {
            searchBarService.setSearchPrompts(buttonList, searchField.getText(), PromptSearchBarServiceHandler.ResultType.STAFF);
        } else {
            searchBarService.setSearchPrompts(buttonList, searchField.getText(), PromptSearchBarServiceHandler.ResultType.CARGO);
        }
        searchTable.setVisible(true);
    }

    private void setTransactionPane() {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/newTransactionPage.fxml")));
            stackPane.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        disableNode(stackPane);
    }

    @FXML
    private void onNotify() {
        executorService.execute(() -> mailNotifyService.notifyUsers());
    }

    private void setWarehousePane() {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/warehousePage.fxml")));
            stackPaneForWarehouse.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        disableNode(stackPaneForWarehouse);
    }

    private void setStaffPane() {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/staffPage.fxml")));
            staffPane.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        disableNode(staffPane);
    }

    private void setMessagePane() {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/messagePage.fxml")));
            messagePane.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        disableNode(messagePane);
    }

    @FXML
    private void onScrollForRegulatory(ScrollEvent event){
        if (event.getDeltaY() < 0) {
            cargoPagination.setCurrentPageIndex(cargoPagination.getCurrentPageIndex() + 1);
        }
        if (event.getDeltaY() > 0) {
            cargoPagination.setCurrentPageIndex(cargoPagination.getCurrentPageIndex() - 1);
        }
    }

    @FXML
    private void onScrollForEmail(ScrollEvent event){
        if (event.getDeltaY() < 0) {
            emailPagination.setCurrentPageIndex(emailPagination.getCurrentPageIndex() + 1);
        }
        if (event.getDeltaY() > 0) {
            emailPagination.setCurrentPageIndex(emailPagination.getCurrentPageIndex() - 1);
        }
    }

    private void setDrawer() {
        drawerVBox.setOnMouseExited(event -> {
            Timeline timeline_menuDelay = new Timeline(new KeyFrame(Duration.millis(0.1), e -> {
                if (isMouseExitInformationPage) {
                    if ((isVBoxOpened) || (isVBoxOnOpenAnimation)) {
                        closeMenuVBox();
                    }
                }
                isMouseExitInformationPage = true;
            }));
            timeline_menuDelay.play();
        });
        drawerVBox.setOnMouseEntered(event -> {
            isMouseExitInformationPage = false;
        });
        vbox.getChildren().add(drawerVBox);
    }

    private void openMenuVBox() {
        isVBoxOnOpenAnimation = true;
        rotateTransition_openMenu = RotationUtils.getRotationTransitionTo(extendArrow, (1 - extendArrow.getRotate() / -90) * 300, -90);
        translateTransition_openMenu = TranslateUtils.getTranslateTransitionToY(vbox, (1 - vbox.getTranslateY() / 200) * 300, 200);
        translateTransition_openMenu.setOnFinished(event -> {
            isVBoxOnOpenAnimation = false;
        });
        rotateTransition_closeMenu.stop();
        rotateTransition_openMenu.play();
        translateTransition_closeMenu.stop();
        translateTransition_openMenu.play();
        isVBoxOpened = true;
    }

    private void closeMenuVBox() {
        isVBoxOnCloseAnimation = true;
        rotateTransition_closeMenu = RotationUtils.getRotationTransitionTo(extendArrow, (extendArrow.getRotate() / -90) * 300, 0);
        translateTransition_closeMenu = TranslateUtils.getTranslateTransitionToY(vbox, (vbox.getTranslateY() / 200) * 300, 0);
        translateTransition_closeMenu.setOnFinished(event -> {
            isVBoxOnCloseAnimation = false;
        });
        rotateTransition_openMenu.stop();
        rotateTransition_closeMenu.play();
        translateTransition_openMenu.stop();
        translateTransition_closeMenu.play();
        isVBoxOpened = false;
        timeline_menuDelay.stop();
    }

    @FXML
    @SuppressWarnings("all")
    private void onClickAppPageButton() {
        if (currentPaneStatus != CurrentPaneStatus.HOMEPAGE) {
            changePaneAnimation(currentPaneStatus, CurrentPaneStatus.HOMEPAGE);
            changeButtonColorOff(currentPaneStatus);
            currentPaneStatus = CurrentPaneStatus.HOMEPAGE;
            changeButtonColorOn(currentPaneStatus);
        }
    }

    @FXML
    private void onClickMessage() {
        if (currentPaneStatus != CurrentPaneStatus.MESSAGE) {
            changePaneAnimation(currentPaneStatus, CurrentPaneStatus.MESSAGE);
            changeButtonColorOff(currentPaneStatus);
            currentPaneStatus = CurrentPaneStatus.MESSAGE;
            changeButtonColorOn(currentPaneStatus);
        }
    }

    private void setButtonList() {
        buttonOne.setAlignment(Pos.CENTER_LEFT);
        buttonTwo.setAlignment(Pos.CENTER_LEFT);
        buttonThree.setAlignment(Pos.CENTER_LEFT);
        buttonFour.setAlignment(Pos.CENTER_LEFT);
        buttonFive.setAlignment(Pos.CENTER_LEFT);
        buttonList.add(buttonOne);
        buttonList.add(buttonTwo);
        buttonList.add(buttonThree);
        buttonList.add(buttonFour);
        buttonList.add(buttonFive);
    }

    @FXML
    private void onClickAppPagePane() {
        searchTable.setVisible(false);
    }

    @FXML
    private void onEnterExtend() {
        isMouseExitInformationPage = false;
        if ((!isVBoxOpened) || (isVBoxOnCloseAnimation)) {
            openMenuVBox();
        }
    }

    @FXML
    private void onExitExtend() {
        isMouseExitInformationPage = true;
        Timeline timeline_menuDelay = new Timeline(new KeyFrame(Duration.millis(0.1), e -> {
            if (isMouseExitInformationPage) {
                if ((isVBoxOpened) || (isVBoxOnOpenAnimation)) {
                    closeMenuVBox();
                }
            }
            isMouseExitInformationPage = true;
        }));
        timeline_menuDelay.play();

    }

    @FXML
    private void onEnterHome() {
        if (currentPaneStatus != CurrentPaneStatus.HOMEPAGE) {
            changeButtonColorOn(CurrentPaneStatus.HOMEPAGE);
        }
    }

    @FXML
    private void onExitHome() {
        if (currentPaneStatus != CurrentPaneStatus.HOMEPAGE) {
            changeButtonColorOff(CurrentPaneStatus.HOMEPAGE);
        }
    }

    @FXML
    private void enterWarehouseButton() {
        if (currentPaneStatus != CurrentPaneStatus.WAREHOUSE) {
            changeButtonColorOn(CurrentPaneStatus.WAREHOUSE);
        }
    }

    @FXML
    private void exitWarehouseButton() {
        if (currentPaneStatus != CurrentPaneStatus.WAREHOUSE) {
            changeButtonColorOff(CurrentPaneStatus.WAREHOUSE);
        }
    }

    @FXML
    private void enterStaffButton() {
        if (currentPaneStatus != CurrentPaneStatus.STAFF) {
            changeButtonColorOn(CurrentPaneStatus.STAFF);
        }
    }

    @FXML
    private void exitStaffButton() {
        if (currentPaneStatus != CurrentPaneStatus.STAFF) {
            changeButtonColorOff(CurrentPaneStatus.STAFF);
        }
    }

    @FXML
    private void enterTransactionButton() {
        if (currentPaneStatus != CurrentPaneStatus.TRANSACTION) {
            changeButtonColorOn(CurrentPaneStatus.TRANSACTION);
        }
    }

    @FXML
    private void exitTransactionButton() {
        if (currentPaneStatus != CurrentPaneStatus.TRANSACTION) {
            changeButtonColorOff(CurrentPaneStatus.TRANSACTION);
        }
    }

    @FXML
    private void enterMessageButton() {
        if (currentPaneStatus != CurrentPaneStatus.MESSAGE) {
            changeButtonColorOn(CurrentPaneStatus.MESSAGE);
        }
    }

    @FXML
    private void exitMessageButton() {
        if (currentPaneStatus != CurrentPaneStatus.MESSAGE) {
            changeButtonColorOff(CurrentPaneStatus.MESSAGE);
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
    public void onClickTransaction() {
        if (currentPaneStatus != CurrentPaneStatus.TRANSACTION) {
            changePaneAnimation(currentPaneStatus, CurrentPaneStatus.TRANSACTION);
            changeButtonColorOff(currentPaneStatus);
            currentPaneStatus = CurrentPaneStatus.TRANSACTION;
            changeButtonColorOn(currentPaneStatus);
        }
    }

    /**
     * close and erase all operations in setting dialog
     */
    @FXML
    private void onCloseSettings() {
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(settingsDialog, 300, 1, 0);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(settingsDialog, 300, 0, -150);
        translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
        translateTransition.setOnFinished(event -> {
            settingsDialog.setVisible(false);
            infoVBox.setVisible(true);
            passwordVBox.setVisible(false);
            currentInfoTextField.clear();
            newInfoTextField.clear();
            currentPasswordField.clear();
            newPasswordField.clear();
            notificationLabel.setText("");
            blockPane.setVisible(false);
        });
        fadeTransition.play();
        translateTransition.play();
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
    private void onEnterCargoBox1() {
        cargoBoxNumber = CargoBoxNumber.ONE;
        enterCargoBoxAnimation(cargoBoxNumber);
    }

    @FXML
    private void onEnterCargoBox2() {
        cargoBoxNumber = CargoBoxNumber.TWO;
        enterCargoBoxAnimation(cargoBoxNumber);
    }

    @FXML
    private void onEnterCargoBox3() {
        cargoBoxNumber = CargoBoxNumber.THREE;
        enterCargoBoxAnimation(cargoBoxNumber);
    }

    @FXML
    private void onEnterCargoBox4() {
        cargoBoxNumber = CargoBoxNumber.FOUR;
        enterCargoBoxAnimation(cargoBoxNumber);
    }

    @FXML
    private void onExitCargoBox1() {
        if (!isAppPageBlockPaneOpen) {
            cargoBoxNumber = CargoBoxNumber.ONE;
            exitCargoBoxAnimation(cargoBoxNumber);
        }
    }

    @FXML
    private void onExitCargoBox2() {
        if (!isAppPageBlockPaneOpen) {
            cargoBoxNumber = CargoBoxNumber.TWO;
            exitCargoBoxAnimation(cargoBoxNumber);
        }
    }

    @FXML
    private void onExitCargoBox3() {
        if (!isAppPageBlockPaneOpen) {
            cargoBoxNumber = CargoBoxNumber.THREE;
            exitCargoBoxAnimation(cargoBoxNumber);
        }
    }

    @FXML
    private void onExitCargoBox4() {
        if (!isAppPageBlockPaneOpen) {
            cargoBoxNumber = CargoBoxNumber.FOUR;
            exitCargoBoxAnimation(cargoBoxNumber);
        }
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
                currentInfoTextField.setFloatingText(" Old " + infoType);
                newInfoTextField.setFloatingText(" New " + infoType);
                settingsDialog.setHeaderText("Update your Username");
                break;
            case "Email":
                isUpdatingUsername = false;
                isUpdatingEmail = true;
                isUpdatingPassword = false;
                infoVBox.setVisible(true);
                passwordVBox.setVisible(false);
                currentInfoTextField.setFloatingText(" Old " + infoType);
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
        currentInfoTextField.clear();
        newInfoTextField.clear();
        currentPasswordField.clear();
        newPasswordField.clear();
        notificationLabel.setText("");
    }

    /**
     * shift the line under the buttons pursuant to the info that is being updating
     */
    private void setTransitionSettingLine() {
        if (isUpdatingUsername && !isUpdatingEmail && !isUpdatingPassword) {
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionToX(transitionSettingLine, 500, 0);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.play();
        } else if (!isUpdatingUsername && isUpdatingEmail && !isUpdatingPassword) {
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionToX(transitionSettingLine, 500, 90);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.play();
        } else if (!isUpdatingUsername && !isUpdatingEmail && isUpdatingPassword) {
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
        //get new password
        String newPassword = newPasswordField.getText();
        if (isUpdatingUsername && !isUpdatingEmail && !isUpdatingPassword) {
            // updating username
            if (currentInfo.equals(currentUsername) && newInfo.length() > 1) {
                // old username is matched with database and new username has length of at least 2
                currentUser.setUsername(newInfo);
                userService.update(currentUser);
                notificationLabel.setText("Username updated");
            } else {
                notificationLabel.setText("Invalid information");
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
                notificationLabel.setText("Invalid information");
            }
        } else if (!isUpdatingUsername && !isUpdatingEmail && isUpdatingPassword) {
            // updating password
            if (currentPassword.equals(currentUserPassword) && newPassword.length() > 5) {
                // old password is matched and new password has length of at least 6
                currentUser.setPassword(newPassword);
                userService.update(currentUser);
                notificationLabel.setText("Password updated");
            } else {
                notificationLabel.setText("Invalid information");
            }
        }
    }

    private boolean isTransactionStatusTaken(Transaction transaction) {
        return Objects.equals(transaction.getStatus(), "TAKEN");
    }

    @SuppressWarnings("all")
    private void setTransactionId(Label label, Transaction transaction) {
        String string = Integer.valueOf(transaction.getID()).toString();
        int zeroFill = 4 - string.length();
        if (string.length() < 4) {
            for (int i = 0; i < zeroFill; i++) {
                string = "0" + string;
            }
        }
        label.setText(string);
    }

    @Warning(Warning.WarningType.DELETE_IN_FUTURE)
    private void setTransactionTime(TextField textField, Transaction transaction) {
        String recordTime = transaction.getTransactionTime();
        if (recordTime != null && recordTime != "") {
            System.out.println(recordTime);
            String[] s = recordTime.trim().split(" ");
            String[] split = s[1].split(":");
            textField.setText(split[0] + " : " + split[1] + " : " + split[2]);
        } else {
            textField.setText("Not Applicable");
        }
    }

    private void setTransactionDialog(Transaction transaction) {
        setTransactionId(transactionIdLabel, transaction);
        encapsulatedTransaction.setID(transaction.getID());
        transactionNameInDetails.setText(transaction.getItemName());
        staffNameInDetails.setText(transaction.getStaffName());
        purposeTextInDetails.setText(transaction.getPurpose());
        transactionStatusInDetails.setText(transaction.getStatus());
        transactionAmountInDetails.setText(String.valueOf(transaction.getUnit()));
        transactionDateInDetails.setText(transaction.getTransactionTime());
        isAppPageBlockPaneOpen = true;
        appPageBlockPane.setVisible(true);
        transactionDialog.setVisible(true);
        transactionDialog.setOpacity(0);
        transactionDialog.setPickOnBounds(true);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(transactionDialog, 300, 0, 1);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(transactionDialog, 300, -200, 0);
        translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
        fadeTransition.play();
        translateTransition.play();
    }
    
    @FXML
    private void onClickTransactionOne() {
        transaction = dateTransactionListInAppPage[0];
        setTransactionDialog(dateTransactionListInAppPage[0]);
    }

    @FXML
    private void onClickTransactionTwo() {
        transaction = dateTransactionListInAppPage[1];
        setTransactionDialog(dateTransactionListInAppPage[1]);
    }

    @FXML
    private void onClickTransactionThree() {
        transaction = dateTransactionListInAppPage[2];
        setTransactionDialog(dateTransactionListInAppPage[2]);
    }

    @FXML
    private void onClickTransactionFour() {
        transaction = dateTransactionListInAppPage[3];
        setTransactionDialog(dateTransactionListInAppPage[3]);
    }

    /**
     * !!!!!! WARNING !!!!!!
     * Check the catch ignore section for error message:
     * 1. Duplicate transaction
     */
    @Warning(Warning.WarningType.DEBUG)
    @FXML
    private void onClickApply() {
        try {
            generateEncapsulatedTransaction();
        } catch (Exception exception) {
            warnMessageInAdd.setVisible(true);
            return;
        }
        if (Objects.equals(encapsulatedTransaction, transaction)) {
            return;
        }
        cargoDialogApplyButton.setVisible(false);
        loadSpinnerInAdd.setVisible(true);
        executorService.execute(() -> {
            cachedTransactionService.updateTransaction(encapsulatedTransaction);
            try {

            } finally {
                Platform.runLater(() -> {
                    fillCargoBoxesInformation(buttonSelected);
                    cargoDialogApplyButton.setVisible(true);
                    loadSpinnerInAdd.setVisible(false);
                    warnMessageInAdd.setVisible(false);
                });
            }
        });
    }

    private void generateEncapsulatedTransaction() throws EmptyValueException {
        encapsulatedTransaction.setUnit(Integer.parseInt(transactionAmountInDetails.getText()));
        encapsulatedTransaction.setTransactionTime(transactionDateInDetails.getText());
        encapsulatedTransaction.setPurpose(purposeTextInDetails.getText());
        if ((staffNameInDetails.getText().isBlank()) || (transactionNameInDetails.getText().isBlank())) {
            throw new EmptyValueException("Input Value for item/staff name is empty or blank");
        } else {
            encapsulatedTransaction.setStaffName(staffNameInDetails.getText().trim());
            encapsulatedTransaction.setItemName(transactionNameInDetails.getText().trim());
        }
        encapsulatedTransaction.setStatus(transactionStatusInDetails.getText());
    }

    /**
     * search an indicated staff name
     */
    @FXML
    private void onClickStaffSearch() {
        setSearchProperty(true);
        DataUtils.transactionPageController.setSearchProperty(true);
    }

    /**
     * search an indicated cargo name
     */
    @FXML
    private void onClickCargoSearch() {
        setSearchProperty(false);
        DataUtils.transactionPageController.setSearchProperty(false);
    }

    /**
     * indicates if the keyword is related to cargo or staff
     *
     * @param isStaff searching staff if true
     */
    public void setSearchProperty(boolean isStaff) {
        if (isStaff) {
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
            NewTransactionPageController.isSearchingStaff = false;
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
            NewTransactionPageController.isSearchingStaff = true;
        }
    }

    /**
     * reload cache from database
     */
    private void refreshCache() throws UnsupportedPojoException {
        regulatoryService.updateAllRegulatoryData();
        emailService.updateCachedEmailData();
        Platform.runLater(() -> {
            setCargoPageCount();
            setEmailPageCount();
            setCargoTable(0);
            setEmailTable(0);
            try {
                DataUtils.warehouseController.onRefresh();
                DataUtils.transactionPageController.onRefresh();
                DataUtils.staffController.onRefresh();
            } catch (UnsupportedPojoException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void onRefresh() throws UnsupportedPojoException {
        refreshImage.setVisible(false);
        refreshSpinner.setVisible(true);
        executorService.execute(() -> {
            try {
                refreshCache();
                Platform.runLater(()->{
                    fillCargoBoxesInformation(buttonSelected);
                });
            } catch (UnsupportedPojoException e) {
                throw new RuntimeException(e);
            } finally {
                refreshImage.setVisible(true);
                refreshSpinner.setVisible(false);
            }
        });
    }

}