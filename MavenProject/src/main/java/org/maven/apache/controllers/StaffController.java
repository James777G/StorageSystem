package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.MyLauncher;
import org.maven.apache.exception.EmptyValueException;
import org.maven.apache.exception.UnsupportedPojoException;
import org.maven.apache.service.search.SearchResultService;
import org.maven.apache.service.search.SearchResultServiceHandler;
import org.maven.apache.service.staff.CachedStaffService;
import org.maven.apache.staff.Staff;
import org.maven.apache.utils.*;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class StaffController implements Initializable {

    private enum Status {
        ACTIVE, ALL, INACTIVE
    }

    @FXML
    private Label staffNameOne, staffNameTwo, staffNameThree, staffNameFour, staffNameFive, staffNameSix, staffNameSeven;

    @FXML
    private Label staffIdOne, staffIdTwo, staffIdThree, staffIdFour, staffIdFive, staffIdSix, staffIdSeven;

    @FXML
    private Label staffStatusOne, staffStatusTwo, staffStatusThree, staffStatusFour, staffStatusFive, staffStatusSix, staffStatusSeven;

    @FXML
    private Label warnMessageInAdd;

    @FXML
    private Label staffIdInDetails;

    @FXML
    private Label warnMessageInDetails;

    @FXML
    private JFXButton editOne, editTwo, editThree, editFour, editFive, editSix, editSeven;

    @FXML
    private JFXButton applyButton;

    @FXML
    private JFXButton applyButtonInAdd;

    @FXML
    private ImageView deleteOne, deleteTwo, deleteThree, deleteFour, deleteFive, deleteSix, deleteSeven;

    @FXML
    private ImageView doNotContinueButton;

    @FXML
    private ImageView doContinueButton;

    @FXML
    private MFXGenericDialog descriptionDialog;

    @FXML
    private MFXProgressSpinner loadSpinner;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField staffNameInDetails;

    @FXML
    private TextField staffNameInAdd;

    @FXML
    private MFXTextField searchBar;

    @FXML
    private MFXToggleButton staffStatusInDetails;

    @FXML
    private TextArea staffDescriptionInDetails;

    @FXML
    private TextArea staffDescriptionInAdd;

    @FXML
    private AnchorPane addButton;

    @FXML
    private AnchorPane blockPane;

    @FXML
    private AnchorPane addStaffPane;

    @FXML
    private AnchorPane staffPane1, staffPane2, staffPane3, staffPane4, staffPane5, staffPane6, staffPane7;

    @FXML
    private AnchorPane deleteItemPane;

    @FXML
    private MFXCheckbox staffStatusInAdd;

    @FXML
    private MFXCheckbox statusButton;

    @FXML
    private MFXProgressSpinner loadSpinnerInAdd;

    @FXML
    private MFXProgressSpinner loadSpinnerOnDeletePane;

    @SuppressWarnings("all")
    private final SearchResultService<Staff> searchResultService = MyLauncher.context.getBean("searchResultService", SearchResultService.class);

    private final ExecutorService executorService = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

    private final CachedStaffService staffService = MyLauncher.context.getBean("staffService", CachedStaffService.class);

    private final Label[] nameList = new Label[7];

    private final Label[] idList = new Label[7];

    private final Label[] statusList = new Label[7];

    private final JFXButton[] buttonList = new JFXButton[7];

    private final ImageView[] deleteList = new ImageView[7];

    private AnchorPane[] staffPanes = new AnchorPane[7];

    private List<Staff> currentStaffList;

    private List<Staff> currentActiveStaffList;

    private List<Staff> currentInactiveStaffList;

    private Status status = Status.ALL;

    private Integer selectedStaffId;

    private Staff selectedStaff;

    private int pageNumber;

    private boolean isBlockPaneOpen = false;

    /**
     * Please check if text formatter has been applied
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
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
        getStaffList(pagination.getCurrentPageIndex());
        try {
            calculatePageNumber();
        } catch (UnsupportedPojoException e) {
            throw new RuntimeException(e);
        }
        pagination.setPageCount(pageNumber);
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            getStaffList(newValue);
            assignStaffValue();
        });
        DataUtils.staffController = this;
        initializeNameList();
        initializeIdList();
        initializeStatusList();
        initializeButtonList();
        initializeDeleteList();
        initializeStaffPaneList();
        assignStaffValue();
        setInitialDeleteImageViews();
        loadSpinnerInAdd.setVisible(false);
        warnMessageInAdd.setVisible(false);
        warnMessageInDetails.setVisible(false);
        deleteItemPane.setVisible(false);
        addStaffPane.setVisible(false);
        loadSpinnerOnDeletePane.setVisible(false);
        statusButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                status = Status.ACTIVE;
            } else {
                status = Status.ALL;
            }
            try {
                calculatePageNumber();
            } catch (UnsupportedPojoException e) {
                throw new RuntimeException(e);
            }
            assignStaffValue();
        });
        blockPane.setVisible(false);
    }

    private void run() {
        warnMessageInDetails.setVisible(false);
    }

    @FXML
    private void onScrolled(ScrollEvent event) {
        if (event.getDeltaY() < 0) {
            pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() + 1);
        }
        if (event.getDeltaY() > 0) {
            pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() - 1);
        }
    }

    /**
     * This method updates the latest page number, thus should be executed every time when
     * there is an update in data or change in status
     */
    private void calculatePageNumber() throws UnsupportedPojoException {
        if (status == Status.ALL) {
            if (!searchBar.getText().isBlank()) {
                pageNumber = searchResultService
                        .getPagedResultList(StaffCachedUtils
                                        .getLists(StaffCachedUtils.listType.ALL),
                                searchBar.getText(),
                                SearchResultServiceHandler.ResultType.STAFF)
                        .size();
            } else {
                pageNumber = StaffCachedUtils.getLists(StaffCachedUtils.listType.ALL).size();
            }
        } else if (status == Status.INACTIVE) {
            if (!searchBar.getText().isBlank()) {
                pageNumber = searchResultService
                        .getPagedResultList(StaffCachedUtils
                                        .getLists(StaffCachedUtils.listType.INACTIVE),
                                searchBar.getText(),
                                SearchResultServiceHandler.ResultType.STAFF)
                        .size();
            } else {
                pageNumber = StaffCachedUtils.getLists(StaffCachedUtils.listType.INACTIVE).size();
            }
        } else {
            if (!searchBar.getText().isBlank()) {
                pageNumber = searchResultService
                        .getPagedResultList(StaffCachedUtils
                                        .getLists(StaffCachedUtils.listType.ACTIVE),
                                searchBar.getText(),
                                SearchResultServiceHandler.ResultType.STAFF)
                        .size();
            } else {
                pageNumber = StaffCachedUtils.getLists(StaffCachedUtils.listType.ACTIVE).size();
            }
        }
        if (pageNumber != 0) {
            pagination.setPageCount(pageNumber);
        } else {
            pagination.setPageCount(1);
        }

    }

    /**
     * This method extracts the data from cache and store them as local variables, thus
     * this method should be executed every time when there is a change in the cache.
     *
     * @param newValue newValue stands for the current page number.
     */
    private void getStaffList(Number newValue) {
        try {
            if (newValue.intValue() < StaffCachedUtils.getLists(StaffCachedUtils.listType.ALL).size() && searchBar.getText().isBlank()) {
                currentStaffList = StaffCachedUtils.getLists(StaffCachedUtils.listType.ALL).get(newValue.intValue());
            } else if (!searchBar.getText().isBlank()) {
                currentStaffList = searchResultService
                        .getPagedResultList(StaffCachedUtils.getLists(StaffCachedUtils.listType.ALL),
                                searchBar.getText(),
                                SearchResultServiceHandler.ResultType.STAFF)
                        .get(newValue.intValue());
            }
        } catch (Exception e) {
            currentStaffList = new ArrayList<>();
        }
        try {
            if (newValue.intValue() < StaffCachedUtils.getLists(StaffCachedUtils.listType.ACTIVE).size() && searchBar.getText().isBlank()) {
                currentActiveStaffList = StaffCachedUtils.getLists(StaffCachedUtils.listType.ACTIVE).get(newValue.intValue());
            } else if (!searchBar.getText().isBlank()) {
                currentActiveStaffList = searchResultService
                        .getPagedResultList(StaffCachedUtils.getLists(StaffCachedUtils.listType.ACTIVE),
                                searchBar.getText(),
                                SearchResultServiceHandler.ResultType.STAFF)
                        .get(newValue.intValue());
            }
        } catch (Exception e) {
            currentActiveStaffList = new ArrayList<>();
        }
        try {
            if (newValue.intValue() < StaffCachedUtils.getLists(StaffCachedUtils.listType.INACTIVE).size() && searchBar.getText().isBlank()) {
                currentInactiveStaffList = StaffCachedUtils.getLists(StaffCachedUtils.listType.INACTIVE).get(newValue.intValue());
            } else if (!searchBar.getText().isBlank()) {
                currentInactiveStaffList = searchResultService
                        .getPagedResultList(StaffCachedUtils.getLists(StaffCachedUtils.listType.INACTIVE),
                                searchBar.getText(),
                                SearchResultServiceHandler.ResultType.STAFF)
                        .get(newValue.intValue());
            }
        } catch (Exception e) {
            currentInactiveStaffList = new ArrayList<>();
        }

    }

    /**
     * This method assigns the values based on the current selected status, and this must be called
     * everytime when there is a change in status
     */
    private void assignStaffValue() {
        if (Status.ALL == status) {
            setTextWithCurrentList(currentStaffList);
        } else if (Status.ACTIVE == status) {
            setTextWithCurrentList(currentActiveStaffList);
        } else {
            setTextWithCurrentList(currentInactiveStaffList);
        }
    }

    /**
     * This method is responsible for setting data to the table and display to the users.
     * however, this method should not be used directly, please use {@link #assignStaffValue()};
     *
     * @param currentStaffList the list chosen to display the data
     */
    @Deprecated
    @SuppressWarnings("all")
    private void setTextWithCurrentList(List<Staff> currentStaffList) {
        for (int i = 0; i < currentStaffList.size(); i++) {
            staffPanes[i].setVisible(true);
            buttonList[i].setDisable(false);
            nameList[i].setText(currentStaffList.get(i).getStaffName());
            idList[i].setText(Integer.valueOf(currentStaffList.get(i).getStaffID()).toString());
            statusList[i].setText(currentStaffList.get(i).getStatus());
        }
        for (int j = currentStaffList.size(); j < nameList.length; j++) {
            staffPanes[j].setVisible(false);
        }
    }

    /**
     * This method initializes the name elements into a list, so that any further modifications
     * on the names can be done easily.
     *
     * <p>
     * This method is only used in {@link #initialize(URL, ResourceBundle)}, and should not
     * be used in anywhere else
     * </p>
     */
    @Deprecated
    @SuppressWarnings("all")
    private void initializeNameList() {
        nameList[0] = staffNameOne;
        nameList[1] = staffNameTwo;
        nameList[2] = staffNameThree;
        nameList[3] = staffNameFour;
        nameList[4] = staffNameFive;
        nameList[5] = staffNameSix;
        nameList[6] = staffNameSeven;
    }

    /**
     * This method initializes the id elements into a list, so that any further modifications
     * on the ids can be done easily.
     *
     * <p>
     * This method is only used in {@link #initialize(URL, ResourceBundle)}, and should not
     * be used in anywhere else
     * </p>
     */
    @Deprecated
    @SuppressWarnings("all")
    private void initializeIdList() {
        idList[0] = staffIdOne;
        idList[1] = staffIdTwo;
        idList[2] = staffIdThree;
        idList[3] = staffIdFour;
        idList[4] = staffIdFive;
        idList[5] = staffIdSix;
        idList[6] = staffIdSeven;
    }

    /**
     * This method initializes the status elements into a list, so that any further modifications
     * on the status can be done easily.
     *
     * <p>
     * This method is only used in {@link #initialize(URL, ResourceBundle)}, and should not
     * be used in anywhere else
     * </p>
     */
    @Deprecated
    @SuppressWarnings("all")
    private void initializeStatusList() {
        statusList[0] = staffStatusOne;
        statusList[1] = staffStatusTwo;
        statusList[2] = staffStatusThree;
        statusList[3] = staffStatusFour;
        statusList[4] = staffStatusFive;
        statusList[5] = staffStatusSix;
        statusList[6] = staffStatusSeven;
    }

    /**
     * This method initializes the button elements into a list, so that any further modifications
     * on the buttons can be done easily.
     *
     * <p>
     * This method is only used in {@link #initialize(URL, ResourceBundle)}, and should not
     * be used in anywhere else
     * </p>
     */
    @Deprecated
    @SuppressWarnings("all")
    private void initializeButtonList() {
        buttonList[0] = editOne;
        buttonList[1] = editTwo;
        buttonList[2] = editThree;
        buttonList[3] = editFour;
        buttonList[4] = editFive;
        buttonList[5] = editSix;
        buttonList[6] = editSeven;
    }

    @Deprecated
    @SuppressWarnings("all")
    private void initializeDeleteList() {
        deleteList[0] = deleteOne;
        deleteList[1] = deleteTwo;
        deleteList[2] = deleteThree;
        deleteList[3] = deleteFour;
        deleteList[4] = deleteFive;
        deleteList[5] = deleteSix;
        deleteList[6] = deleteSeven;
    }

    private void initializeStaffPaneList() {
        staffPanes[0] = staffPane1;
        staffPanes[1] = staffPane2;
        staffPanes[2] = staffPane3;
        staffPanes[3] = staffPane4;
        staffPanes[4] = staffPane5;
        staffPanes[5] = staffPane6;
        staffPanes[6] = staffPane7;
    }

    private void setInitialDeleteImageViews() {
        deleteList[0].setVisible(false);
        deleteList[1].setVisible(false);
        deleteList[2].setVisible(false);
        deleteList[3].setVisible(false);
        deleteList[4].setVisible(false);
        deleteList[5].setVisible(false);
        deleteList[6].setVisible(false);

    }

    @FXML
    private void onClickSearch() throws UnsupportedPojoException {
        getStaffList(pagination.getCurrentPageIndex());
        assignStaffValue();
        calculatePageNumber();
    }

    @FXML
    private void doNotContinue() {
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteItemPane, 300, 1, 0);
        fadeTransition.setOnFinished(event -> {
            deleteItemPane.setVisible(false);
            isBlockPaneOpen = false;
            blockPane.setVisible(false);
            setInitialDeleteImageViews();
        });
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteItemPane, 300, 0, -45.5);
        translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
        translateTransition.play();
        fadeTransition.play();
    }

    @FXML
    private void doContinue() {
        loadSpinnerOnDeletePane.setVisible(true);
        doContinueButton.setVisible(false);
        executorService.execute(() -> {
            try {
                staffService.deleteStaffById(selectedStaffId);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                staffService.updateAllCachedStaffData();
                getStaffList(pagination.getCurrentPageIndex());
                Platform.runLater(this::assignStaffValue);
            }
            executorService.execute(() -> {
                Platform.runLater(() -> {
                    try {
                        calculatePageNumber();
                    } catch (UnsupportedPojoException e) {
                        throw new RuntimeException(e);
                    }
                });
                ;
            });
            Platform.runLater(() -> pagination.setPageCount(pageNumber));
            getStaffList(pagination.getCurrentPageIndex());
            Platform.runLater(this::assignStaffValue);
            Platform.runLater(() -> {
                FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteItemPane, 300, 1, 0);
                fadeTransition.setOnFinished(event -> {
                    loadSpinnerOnDeletePane.setVisible(false);
                    doContinueButton.setVisible(true);
                    deleteItemPane.setVisible(false);
                    isBlockPaneOpen = false;
                    blockPane.setVisible(false);
                    setInitialDeleteImageViews();
                });
                TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteItemPane, 300, 0, -45.5);
                translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
                translateTransition.play();
                fadeTransition.play();
            });
        });
    }

    @FXML
    private void onEnterStaffPane1() {
        deleteList[0].setVisible(true);
    }

    @FXML
    private void onEnterStaffPane2() {
        deleteList[1].setVisible(true);
    }

    @FXML
    private void onEnterStaffPane3() {
        deleteList[2].setVisible(true);
    }

    @FXML
    private void onEnterStaffPane4() {
        deleteList[3].setVisible(true);
    }

    @FXML
    private void onEnterStaffPane5() {
        deleteList[4].setVisible(true);
    }

    @FXML
    private void onEnterStaffPane6() {
        deleteList[5].setVisible(true);
    }

    @FXML
    private void onEnterStaffPane7() {
        deleteList[6].setVisible(true);
    }

    @FXML
    private void onExitStaffPane1() {
        if (!isBlockPaneOpen) {
            deleteList[0].setVisible(false);
        }
    }

    @FXML
    private void onExitStaffPane2() {
        if (!isBlockPaneOpen) {
            deleteList[1].setVisible(false);
        }
    }

    @FXML
    private void onExitStaffPane3() {
        if (!isBlockPaneOpen) {
            deleteList[2].setVisible(false);
        }
    }

    @FXML
    private void onExitStaffPane4() {
        if (!isBlockPaneOpen) {
            deleteList[3].setVisible(false);
        }
    }

    @FXML
    private void onExitStaffPane5() {
        if (!isBlockPaneOpen) {
            deleteList[4].setVisible(false);
        }
    }

    @FXML
    private void onExitStaffPane6() {
        if (!isBlockPaneOpen) {
            deleteList[5].setVisible(false);
        }
    }

    @FXML
    private void onExitStaffPane7() {
        if (!isBlockPaneOpen) {
            deleteList[6].setVisible(false);
        }
    }

    @FXML
    private void onEnterTick() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(doContinueButton, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitTick() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(doContinueButton, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterCross() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(doNotContinueButton, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitCross() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(doNotContinueButton, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteOne() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteOne, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteOne() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteOne, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteTwo() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteTwo, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteTwo() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteTwo, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteThree() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteThree, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteThree() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteThree, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteFour() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteFour, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteFour() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteFour, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteFive() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteFive, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteFive() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteFive, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteSix() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteSix, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteSix() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteSix, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteSeven() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteSeven, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteSeven() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteSeven, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onClickDeleteOne() {
        setDeletionPanes(0);
    }

    @FXML
    private void onClickDeleteTwo() {
        setDeletionPanes(1);
    }

    @FXML
    private void onClickDeleteThree() {
        setDeletionPanes(2);
    }

    @FXML
    private void onClickDeleteFour() {
        setDeletionPanes(3);
    }

    @FXML
    private void onClickDeleteFive() {
        setDeletionPanes(4);
    }

    @FXML
    private void onClickDeleteSix() {
        setDeletionPanes(5);
    }

    @FXML
    private void onClickDeleteSeven() {
        setDeletionPanes(6);
    }

    /**
     * This method invokes the existence of block pane and the deletion notification
     */
    private void setDeletionPanes(int row) {
        List<Staff> currentList = getCurrentList();
        Staff staff = currentList.get(row);
        selectedStaffId = staff.getStaffID();
        isBlockPaneOpen = true;
        blockPane.setVisible(true);
        deleteItemPane.setOpacity(0);
        deleteItemPane.setVisible(true);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteItemPane, 300, 0, 1);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteItemPane, 300, -45.5, 0);
        translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
        translateTransition.play();
        fadeTransition.play();
    }

    /**
     * This method justifies which list is to be used under the current selected status.
     *
     * @return the list to be used and displayed.
     */
    private List<Staff> getCurrentList() {
        List<Staff> currentList;
        if (Status.ALL.equals(status)) {
            currentList = currentStaffList;
        } else if (Status.ACTIVE.equals(status)) {
            currentList = currentActiveStaffList;
        } else {
            currentList = currentInactiveStaffList;
        }
        return currentList;
    }

    /**
     * This method assigns the values for the details' pane {@link #descriptionDialog}, and is used every time entering
     * the details' pane in prior.
     */
    private void assignStaffDetails() {
        staffNameInDetails.setText(selectedStaff.getStaffName());
        staffIdInDetails.setText(Integer.valueOf(selectedStaff.getStaffID()).toString());
        staffStatusInDetails.setSelected("ACTIVE".equals(selectedStaff.getStatus()));
        staffDescriptionInDetails.setText(selectedStaff.getOtherInfo());
        blockPane.setVisible(true);
        descriptionDialog.setOpacity(0);
        descriptionDialog.setVisible(true);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(descriptionDialog, 300, 0, 1);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(descriptionDialog, 300, -170, 0);
        translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
        fadeTransition.play();
        translateTransition.play();
    }

    /**
     * Opens up the details pane {@link #descriptionDialog} with the corresponding values.
     */
    @FXML
    private void onClickEditOne() {
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(0);
        assignStaffDetails();
    }

    /**
     * Opens up the details pane {@link #descriptionDialog} with the corresponding values.
     */
    @FXML
    private void onClickEditTwo() {
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(1);
        assignStaffDetails();
    }

    /**
     * Opens up the details pane {@link #descriptionDialog} with the corresponding values.
     */
    @FXML
    private void onClickEditThree() {
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(2);
        assignStaffDetails();
    }

    /**
     * Opens up the details pane {@link #descriptionDialog} with the corresponding values.
     */
    @FXML
    private void onClickEditFour() {
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(3);
        assignStaffDetails();
    }

    /**
     * Opens up the details pane {@link #descriptionDialog} with the corresponding values.
     */
    @FXML
    private void onClickEditFive() {
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(4);
        assignStaffDetails();
    }

    /**
     * Opens up the details pane {@link #descriptionDialog} with the corresponding values.
     */
    @FXML
    private void onClickEditSix() {
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(5);
        assignStaffDetails();
    }

    /**
     * Opens up the details pane {@link #descriptionDialog} with the corresponding values.
     */
    @FXML
    private void onClickEditSeven() {
        List<Staff> currentList = getCurrentList();
        selectedStaff = currentList.get(6);
        assignStaffDetails();
    }

    /**
     * Closes the details pane and reset any animation inside the details' pane
     */
    @FXML
    private void onCloseDescription() {
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(descriptionDialog, 300, 1, 0);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(descriptionDialog, 300, 0, -170);
        translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
        translateTransition.setOnFinished(event -> {
            descriptionDialog.setVisible(false);
            loadSpinner.setVisible(false);
            warnMessageInDetails.setVisible(false);
            blockPane.setVisible(false);
        });
        fadeTransition.play();
        translateTransition.play();
    }

    /**
     * Opens up add pane for inserting new staff data.
     */
    @FXML
    private void onClickAddButton() {
        addStaffPane.setOpacity(0);
        addStaffPane.setVisible(true);
        staffStatusInAdd.setSelected(true);
        blockPane.setVisible(true);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(addStaffPane, 300, 0, 1);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(addStaffPane, 300, -170, 0);
        translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
        fadeTransition.play();
        translateTransition.play();
    }

    /**
     * This method is responsible for half of the hovering animation for {@link #addButton}
     * when mouse is entered.
     */
    @FXML
    private void onEnterAddButton() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(addButton, 500, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    /**
     * This method is responsible for half of the hovering animation for {@link #addButton}
     * when mouse is exited.
     */
    @FXML
    private void onExitAddButton() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(addButton, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    /**
     * This method only delegates the functionalities of {@link #onCloseDescription()}
     */
    @FXML
    private void onClickOkay() {
        onCloseDescription();
    }

    /**
     * This method is responsible for the updating of the current staff data
     * <p>
     * 1. This method is highly time consuming, thus should be executed inside the thread pool
     * 2. This method handles the exceptions that come with encapsulation of the data
     * {@link #encapsulateCurrentStaffData()}
     * 3. This method controls the loading animation {@link #loadSpinner} and the error message
     * {@link #warnMessageInDetails}
     * </p>
     */
    @FXML
    private void onClickApply() {
        applyButton.setVisible(false);
        loadSpinner.setVisible(true);
        executorService.execute(() -> {
            Staff staff;
            try {
                staff = encapsulateCurrentStaffData();
                if (selectedStaff.equals(staff)) {
                    return;
                }
                staffService.updateStaff(staff);
                getStaffList(pagination.getCurrentPageIndex());
                Platform.runLater(this::run);
            } catch (Exception e) {
                warnMessageInDetails.setVisible(true);
            } finally {
                staffService.updateAllCachedStaffData();
                Platform.runLater(() -> {
                    assignStaffValue();
                    loadSpinner.setVisible(false);
                    applyButton.setVisible(true);
                });
            }
        });
    }

    /**
     * This method is responsible for encapsulating all the displayed data in the details' pane
     * {@link #descriptionDialog}, and provide the encapsulated object to {@link #staffService}
     *
     * @return a Staff object containing all the information for update action.
     * @throws EmptyValueException some not-null values are left empty
     */
    private Staff encapsulateCurrentStaffData() throws EmptyValueException {
        Staff staff = new Staff();
        if (staffNameInDetails.getText().isBlank()) {
            throw new EmptyValueException("Empty input values in staff name section");
        }
        staff.setStaffName(staffNameInDetails.getText());
        staff.setStaffID(Integer.parseInt(staffIdInDetails.getText()));
        staff.setOtherInfo(staffDescriptionInDetails.getText());
        staff.setStatus(staffStatusInDetails.isSelected() ? "ACTIVE" : "INACTIVE");
        return staff;
    }

    /**
     * This method closes the add pane.
     */
    @FXML
    private void onClickOkayInAdd() {
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(addStaffPane, 300, 1, 0);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(addStaffPane, 300, 0, -170);
        translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
        translateTransition.setOnFinished(event -> {
            addStaffPane.setVisible(false);
            warnMessageInAdd.setVisible(false);
            staffNameInAdd.setText("");
            staffDescriptionInAdd.setText("");
            staffStatusInAdd.setSelected(true);
            blockPane.setVisible(false);
        });
        fadeTransition.play();
        translateTransition.play();
    }

    /**
     * This method is responsible for the addition of the current staff data
     * <p>
     * 1. This method is highly time consuming, thus should be executed inside the thread pool
     * 2. This method handles the exceptions that come with encapsulation of the data
     * {@link #encapsulateStaffDataInAdd()}
     * 3. This method controls the loading animation {@link #loadSpinnerInAdd} and the error message
     * {@link #warnMessageInAdd}
     * </p>
     */
    @FXML
    private void onClickApplyInAdd() {
        applyButtonInAdd.setVisible(false);
        loadSpinnerInAdd.setVisible(true);
        executorService.execute(() -> {
            Staff staff;
            try {
                staff = encapsulateStaffDataInAdd();
                staffService.addNewStaff(staff);
                getStaffList(pagination.getCurrentPageIndex());
                Platform.runLater(() -> {
                    try {
                        calculatePageNumber();
                    } catch (UnsupportedPojoException e) {
                        throw new RuntimeException(e);
                    }
                });
                Platform.runLater(() -> warnMessageInAdd.setVisible(false));
            } catch (Exception e) {
                warnMessageInAdd.setVisible(true);
            } finally {
                staffService.updateAllCachedStaffData();
                Platform.runLater(() -> {
                    pagination.setPageCount(pageNumber);
                    assignStaffValue();
                    loadSpinnerInAdd.setVisible(false);
                    applyButtonInAdd.setVisible(true);
                });
            }
        });
    }

    /**
     * This method is responsible for encapsulating all the displayed data in the addition pane
     * {@link #addStaffPane}, and provide the encapsulated object to {@link #staffService}
     *
     * @return a Staff object containing all the information for update action.
     * @throws EmptyValueException some not-null values are left empty
     */
    private Staff encapsulateStaffDataInAdd() throws EmptyValueException {
        Staff staff = new Staff();
        if (staffNameInAdd.getText().isBlank()) {
            throw new EmptyValueException("User did not input a name when it is required");
        }
        staff.setStaffName(staffNameInAdd.getText());
        staff.setStatus(staffStatusInAdd.isSelected() ? "ACTIVE" : "INACTIVE");
        staff.setOtherInfo(staffDescriptionInAdd.getText());
        return staff;
    }

    /**
     * search the corresponding transactions pursuant to a particular staff name while moving to transaction page
     */
    @FXML
    private void onViewTransaction() throws UnsupportedPojoException {
        DataUtils.appPage2Controller.setSearchProperty(false);
        DataUtils.transactionPageController.setSearchProperty(false);
        DataUtils.transactionPageController.setKeyword(staffNameInDetails.getText());
        DataUtils.transactionPageController.onClickSearch();
        DataUtils.appPage2Controller.onClickTransaction();
    }

    /**
     * reload cache from database
     */
    @FXML
    public void onRefresh() throws UnsupportedPojoException {
        staffService.updateAllCachedStaffData();
        getStaffList(0);
        assignStaffValue();
        calculatePageNumber();
        pagination.setCurrentPageIndex(0);
    }

}