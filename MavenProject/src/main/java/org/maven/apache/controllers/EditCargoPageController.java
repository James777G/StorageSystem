package org.maven.apache.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import org.maven.apache.utils.DataUtils;

public class EditCargoPageController {

    @FXML
    private ImageView crossImage;

    /**
     * close the pane of adding new transaction
     */
    @FXML
    private void onClickCross(){
        DataUtils.editCargoPane.setVisible(false);
        DataUtils.publicTransactionBlockPane.setVisible(false);
    }

    @FXML
    private void onEnterCross(){
        crossImage.setScaleX(1.5);
        crossImage.setScaleY(1.5);
    }

    @FXML
    private void onExitCross(){
        crossImage.setScaleX(1);
        crossImage.setScaleY(1);
    }

    @FXML
    private void onPressCross(){
        crossImage.setScaleX(1.3);
        crossImage.setScaleY(1.3);
    }

    @FXML
    private void onReleaseCross(){
        crossImage.setScaleX(1.5);
        crossImage.setScaleY(1.5);
    }


}
