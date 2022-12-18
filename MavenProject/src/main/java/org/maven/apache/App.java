package org.maven.apache;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

	public static void main(final String[] args) {
		// TODO Auto-generated method stub
		launch();

	}

	/**
	 * Returns the node associated to the input file. The method expects that the
	 * file is located in "src/main/resources/fxml".
	 *
	 * @param fxml The name of the FXML file (without extension).
	 * @return The node of the input file.
	 * @throws IOException If the file is not found.
	 */
	protected static Parent loadFxml(final String fxml) throws IOException {
		return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
	}

	  /**
	   * This method is invoked when the application starts. It loads and shows the scene.
	   *
	   * @param stage The primary stage of the application.
	   * @throws IOException If "src/main/resources/fxml/canvas.fxml" is not found.
	   */
	  @Override
	  public void start(final Stage stage) throws IOException {
		  
	    final Scene scene = new Scene(loadFxml("logInPage"));
		scene.setFill(Color.TRANSPARENT);
	    stage.setScene(scene);
	    stage.initStyle(StageStyle.TRANSPARENT);
	    stage.show();
	  }

}
