package com.journaldev.spring.model;

import java.time.LocalDate;

public class TransactionDTO {

	private int amount;
	private LocalDate date;
	private int runnintBalance; // keep it for now
	private String user;
	
	
	public TransactionDTO(int amount) {
		super();
		this.amount = amount;
	}
	
	public TransactionDTO(int amount, LocalDate date, int runnintBalance) {
		super();
		this.amount = amount;
		this.date = date;
		this.runnintBalance = runnintBalance;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getRunnintBalance() {
		return runnintBalance;
	}
	public void setRunnintBalance(int runnintBalance) {
		this.runnintBalance = runnintBalance;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public TransactionEntity toEntity() {
		TransactionEntity entity = new TransactionEntity();
		entity.setAmount(amount);
		entity.setDate(date!= null ? date : LocalDate.now());
		entity.setUser(user);
		return entity;
				
	}
	public static TransactionDTO toDto(TransactionEntity entity) {
		TransactionDTO dto= new TransactionDTO(entity.getAmount());
		dto.setDate(entity.getDate());
		dto.setUser(entity.getUser());
		return dto;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + runnintBalance;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionDTO other = (TransactionDTO) obj;
		if (amount != other.amount)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (runnintBalance != other.runnintBalance)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
	
}
