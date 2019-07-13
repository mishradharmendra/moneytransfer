package com.moneytransfer.account;

import com.moneytransfer.account.exception.InsufficientAmountException;
import com.moneytransfer.account.exception.InvalidTransferException;
import com.moneytransfer.account.services.AccountService;
import com.moneytransfer.dto.AccountDto;
import com.moneytransfer.dto.AccountRequest;
import com.moneytransfer.dto.AccountTransferRequest;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class AccountController  {

    private AccountService accountService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public void getAllAccounts (Context context) {
        LOGGER.info("Fetching all Accounts");
        context.json(accountService.getAllAccounts());
    }

    public void createAccount(Context context) {
        LOGGER.info("Creating account");
        var accountReq = context.bodyAsClass(AccountRequest.class);
        var account = accountService.createAccount(accountReq);
        context.json(account);
        LOGGER.info("Account created successfully ", account);
        context.status(HttpStatus.CREATED_201);
    }

    public void deleteAccount(Context context, String id) {
        LOGGER.info("Deleting account ", id);
        accountService.deleteAccount(Integer.parseInt(id));
        context.status(HttpStatus.NO_CONTENT_204);
    }

    public void getAccountBalance(Context context, String accountId) {
        List<AccountDto> accounts = accountService.getAllAccounts().stream().filter(acc -> acc.getId() == Integer.parseInt(accountId)).collect(Collectors.toList());
        context.json(accounts);
    }

    public void transferAmount(Context context) throws InvalidTransferException, InsufficientAmountException {
        var transReq = context.bodyAsClass(AccountTransferRequest.class);
        LOGGER.info("Transferring amount " + transReq.getAmount() + " from " + transReq.getAccountFrom() + " to " + transReq.getAccountTo());
        context.json(accountService.transferAmount(transReq.getAccountFrom(), transReq.getAccountTo(), transReq.getAmount()));
    }

    public void getAllAccountTransaction(Context context, String id) {
        context.json(accountService.getAccountTransaction(Integer.parseInt(id)));
    }
    public void addBalance(Context context) {
        var account = context.bodyAsClass(AccountRequest.class);
        LOGGER.info("Adding amount ", account.getAmount() + " to account id ", account.getId());
        accountService.addBalance(account.getId(), account.getAmount());
        context.json(accountService.getAllAccounts().stream().filter( aa -> aa.getId()==account.getId()).collect(Collectors.toList()));
    }
    public void withDrawBalance(Context context) {
        var account = context.bodyAsClass(AccountRequest.class);
        LOGGER.info("Withdrawing amount ", account.getAmount() + " to account id ", account.getId());
        accountService.withdrowAmount(account.getId(), account.getAmount());
        context.json(accountService.getAllAccounts().stream().filter( aa -> aa.getId()==account.getId()).collect(Collectors.toList()));
    }
}
