package com.journaldev.spring.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.journaldev.spring.model.TransactionEntity;

public class AccountRepository {

	List<TransactionEntity> entities= new ArrayList<>();
	
	public void deposit(TransactionEntity accountEntity) {
		entities.add(accountEntity);
	}

	public void withdraw(TransactionEntity entity) {
		entities.add(entity);
		
	}

	public List<TransactionEntity> findAll() {
		return Collections.unmodifiableList(entities);
	}

}
