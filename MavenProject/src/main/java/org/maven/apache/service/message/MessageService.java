package org.maven.apache.service.message;


import org.maven.apache.message.Message;

import java.util.List;

public interface MessageService {
    /**
     * This method adds a new message to the message table
     *
     * @param message an instance of message
     */
    void addNewMessage(Message message);

    /**
     * This method returns a list of message that is currently in message Table
     *
     * @return a list of messages
     */
    List<Message> selectAll();

    /**
     * This method returns an message that has the same ID value in the table
     *
     * @param id integer message ID
     * @return an instance of message
     */
    Message selectById(int id);

    /**
     * updates the value of an message in messageTable based on their ID values
     *
     * @param message an instance of Message
     * @return an integer showing how many number of rows in the table are affected
     */
    int update(Message message);

    /**
     * deletes an message in the message table by its id
     *
     * @param id an integer ID in message
     */
    void deleteById(int id);

    /**
     * star the message
     *
     * @param id the ID that represent the message
     */
    void starMessage(int id);

    /**
     * this method should be use after using delete by id
     */
    void IdGapInside();
}
