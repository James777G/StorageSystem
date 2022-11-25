package org.maven.apache.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.maven.apache.item.Item;
import org.maven.apache.item.ItemFX;
import org.maven.apache.item.ItemFXBuilder;
import org.maven.apache.mapper.ItemMapper;

import org.maven.apache.mybatis.MyBatisItemConnector;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class ItemPageController implements Initializable {

    @FXML
    private JFXTreeTableView<ItemFX> itemTable;

    @FXML
    private final JFXTreeTableColumn<ItemFX, String> idColumn = new JFXTreeTableColumn("ID");

    @FXML
    private final JFXTreeTableColumn<ItemFX, String> nameColumn = new JFXTreeTableColumn("Item Name");

    @FXML
    private final JFXTreeTableColumn<ItemFX, String> unitColumn = new JFXTreeTableColumn("Unit");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setPrefWidth(100);
        idColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ItemFX, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ItemFX, String> param) {
                return param.getValue().getValue().getID();
            }
        });

        nameColumn.setPrefWidth(300);
        nameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ItemFX, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ItemFX, String> param) {
                return param.getValue().getValue().getName();
            }
        });

        unitColumn.setPrefWidth(100);
        unitColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ItemFX, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ItemFX, String> param) {
                return param.getValue().getValue().getUnit();
            }
        });

        ObservableList<ItemFX> items = FXCollections.observableArrayList();

        try {
            List<ItemFX> ItemFXList = getList();
            for (ItemFX item : ItemFXList) {
                items.add(item);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final TreeItem<ItemFX> root = new RecursiveTreeItem<ItemFX>(items, RecursiveTreeObject::getChildren);
        itemTable.getColumns().setAll(idColumn, nameColumn, unitColumn);
        itemTable.setRoot(root);
        itemTable.setShowRoot(false);


    }

    private List<ItemFX> getList() throws IOException {
        ItemMapper itemMapper = MyBatisItemConnector.getItemMapper();
        List<Item> items = itemMapper.selectAll();
        return ItemFXBuilder.buildList(items);
    }

}
