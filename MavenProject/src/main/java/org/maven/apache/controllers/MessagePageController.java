package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.MyLauncher;
import org.maven.apache.exception.UnsupportedPojoException;
import org.maven.apache.message.Message;
import org.maven.apache.service.message.CachedMessageService;
import org.maven.apache.service.text.AbstractMessageTextService;
import org.maven.apache.user.User;
import org.maven.apache.utils.*;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private AnchorPane deleteMessagePane;

    @FXML
    private AnchorPane blockPane;

    @FXML
    private AnchorPane movingLinePane;

    @FXML
    private AnchorPane addButton;

    @FXML
    private AnchorPane deleteOne, deleteTwo, deleteThree, deleteFour, deleteFive;

    @FXML
    private AnchorPane addTransactionPane;

    @FXML
    private AnchorPane onMoveToStarredBlockPane;

    @FXML
    private AnchorPane onMoveToMessageBlockPane;

    @FXML
    private Pagination newPagination;

    @FXML
    private MFXGenericDialog descriptionDialog;

    @FXML
    private Label staffName1, staffName2, staffName3, staffName4, staffName5;

    @FXML
    private Label category1, category2, category3, category4, category5;

    @FXML
    private Label time1, time2, time3, time4, time5;

    @FXML
    private Label message1, message2, message3, message4, message5;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label messageDateLabel;

    @FXML
    private Label warnMessageInAdd;

    @FXML
    private Label staffNameInDetail;

    @FXML
    private Label categoryDetail;

    @FXML
    private Label dateDetail;

    @FXML
    private ImageView star1, star2, star3, star4, star5;

    @FXML
    private ImageView doContinueButton;

    @FXML
    private ImageView doNotContinueButton;

    @FXML
    private MFXProgressSpinner loadSpinnerOnDeletePane;

    @FXML
    private MFXProgressSpinner loadSpinnerInAdd;

    @FXML
    private MFXProgressSpinner refreshSpinner;

    @FXML
    private JFXButton clearButton;

    @FXML
    private JFXButton okayButton;

    @FXML
    private JFXButton applyButtonInAdd;

    @FXML
    private JFXButton refreshButton;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField newUnitTextField;

    @FXML
    private TextArea itemDescriptionInDetails;

    private final User user = DataUtils.currentUser;

    private final AbstractMessageTextService staffMessage = MyLauncher.context.getBean("staffMessage", AbstractMessageTextService.class);

    private final AbstractMessageTextService transactionMessage = MyLauncher.context.getBean("transactionMessage", AbstractMessageTextService.class);

    private final AbstractMessageTextService cargoMessage = MyLauncher.context.getBean("cargoMessage", AbstractMessageTextService.class);

    private final CachedMessageService cachedMessageService = MyLauncher.context.getBean("cachedMessageService", CachedMessageService.class);

    private final ExecutorService executorService = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

    private Image emptyStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/icons8-star-empty-48.png")));

    private Image filledStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/icons8-star-filled-96.png")));

    private int MessageID;

    private final Label[] staffNameArray = new Label[5];

    private final Label[] categoryArray = new Label[5];

    private final Label[] timeArray = new Label[5];

    private final Label[] messageArray = new Label[5];

    private final AnchorPane[] notePadPaneArray = new AnchorPane[5];

    private final ImageView[] starArray = new ImageView[5];

    private final AnchorPane[] deleteArray = new AnchorPane[5];

    private boolean clickStarredMessage;

    private boolean isMovingLineOnMessage = true;

    private boolean isLineMoving = false;

    private boolean isAdditionSucceed;

    private List<List<Message>> messagePageList;

    private List<Message> currentPageList;

    private Message message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cachedMessageService.updateAllCachedMessageData();
        itemDescriptionInDetails.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 250 ? change : null));
        clickStarredMessage = false;
        descriptionDialog.setVisible(false);
        blockPane.setVisible(false);
        deleteMessagePane.setVisible(false);
        addTransactionPane.setVisible(false);
        loadSpinnerOnDeletePane.setVisible(false);
        loadSpinnerInAdd.setVisible(false);
        refreshSpinner.setVisible(false);
        warnMessageInAdd.setVisible(false);
        isAdditionSucceed = false;
        DataUtils.messageController = this;
        initializeLabels();
        executorService.execute(this::setContent);
        newPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            updatePagination(newValue);
        });
    }

    @FXML
    private void onCloseDescriptionDialog() {
        descriptionDialog.setVisible(false);
        blockPane.setVisible(false);
    }

    @FXML
    private void onEnterAddButton() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(addButton, 500, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onClickOkay() {
        onCloseDescriptionDialog();
    }

    @FXML
    private void onExitAddButton() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(addButton, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onClickAddButton() {
        addTransactionPane.setVisible(true);
        blockPane.setVisible(true);
        userNameLabel.setText(user.getName());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date);
        messageDateLabel.setText(today);
    }

    private void onClickAddButtonForStaff(String id, String name) {
        addTransactionPane.setVisible(true);
        blockPane.setVisible(true);
        userNameLabel.setText(user.getName());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date);
        descriptionTextArea.setText(staffMessage.getHeaderText(id, name));
        newUnitTextField.setText("Staff");
        messageDateLabel.setText(today);
    }

    private void onClickAddButtonForCargo(String id, String name) {
        addTransactionPane.setVisible(true);
        blockPane.setVisible(true);
        userNameLabel.setText(user.getName());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date);
        descriptionTextArea.setText(cargoMessage.getHeaderText(id, name));
        newUnitTextField.setText("Item");
        messageDateLabel.setText(today);
    }

    private void onClickAddButtonForTransaction(String id, String name) {
        addTransactionPane.setVisible(true);
        blockPane.setVisible(true);
        userNameLabel.setText(user.getName());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date);
        descriptionTextArea.setText(transactionMessage.getHeaderText(id, name));
        newUnitTextField.setText("Transaction");
        messageDateLabel.setText(today);
    }

    @FXML
    private void onClickApplyInAdd() {
        if (!isValidated()) {
            warnMessageInAdd.setVisible(true);
        } else {
//            Date date = new Date();
//            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//
//            java.sql.Date date1 = new java.sql.Date(date.getTime());
            isAdditionSucceed = true;
            applyButtonInAdd.setVisible(false);
            loadSpinnerInAdd.setVisible(true);
            okayButton.setDisable(true);
            clearButton.setDisable(true);
            //   int newMessageID = getNumOfTransaction() + 1;
            //   message.setMessageID(newMessageID);
            Message insertMessage = new Message();
            insertMessage.setStaffName(user.getName());
            insertMessage.setCategory(newUnitTextField.getText());
            insertMessage.setInformation(descriptionTextArea.getText());
            executorService.execute(() -> {
                try {
                    cachedMessageService.addNewMessage(insertMessage);
                    Platform.runLater(() -> {
                        setContent();
                    });
                } catch (Exception e) {
                    warnMessageInAdd.setVisible(true);
                    isAdditionSucceed = false;
                } finally {
                    // restore nodes after a succeesful addition
                    applyButtonInAdd.setVisible(true);
                    loadSpinnerInAdd.setVisible(false);
                    if (isAdditionSucceed) {
                        warnMessageInAdd.setVisible(false);
                        isAdditionSucceed = false;
                    }
                    okayButton.setDisable(false);
                    clearButton.setDisable(false);
                }
            });
        }
    }

    private int getNumOfTransaction() {
        int count = 0;
        for (int i = 0; i < MessageCachedUtils.getLists(MessageCachedUtils.listType.All_MESSAGE).size(); i++) {
            for (int j = 0; j < MessageCachedUtils.getLists(MessageCachedUtils.listType.All_MESSAGE).get(i).size(); j++) {
                count++;
            }
        }
        return count;
    }

    @FXML
    private void onClickOkayInAdd() {
        addTransactionPane.setVisible(false);
        blockPane.setVisible(false);
    }

    @FXML
    private void onClickClearInAdd() {

        newUnitTextField.clear();


        descriptionTextArea.clear();
        warnMessageInAdd.setVisible(false);
    }

    private boolean isValidated() {
        if (!descriptionTextArea.getText().equals("") && !newUnitTextField.getText().equals("")) {
            return true;
        }
        return false;
    }

    private void setContent() {
        setMessagePageList();
        updatePagination(0);
        newPagination.setCurrentPageIndex(0);
        setPaginationPages(messagePageList);
    }

    private void setPaginationPages(List<List<Message>> messagePageList) {
        if(messagePageList.size() > 0){
            newPagination.setPageCount(messagePageList.size());
        } else{
            newPagination.setPageCount(1);
        }

    }

    private void updatePagination(Number currentPage) {
        if(!messagePageList.isEmpty() && messagePageList != null){
            currentPageList = messagePageList.get(currentPage.intValue());
        }else{
            currentPageList = new ArrayList<>();
        }
        // set non-empty labels
        for (int i = 0; i < currentPageList.size(); i++) {
            staffNameArray[i].setText(currentPageList.get(i).getStaffName());
            categoryArray[i].setText(currentPageList.get(i).getCategory());
            timeArray[i].setText(dateText(currentPageList.get(i).getMessageTime()));
            messageArray[i].setText(currentPageList.get(i).getInformation());
            if (!clickStarredMessage) {
                if (currentPageList.get(i).getStar() == 1) {
                    starArray[i].setImage(filledStar);
                } else {
                    starArray[i].setImage(emptyStar);
                }
            } else {
                starArray[i].setImage(filledStar);
            }
            notePadPaneArray[i].setVisible(true);
            starArray[i].setVisible(true);
            deleteArray[i].setVisible(true);
        }
        // set empty labels
        if (currentPageList.size() != 5) {
            for (int j = 4; j >= currentPageList.size(); j--) {
                notePadPaneArray[j].setVisible(false);
                starArray[j].setVisible(false);
                deleteArray[j].setVisible(false);
            }
        }
    }

    @FXML
    private void OnStar1() {
        onClickStarMethodNumber(0);
    }

    @FXML
    private void OnStar2() {
        onClickStarMethodNumber(1);
    }

    @FXML
    private void OnStar3() {
        onClickStarMethodNumber(2);
    }

    @FXML
    private void OnStar4() {
        onClickStarMethodNumber(3);
    }

    @FXML
    private void OnStar5() {
        onClickStarMethodNumber(4);
    }

    @FXML
    private void OnPane1() {
        clickMessagePane(0);
    }

    @FXML
    private void OnPane2() {
        clickMessagePane(1);
    }

    @FXML
    private void OnPane3() {
        clickMessagePane(2);
    }

    @FXML
    private void OnPane4() {
        clickMessagePane(3);
    }

    @FXML
    private void OnPane5() {
        clickMessagePane(4);
    }

    private void clickMessagePane(int row) {
        message = currentPageList.get(row);
        descriptionDialog.setVisible(true);
        blockPane.setVisible(true);
        staffNameInDetail.setText(message.getStaffName());
        categoryDetail.setText(message.getCategory());
        dateDetail.setText(dateText(message.getMessageTime()));
        itemDescriptionInDetails.setText(message.getInformation());
        itemDescriptionInDetails.setWrapText(true);
        itemDescriptionInDetails.setEditable(false);
    }

    @FXML
    private void onClickStarMethodNumber(int row) {
        message = currentPageList.get(row);
        int paneID = row + 1;
        boolean star = message.getStar() == 1;
        //filled
        if (star) {
            message.setStar(0);
            changeToEmptyStarImage(paneID);
            executorService.execute(() -> {
                cachedMessageService.updateMessage(message);
//                Platform.runLater(this::setContent);
            });
        } else {
            //empty
            message.setStar(1);
            changeToFilledStarImage(paneID);
        }
        executorService.execute(() -> {
            cachedMessageService.updateMessage(message);
//            Platform.runLater(this::setContent);
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
        deleteMessagePane.setVisible(true);
        blockPane.setVisible(true);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteMessagePane, 300, 0, 1);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteMessagePane, 300, -45, 0);
        translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
        fadeTransition.play();
        translateTransition.play();
    }

    @FXML
    private void doNotContinue() {
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteMessagePane, 300, 1, 0);
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteMessagePane, 300, 0, -45);
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
                FadeTransition fadeTransition = TransitionUtils.getFadeTransition(deleteMessagePane, 300, 1, 0);
                TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(deleteMessagePane, 300, -0, -45);
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
        switch (paneID) {
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
        staffNameArray[0] = staffName1;
        staffNameArray[1] = staffName2;
        staffNameArray[2] = staffName3;
        staffNameArray[3] = staffName4;
        staffNameArray[4] = staffName5;
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
        messageArray[0] = message1;
        messageArray[1] = message2;
        messageArray[2] = message3;
        messageArray[3] = message4;
        messageArray[4] = message5;
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
        //initialize deletion icon
        deleteArray[0] = deleteOne;
        deleteArray[1] = deleteTwo;
        deleteArray[2] = deleteThree;
        deleteArray[3] = deleteFour;
        deleteArray[4] = deleteFive;
    }

    @FXML
    private void enableNode(Node node) {
        node.setOpacity(1);
        node.setVisible(true);
        node.setPickOnBounds(true);
    }

    @FXML
    private void disable(Node node) {
        node.setOpacity(0);
        node.setVisible(false);
        node.setPickOnBounds(false);
    }

    @FXML

    private void onClickMessage() {
        clickStarredMessage = false;
        executorService.execute(() -> {
            Platform.runLater(() -> {
                        setContent();
                    }
            );
        });
        if ((!isMovingLineOnMessage) && (!isLineMoving)) {
            isLineMoving = true;
            onMoveToMessageBlockPane.setVisible(true);
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionByX(movingLinePane, 500, -120);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.setOnFinished(event -> {
                onMoveToMessageBlockPane.setVisible(false);
                isLineMoving = false;
                isMovingLineOnMessage = true;
            });
            translateTransition.play();
        }
    }

    private void setMessagePageList() {
        if (clickStarredMessage) {
            messagePageList = MessageCachedUtils.getLists(MessageCachedUtils.listType.STAR_MESSAGE);
        } else {
            messagePageList = MessageCachedUtils.getLists(MessageCachedUtils.listType.All_MESSAGE);
        }
    }

    @FXML
    private void onClickStarred() {
        clickStarredMessage = true;
        executorService.execute(() -> {
            Platform.runLater(() -> {
                        setContent();
                    }
            );
        });
        if ((isMovingLineOnMessage) && (!isLineMoving)) {

            isLineMoving = true;
            onMoveToStarredBlockPane.setVisible(true);
            TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionByX(movingLinePane, 500, 120);
            translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
            translateTransition.setOnFinished(event -> {
                onMoveToStarredBlockPane.setVisible(false);
                isLineMoving = false;
                isMovingLineOnMessage = false;
            });
            translateTransition.play();
        }
    }

    @FXML
    private void onScrolled(ScrollEvent event) {
        if (event.getDeltaY() < 0) {
            newPagination.setCurrentPageIndex(newPagination.getCurrentPageIndex() + 1);
        }
        if (event.getDeltaY() > 0) {
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

    @FXML
    private void onEnterDeleteOne() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteOne, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteOne() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteOne, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteTwo() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteTwo, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteTwo() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteTwo, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteThree() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteThree, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteThree() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteThree, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteFour() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteFour, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteFour() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteFour, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterDeleteFive() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteFive, 250, 1.1);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onExitDeleteFive() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(deleteFive, 250, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    /**
     * reload cache from database
     */
    @FXML
    public void onRefresh() throws UnsupportedPojoException {
        refreshButton.setVisible(false);
        refreshSpinner.setVisible(true);
        executorService.execute(() -> {
            try {
                cachedMessageService.updateAllCachedMessageData();
                Platform.runLater(() -> {
                    setContent();
                });
            } finally {
                refreshButton.setVisible(true);
                refreshSpinner.setVisible(false);
            }
        });
    }

}
