package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
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
import lombok.extern.slf4j.Slf4j;
import org.maven.apache.MyLauncher;
import org.maven.apache.exception.EmptyValueException;
import org.maven.apache.exception.UnsupportedPojoException;
import org.maven.apache.exception.Warning;
import org.maven.apache.item.Item;
import org.maven.apache.service.item.CachedItemService;
import org.maven.apache.service.search.SearchResultService;
import org.maven.apache.service.search.SearchResultServiceHandler;
import org.maven.apache.utils.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

@Slf4j
public class WarehouseController implements Initializable {

    @FXML
    private Label itemNameOne, itemNameTwo, itemNameThree, itemNameFour, itemNameFive, itemNameSix, itemNameSeven;

    @FXML
    private Label itemIdOne, itemIdTwo, itemIdThree, itemIdFour, itemIdFive, itemIdSix, itemIdSeven;

    @FXML
    private Label itemAmountOne, itemAmountTwo, itemAmountThree, itemAmountFour, itemAmountFive, itemAmountSix, itemAmountSeven;

    @FXML
    private Label itemIdInDetails;

    @FXML
    private Label itemAmountInDetails;

    @FXML
    private Label warnMessage;

    @FXML
    private Label warnMessageInAdd;

    @FXML
    private JFXButton edit1, edit2, edit3, edit4, edit5, edit6, edit7;

    @FXML
    private JFXButton applyButton;

    @FXML
    private JFXButton applyButtonInAdd;

    @FXML
    private JFXButton refreshButton;

    @FXML
    private ImageView deleteOne, deleteTwo, deleteThree, deleteFour, deleteFive, deleteSix, deleteSeven;

    @FXML
    private ImageView doContinueButton;

    @FXML
    private ImageView doNotContinueButton;

    @FXML
    private TextField itemNameInDetails;

    @FXML
    private TextField itemAmountInAdd;

    @FXML
    private TextField itemNameInAdd;

    @FXML
    private TextArea itemDescriptionInDetails;

    @FXML
    private TextArea itemDescriptionInAdd;

    @FXML
    private MFXGenericDialog descriptionDialog;

    @FXML
    private MFXProgressSpinner loadSpinner;

    @FXML
    private MFXProgressSpinner loadSpinnerInAdd;

    @FXML
    private MFXProgressSpinner loadSpinnerOnDeletePane;

    @FXML
    private MFXProgressSpinner refreshSpinner;

    @FXML
    private AnchorPane addButton;

    @FXML
    private AnchorPane addItemPane;

    @FXML
    private AnchorPane blockPane;

    @FXML
    private AnchorPane transactionPane1, transactionPane2, transactionPane3, transactionPane4, transactionPane5, transactionPane6, transactionPane7;

    @FXML
    private AnchorPane deleteItemPane;

    @FXML
    private Pagination newPagination;

    @FXML
    private MFXTextField searchBar;

    @FXML
    private ImageView searchImage11;

    private final SearchResultService<Item> searchResultService = MyLauncher.context.getBean("searchResultService", SearchResultService.class);

    private final CachedItemService cachedItemService = MyLauncher.context.getBean("cachedItemService", CachedItemService.class);

    private final ExecutorService executorService = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

    private int pageSize;

    private List<Item> itemList;

    private final Label[] nameList = new Label[7];

    private final Label[] idList = new Label[7];

    private final Label[] amountList = new Label[7];

    private final ImageView[] deleteList = new ImageView[7];

    private final JFXButton[] buttonList = new JFXButton[7];

    private AnchorPane[] transactionPanes = new AnchorPane[7];

    private Item selectedItem;

    private Integer selectedItemID;

    private boolean isBlockPaneOpen = false;

