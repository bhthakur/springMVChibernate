package com.journaldev.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import com.journaldev.spring.dao.AccountRepository;
import com.journaldev.spring.model.StatementPrinter;
import com.journaldev.spring.model.TransactionDTO;
import com.journaldev.spring.model.TransactionEntity;

public class AccountServiceImpl implements AccountService {


	private AccountRepository repo;
	private StatementPrinter statementPrinter;


	public AccountServiceImpl(AccountRepository accountRepo, StatementPrinter printer) {
		this.repo= accountRepo;
		this.statementPrinter= printer;
	}

	@Override
	public void deposit(TransactionDTO transactionDTO) {
		repo.deposit(transactionDTO.toEntity());
		
	}

	@Override
	public void withdraw(TransactionDTO transactionDTO) {
		repo.withdraw(transactionDTO.toEntity());
		
	}

	
	@Override
	public void printStatement() {
		List<TransactionEntity> entities = repo.findAll();
		List<TransactionDTO> dtoList = entities.stream()
				  .map(entity -> TransactionDTO.toDto(entity))
				  .collect(Collectors.toList());
		statementPrinter.print(dtoList);
		
	}
}
