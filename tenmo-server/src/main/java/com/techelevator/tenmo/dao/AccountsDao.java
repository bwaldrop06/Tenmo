package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountsDao {

//return balance
    BigDecimal getBalanceForUserId(int loggedInUserId);
    //add/subtract/and find account by Id to incorporate
    BigDecimal addToBalance(BigDecimal amountToAdd, int id);
    BigDecimal subtractFromBalance(BigDecimal amountToSubtract, int id);
    public Account findAccountById(int id);

}

