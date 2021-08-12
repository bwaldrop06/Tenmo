package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.dao.UserDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//import com.techelevator.tenmo.dao.JbdcUserDao;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")

public class AccountController {

    private UserDao userDao;
    private AccountsDao accountsDao;

    public AccountController(AccountsDao accountsDao, UserDao userDao) { //dependency injection for daos
        this.accountsDao = accountsDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/accounts/balance", method = RequestMethod.GET) //map to balance
    public BigDecimal getBalance(Principal principal) {
        String loggedInUserName  = principal.getName();
        int loggedInUserId = userDao.findIdByUsername(loggedInUserName);
        return accountsDao.getBalanceForUserId(loggedInUserId);
    }


}
