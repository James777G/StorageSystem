package org.maven.apache.service.message;

import org.maven.apache.message.Message;

import java.util.List;

/**
 * 这个provider是给数据库的连接方法
 */
public class MessageServiceProvider implements MessageService{
    /**
     * This method adds a new message to the message table
     *
     * @param message an instance of message
     */
    @Override
    public void addNewMessage(Message message) {

    }

    /**
     * This method returns a list of message that is currently in message Table
     *
     * @return a list of messages
     */
    @Override
    public List<Message> selectAll() {
        return null;
    }

    /**
     * This method returns an message that has the same ID value in the table
     *
     * @param id integer message ID
     * @return an instance of message
     */
    @Override
    public Message selectById(int id) {
        return null;
    }

    /**
     * updates the value of an message in messageTable based on their ID values
     *
     * @param message an instance of Message
     * @return an integer showing how many number of rows in the table are affected
     */
    @Override
    public int update(Message message) {
        return 0;
    }

    /**
     * deletes an message in the message table by its id
     *
     * @param id an integer ID in message
     */
    @Override
    public void deleteById(int id) {

    }
}
