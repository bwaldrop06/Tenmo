package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.List;


@Component //step 2 of dependency injection
public class JdbcAccountsDao implements AccountsDao {
    private JdbcTemplate jdbcTemplate;


    public JdbcAccountsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalanceForUserId(int loggedInUserId) {
        String sql = "SELECT SUM(balance) FROM accounts WHERE user_id = ?;";
        try {
            BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, loggedInUserId);
            return balance;
        }
        catch (Exception e){
            System.out.println("Error in getBalance " + loggedInUserId + ": "+e.getMessage());
            return BigDecimal.ZERO; //if user doesn't have account than balance is zero
        }

    }
    //add to balance
    public BigDecimal addToBalance(BigDecimal amountToAdd, int id){
        Account account = findAccountById(id);
        BigDecimal newBalance = account.getBalance().add(amountToAdd);
        System.out.println(newBalance);
        String sqlString = "UPDATE accounts SET balance = ? WHERE user_id = ?;";
        return account.getBalance();
    }

    //subtract to balance
    public BigDecimal subtractFromBalance(BigDecimal amountToSubtract, int id){
        Account account = findAccountById(id);
        BigDecimal newBalance = account.getBalance().subtract(amountToSubtract);
        String sql = "UPDATE accounts SET balance = ? WHERE user_id = ?;";
        return account.getBalance();
    }

//find Account by Id to implement adding and subtracting balances
    @Override
    public Account findAccountById(int id){
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM accounts WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()){
            account = mapRowToAccount(results);
        }
        return account;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setUserId(rs.getLong("user_id"));
        account.setAccountId(rs.getLong("account_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }

}
