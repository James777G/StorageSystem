package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.ScaleUtils;

public class EditCargoPageController {

    @FXML
    private ImageView crossImage;

    @FXML
    private AnchorPane onSelectRestockPane;

    @FXML
    private AnchorPane onSelectTakenPane;

    @FXML
    private JFXButton takenSelectButton;

    @FXML
    private JFXButton restockSelectButton;

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
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(crossImage, 500, 1.5);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitCross(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(crossImage, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onPressCross(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(crossImage, 500, 1.3);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onReleaseCross(){
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(crossImage, 500, 1.5);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onClickRestockSelectButton(){
        enableNodes(onSelectRestockPane);
        disableNodes(onSelectTakenPane);
    }

    @FXML
    private void onClickTakenSelectButton(){
        enableNodes(onSelectTakenPane);
        disableNodes(onSelectRestockPane);
    }

    private void disableNodes(@NotNull Node node){
        node.setOpacity(0);
        node.setVisible(false);
        node.setPickOnBounds(false);
    }

    private void enableNodes(@NotNull Node node){
        node.setOpacity(1);
        node.setVisible(true);
        node.setPickOnBounds(true);
    }

}
