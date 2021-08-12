package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer createTransfer(Transfer newTransfer, int id, int account_Id_To);

    int findAccountIdByUserId(int userId);

    BigDecimal updateBalanceWhenUserSendsMoney(int accountTo, int accountFrom, BigDecimal amount) throws Exception;

    List<Transfer> getTransfers(int accountId);

    Transfer getTransferByTransferId(int id);

    Transfer createRequest(Transfer newTransfer, int id, int account_Id_to);

    Transfer getRequest(int id);

    BigDecimal updateBalanceWhenUserApprovesRequest(int accountTo, int accountFrom, BigDecimal amount) throws Exception;

    List<Transfer> getPending(int accountId);

    Transfer deleteRequest(int id);

}

