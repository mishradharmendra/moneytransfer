package com.moneytransfer.account.repositories;

import com.moneytransfer.account.exception.InsufficientAmountException;
import com.moneytransfer.account.exception.InvalidTransferException;
import com.moneytransfer.account.model.Account;
import com.moneytransfer.account.model.AccountTransaction;
import com.moneytransfer.account.model.TransactionType;
import com.moneytransfer.dto.AccountDto;
import com.moneytransfer.dto.AccountRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@Singleton
public class AccountRepositoriesImpl implements AccountRepositories {

    private AtomicInteger accountId = new AtomicInteger(0);
    private Random random = new Random();
    private Map<Integer, Account> accounts = new ConcurrentHashMap<>();
    private Map<Integer, List<AccountTransaction>> accountTransactionMap = new ConcurrentHashMap<>();

    ReentrantLock lock = new ReentrantLock(true);

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepositoriesImpl.class);
    @Override
    public List<AccountDto> getAllAccounts() {
        try {
            LOGGER.info(" Fetching all accounts ");
            lock.lock();
            List<AccountDto> accounts = this.accounts.values().stream().map(a -> AccountDto.createFromAccount(a)).collect(Collectors.toList());
            LOGGER.info("Total account received "+ accounts.size());
            return accounts;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public AccountDto createAccount(AccountRequest accountRequest) {
        try {
            LOGGER.info("Creating account "+ accountRequest);
            lock.lock();
            int id = accountId.incrementAndGet();
            Account account = new Account(id, accountRequest.getName(), new AtomicReference<>(accountRequest.getAmount()));
            accounts.put(id, account);
            addTransaction(id,accountRequest.getAmount(), TransactionType.CREDIT);
            return AccountDto.createFromAccount(account);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public Account deleteAccount(int accountId) {
        try {
            LOGGER.info(" Deleting account id ", accountId);
            lock.lock();
            accountTransactionMap.remove(accountId);
            return accounts.remove(accountId);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public AccountDto addBalance(int accountId, BigDecimal amount) {
        try {
            LOGGER.info("Adding balance to account "+ accountId, " of amount " + accountId);
            lock.lock();
            Account account = accounts.get(accountId);
            if(account == null)
                return  null;
            account.balance.updateAndGet(current -> current.add(amount));
            addTransaction(accountId,amount, TransactionType.CREDIT);
            LOGGER.info("Balance after adding the amount " + account);
            return AccountDto.createFromAccount(account);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public AccountDto withdrowAmount(int accountId, BigDecimal amount) {
        try {
            LOGGER.info("Withdrawing amount from account "+ accountId + " of amount " + amount);
            lock.lock();
            Account account = accounts.get(accountId);
            if (account == null)
                return null;
            account.balance.updateAndGet(current -> current.subtract(amount));
            addTransaction(accountId,amount, TransactionType.DEBIT);
            LOGGER.info("Balance after adding the amount " + account);
            return AccountDto.createFromAccount(account);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<AccountTransaction> getAccountTransaction(int accountId) {
        lock.lock();
        try {
            return accountTransactionMap.get(accountId);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public List<AccountDto> transferAmount(int fromAccount, int toAccount, BigDecimal amount) throws InsufficientAmountException, InvalidTransferException {
        LOGGER.info(" Transferring amount " + amount + " from account " + fromAccount + " to account " + toAccount);
        lock.lock();
        if (fromAccount <= 0 || toAccount <= 0) return null;
        if (Objects.equals(fromAccount,toAccount)) throw new InvalidTransferException();
        Account accountFrom = accounts.get(fromAccount);
        Account accountTo = accounts.get(toAccount);
        if( accountFrom == null || accountTo == null) return null;
        try {
            if(accountFrom.balance.get().compareTo(amount) >= 0) {
                accountFrom.balance.updateAndGet(current -> current.subtract(amount));
                addTransaction(fromAccount, amount, TransactionType.DEBIT);
                accountTo.balance.updateAndGet(current -> current.add(amount));
                addTransaction(toAccount, amount, TransactionType.CREDIT);
            }else {
                throw new InsufficientAmountException("Account id " + fromAccount + " has not enough money to transfer to Account id " + toAccount);
            }
            LOGGER.info("Balance after transferring from account " + fromAccount + " the amount " + accountFrom);
            LOGGER.info("Balance after transferring to account " + toAccount + " the amount " + accountTo);

            return newArrayList(AccountDto.createFromAccount(accountFrom), AccountDto.createFromAccount(accountTo));
        }finally {
            lock.unlock();
        }
    }

    private void addTransaction(int accountId, BigDecimal amount, TransactionType transactionType) {
        LOGGER.info("Adding to transaction " + amount + " for account "+ accountId);
        AccountTransaction accountTransaction = new AccountTransaction(random.nextInt(), Instant.now(),amount,transactionType);
        List<AccountTransaction> transactionList = null;
        if(accountTransactionMap.containsKey(accountId)) {
            transactionList = accountTransactionMap.get(accountId);
        }else {
            transactionList = new ArrayList<>();
        }
        transactionList.add(accountTransaction);
        accountTransactionMap.put(accountId, transactionList);
        LOGGER.info("Transaction Added");
    }

}
