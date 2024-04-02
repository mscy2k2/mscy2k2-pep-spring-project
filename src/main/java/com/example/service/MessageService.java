package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;
    
    @Transactional
    public Message createMessage(Message message){
        List<Account> accountList = accountRepository.findAll();
        if (message.getMessage_text().length() > 0 && message.getMessage_text().length() < 255) {
            for (Account account : accountList) {
                if (account.getAccount_id().equals(message.getPosted_by())) {
                    return messageRepository.save(message);
                }
            }
        }
        return null; 
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int message_id){
        List<Message> messages = messageRepository.findAll();
        for(Message message : messages) {
            if(message.getMessage_id() == message_id) {
                return message;
            }
        }
        return null;    
    }

    public Integer deleteMessageById(int message_id){
        List<Message> messages = messageRepository.findAll();
        Integer rowsUpdated = 0;
        for(Message message : messages) {
            if(message.getMessage_id() == message_id) {
                messageRepository.delete(message);
                rowsUpdated++;
                return rowsUpdated;
            }
        }
        return null;    
    }

    public Integer updateMessage(Message updatedMessage, int message_id) {
        List<Message> messages = messageRepository.findAll();
        Integer rowsUpdated = 0;
        if (updatedMessage.getMessage_text().length() > 0 && updatedMessage.getMessage_text().length() < 255) {
            for (Message message : messages) {
                if (message.getMessage_id() == message_id) {
                    message.setMessage_text(updatedMessage.getMessage_text());
                    messageRepository.save(message);
                    rowsUpdated++;
                }
            }
        }
        return rowsUpdated;
    }
    
    public List<Message> getAllMessagesByUser(int account_id){
        List<Integer> idList = new ArrayList<>();
        idList.add(account_id);
        return messageRepository.findAllById(idList);    
    } 
}