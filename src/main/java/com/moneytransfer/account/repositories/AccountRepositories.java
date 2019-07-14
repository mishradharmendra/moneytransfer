package com.moneytransfer.account.repositories;

import com.moneytransfer.account.exception.InsufficientAmountException;
import com.moneytransfer.account.exception.InvalidTransferException;
import com.moneytransfer.account.model.Account;
import com.moneytransfer.account.model.AccountTransaction;
import com.moneytransfer.dto.AccountDto;
import com.moneytransfer.dto.AccountRequest;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepositories {
    List<AccountDto> getAllAccounts();
    AccountDto createAccount(AccountRequest accountRequest);
    Account deleteAccount(int accountId);
    AccountDto addBalance(int accountId, BigDecimal amount);
    AccountDto withdrowAmount(int accountId, BigDecimal amount) throws InsufficientAmountException;
    List<AccountTransaction> getAccountTransaction(int accountId);
    List<AccountDto> transferAmount(int fromAccount, int toAccount, BigDecimal amount) throws InsufficientAmountException, InvalidTransferException;
}
