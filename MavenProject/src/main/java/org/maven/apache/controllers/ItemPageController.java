package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import org.maven.apache.item.Item;
import org.maven.apache.item.ItemFX;
import org.maven.apache.item.ItemFXBuilder;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mybatis.MyBatisConnector;
import org.maven.apache.mybatis.MyBatisItemConnector;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ItemPageController implements Initializable {

    @FXML
    private JFXTreeTableView<ItemFX> itemTable;



    @FXML
    private ImageView imageOnExit;

    private final JFXTreeTableColumn<ItemFX, String> idColumn = new JFXTreeTableColumn<>("ID");

    private final JFXTreeTableColumn<ItemFX, String> nameColumn = new JFXTreeTableColumn<>("Item Name");

    private final JFXTreeTableColumn<ItemFX, String> unitColumn = new JFXTreeTableColumn<>("Unit");

    private ObservableList<ItemFX> items = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize columns
        setWidthForColumns(225, 450, 225);
        setColumnFactories();

        // Add all ItemFX into the observable list which is for display.
        try {
            List<ItemFX> ItemFXList = getList();
            items.addAll(ItemFXList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // insert columns and values into the table
        setUpTable();
    }

    @FXML
    private void onExit() {
        MyBatisConnector.closeSession();
        Platform.exit();
        System.exit(0);
    }

    /**
     * This method sets up the display values for the itemTable and inserts all the columns into this
     * table
     */
    private void setUpTable() {
        final TreeItem<ItemFX> root = new RecursiveTreeItem<>(items, RecursiveTreeObject::getChildren);
        //noinspection unchecked
        itemTable.getColumns().setAll(idColumn, nameColumn, unitColumn);
        itemTable.setRoot(root);
        itemTable.setShowRoot(false);

    }

    /**
     * This method sets up all the preferred width for all columns in this scene
     *
     * @param widthForIDColumn   Width
     * @param widthForNameColumn Width
     * @param widthForUnitColumn Width
     */
    private void setWidthForColumns(int widthForIDColumn, int widthForNameColumn, int widthForUnitColumn) {
        idColumn.setPrefWidth(widthForIDColumn);
        nameColumn.setPrefWidth(widthForNameColumn);
        unitColumn.setPrefWidth(widthForUnitColumn);
    }

    /**
     * This Method Allows All The Columns Of The Scene To Be Initialised With
     * The Value They Receive.
     */
    private void setColumnFactories() {
        // Sets ID column to receive value from ItemFX.ID
        idColumn.setCellValueFactory(param -> param.getValue().getValue().getID());
        // Sets name column to receive value from ItemFX.Name
        nameColumn.setCellValueFactory(param -> param.getValue().getValue().getName());
        // Sets unit column to receive value from ItemFX.Unit
        unitColumn.setCellValueFactory(param -> param.getValue().getValue().getUnit());
    }

    /**
     * This method returns the latest list from database and convert this list of items
     * into a list of ItemFXs.
     *
     * @return a list of ItemFXs
     * @throws IOException when fail to connect to the database
     */
    private List<ItemFX> getList() throws IOException {
        ItemMapper itemMapper = MyBatisItemConnector.getItemMapper();
        List<Item> items = itemMapper.selectAll();
        return ItemFXBuilder.buildList(items);
    }

}
