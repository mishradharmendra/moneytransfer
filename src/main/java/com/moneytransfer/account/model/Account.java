package com.moneytransfer.account.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Account {

    public final int id;
    public final String accountName;
    public final  AtomicReference<BigDecimal> balance;

    public Account(int id, String accountName, AtomicReference<BigDecimal> balance) {
        this.id = id;
        this.accountName = accountName;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                Objects.equals(accountName, account.accountName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountName);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
