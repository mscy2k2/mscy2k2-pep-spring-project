
package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import javafx.util.Pair;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> register(@RequestBody Account login_details) {
        Pair<Integer, Account> acctService = accountService.createAccount(login_details);
        return ResponseEntity.status(acctService.getKey()).body(acctService.getValue());
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account login_details) {
        Account account = accountService.verifyAccount(login_details);
        HttpStatus status = account==null ? HttpStatus.UNAUTHORIZED : HttpStatus.OK;
        return ResponseEntity.status(status).body(account);   
    }
   
    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        HttpStatus status = createdMessage==null ? HttpStatus.BAD_REQUEST : HttpStatus.OK;  
        return ResponseEntity.status(status).body(createdMessage);
    }

    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{message_id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Message getMessageById(@PathVariable int message_id) {
        return messageService.getMessageById(message_id);
    }

    @DeleteMapping("/messages/{message_id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Integer deleteMessageById(@PathVariable int message_id) {
        return messageService.deleteMessageById(message_id);
    }

    @PatchMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> updateMessage(@RequestBody Message message, @PathVariable int message_id) {
        Integer rowsUpdated = messageService.updateMessage(message, message_id);
        HttpStatus status = rowsUpdated == 0 ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        return ResponseEntity.status(status).body(rowsUpdated);
    }

    @GetMapping("/accounts/{account_id}/messages")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Message> getAllMessagesByUser(@PathVariable int account_id) {
        return messageService.getAllMessagesByUser(account_id);
    }
}
