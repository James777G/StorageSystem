package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXPagination;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.maven.apache.MyLauncher;
import org.maven.apache.item.Item;
import org.maven.apache.service.item.ItemService;
import org.maven.apache.utils.DataUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

/**
 * Controller of the warehouse page
 *
 * <p>
 *     Author: James Gong
 *     Date: 1/13/2023
 * </p>
 */
public class WarehouseController implements Initializable {

    private final ItemService itemService = MyLauncher.context.getBean("itemService", ItemService.class);

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
    private TextField itemNameInDetails;

    @FXML
    private Label itemIdInDetails;

    @FXML
    private Label itemAmountInDetails;

    @FXML
    private TextArea itemDescriptionInDetails;

    @FXML
    private MFXPagination pagination;

    @FXML
    private MFXGenericDialog descriptionDialog;

    @FXML
    private Label warnMessage;

    @FXML
    private JFXButton applyButton;

    @FXML
    private MFXProgressSpinner loadSpinner;

    private int pageSize;

    private Item[] itemList = new Item[7];

    private final Label[] nameList = new Label[7];

    private final Label[] idList = new Label[7];

    private final Label[] amountList = new Label[7];

    @SuppressWarnings("all")
    private final JFXButton[] buttonList = new JFXButton[7];

    private Item selectedItem;

    /**
     * 1. sets up the word limit for description input field inside the description dialog
     * 2. Initialize the attributes of the data
     * 3. bind listener of the pagination to retrieve current page property dynamically.
     *
     * <p>
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
        itemDescriptionInDetails.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 100 ? change : null));
        loadSpinner.setVisible(false);
        descriptionDialog.setVisible(false);
        warnMessage.setVisible(false);
        calculatePageSize();
        initializeNameList();
        initializeIdList();
        initializeAmountList();
        initializeButtonList();
        generateCachedData();
        pagination.setMaxPage(pageSize);
        initializeItemList();
        setTableContents();
        pagination.currentPageProperty().addListener((observable, oldValue, newValue) -> executorService.execute(() -> {
            generateItemList(newValue.intValue() - 1);
            Platform.runLater(WarehouseController.this::setTableContents);
        }));
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckOne() {
        if (itemList[0] != null) {
            setItemAttributes(itemList[0]);
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckTwo() {
        if (itemList[1] != null) {
            setItemAttributes(itemList[1]);
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckThree() {
        if (itemList[2] != null) {
            setItemAttributes(itemList[2]);
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckFour() {
        if (itemList[3] != null) {
            setItemAttributes(itemList[3]);
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckFive() {
        if (itemList[4] != null) {
            setItemAttributes(itemList[4]);
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckSix() {
        if (itemList[5] != null) {
            setItemAttributes(itemList[5]);
        }
    }

    /**
     * check button event in the warehouse item table
     */
    @FXML
    private void onClickCheckSeven() {
        if (itemList[6] != null) {
            setItemAttributes(itemList[6]);
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
        Item item = encapsulateItemData();
        if (item.equals(selectedItem)) {
            return;
        }
        if (itemNameInDetails.getText().isEmpty()) {
            warnMessage.setVisible(true);
            return;
        }
        executorService.execute(() -> {
            Platform.runLater(() -> {
                applyButton.setVisible(false);
                loadSpinner.setVisible(true);
            });
            try {
                itemService.update(item);
                generateCachedData();
                int currentPage = pagination.getCurrentPage() - 1;
                generateItemList(currentPage);
                Platform.runLater(this::setTableContents);
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
    private Item encapsulateItemData() {
        Item item = new Item();
        item.setItemID(Integer.parseInt(itemIdInDetails.getText()));
        item.setItemName(itemNameInDetails.getText());
        item.setUnit(Integer.parseInt(itemAmountInDetails.getText()));
        item.setDescription(itemDescriptionInDetails.getText());
        return item;
    }

    /**
     * this method acts as an initializer of the item list and should only be used in
     * initialize method
     */
    @Deprecated
    @SuppressWarnings("all")
    private void initializeItemList() {
        List<Item> items = DataUtils.publicCachedWarehouseTableData.get(0);
        setItemList(items);
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
    private void calculatePageSize() {
        List<Item> items = itemService.selectAll();
        pageSize = (items.size() / 7) + 1;
    }

    /**
     * format conversion
     * deprecated and should not be used
     *
     * @param items calculated items (from cached data)
     */
    @Deprecated
    @SuppressWarnings("all")
    private void setItemList(List<Item> items) {
        itemList = new Item[7];
        for (int i = 0; i < items.size(); i++) {
            itemList[i] = items.get(i);
        }
    }

    /**
     * This method sets the latest item list based on the latest cached data
     *
     * @param index page number to be displayed
     */
    private void generateItemList(int index) {
        List<Item> items = DataUtils.publicCachedWarehouseTableData.get(index);
        setItemList(items);
    }

    /**
     * update the data onto the table
     */
    private void setTableContents() {
        setNameContent();
        setIdContent();
        setAmountContent();
    }

    /**
     * update name content in the table
     */
    private void setNameContent() {
        for (int i = 0; i < nameList.length; i++) {
            if (itemList[i] != null) {
                nameList[i].setText(itemList[i].getItemName());
            }
        }
    }

    /**
     * update id content in the table
     */
    private void setIdContent() {
        for (int i = 0; i < idList.length; i++) {
            if (itemList[i] != null) {
                idList[i].setText(itemList[i].getItemID().toString());
            }
        }
    }

    /**
     * update amount content in the table
     */
    private void setAmountContent() {
        for (int i = 0; i < amountList.length; i++) {
            if (itemList[i] != null) {
                amountList[i].setText(itemList[i].getUnit().toString());
            }
        }
    }

    /**
     * 1. generate the cached data and store it as List<List<Item>>
     * 2. The loading of this cached data is slow, thus this process must be delegated to loading animation
     * 3. The cached data generated must be cleared and regenerated everytime when there is a change to the data
     * 4. The cached data is stored as a global variable in DataUtils class.
     */
    public void generateCachedData() {
        calculatePageSize();
        DataUtils.publicCachedWarehouseTableData.clear();
        for (int i = 1; i <= pageSize; i++) {
            List<Item> items = itemService.pageAskedNOOrder(i, 7);
            DataUtils.publicCachedWarehouseTableData.add(items);
        }
    }
}
