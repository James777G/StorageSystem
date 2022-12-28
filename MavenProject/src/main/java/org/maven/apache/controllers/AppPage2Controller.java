package org.maven.apache.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.maven.apache.MyLauncher;
import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.item.Item;
import org.maven.apache.service.DateTransaction.DateTransactionService;
import org.maven.apache.service.item.ItemService;
import org.maven.apache.user.User;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.RotationUtils;
import org.maven.apache.utils.SearchUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.maven.apache.user.User;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.RotationUtils;
import org.maven.apache.utils.SearchUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AppPage2Controller implements Initializable {

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
    private ImageView refreshImage;

    @FXML
    private ImageView extendArrow;

    @FXML
    private MFXTextField searchField;

    @FXML
    private VBox searchTable;

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
    private JFXDrawer VBoxDrawer;

    @FXML
    private AnchorPane appPagePane;

    //pass the user from login page
    private final User user = DataUtils.currentUser;

    private final JFXButton[] buttonList = new JFXButton[5];



    private final Timeline timeline = new Timeline();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText(user.getName());
        warehouseButton.setOpacity(0);
        staffButton.setOpacity(0);
        transactionButton.setOpacity(0);
        messageButton.setOpacity(0);
        searchTable.setPickOnBounds(false);
        searchTable.setOpacity(0);
        setButtonList();
        // initialize search per sec when search field is chosen
        searchField.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                searchOnBackgroundPerSec();
                searchTable.setOpacity(1);
                searchTable.setPickOnBounds(true);
            }else{
                timeline.stop();
                searchTable.setOpacity(0);
                searchTable.setPickOnBounds(false);
            }
        });
        // load the menu VBox to drawer
        setDrawer();
    }

    @FXML
    private void onClickSearch(){
        searchOnBackgroundPerSec();
        searchTable.setOpacity(1);
        searchTable.setPickOnBounds(true);
    }

    @FXML
    private void onClickSearchBar(){
        searchOnBackgroundPerSec();
        searchTable.setOpacity(1);
        searchTable.setPickOnBounds(true);
    }

    private void setDrawer(){
        try {
            VBox vbox = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/menuPage.fxml")));
            vbox.setOnMouseExited(event -> {
                RotateTransition rotate = RotationUtils.getRotationTransitionFromTo(extendArrow,300,-90, 0);
                rotate.play();
                VBoxDrawer.close();
            });
            VBoxDrawer.setSidePane(vbox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setButtonList(){
        buttonOne.setAlignment(Pos.CENTER_LEFT);
        buttonTwo.setAlignment(Pos.CENTER_LEFT);
        buttonThree.setAlignment(Pos.CENTER_LEFT);
        buttonFour.setAlignment(Pos.CENTER_LEFT);
        buttonFive.setAlignment(Pos.CENTER_LEFT);
        buttonList[0] = buttonOne;
        buttonList[1] = buttonTwo;
        buttonList[2] = buttonThree;
        buttonList[3] = buttonFour;
        buttonList[4] = buttonFive;
    }

    @FXML
    private void onClickExtend(){
        RotateTransition rotate;
        if (VBoxDrawer.isOpened()){
            rotate = RotationUtils.getRotationTransitionFromTo(extendArrow,300,-90, 0);
        }else{
            rotate = RotationUtils.getRotationTransitionFromTo(extendArrow,300,0, -90);
        }
        rotate.play();
        if(VBoxDrawer.isOpened()){
            VBoxDrawer.close();
        }else{
            VBoxDrawer.open();
        }
    }

    @FXML
    private void onClickAppPagePane(){
        timeline.stop();
        searchTable.setOpacity(0);
        searchTable.setPickOnBounds(false);
    }

    @FXML
    private void onEnterExtend(){
        if(VBoxDrawer.isClosed()){
            VBoxDrawer.open();
            RotateTransition rotate = RotationUtils.getRotationTransitionFromTo(extendArrow,300,0, -90);
            rotate.play();
        }
    }

    /**
     * perform fuzzy search and show the list of relevant cargos in background per sec
     */
    private void searchOnBackgroundPerSec() {
        KeyFrame keyFrame = SearchUtils.generateKeyFrame(buttonList, searchField);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        timeline.playFromStart();
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

    @FXML
    private void refreshPage(){
        RotateTransition rotate = RotationUtils.getRotationTransitionFromBy(refreshImage,500,0, RotationUtils.Direction.COUNTERCLOCKWISE,360);
        rotate.play();
    }
    @FXML
    private void enterRefreshImage(){
        refreshImage.setScaleX(1.2);
        refreshImage.setScaleY(1.2);
    }
    @FXML
    private void exitRefreshImage(){
        refreshImage.setScaleX(1);
        refreshImage.setScaleY(1);
    }
}

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
	private Label staffNameLabel01;

	@FXML
	private Label staffNameLabel02;

	@FXML
	private Label staffNameLabel03;

	@FXML
	private Label staffNameLabel04;

	@FXML
	private ImageView foldArrow;

	@FXML
	private ImageView refreshImage;

	@FXML
	private ImageView extendArrow;

	@FXML
	private MFXTextField searchField;

	@FXML
	private MFXTableView<Item> itemTable;

	@FXML
	private MFXTableView<Item> searchTable; // need parameter data type??

	@FXML
	private JFXDrawer VBoxDrawer;

	private final MFXTableColumn<Item> nameColumn = new MFXTableColumn<>("Product Name");
	private final MFXTableColumn<Item> idColumn = new MFXTableColumn<>("Product ID");
	private final MFXTableColumn<Item> amountColumn = new MFXTableColumn<>("Product Amount");
	private final MFXTableColumn<Item> descriptionColumn = new MFXTableColumn<>("Product Description");

	// pass the user from login page
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
		// initialize search per sec when search field is chosen
		searchField.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				searchOnBackgroundPerSec();
			} else {
				timeline.stop();
			}
		});
		// load the menu VBox to drawer
		try {
			VBox vbox = FXMLLoader.load(getClass().getResource("/fxml/menuPage.fxml"));
			vbox.setOnMouseExited(event -> {
				RotateTransition rotate = RotationUtils.getRotationTransitionFromTo(extendArrow, 300, -90, 0);
				rotate.play();
				VBoxDrawer.close();
			});
			VBoxDrawer.setSidePane(vbox);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	private void onClickExtend() {
		RotateTransition rotate;
		if (VBoxDrawer.isOpened()) {
			rotate = RotationUtils.getRotationTransitionFromTo(extendArrow, 300, -90, 0);
		} else {
			rotate = RotationUtils.getRotationTransitionFromTo(extendArrow, 300, 0, -90);
		}
		rotate.play();
		if (VBoxDrawer.isOpened()) {
			VBoxDrawer.close();
		} else {
			VBoxDrawer.open();
		}
	}

	@FXML
	private void onEnterExtend() {
		if (VBoxDrawer.isClosed()) {
			VBoxDrawer.open();
			RotateTransition rotate = RotationUtils.getRotationTransitionFromTo(extendArrow, 300, 0, -90);
			rotate.play();
		}
	}

	/**
	 * perform fuzzy search and show the list of relevant cargos in background per
	 * sec
	 */
	private void searchOnBackgroundPerSec() {
		KeyFrame keyFrame = SearchUtils.generateKeyFrame(searchTable, searchField);
		timeline.setCycleCount(Timeline.INDEFINITE);
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
		// set the appearance of the table and fold arrow
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

	@FXML
	private void refreshPage() {
		RotateTransition rotate = RotationUtils.getRotationTransitionFromBy(refreshImage, 500, 0,
				RotationUtils.Direction.COUNTERCLOCKWISE, 360);
		rotate.play();
	}

	@FXML
	private void enterRefreshImage() {
		refreshImage.setScaleX(1.2);
		refreshImage.setScaleY(1.2);
	}

	@FXML
	private void exitRefreshImage() {
		refreshImage.setScaleX(1);
		refreshImage.setScaleY(1);
	}

	private void getDataTransitionData() {
		DateTransactionService dateTransactionService = MyLauncher.context.getBean("dateTransaction",
				DateTransactionService.class);
		List<DateTransaction> dateTransactionList = dateTransactionService.selectAll();
	}
}
