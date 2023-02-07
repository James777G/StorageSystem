package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
import org.maven.apache.utils.CargoCachedUtils;
import org.maven.apache.utils.ScaleUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

@Slf4j
public class WarehouseController implements Initializable {

    private final SearchResultService<Item> searchResultService = MyLauncher.context.getBean("searchResultService", SearchResultService.class);

    private final CachedItemService cachedItemService = MyLauncher.context.getBean("cachedItemService", CachedItemService.class);

    private final ExecutorService executorService = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

    @FXML
    private Label itemNameOne, itemNameTwo, itemNameThree, itemNameFour, itemNameFive, itemNameSix, itemNameSeven;

    @FXML
    private Label itemIdOne, itemIdTwo, itemIdThree, itemIdFour, itemIdFive, itemIdSix, itemIdSeven;

    @FXML
    private Label itemAmountOne, itemAmountTwo, itemAmountThree, itemAmountFour, itemAmountFive, itemAmountSix, itemAmountSeven;

    @FXML
    private JFXButton checkOne, checkTwo, checkThree, checkFour, checkFive, checkSix, checkSeven;

    @FXML
    private ImageView deleteOne, deleteTwo, deleteThree, deleteFour, deleteFive, deleteSix, deleteSeven;

    @FXML
    private TextField itemNameInDetails;

    @FXML
    private Label itemIdInDetails;

    @FXML
    private Label itemAmountInDetails;

    @FXML
    private TextArea itemDescriptionInDetails;

    @FXML
    private MFXGenericDialog descriptionDialog;

    @FXML
    private Label warnMessage;

    @FXML
    private JFXButton applyButton;

    @FXML
    private MFXProgressSpinner loadSpinner;

    @FXML
    private AnchorPane addButton;

    @FXML
    private AnchorPane addItemPane;

    @FXML
    private AnchorPane blockPane;

    @FXML
    private MFXProgressSpinner loadSpinnerInAdd;

    @FXML
    private TextField itemAmountInAdd;

    @FXML
    private TextField itemNameInAdd;

    @FXML
    private TextArea itemDescriptionInAdd;

    @FXML
    private Label warnMessageInAdd;

    @FXML
    private JFXButton applyButtonInAdd;

    @FXML
    private AnchorPane deleteItemPane;

    @FXML
    private Pagination newPagination;

    @FXML
    private ImageView doContinueButton;

    @FXML
    private MFXProgressSpinner loadSpinnerOnDeletePane;

    @FXML
    private ImageView doNotContinueButton;

    @FXML
    private MFXTextField searchBar;

    private int pageSize;

    private List<Item> itemList;

    private final Label[] nameList = new Label[7];

    private final Label[] idList = new Label[7];

    private final Label[] amountList = new Label[7];

    private final ImageView[] deleteList = new ImageView[7];

    private final JFXButton[] buttonList = new JFXButton[7];

    private Item selectedItem;

