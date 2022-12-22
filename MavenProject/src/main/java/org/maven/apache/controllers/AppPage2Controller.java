package org.maven.apache.controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import org.maven.apache.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AppPage2Controller {
	
	@FXML
	private Button backButton;
	
	@FXML
	private ImageView backArrow;

	@FXML
	private JFXButton warehouseButton;

	@FXML
	private JFXButton messageButton;

	@FXML
	private JFXButton staffButton;

	@FXML
	private JFXButton transactionButton;
	
	@FXML
	private void onBackToLoginPage() throws IOException {
		Stage stage = (Stage) backButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/logInPage.fxml"));
		Scene scene = new Scene(loader.load());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void onEnterBackArrow() {
		backArrow.setFitWidth(16);
		backArrow.setFitHeight(16);
	}
	
	@FXML
	private void onExitBackArrow() {
		backArrow.setFitWidth(13);
		backArrow.setFitHeight(13);
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
