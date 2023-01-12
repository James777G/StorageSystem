package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXPagination;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.maven.apache.MyLauncher;
import org.maven.apache.item.Item;
import org.maven.apache.service.item.ItemService;
import org.maven.apache.utils.DataUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
    private TextField itemAmountInDetails;

    @FXML
    private TextArea itemDescriptionInDetails;

    @FXML
    private MFXPagination pagination;

    @FXML
    private MFXGenericDialog descriptionDialog;

    @FXML
    private Label warnMessage;

    private int pageSize;

    private Item[] itemList = new Item[7];

    private final Label[] nameList = new Label[7];

    private final Label[] idList = new Label[7];

    private final Label[] amountList = new Label[7];

    private final JFXButton[] buttonList = new JFXButton[7];

    private Item selectedItem;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    @FXML
    private void onClickCheckOne() {
        if (itemList[0] != null) {
            setItemAttributes(itemList[0]);
        }
    }

    @FXML
    private void onClickCheckTwo() {
        if (itemList[1] != null) {
            setItemAttributes(itemList[1]);
        }
    }

    @FXML
    private void onClickCheckThree() {
        if (itemList[2] != null) {
            setItemAttributes(itemList[2]);
        }
    }

    @FXML
    private void onClickCheckFour() {
        if (itemList[3] != null) {
            setItemAttributes(itemList[3]);
        }
    }

    @FXML
    private void onClickCheckFive() {
        if (itemList[4] != null) {
            setItemAttributes(itemList[4]);
        }
    }

    @FXML
    private void onClickCheckSix() {
        if (itemList[5] != null) {
            setItemAttributes(itemList[5]);
        }
    }

    @FXML
    private void onClickCheckSeven() {
        if (itemList[6] != null) {
            setItemAttributes(itemList[6]);
        }
    }

    @FXML
    private void onCloseDescriptionDialog() {
        descriptionDialog.setVisible(false);
        warnMessage.setVisible(false);
    }

    /**
     * need improvements
     */
    @FXML
    private void onClickApply() {
        try{
            Item item = encapsulateItemData();
            itemService.update(item);
            generateCachedData();
            int currentPage = pagination.getCurrentPage() - 1;
            generateItemList(currentPage);
            setTableContents();
        }catch(Exception e){
            warnMessage.setVisible(true);
        }
    }

    private boolean isItemNameAlreadyExisted() {
        for (int i = 0; i < DataUtils.publicCachedWarehouseTableData.size(); i++) {
            for (int j = 0; j < DataUtils.publicCachedWarehouseTableData.get(i).size(); j++) {
                if (DataUtils.publicCachedWarehouseTableData.get(i).get(j).getItemName().equals(itemNameInDetails.getText())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Item encapsulateItemData() {
        Item item = new Item();
        item.setItemID(Integer.parseInt(itemIdInDetails.getText()));
        item.setItemName(itemNameInDetails.getText());
        item.setUnit(Integer.parseInt(itemAmountInDetails.getText()));
        item.setDescription(itemDescriptionInDetails.getText());
        return item;
    }

    private void initializeItemList() {
        List<Item> items = DataUtils.publicCachedWarehouseTableData.get(0);
        setItemList(items);
    }

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