    private Integer selectedItemID;

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
        descriptionDialog.setVisible(false);
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
        deleteItemPane.setVisible(true);
        blockPane.setVisible(true);
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckOne() {
        setItemAttributes(itemList.get(0));
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckTwo() {
        if (itemList.get(1) != null) {
            setItemAttributes(itemList.get(1));
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

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckThree() {
        if (itemList.get(2) != null) {
            setItemAttributes(itemList.get(2));
        }
    }

    @FXML
    private void doNotContinue() {
        deleteItemPane.setVisible(false);
        blockPane.setVisible(false);
    }

    @FXML
    private void doContinue() {
        loadSpinnerOnDeletePane.setVisible(true);
        doContinueButton.setVisible(false);
        executorService.execute(() -> {
            cachedItemService.deleteItemById(selectedItemID);
            Platform.runLater(() -> {
                try {
                    generateCachedData();
                } catch (UnsupportedPojoException e) {
                    throw new RuntimeException(e);
                }
            });
            generateItemList(newPagination.getCurrentPageIndex());
            Platform.runLater(this::setTableContents);
            Platform.runLater(() -> {
                loadSpinnerOnDeletePane.setVisible(false);
                doContinueButton.setVisible(true);
                deleteItemPane.setVisible(false);
                blockPane.setVisible(false);
            });
        });
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckFour() {
        if (itemList.get(3) != null) {
            setItemAttributes(itemList.get(3));
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckFive() {
        if (itemList.get(4) != null) {
            setItemAttributes(itemList.get(4));
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckSix() {
        if (itemList.get(5) != null) {
            setItemAttributes(itemList.get(5));
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckSeven() {
        if (itemList.get(6) != null) {
            setItemAttributes(itemList.get(6));
        }
    }

    /**
     * close description dialog event, and set the warning message visibility to false
     */
    @FXML
    private void onCloseDescriptionDialog() {
        descriptionDialog.setVisible(false);
        warnMessage.setVisible(false);
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
//                warnMessageInAdd.setVisible(true);
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
        addItemPane.setVisible(false);
        warnMessageInAdd.setVisible(false);
        loadSpinnerInAdd.setVisible(false);
        blockPane.setVisible(false);
    }

    /**
     * this method acts as an initializer of the item list and should only be used in
     * initialize method
     */
    @Deprecated
    @SuppressWarnings("all")
    private void initializeItemList() {
        itemList = CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL).get(0);
    }

    @FXML
    private void onClickAddButton() {
        addItemPane.setVisible(true);
        blockPane.setVisible(true);
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
        descriptionDialog.setVisible(true);
        itemNameInDetails.setText(item.getItemName());
        itemDescriptionInDetails.setText(item.getDescription());
        itemAmountInDetails.setText(item.getUnit().toString());
        itemIdInDetails.setText(item.getItemID().toString());
        selectedItem = item;
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
        buttonList[0] = checkOne;
        buttonList[1] = checkTwo;
        buttonList[2] = checkThree;
        buttonList[3] = checkFour;
        buttonList[4] = checkFive;
        buttonList[5] = checkSix;
        buttonList[6] = checkSeven;
    }

    /**
     * Return the maximum number of pages of the current data
     */
    private void calculatePageSize() throws UnsupportedPojoException {
        try{
            if(searchBar.getText().isBlank()){
                pageSize = CargoCachedUtils.getLists(CargoCachedUtils.listType.ALL).size();
            } else {
                pageSize = searchResultService.getPagedResultList(CargoCachedUtils
                                .getLists(CargoCachedUtils.listType.ALL),
                        searchBar.getText(),
                        SearchResultServiceHandler.ResultType.CARGO).size();
            }
        } catch(Exception e){
            pageSize = 0;
        }
        if(pageSize != 0){
            newPagination.setPageCount(pageSize);
        } else{
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
            if(!searchBar.getText().isBlank()){
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

    private void setDeleteContent() {
        for (int i = 0; i < itemList.size(); i++) {
            deleteList[i].setVisible(true);
        }
        for (int j = itemList.size(); j < deleteList.length; j++) {
            deleteList[j].setVisible(false);
        }
    }

    private void setButtonContent() {
        for (int j = 0; j < itemList.size(); j++) {
            buttonList[j].setDisable(false);
        }
        for (int i = itemList.size(); i < buttonList.length; i++) {
            buttonList[i].setDisable(true);
        }
    }

    /**
     * update name content in the table
     */
    private void setNameContent() {
        for (int i = 0; i < itemList.size(); i++) {
            nameList[i].setText(itemList.get(i).getItemName());
        }
        for (int j = itemList.size(); j < nameList.length; j++) {
            nameList[j].setText("N/A");
        }
    }

    /**
     * update id content in the table
     */
    private void setIdContent() {
        for (int i = 0; i < itemList.size(); i++) {
            idList[i].setText(itemList.get(i).getItemID().toString());
        }
        for (int j = itemList.size(); j < idList.length; j++) {
            idList[j].setText("N/A");
        }
    }

    /**
     * update amount content in the table
     */
    private void setAmountContent() {
        for (int i = 0; i < itemList.size(); i++) {
            amountList[i].setText(itemList.get(i).getUnit().toString());
        }
        for (int j = itemList.size(); j < amountList.length; j++) {
            amountList[j].setText("N/A");
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
}
