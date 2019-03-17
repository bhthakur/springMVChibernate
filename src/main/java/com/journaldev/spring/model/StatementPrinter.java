package com.journaldev.spring.model;

import java.text.DecimalFormat;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.journaldev.spring.helper.DateUtils;

public class StatementPrinter {

	private Console console;
	private DecimalFormat formatter= new DecimalFormat("#.00");
	private static final String STATEMENT_HEADER = "DATE | AMOUNT | BALANCE";
	
	public StatementPrinter(Console console) {
		this.console = console;
	}

	public void print(List<TransactionDTO> transactions) {
		printHeader();
		printTrasactions(transactions);
	}

	private void printTrasactions(List<TransactionDTO> transactions) {
		final AtomicInteger runningBalance= new AtomicInteger(0);
		transactions.stream()
			.map(transaction -> format(runningBalance, transaction))
			.collect(Collectors.toCollection(LinkedList::new))
			.descendingIterator()
			.forEachRemaining(console::printLine);
	}

	private void printHeader() {
		console.printLine(STATEMENT_HEADER);
	}

	private String format(final AtomicInteger runningBalance, TransactionDTO transaction) {
		return DateUtils.formatDate(transaction.getDate(), "dd/MM/yyyy") 
				+ " | " +
				formatter.format(transaction.getAmount()) 
				+ " | " +
				runningBalance.addAndGet(transaction.getAmount()) ;
	}

}
