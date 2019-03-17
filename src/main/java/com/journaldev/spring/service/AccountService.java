package com.journaldev.spring.service;

import com.journaldev.spring.model.TransactionDTO;

public interface AccountService {

	void deposit(TransactionDTO transactionDTO);

	void withdraw(TransactionDTO transactionDTO);

	void printStatement();

}
