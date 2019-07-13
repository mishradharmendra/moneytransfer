package com.moneytransfer.account.services;

import com.moneytransfer.account.exception.InsufficientAmountException;
import com.moneytransfer.account.exception.InvalidTransferException;
import com.moneytransfer.account.model.Account;
import com.moneytransfer.account.model.AccountTransaction;
import com.moneytransfer.account.repositories.AccountRepositories;
import com.moneytransfer.dto.AccountDto;
import com.moneytransfer.dto.AccountRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;

@Singleton
public class AccountServiveImpl implements AccountService {
    private AccountRepositories accountRepositories;

    @Inject
    public AccountServiveImpl(AccountRepositories accountRepositories) {
        this.accountRepositories = accountRepositories;
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepositories.getAllAccounts();
    }

    @Override
    public AccountDto createAccount(AccountRequest accountRequest) {
        return accountRepositories.createAccount(accountRequest);
    }

    @Override
    public Account deleteAccount(int accountId) {
        return accountRepositories.deleteAccount(accountId);
    }

    @Override
    public AccountDto addBalance(int accountId, BigDecimal amount) {
        return accountRepositories.addBalance(accountId,amount);
    }

    @Override
    public AccountDto withdrowAmount(int accountId, BigDecimal amount) {
        return accountRepositories.withdrowAmount(accountId, amount);
    }

    @Override
    public List<AccountTransaction> getAccountTransaction(int accountId) {
        return accountRepositories.getAccountTransaction(accountId);
    }

    @Override
    public List<AccountDto> transferAmount(int fromAccount, int toAccount, BigDecimal amount) throws InsufficientAmountException, InvalidTransferException {
        return accountRepositories.transferAmount(fromAccount, toAccount, amount);
    }
}
