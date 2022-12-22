package org.maven.apache.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.maven.apache.App;
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
	private Label usernameLabel;
	
	//pass the user from login page
	private User user = DataUtils.currentUser;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usernameLabel.setText(user.getName());
	}
	
	@FXML
	private void onBackToLoginPage() throws IOException {
		Stage stage = (Stage) backButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/logInPage.fxml"));
		Scene scene = new Scene(loader.load());
		stage.setScene(scene);
		stage.show();
	}
	
	
}
