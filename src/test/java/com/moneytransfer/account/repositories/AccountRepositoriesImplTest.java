package com.moneytransfer.account.repositories;

import com.moneytransfer.account.exception.InsufficientAmountException;
import com.moneytransfer.account.exception.InvalidTransferException;
import com.moneytransfer.dto.AccountRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;

public class AccountRepositoriesImplTest {

    AccountRepositories accountRepositories;

    @Before
    public void setUp() {
        accountRepositories = new AccountRepositoriesImpl();
    }

    @Test
    public void createAndTestAccounts() {
        AccountRequest request = new AccountRequest();
        request.setAmount(new BigDecimal("1000"));
        request.setName("Demo");
        accountRepositories.createAccount(request);
        Assert.assertTrue(accountRepositories.getAllAccounts().size() == 1);
    }


    @Test
    public void deleteAccount() {
        AccountRequest request = new AccountRequest();
        request.setAmount(new BigDecimal("1000"));
        request.setName("Demo");
        accountRepositories.createAccount(request);
        Assert.assertTrue(accountRepositories.getAllAccounts().size() == 1);

        AccountRequest request1 = new AccountRequest();
        request1.setAmount(new BigDecimal("1000"));
        request1.setName("Demo");
        accountRepositories.createAccount(request1);
        Assert.assertTrue(accountRepositories.getAllAccounts().size() == 2);

        accountRepositories.deleteAccount(1);
        Assert.assertTrue(accountRepositories.getAllAccounts().size() == 1);
    }

    @Test
    public void addBalance() {
        AccountRequest request = new AccountRequest();
        request.setAmount(new BigDecimal("1000"));
        request.setName("Demo");
        accountRepositories.createAccount(request);
        Assert.assertTrue(accountRepositories.getAllAccounts().size() == 1);

        accountRepositories.addBalance(1, new BigDecimal("1000"));
        Assert.assertTrue(accountRepositories.getAllAccounts().get(0).getBalance().compareTo(new BigDecimal("2000")) <=1);
    }

    @Test
    public void withdrowAmount() {
        AccountRequest request = new AccountRequest();
        request.setAmount(new BigDecimal("1000"));
        request.setName("Demo");
        accountRepositories.createAccount(request);
        Assert.assertTrue(accountRepositories.getAllAccounts().size() == 1);

        accountRepositories.withdrowAmount(1, new BigDecimal("500"));
        Assert.assertTrue(accountRepositories.getAllAccounts().get(0).getBalance().compareTo(new BigDecimal("500")) <=1);
    }

    @Test
    public void transferAmount() {
        AccountRequest request = new AccountRequest();
        request.setAmount(new BigDecimal("1000"));
        request.setName("Demo");
        accountRepositories.createAccount(request);
        Assert.assertTrue(accountRepositories.getAllAccounts().size() == 1);

        AccountRequest request1 = new AccountRequest();
        request1.setAmount(new BigDecimal("1000"));
        request1.setName("Demo");
        accountRepositories.createAccount(request1);
        Assert.assertTrue(accountRepositories.getAllAccounts().size() == 2);

        try {
            accountRepositories.transferAmount(1, 2 , new BigDecimal("500"));
            BigDecimal account1Bal = accountRepositories.getAllAccounts().stream().filter( aa -> aa.getId() == 1)
                    .map(a -> a.getBalance()).findFirst().get();
            Assert.assertTrue(account1Bal.compareTo(new BigDecimal("500")) <=1);

            BigDecimal account2Bal = accountRepositories.getAllAccounts().stream().filter( aa -> aa.getId() == 2)
                    .map(a -> a.getBalance()).findFirst().get();
            Assert.assertTrue(account1Bal.compareTo(new BigDecimal("1500")) <=1);


        } catch (InsufficientAmountException e) {
            e.printStackTrace();
        } catch (InvalidTransferException e) {
            e.printStackTrace();
        }

        try {
            accountRepositories.transferAmount(1, 2 , new BigDecimal("2500"));
        } catch (InsufficientAmountException e) {
            Assert.assertEquals("Account id " + 1 + " has not enough money to transfer to Account id " + 2, e.getMessage());
        } catch (InvalidTransferException e) {
            e.printStackTrace();
        }

    }
}