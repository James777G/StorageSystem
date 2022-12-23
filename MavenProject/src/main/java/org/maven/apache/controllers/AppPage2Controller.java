package org.maven.apache.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import org.maven.apache.App;
import org.maven.apache.MyLauncher;
import org.maven.apache.item.Item;
import org.maven.apache.service.item.ItemService;
import org.maven.apache.user.User;
import org.maven.apache.utils.DataUtils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
	private ImageView searchImage;

	@FXML
	private ImageView foldArrow;

	@FXML
	private MFXTableView<Item> itemTable;

	@FXML
	private MFXTableView everythingTable; //need data type??

	private final MFXTableColumn<Item> nameColumn = new MFXTableColumn<>("Product Name");
	private final MFXTableColumn<Item> idColumn = new MFXTableColumn<>("Product ID");
	private final MFXTableColumn<Item> amountColumn = new MFXTableColumn<>("Product Amount");
	private final MFXTableColumn<Item> descriptionColumn = new MFXTableColumn<>("Product Description");

	//pass the user from login page
	private final User user = DataUtils.currentUser;

	private final ObservableList<Item> dataList = FXCollections.observableArrayList();

	private final List<Item> items = MyLauncher.context.getBean("itemService", ItemService.class).selectAll();

	private double tableOpacity = 1;

	private int rotateAngle = 0;

	private boolean isPickOnBounds = true;

	private boolean isTableShown = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usernameLabel.setText(user.getName());
		warehouseButton.setOpacity(0);
		staffButton.setOpacity(0);
		transactionButton.setOpacity(0);
		messageButton.setOpacity(0);
		searchImage.setPickOnBounds(false);
		dataList.addAll(items);
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
		nameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(Item::getItemName));
		idColumn.setRowCellFactory(item -> new MFXTableRowCell<>(Item::getItemID));
		descriptionColumn.setRowCellFactory(item -> new MFXTableRowCell<>(Item::getDescription));
		amountColumn.setRowCellFactory(item -> new MFXTableRowCell<>(Item::getUnit));
		everythingTable.setPickOnBounds(false);
	}

	@FXML
	private void onBackToLoginPage() throws IOException {
		Stage stage = (Stage) backButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/logInPage.fxml"));
		Scene scene = new Scene(loader.load());
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * show the list of relevant cargos and transactions
	 */
	@FXML
	private void onSearch() {
		everythingTable.setOpacity(1);
		everythingTable.setPickOnBounds(true);
		isTableShown = true;
		foldArrow.setRotate(180);
		rotateAngle = 180;
	}

	@FXML
	private void onEnterSearch(){
		searchImage.setFitWidth(25);
		searchImage.setFitHeight(25);

	}

	@FXML
	private void onExitSearch(){
		searchImage.setFitWidth(20);
		searchImage.setFitHeight(20);
	}

	@FXML
	private void onEnterFoldArrow(){
		foldArrow.setFitWidth(25);
		foldArrow.setFitHeight(25);
	}

	@FXML
	private void onExitFoldArrow(){
		foldArrow.setFitWidth(20);
		foldArrow.setFitHeight(20);
	}

	/**
	 * fold or unfold the table that contains cargos and transactions
	 */
	@FXML
	private void onClickFoldArrow(){
		if (isTableShown){
			// table is shown, should close it
			tableOpacity = 0;
			isPickOnBounds = false;
			isTableShown = false;
			rotateAngle = rotateAngle - 180;
			// angle should be 180 (rotate from original image)
		}else {
			// table is closed, should expand it
			tableOpacity = 1;
			isPickOnBounds = true;
			isTableShown = true;
			rotateAngle = rotateAngle + 180;
			// angle should be 0 (image view unchanged)
		}
		//set the appearance of the table and fold arrow
		everythingTable.setOpacity(tableOpacity);
		everythingTable.setPickOnBounds(isPickOnBounds);
		foldArrow.setRotate(rotateAngle);
	}

	@FXML
	private void onReleaseFoldArrow(){
		foldArrow.setFitWidth(25);
		foldArrow.setFitHeight(25);
	}

	@FXML
	private void onPressFoldArrow(){
		foldArrow.setFitWidth(20);
		foldArrow.setFitHeight(20);
	}

	@FXML
	private void enterWarehouseButton(){
		warehouseButton.setOpacity(1);
	}

	@FXML
	private void exitWarehouseButton(){
		warehouseButton.setOpacity(0);
	}

	@FXML
	private void enterStaffButton(){
		staffButton.setOpacity(1);
	}

	@FXML
	private void exitStaffButton(){
		staffButton.setOpacity(0);
	}

	@FXML
	private void enterTransactionButton(){
		transactionButton.setOpacity(1);
	}

	@FXML
	private void exitTransactionButton(){
		transactionButton.setOpacity(0);
	}

	@FXML
	private void enterMessageButton(){
		messageButton.setOpacity(1);
	}

	@FXML
	private void exitMessageButton(){
		messageButton.setOpacity(0);
	}

}
