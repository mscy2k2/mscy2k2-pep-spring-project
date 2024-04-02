
package com.example.service;

import java.util.List;
import javafx.util.Pair;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
  
    @Transactional
    public Pair<Integer, Account> createAccount(Account login_details){
        List<Account> accountList = accountRepository.findAll();
    
        if(login_details.getUsername() == null && login_details.getPassword().length() < 4){
            return new Pair<>(400, null);  
        }
        for(Account acct : accountList) {
            if(acct.getUsername().equals(login_details.getUsername())){
                return new Pair<>(409, null);  
            }
        }
        return new Pair<>(200, accountRepository.save(login_details));
    }

    @Transactional
    public Account verifyAccount(Account login_details){
        List<Account> accounts = accountRepository.findAll();

        for(Account account : accounts) {
            if(account.getUsername().equals(login_details.getUsername()) && account.getPassword().equals(login_details.getPassword())) {
                return account;
            }
        }
        return null;    
    }
}
