package org.maven.apache.controllers;



import java.io.IOException;

import org.maven.apache.mybatis.MyBatisTester;
import org.maven.apache.user.User;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Label;

public class initialController {
	
	@FXML
	private Label nameOne;
	
	@FXML
	private Label nameTwo;
	
	@FXML
	private Label nameThree;
	
	@FXML
	private Label nameFour;
	
	@FXML
	private Label nameFive;
	
	@FXML
	private Label priceOne;
	
	@FXML
	private Label priceTwo;
	
	@FXML
	private Label priceThree;
	
	@FXML
	private Label priceFour;
	
	@FXML
	private Label priceFive;
	
	@FXML
	private JFXButton buttonOnRefresh;
	
	@FXML
	private JFXButton buttonOnExit;

	public void initialize() {
		MyBatisTester.refreshUsersList();
		// TODO Auto-generated method stub
		String textOne;
		User userOne = (User) MyBatisTester.users.get(0);
		textOne = userOne.getName();
		nameOne.setText(textOne);
		
		String textTwo;
		User userTwo = (User) MyBatisTester.users.get(1);
		textTwo = userTwo.getName();
		nameTwo.setText(textTwo);
		
		String textThree;
		User userThree = (User) MyBatisTester.users.get(2);
		textThree = userThree.getName();
		nameThree.setText(textThree);
		
		String textFour;
		User userFour = (User) MyBatisTester.users.get(3);
		textFour = userFour.getName();
		nameFour.setText(textFour);
		
		String textFive;
		User userFive = (User) MyBatisTester.users.get(4);
		textFive = userFive.getName();
		nameFive.setText(textFive);
		
		String priceOne;
		priceOne = userOne.getPrice();
		this.priceOne.setText(priceOne);
		
		String priceTwo;
		priceTwo = userTwo.getPrice();
		this.priceTwo.setText(priceTwo);
		
		String priceThree;
		priceThree = userThree.getPrice();
		this.priceThree.setText(priceThree);
		
		String priceFour;
		priceFour = userFour.getPrice();
		this.priceFour.setText(priceFour);
		
		String priceFive;
		priceFive = userFive.getPrice();
		this.priceFive.setText(priceFive);
	}
	
	@FXML
	private void onRefresh() throws IOException {
		MyBatisTester.disconnect();
		MyBatisTester.initialize();
		MyBatisTester.refreshUsersList();
		initialize();
	}
	
	@FXML
	private void onExit() {
		MyBatisTester.disconnect();
		Platform.exit();
		System.exit(0);
	}
	
	
	
	


}
