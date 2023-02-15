package org.maven.apache.service.message;

import lombok.Data;
import org.maven.apache.mapper.MessageMapper;
import org.maven.apache.message.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 这个provider是给数据库的连接方法
 */
@Component("messageService")
@Transactional
@Data
public class MessageServiceProvider implements MessageService {
    @Resource
    private MessageMapper messageMapper;

    @Resource
    private MessageService messageService;

    public MessageMapper getMessageMapper() {
        return messageMapper;
    }

    public void setMessageMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    /**
     * This method adds a new message to the message table
     *
     * @param message an instance of message
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewMessage(Message message) {
        messageMapper.addNewMessage(message);
    }

    /**
     * This method returns a list of message that is currently in message Table
     *
     * @return a list of messages
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Message> selectAll() {
        return messageMapper.selectAll();
    }

    /**
     * This method returns an message that has the same ID value in the table
     *
     * @param id integer message ID
     * @return an instance of message
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Message selectById(int id) {
        return messageMapper.selectById(id);
    }

    /**
     * updates the value of an message in messageTable based on their ID values
     *
     * @param message an instance of Message
     * @return an integer showing how many number of rows in the table are affected
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(Message message) {
        return messageMapper.update(message);
    }

    /**
     * deletes an message in the message table by its id
     *
     * @param id an integer ID in message
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(int id) {
        messageMapper.deleteById(id);
        IdGapInside();
    }

    /**
     * star the message
     *
     * @param id the ID that represent the message
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void starMessage(int id) {
        messageMapper.starMessage(id);
    }

    /**
     * this method should be use after using delete by id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void IdGapInside() {
        messageMapper.IdGapInside();
    }

}
