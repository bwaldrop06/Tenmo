package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")

public class TransferController {

    private TransferDao transferDao;
    private AccountsDao accountsDao;
    private UserDao userDao;

    public TransferController(UserDao userDao, AccountsDao accountsDao, TransferDao transferDao) {
        this.accountsDao = accountsDao;
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    //send transfer
    @RequestMapping(path = "/transfers/sendmoney", method = RequestMethod.POST)
    public Transfer sendTransfer(@RequestBody Transfer transfer, Principal principal) throws Exception {
        String loggedInUserName = principal.getName();
        int loggedInUserId = userDao.findIdByUsername(loggedInUserName);
        int accountId = transferDao.findAccountIdByUserId(loggedInUserId);
        int accountIdTo = transferDao.findAccountIdByUserId(transfer.getUser_Id());
        transferDao.updateBalanceWhenUserSendsMoney(accountIdTo, accountId, transfer.getAmount());
        return transferDao.createTransfer(transfer, accountId, accountIdTo);
    }

    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransferByTransferId(@PathVariable int id){
        return transferDao.getTransferByTransferId(id);
    }

    @RequestMapping(path = "/accounts/transfers/listtransfers", method = RequestMethod.GET)
    public List<Transfer> listTransfers(Principal principal){
        String loggedInUserName = principal.getName();
        int loggedInUserId = userDao.findIdByUsername(loggedInUserName);
        int accountId = transferDao.findAccountIdByUserId(loggedInUserId);
        List<Transfer> transfers = transferDao.getTransfers(accountId);
        return transfers;

    }
    @RequestMapping(path="/accounts/transfers/listpending", method =  RequestMethod.GET)
    public List<Transfer> listPending(Principal principal){
        String loggedInUserName = principal.getName();
        int loggedInUserId = userDao.findIdByUsername(loggedInUserName);
        int accountId = transferDao.findAccountIdByUserId(loggedInUserId);
        List<Transfer> transfers = transferDao.getPending(accountId);
        return transfers;
    }

   @RequestMapping(path = "/transfers/requestmoney", method = RequestMethod.POST)
    public Transfer requestTransfer(@RequestBody Transfer transfer, Principal principal) throws Exception {
        String loggedInUserName = principal.getName();
        int loggedInUserId = userDao.findIdByUsername(loggedInUserName);
        int accountId = transferDao.findAccountIdByUserId(loggedInUserId);
        int accountIdTo = transferDao.findAccountIdByUserId(transfer.getUser_Id());
       return transferDao.createRequest(transfer, accountId, accountIdTo);
    }

    @RequestMapping(path = "/transfers/accepttransfer", method = RequestMethod.PUT)
    public Transfer acceptUpdate(@RequestBody Transfer transfer, Principal principal) throws Exception {
        String loggedInUserName = principal.getName();
        int loggedInUserId = userDao.findIdByUsername(loggedInUserName);
        int accountId = transferDao.findAccountIdByUserId(loggedInUserId);
        int accountIdTo = transferDao.findAccountIdByUserId(transfer.getUser_Id());
        transferDao.updateBalanceWhenUserApprovesRequest(accountIdTo, accountId, transfer.getAmount());
        return transferDao.createTransfer(transfer, accountId, accountIdTo);
    }

    @RequestMapping(path = "/accounts/transfers/denytransfer", method = RequestMethod.DELETE)
    public Transfer deleteUpdate(@PathVariable int id) {
      return  transferDao.deleteRequest(id);
    }

}
