package org.maven.apache.controllers;

import io.github.palexdev.materialfx.controls.MFXPagination;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import org.aspectj.bridge.MessageUtil;
import org.maven.apache.MyLauncher;
import org.maven.apache.exception.NegativeDataException;
import org.maven.apache.exception.UnsupportedPojoException;
import org.maven.apache.message.Message;
import org.maven.apache.service.item.CachedItemService;
import org.maven.apache.service.message.CachedMessageService;
import org.maven.apache.service.message.CachedMessageServiceProvider;
import org.maven.apache.utils.*;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import javafx.scene.image.ImageView;
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
    private ImageView star1,star2,star3,star4,star5;

    @FXML
    private AnchorPane deleteOne,deleteTwo,deleteThree,deleteFour,deleteFive;

    @FXML
    private ImageView doContinueButton;

    @FXML
    private ImageView doNotContinueButton;

    @FXML
    private AnchorPane deleteMessagePane;

    @FXML
    private AnchorPane blockPane;

    @FXML
    private MFXProgressSpinner loadSpinnerOnDeletePane;


    @FXML
    private  final AnchorPane editMessagePane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/editMessagePage.fxml")));

    private final CachedMessageService cachedMessageService = MyLauncher.context.getBean("cachedMessageService", CachedMessageService.class);

    private final ExecutorService executorService = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

    private Image emptyStar = new Image(
         Objects.requireNonNull(getClass().getResourceAsStream("/image/aicons8-Message-emptyStar.png"))
    );

    private Image filledStar = new Image(
            Objects.requireNonNull(getClass().getResourceAsStream("/image/icons8-star-filled-96.png"))
    );

    private int MessageID;

    private Label[] staffNameArray= new Label[5];

    private Label[] categoryArray= new Label[5];

    private Label[] timeArray= new Label[5];

    private Label[] messageArray = new Label[5];

    private AnchorPane[] notePadPaneArray = new AnchorPane[5];

    private  ImageView[] starArray = new ImageView[5];

    private boolean clickStarredMessage;

    private List<List<Message>> messagePageList;

    private List<Message> currentPageList;
    private boolean isMovingLineOnMessage = true;

    private boolean isLineMoving = false;

    private Message message;

    public MessagePageController() throws IOException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cachedMessageService.updateAllCachedMessageData();
        clickStarredMessage=false;
        blockPane.setVisible(false);
        deleteMessagePane.setVisible(false);
        loadSpinnerOnDeletePane.setVisible(false);


        initializeLabels();
        executorService.execute(()->{
            setContent();
        });


        newPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            updatePagination(newValue);
        });

    }



    private void setContent(){
        setMessagePageList();
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
            if(!clickStarredMessage){
                if(currentPageList.get(i).getStar()==1){
                    starArray[i].setImage(filledStar);
                }else{
                    starArray[i].setImage(emptyStar);
                }
            }else{
                starArray[i].setImage(filledStar);
            }


           notePadPaneArray[i].setVisible(true);

       }
        // set empty labels
        if (currentPageList.size() != 5) {
            for (int j = 4; j >= currentPageList.size(); j--) {
                notePadPaneArray[j].setVisible(false);
            }
        }


    }

    @FXML
    private void OnStar1() {onClickStarMethodNumber(0);}

    @FXML
    private void OnStar2() {onClickStarMethodNumber(1);}

    @FXML
    private void OnStar3() {onClickStarMethodNumber(2);}

    @FXML
    private void OnStar4() {onClickStarMethodNumber(3);}

    @FXML
    private void OnStar5() {onClickStarMethodNumber(4);}



    @FXML
    private void onClickStarMethodNumber(int row){
        message = currentPageList.get(row);
        int paneID = row+1;
    boolean star = message.getStar()==1?true:false;
    //filled
    if(star){
        message.setStar(0);
        changeToEmptyStarImage(paneID);
        executorService.execute(() -> {
            cachedMessageService.updateMessage(message);

            Platform.runLater(() -> {

                setContent();
            });

        });
    }else{
        //empty
        message.setStar(1);
        changeToFilledStarImage(paneID);
    }
        executorService.execute(() -> {
            cachedMessageService.updateMessage(message);

            Platform.runLater(() -> {

                setContent();
            });

                });


    }
    @FXML
    private void onClickDeleteOne() {
        setDeletionPanes(0);
    }

    @FXML
    private void onClickDeleteTwo() {
        setDeletionPanes(1);
    }

    @FXML
    private void onClickDeleteThree() {
        setDeletionPanes(2);
    }

    @FXML
    private void onClickDeleteFour() {
        setDeletionPanes(3);
    }

    @FXML
    private void onClickDeleteFive() {
        setDeletionPanes(4);
    }


    private void setDeletionPanes(int row) {

        MessageID = currentPageList.get(row).getMessageID(); // 为了使用deletebyID
        int paneID = row+1;
        deleteMessagePane.setVisible(true);
        blockPane.setVisible(true);

        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteMessagePane,300,0,1);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteMessagePane,300,-45,0);
        translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
        fadeTransition.play();
        translateTransition.play();
    }

    @FXML
    private void doNotContinue() {
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteMessagePane,300,1,0);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteMessagePane,300,0,-45);
        translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
        translateTransition.setOnFinished(event -> {
            deleteMessagePane.setVisible(false);
            blockPane.setVisible(false);
        });
        fadeTransition.play();
        translateTransition.play();
    }

    @FXML
    private void doContinue() {
        loadSpinnerOnDeletePane.setVisible(true);
        doContinueButton.setVisible(false);
        executorService.execute(() -> {
            cachedMessageService.deleteMessageById(MessageID);
        //    cachedMessageService.updateAllCachedMessageData();


        //    generateItemList(newPagination.getCurrentPageIndex());
            Platform.runLater(this::setContent);
            Platform.runLater(() -> {
                FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteMessagePane,300,1,0);
                TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteMessagePane,300,-0,-45);
                translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
                translateTransition.setOnFinished(event -> {
                    loadSpinnerOnDeletePane.setVisible(false);
                    doContinueButton.setVisible(true);
                    deleteMessagePane.setVisible(false);
                    blockPane.setVisible(false);
                });
                fadeTransition.play();
                translateTransition.play();
            });
        });
    }

    private void changeToFilledStarImage(int paneID) {
        switch (paneID) {
            case 1 -> star1.setImage(filledStar);
            case 2 -> star2.setImage(filledStar);
            case 3 -> star3.setImage(filledStar);
            case 4 -> star4.setImage(filledStar);
            case 5 -> star5.setImage(filledStar);
        }
    }

    private void changeToEmptyStarImage(int paneID) {
        switch (paneID){
            case 1:
                star1.setImage(emptyStar);
                break;
            case 2:
                star2.setImage(emptyStar);
                break;
            case 3:
                star3.setImage(emptyStar);
                break;
            case 4:
                star4.setImage(emptyStar);
                break;
            case 5:
                star5.setImage(emptyStar);
                break;
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
        //initialize star imageview
        starArray[0] = star1;
        starArray[1] = star2;
        starArray[2] = star3;
        starArray[3] = star4;
        starArray[4] = star5;

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
        clickStarredMessage = false;
        executorService.execute(()->{

            Platform.runLater(()->{
                        setContent();
                    }

            );
        });
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

    private void setMessagePageList(){
        if(clickStarredMessage){
            messagePageList = MessageCachedUtils.getLists(MessageCachedUtils.listType.STAR_MESSAGE);
        }else{
            messagePageList= MessageCachedUtils.getLists(MessageCachedUtils.listType.All_MESSAGE);
        }

    }

    @FXML
    private void onClickStarred(){
        clickStarredMessage=true;
        executorService.execute(()->{

            Platform.runLater(()->{
                        setContent();
                    }

            );
        });
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

    @FXML
    private void onEnterTick() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(doContinueButton, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitTick() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(doContinueButton, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterCross() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(doNotContinueButton, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitCross() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(doNotContinueButton, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }


    private void onClickNotePads(){

    }
}