    /**
     * 1. sets up the word limit for description input field inside the description dialog
     * 2. Initialize the attributes of the data
     * 3. bind listener of the pagination to retrieve current page property dynamically.
     *
     * <p>
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cachedItemService.updateAllCachedItemData();
        itemDescriptionInDetails.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 100 ? change : null));
        itemNameInDetails.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 50 ? change : null));
        loadSpinner.setVisible(false);
        refreshSpinner.setVisible(false);
        descriptionDialog.setVisible(false);
        DataUtils.pagination = newPagination;
        DataUtils.warehouseController = this;
        warnMessage.setVisible(false);
        try {
            calculatePageSize();
        } catch (UnsupportedPojoException e) {
            throw new RuntimeException(e);
        }
        initializeNameList();
        initializeIdList();
        initializeAmountList();
        initializeButtonList();
        initializeDeleteList();
        initializeTransactionPaneList();
        try {
            generateCachedData();
        } catch (UnsupportedPojoException e) {
            throw new RuntimeException(e);
        }
        newPagination.setMaxPageIndicatorCount(8);
        initializeItemList();
        setTableContents();
        deleteItemPane.setVisible(false);
        loadSpinnerInAdd.setVisible(false);
        addItemPane.setVisible(false);
        warnMessageInAdd.setVisible(false);
        loadSpinnerOnDeletePane.setVisible(false);
        newPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> executorService.execute(() -> {
            generateItemList(newValue.intValue());
            Platform.runLater(WarehouseController.this::setTableContents);
        }));
        blockPane.setVisible(false);
        hideDeleteImageViews();
        setTotalPrice();
    }

    private void setTotalPrice(){
        double totalPrice = 0;
        for (int i = 0; i < CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL).size(); i++){
            for (int j = 0; j < CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL).get(i).size(); j++){
                totalPrice = totalPrice + Double.valueOf(CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL).get(i).get(j).getDescription().split("%%")[0]) * CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL).get(i).get(j).getUnit();
            }
        }
        DataUtils.totalPriceLabel.setText(String.valueOf("Total Price: " + totalPrice));
    }

    private void initializeTransactionPaneList() {
        transactionPanes[0] = transactionPane1;
        transactionPanes[1] = transactionPane2;
        transactionPanes[2] = transactionPane3;
        transactionPanes[3] = transactionPane4;
        transactionPanes[4] = transactionPane5;
        transactionPanes[5] = transactionPane6;
        transactionPanes[6] = transactionPane7;
    }

    @FXML
    private void onEnterTransactionPane1() {
        deleteList[0].setVisible(true);
    }

    @FXML
    private void onEnterTransactionPane2() {
        deleteList[1].setVisible(true);
    }

    @FXML
    private void onEnterTransactionPane3() {
        deleteList[2].setVisible(true);
    }

    @FXML
    private void onEnterTransactionPane4() {
        deleteList[3].setVisible(true);
    }

    @FXML
    private void onEnterTransactionPane5() {
        deleteList[4].setVisible(true);
    }

    @FXML
    private void onEnterTransactionPane6() {
        deleteList[5].setVisible(true);
    }

    @FXML
    private void onEnterTransactionPane7() {
        deleteList[6].setVisible(true);
    }

    @FXML
    private void onExitTransactionPane1() {
        if (!isBlockPaneOpen) {
            deleteList[0].setVisible(false);
        }
    }

    @FXML
    private void onExitTransactionPane2() {
        if (!isBlockPaneOpen) {
            deleteList[1].setVisible(false);
        }
    }

    @FXML
    private void onExitTransactionPane3() {
        if (!isBlockPaneOpen) {
            deleteList[2].setVisible(false);
        }
    }

    @FXML
    private void onExitTransactionPane4() {
        if (!isBlockPaneOpen) {
            deleteList[3].setVisible(false);
        }
    }

    @FXML
    private void onExitTransactionPane5() {
        if (!isBlockPaneOpen) {
            deleteList[4].setVisible(false);
        }
    }

    @FXML
    private void onExitTransactionPane6() {
        if (!isBlockPaneOpen) {
            deleteList[5].setVisible(false);
        }
    }

    @FXML
    private void onExitTransactionPane7() {
        if (!isBlockPaneOpen) {
            deleteList[6].setVisible(false);
        }
    }

    private void initializeDeleteList() {
        deleteList[0] = deleteOne;
        deleteList[1] = deleteTwo;
        deleteList[2] = deleteThree;
        deleteList[3] = deleteFour;
        deleteList[4] = deleteFive;
        deleteList[5] = deleteSix;
        deleteList[6] = deleteSeven;
    }

    private void hideDeleteImageViews() {
        Arrays.stream(deleteList).forEach(imageView -> {
            imageView.setVisible(false);
        });
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
     * This methods invokes the existence of block pane and the deletion notification
     */
    private void setDeletionPanes(int row) {
        selectedItemID = itemList.get(row).getItemID();
        deleteItemPane.setOpacity(0);
        deleteItemPane.setVisible(true);
        isBlockPaneOpen = true;
        blockPane.setVisible(true);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteItemPane, 300, 0, 1);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteItemPane, 300, -45, 0);
        translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
        fadeTransition.play();
        translateTransition.play();
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onEdit1() {
        if (itemList.get(0) != null) {
            setItemAttributes(itemList.get(0));
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onEdit2() {
        if (itemList.get(1) != null) {
            setItemAttributes(itemList.get(1));
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onEdit3() {
        if (itemList.get(2) != null) {
            setItemAttributes(itemList.get(2));
        }
    }

    @FXML
    private void onEnterSearchButton(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(searchImage11, 500, 1.2);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();

    }

    @FXML
    private void onExitSearchButton(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(searchImage11, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onEdit4() {
        if (itemList.get(3) != null) {
            setItemAttributes(itemList.get(3));
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onEdit5() {
        if (itemList.get(4) != null) {
            setItemAttributes(itemList.get(4));
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onEdit6() {
        if (itemList.get(5) != null) {
            setItemAttributes(itemList.get(5));
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onEdit7() {
        if (itemList.get(6) != null) {
            setItemAttributes(itemList.get(6));
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
    private void onClickSearch() throws UnsupportedPojoException {
        generateItemList(newPagination.getCurrentPageIndex());
        setTableContents();
        calculatePageSize();
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
    private void doNotContinue() {
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteItemPane, 300, 1, 0);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteItemPane, 300, 0, -45);
        translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
        translateTransition.setOnFinished(event -> {
            deleteItemPane.setVisible(false);
            isBlockPaneOpen = false;
            blockPane.setVisible(false);
            hideDeleteImageViews();
        });
        fadeTransition.play();
        translateTransition.play();
    }

    @FXML
    private void doContinue() {
        loadSpinnerOnDeletePane.setVisible(true);
        doContinueButton.setVisible(false);
        executorService.execute(() -> {
            try {
                cachedItemService.deleteItemById(selectedItemID);
            } catch (Exception e) {
                cachedItemService.updateAllCachedItemData();
            }
            Platform.runLater(() -> {
                try {
                    calculatePageSize();
                } catch (UnsupportedPojoException e) {
                    throw new RuntimeException(e);
                }
            });
            generateItemList(newPagination.getCurrentPageIndex());
            Platform.runLater(this::setTableContents);
            Platform.runLater(() -> {
                FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteItemPane, 300, 1, 0);
                TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteItemPane, 300, -0, -45);
                translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
                translateTransition.setOnFinished(event -> {
                    loadSpinnerOnDeletePane.setVisible(false);
                    doContinueButton.setVisible(true);
                    deleteItemPane.setVisible(false);
                    isBlockPaneOpen = false;
                    blockPane.setVisible(false);
                    hideDeleteImageViews();
                });
                fadeTransition.play();
                translateTransition.play();
            });
        });
    }

    /**
     * close description dialog event, and set the warning message visibility to false
     */
    @FXML
    private void onCloseDescriptionDialog() {
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(descriptionDialog, 300, 1, 0);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(descriptionDialog, 300, 0, -170);
        translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
        translateTransition.setOnFinished(event -> {
            descriptionDialog.setVisible(false);
            warnMessage.setVisible(false);
            blockPane.setVisible(false);
        });
        fadeTransition.play();
        translateTransition.play();
    }

    /**
     * This method sets up the onClickApply event;
     * <p>
     * 1. Check if the data input has changed, if no changes detected,
     * this method is terminated immediately.
     * <p>
     * 2. Check if the item name is left blank, if yes,
     * display the warning message and terminate immediately.
     * <p>
     * 3. Check if the data can be updated in the database, if no,
     * display the warning message; if yes, update the corresponding
     * data, refresh the cached data and display the new data.
     * <p>
     * 4. This method is also responsible for setting the loading animation
     * when performing SQL operations.
     */
    @FXML
    private void onClickApply() {
        warnMessage.setVisible(false);
        Item item;
        try {
            item = encapsulateItemData();
        } catch (Exception e) {
            warnMessage.setVisible(true);
            return;
        }
        if (item.equals(selectedItem)) {
            return;
        }
        Item finalItem = item;
        executorService.execute(() -> {
            Platform.runLater(() -> {
                applyButton.setVisible(false);
                loadSpinner.setVisible(true);
            });
            try {
                cachedItemService.updateItem(finalItem);
                Platform.runLater(() -> {
                    try {
                        generateCachedData();
                    } catch (UnsupportedPojoException e) {
                        throw new RuntimeException(e);
                    }
                    int currentPage = newPagination.getCurrentPageIndex();
                    generateItemList(currentPage);
                    setTableContents();
                });
                Platform.runLater(() -> {
                    applyButton.setVisible(true);
                    loadSpinner.setVisible(false);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    applyButton.setVisible(true);
                    loadSpinner.setVisible(false);
                });
                warnMessage.setVisible(true);
            } finally {
                cachedItemService.updateAllCachedItemData();
                Platform.runLater(() -> {
                    try {
                        generateCachedData();
                    } catch (UnsupportedPojoException e) {
                        throw new RuntimeException(e);
                    }
                    int currentPage = newPagination.getCurrentPageIndex();
                    generateItemList(currentPage);
                    setTableContents();
                });
            }
        });
    }

    /**
     * On click okay event, should have the same functionality as on close event
     */
    @FXML
    private void onClickOkay() {
        onCloseDescriptionDialog();
    }

    /**
     * this method encapsulate all the input and current values in the description dialog
     * into an item object
     *
     * @return item object containing all the attributes
     */
    private Item encapsulateItemData() throws EmptyValueException {
        Item item = new Item();
        item.setItemID(Integer.parseInt(itemIdInDetails.getText()));
        if (itemNameInDetails.getText().isEmpty()) {
            throw new EmptyValueException("empty item name detected");
        }
        item.setItemName(itemNameInDetails.getText());
        if (itemAmountInDetails.getText().isEmpty()) {
            throw new EmptyValueException("empty item amount detected");
        }
        item.setUnit(Integer.parseInt(itemAmountInDetails.getText()));
        item.setDescription(itemDescriptionInDetails.getText());
        return item;
    }

    @FXML
    @Warning(Warning.WarningType.DEBUG)
    private void onClickApplyInAdd() {
        Item item;
        try {
            item = encapsulateCurrentItemInAdd();
        } catch (Exception e) {
            warnMessageInAdd.setVisible(true);
            return;
        }
        applyButtonInAdd.setVisible(false);
        loadSpinnerInAdd.setVisible(true);
        executorService.execute(() -> {
            try {
                cachedItemService.addNewItem(item);
                Platform.runLater(() -> {
                    generateItemList(newPagination.getCurrentPageIndex());
                    try {
                        calculatePageSize();
                    } catch (UnsupportedPojoException e) {
                        throw new RuntimeException(e);
                    }
                    setTableContents();
                    warnMessageInAdd.setVisible(false);
                });
            } catch (Exception e) {
                warnMessageInAdd.setVisible(true);
            } finally {
                cachedItemService.updateAllCachedItemData();
                Platform.runLater(() -> {
                    generateItemList(newPagination.getCurrentPageIndex());
                    try {
                        calculatePageSize();
                    } catch (UnsupportedPojoException e) {
                        throw new RuntimeException(e);
                    }
                    setTableContents();
                });
                loadSpinnerInAdd.setVisible(false);
                applyButtonInAdd.setVisible(true);
            }
        });
    }

    private Item encapsulateCurrentItemInAdd() throws EmptyValueException {
        Item item = new Item();
        if (itemNameInAdd.getText().isBlank() || itemAmountInAdd.getText().isBlank()) {
            throw new EmptyValueException("Input Value for item name/amount is empty or blank");
        } else {
            item.setItemName(itemNameInAdd.getText());
            item.setUnit(Integer.parseInt(itemAmountInAdd.getText()));
            item.setDescription(itemDescriptionInAdd.getText());
        }
        return item;
    }

    @FXML
    private void onClickOkayInAdd() {
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(addItemPane, 300, 1, 0);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(addItemPane, 300, 0, -170);
        translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
        translateTransition.setOnFinished(event -> {
            addItemPane.setVisible(false);
            warnMessageInAdd.setVisible(false);
            loadSpinnerInAdd.setVisible(false);
            blockPane.setVisible(false);
        });
        fadeTransition.play();
        translateTransition.play();
    }

    /**
     * this method acts as an initializer of the item list and should only be used in
     * initialize method
     */
    @Deprecated
    @SuppressWarnings("all")
    private void initializeItemList() {
        if(CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL).isEmpty()){
            itemList = new ArrayList<>();
        }else{
            itemList = CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL).get(0);
        }
    }

    @FXML
    private void onClickAddButton() {
        addItemPane.setOpacity(0);
        addItemPane.setVisible(true);
        blockPane.setVisible(true);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(addItemPane, 300, 0, 1);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(addItemPane, 300, -170, 0);
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
     * this method sets the attributes of the description dialog when check button
     * is clicked
     *
     * @param item the selected item
     */
    private void setItemAttributes(Item item) {
        itemNameInDetails.setText(item.getItemName());
        itemDescriptionInDetails.setText(item.getDescription());
        itemAmountInDetails.setText(item.getUnit().toString());
        itemIdInDetails.setText(item.getItemID().toString());
        selectedItem = item;
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
     * initialize everything in the table into an array for future access
     */
    private void initializeNameList() {
        nameList[0] = itemNameOne;
        nameList[1] = itemNameTwo;
        nameList[2] = itemNameThree;
        nameList[3] = itemNameFour;
        nameList[4] = itemNameFive;
        nameList[5] = itemNameSix;
        nameList[6] = itemNameSeven;
    }

    /**
     * initialize everything in the table into an array for future access
     */
    private void initializeIdList() {
        idList[0] = itemIdOne;
        idList[1] = itemIdTwo;
        idList[2] = itemIdThree;
        idList[3] = itemIdFour;
        idList[4] = itemIdFive;
        idList[5] = itemIdSix;
        idList[6] = itemIdSeven;
    }

    /**
     * initialize everything in the table into an array for future access
     */
    private void initializeAmountList() {
        amountList[0] = itemAmountOne;
        amountList[1] = itemAmountTwo;
        amountList[2] = itemAmountThree;
        amountList[3] = itemAmountFour;
        amountList[4] = itemAmountFive;
        amountList[5] = itemAmountSix;
        amountList[6] = itemAmountSeven;
    }

    /**
     * initialize everything in the table into an array for future access
     */
    private void initializeButtonList() {
        buttonList[0] = edit1;
        buttonList[1] = edit2;
        buttonList[2] = edit3;
        buttonList[3] = edit4;
        buttonList[4] = edit5;
        buttonList[5] = edit6;
        buttonList[6] = edit7;
    }

    /**
     * Return the maximum number of pages of the current data
     */
    private void calculatePageSize() throws UnsupportedPojoException {
        try {
            if (searchBar.getText().isBlank()) {
                pageSize = CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL).size();
            } else {
                pageSize = searchResultService.getPagedResultList(CargoCachedUtils
                                .getLists(CargoCachedUtils.listType.ALL),
                        searchBar.getText(),
                        SearchResultServiceHandler.ResultType.CARGO).size();
            }
        } catch (Exception e) {
            pageSize = 0;
        }
        if (pageSize != 0) {
            newPagination.setPageCount(pageSize);
        } else {
            newPagination.setPageCount(1);
        }
    }


    /**
     * This method sets the latest item list based on the latest cached data
     *
     * @param index page number to be displayed
     */
    private void generateItemList(int index) {
        try {
            if (!searchBar.getText().isBlank()) {
                itemList = searchResultService.getPagedResultList(CargoCachedUtils
                                .getLists(CargoCachedUtils.listType.ALL),
                        searchBar.getText(),
                        SearchResultServiceHandler.ResultType.CARGO).get(index);
            } else {
                itemList = CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL).get(index);
            }
        } catch (Exception e) {
            itemList = new ArrayList<>();
        }
    }

    /**
     * update the data onto the table
     */
    private void setTableContents() {
        setNameContent();
        setIdContent();
        setAmountContent();
        setButtonContent();
        setDeleteContent();
    }

    @FXML
    private void onScrolled(ScrollEvent event) {
        if (event.getDeltaY() < 0) {
            newPagination.setCurrentPageIndex(newPagination.getCurrentPageIndex() + 1);
        }
        if (event.getDeltaY() > 0) {
            newPagination.setCurrentPageIndex(newPagination.getCurrentPageIndex() - 1);
        }
    }

    private void setDeleteContent() {
        for (int i = 0; i < itemList.size(); i++) {
            transactionPanes[i].setVisible(true);
        }
        for (int j = itemList.size(); j < deleteList.length; j++) {
            transactionPanes[j].setVisible(false);
        }
    }

    @FXML
    private void bringToNote() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<AppPage2Controller> appPage2ControllerClass = AppPage2Controller.class;
        Method onClickMessage = appPage2ControllerClass.getDeclaredMethod("onClickMessage");
        onClickMessage.setAccessible(true);
        Class<MessagePageController> messagePageControllerClass = MessagePageController.class;
        Method onClickAddButtonForStaff = messagePageControllerClass.getDeclaredMethod("onClickAddButtonForCargo", String.class, String.class);
        onClickAddButtonForStaff.setAccessible(true);
        onClickMessage.invoke(DataUtils.appPage2Controller);
        onClickAddButtonForStaff.invoke(DataUtils.messageController, itemIdInDetails.getText(), itemNameInDetails.getText());
    }


    private void setButtonContent() {
        for (int j = 0; j < itemList.size(); j++) {
            buttonList[j].setDisable(false);
        }
    }

    /**
     * update name content in the table
     */
    private void setNameContent() {
        for (int i = 0; i < itemList.size(); i++) {
            nameList[i].setText(itemList.get(i).getItemName());
        }
    }

    /**
     * update id content in the table
     */
    private void setIdContent() {
        for (int i = 0; i < itemList.size(); i++) {
            idList[i].setText(itemList.get(i).getItemID().toString());
        }
    }

    /**
     * update amount content in the table
     */
    private void setAmountContent() {
        for (int i = 0; i < itemList.size(); i++) {
            amountList[i].setText(itemList.get(i).getUnit().toString());
        }
    }

    /**
     * 1. generate the cached data and store it as List<List<Item>>
     * 2. The loading of this cached data is slow, thus this process must be delegated to loading animation
     * 3. The cached data generated must be cleared and regenerated everytime when there is a change to the data
     * 4. The cached data is stored as a global variable in DataUtils class.
     * 5. This method is deliberately left public, so as it can be accessed by other controllers.
     */
    public void generateCachedData() throws UnsupportedPojoException {
        calculatePageSize();
        cachedItemService.updateAllCachedItemData();
    }

    /**
     * search the corresponding transactions pursuant to a particular item name while moving to transaction page
     */
    @FXML
    private void onViewTransaction() throws UnsupportedPojoException {
        DataUtils.appPage2Controller.setSearchProperty(true);
        DataUtils.transactionPageController.setSearchProperty(true);
        DataUtils.transactionPageController.setKeyword(itemNameInDetails.getText());
        DataUtils.transactionPageController.onClickSearch();
        DataUtils.appPage2Controller.onClickTransaction();
    }

    /**
     * reload cache from database
     */
    @FXML
    public void onRefresh() throws UnsupportedPojoException {
        refreshButton.setVisible(false);
        refreshSpinner.setVisible(true);
        executorService.execute(() -> {
            try {
                cachedItemService.updateAllCachedItemData();
                Platform.runLater(() -> {
                    try {
                        DataUtils.transactionPageController.setPromptTextForRegulatory();
                        DataUtils.transactionPageController.setPromptTextForStaff();
                        DataUtils.appPage2Controller.setPromptTextForRegulatory();
                        DataUtils.appPage2Controller.setPromptTextForStaff();
                        calculatePageSize();
                        setTableContents();
                        generateItemList(0);
                        setTotalPrice();
                    } catch (UnsupportedPojoException e) {
                        throw new RuntimeException(e);
                    }
                    newPagination.setCurrentPageIndex(0);
                });
            } finally {
                refreshButton.setVisible(true);
                refreshSpinner.setVisible(false);
            }
        });
    }
}