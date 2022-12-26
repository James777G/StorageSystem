package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.maven.apache.App;
import org.maven.apache.MyLauncher;
import org.maven.apache.item.Item;
import org.maven.apache.search.FuzzySearch;
import org.maven.apache.service.item.ItemService;
import org.maven.apache.user.User;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.SearchUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class AppPage2Controller implements Initializable {

    @FXML
    private Button backButton;

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
    private ImageView foldArrow;

    @FXML
    private MFXTextField searchField;

    @FXML
    private MFXTableView<Item> itemTable;

    @FXML
    private MFXTableView<Item> searchTable; //need parameter data type??

    private final MFXTableColumn<Item> nameColumn = new MFXTableColumn<>("Product Name");
    private final MFXTableColumn<Item> idColumn = new MFXTableColumn<>("Product ID");
    private final MFXTableColumn<Item> amountColumn = new MFXTableColumn<>("Product Amount");
    private final MFXTableColumn<Item> descriptionColumn = new MFXTableColumn<>("Product Description");

    //pass the user from login page
    private final User user = DataUtils.currentUser;

    private final ObservableList<Item> dataList = FXCollections.observableArrayList();

    private final List<Item> items = MyLauncher.context.getBean("itemService", ItemService.class).selectAll();
    // get the item list from table stored in database

    private double tableOpacity = 1;

    private int rotateAngle = 0;

    private boolean isPickOnBounds = true;

    private boolean isTableShown = false;

    private final Timeline timeline = new Timeline();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText(user.getName());
        warehouseButton.setOpacity(0);
        staffButton.setOpacity(0);
        transactionButton.setOpacity(0);
        messageButton.setOpacity(0);
        dataList.addAll(items);
        // load columns in itemTable
        itemTable.autosize();
        itemTable.setItems(dataList);
        idColumn.setPrefWidth(100);
        nameColumn.setPrefWidth(350);
        amountColumn.setPrefWidth(200);
        descriptionColumn.setPrefWidth(575);
        itemTable.getTableColumns().add(idColumn);
        itemTable.getTableColumns().add(nameColumn);
        itemTable.getTableColumns().add(amountColumn);
        itemTable.getTableColumns().add(descriptionColumn);
        // load columns in searchTable
        searchTable.autosize();
        searchTable.getTableColumns().add(idColumn);
        searchTable.getTableColumns().add(nameColumn);
        searchTable.getTableColumns().add(amountColumn);
        searchTable.getTableColumns().add(descriptionColumn);
        nameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(Item::getItemName));
        idColumn.setRowCellFactory(item -> new MFXTableRowCell<>(Item::getItemID));
        descriptionColumn.setRowCellFactory(item -> new MFXTableRowCell<>(Item::getDescription));
        amountColumn.setRowCellFactory(item -> new MFXTableRowCell<>(Item::getUnit));
        searchTable.setPickOnBounds(false);
        // initialize search per sec
        timeline.setCycleCount(Timeline.INDEFINITE);
        searchField.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                searchOnBackgroundPerSec();
            }else{
                timeline.stop();
            }
        });
    }

    @FXML
    private void onBackToLoginPage() throws IOException {
        timeline.stop(); // stop searching per sec
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/logInPage.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * perform fuzzy search and show the list of relevant cargos in background per sec
     */
    private void searchOnBackgroundPerSec() {
        timeline.stop();
        KeyFrame keyFrame = SearchUtils.generateKeyFrame(searchTable, searchField);
        timeline.getKeyFrames().add(keyFrame);
        timeline.playFromStart();
    }

    @FXML
    private void onEnterFoldArrow() {
        foldArrow.setFitWidth(25);
        foldArrow.setFitHeight(25);
    }

    @FXML
    private void onExitFoldArrow() {
        foldArrow.setFitWidth(20);
        foldArrow.setFitHeight(20);
    }

    /**
     * fold or unfold the table that contains cargos and transactions
     */
    @FXML
    private void onClickFoldArrow() {
        if (isTableShown) {
            // table is shown, should close it
            tableOpacity = 0;
            isPickOnBounds = false;
            isTableShown = false;
            rotateAngle = rotateAngle - 180;
            // angle should be 180 (rotate from original image)
        } else {
            // table is closed, should expand it
            tableOpacity = 1;
            isPickOnBounds = true;
            isTableShown = true;
            rotateAngle = rotateAngle + 180;
            // angle should be 0 (image view unchanged)
        }
        //set the appearance of the table and fold arrow
        searchTable.setOpacity(tableOpacity);
        searchTable.setPickOnBounds(isPickOnBounds);
        foldArrow.setRotate(rotateAngle);
    }

    @FXML
    private void onReleaseFoldArrow() {
        foldArrow.setFitWidth(25);
        foldArrow.setFitHeight(25);
    }

    @FXML
    private void onPressFoldArrow() {
        foldArrow.setFitWidth(20);
        foldArrow.setFitHeight(20);
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

}
