package org.maven.apache.controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.MyLauncher;
import org.maven.apache.service.item.CachedItemService;
import org.maven.apache.service.message.CachedMessageService;
import org.maven.apache.service.message.CachedMessageServiceProvider;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.TranslateUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class MessagePageController implements Initializable {

    @FXML
    private AnchorPane notePadPane1;

    @FXML
    private AnchorPane notePadPane2;

    @FXML
    private AnchorPane notePadPane3;

    @FXML
    private AnchorPane notePadPane4;

    @FXML
    private AnchorPane notePadPane5;

    @FXML
    private AnchorPane onMoveToMessagePane;

    @FXML
    private AnchorPane onMoveToStarredPane;

    @FXML
    private AnchorPane movingLinePane;
    @FXML
    private Pagination newPagination;

    @FXML
    private  final AnchorPane editMessagePane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/editMessagePage.fxml")));

    private final CachedMessageService cachedMessageService = MyLauncher.context.getBean("cachedMessageService", CachedMessageService.class);

    private final ExecutorService executorService = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

    private Label[] staffNameArray= new Label[5];

    private boolean isMovingLineOnMessage = true;

    private boolean isLineMoving = false;

    public MessagePageController() throws IOException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cachedMessageService.updateAllCachedMessageData();

//        DataUtils.pagination=newPagination;
//        newPagination.setMaxPageIndicatorCount(8);

    }

    @FXML
    private void enableNode(Node node){
        node.setOpacity(1);
        node.setVisible(true);
        node.setPickOnBounds(true);
    }

    @FXML
    private void disable(Node node){
        node.setOpacity(0);
        node.setVisible(false);
        node.setPickOnBounds(false);
    }
    @FXML
    private void onClickMessage(){
        if((!isMovingLineOnMessage) && (!isLineMoving)){
            isLineMoving = true;
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionByX(movingLinePane, 500, -120);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.setOnFinished(event -> {
                isLineMoving = false;
                isMovingLineOnMessage = true;
            });
            translateTransition.play();
        }
    }

    @FXML
    private void onClickStarred(){
        if((isMovingLineOnMessage) && (!isLineMoving)){
            isLineMoving = true;
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionByX(movingLinePane, 500, 120);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.setOnFinished(event -> {
                isLineMoving = false;
                isMovingLineOnMessage = false;
            });
            translateTransition.play();
        }
    }

    private void onClickNotePads(){

    }
}
