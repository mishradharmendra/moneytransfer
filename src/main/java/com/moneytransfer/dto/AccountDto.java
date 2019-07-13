package com.moneytransfer.dto;

import com.moneytransfer.account.model.Account;

import java.math.BigDecimal;

public class AccountDto {
    private final int id;
    private final String accountName;
    private final BigDecimal balance;
    private AccountDto(int id, String accountName, BigDecimal balance) {
        this.id = id;
        this.accountName = accountName;
        this.balance = balance;
    }

    public static AccountDto createFromAccount(Account account) {
        return new AccountDto(account.id, account.accountName, account.balance.get());
    }
    public int getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

}
