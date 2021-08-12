package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")


public class UserController {

    private UserDao userDao;
    private AccountsDao accountsDao;

   public UserController(UserDao userDao, AccountsDao accountsDao) {
       this.userDao = userDao;
       this.accountsDao = accountsDao;
   }

//list all users
    @PreAuthorize("permitAll")
    @RequestMapping(path="/users", method = RequestMethod.GET)
    public List<User> listAllUsers() {return userDao.findAll();}

}
