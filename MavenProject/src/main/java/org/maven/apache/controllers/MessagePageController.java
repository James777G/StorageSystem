package org.maven.apache.controllers;

import io.github.palexdev.materialfx.controls.MFXPagination;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import org.aspectj.bridge.MessageUtil;
import org.maven.apache.MyLauncher;
import org.maven.apache.message.Message;
import org.maven.apache.service.item.CachedItemService;
import org.maven.apache.service.message.CachedMessageService;
import org.maven.apache.service.message.CachedMessageServiceProvider;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.MessageCachedUtils;
import org.maven.apache.utils.TranslateUtils;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    private Label staffName1,staffName2,staffName3,staffName4,staffName5;

    @FXML
    private Label category1,category2,category3,category4,category5;

    @FXML
    private Label time1,time2,time3,time4,time5;

    @FXML
    private Label message1,message2,message3,message4,message5;

    @FXML
    private  final AnchorPane editMessagePane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/editMessagePage.fxml")));

    private final CachedMessageService cachedMessageService = MyLauncher.context.getBean("cachedMessageService", CachedMessageService.class);

    private final ExecutorService executorService = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);



    private Label[] staffNameArray= new Label[5];

    private Label[] categoryArray= new Label[5];

    private Label[] timeArray= new Label[5];

    private Label[] messageArray = new Label[5];

    private AnchorPane[] notePadPaneArray = new AnchorPane[5];

    private List<List<Message>> messagePageList;

    private List<Message> currentPageList;
    private boolean isMovingLineOnMessage = true;

    private boolean isLineMoving = false;

    public MessagePageController() throws IOException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cachedMessageService.updateAllCachedMessageData();

        initializeLabels();

        setAllMessageContent();

        newPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            updatePagination(newValue);
        });

    }



    private void setAllMessageContent() {
       messagePageList = MessageCachedUtils.getLists(MessageCachedUtils.listType.All_MESSAGE);
       setContent();

    }

    private void setStarMessageContent(){
        messagePageList = MessageCachedUtils.getLists(MessageCachedUtils.listType.STAR_MESSAGE);
        setContent();
    }

    private void setContent(){
        updatePagination(0);
        newPagination.setCurrentPageIndex(0);
        setPaginationPages(messagePageList);
    }


    private void setPaginationPages(List<List<Message>> messagePageList) {
        newPagination.setPageCount(messagePageList.size());
    }


    private void updatePagination(Number currentPage) {
       currentPageList = messagePageList.get(currentPage.intValue());

        // set non-empty labels
        for (int i = 0; i < currentPageList.size(); i++) {
            staffNameArray[i].setText(currentPageList.get(i).getStaffName());
            categoryArray[i].setText(currentPageList.get(i).getCategory());
            timeArray[i].setText(dateText(currentPageList.get(i).getMessageTime()));
            messageArray[i].setText(currentPageList.get(i).getInformation());
           notePadPaneArray[i].setVisible(true);

       }
        // set empty labels
        if (currentPageList.size() != 5) {
            for (int j = 4; j >= currentPageList.size(); j--) {
                notePadPaneArray[j].setVisible(false);
            }
        }


    }

    private String dateText(Date messageTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(messageTime);
        return dateString;
    }


    private void initializeLabels() {
        //initialize staffName Label
        staffNameArray[0] =staffName1;
        staffNameArray[1] =staffName2;
        staffNameArray[2] =staffName3;
        staffNameArray[3] =staffName4;
        staffNameArray[4] =staffName5;
        //initialize Category Label
        categoryArray[0] = category1;
        categoryArray[1] = category2;
        categoryArray[2] = category3;
        categoryArray[3] = category4;
        categoryArray[4] = category5;
        //initialize  Time Label
        timeArray[0] = time1;
        timeArray[1] = time2;
        timeArray[2] = time3;
        timeArray[3] = time4;
        timeArray[4] = time5;
        //initialize message label
        messageArray[0] =message1;
        messageArray[1] =message2;
        messageArray[2] =message3;
        messageArray[3] =message4;
        messageArray[4] =message5;
        //initialize anchorpane
        notePadPaneArray[0] = notePadPane1;
        notePadPaneArray[1] = notePadPane2;
        notePadPaneArray[2] = notePadPane3;
        notePadPaneArray[3] = notePadPane4;
        notePadPaneArray[4] = notePadPane5;
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

    @FXML
    private void onScrolled(ScrollEvent event){
        if(event.getDeltaY() > 0){
            newPagination.setCurrentPageIndex(newPagination.getCurrentPageIndex() + 1);
        }
        if(event.getDeltaY() < 0){
            newPagination.setCurrentPageIndex(newPagination.getCurrentPageIndex() - 1);
        }
    }

    private void onClickNotePads(){

    }
}
