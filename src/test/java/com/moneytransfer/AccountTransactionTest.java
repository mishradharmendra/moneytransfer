package com.moneytransfer;

import java.math.BigDecimal;
import java.time.Instant;

import com.moneytransfer.account.model.TransactionType;

public class AccountTransactionTest {

	private int transactionId;
    private Instant transactionTime;
    private BigDecimal accountTransaction;
    private TransactionType transactionType;
    public AccountTransactionTest() {}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public Instant getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(Instant transactionTime) {
		this.transactionTime = transactionTime;
	}
	public BigDecimal getAccountTransaction() {
		return accountTransaction;
	}
	public void setAccountTransaction(BigDecimal accountTransaction) {
		this.accountTransaction = accountTransaction;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

}
