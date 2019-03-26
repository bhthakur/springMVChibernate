package printStatement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.journaldev.spring.model.Console;
import com.journaldev.spring.model.StatementPrinter;
import com.journaldev.spring.model.TransactionDTO;

@RunWith(MockitoJUnitRunner.class)
public class StatementPrinterTest {
	
	@Mock
	private Console console;
	private StatementPrinter printer;

	@Before
	public void initialise(){
		printer= new StatementPrinter(console);
	}
	
	
	@Test
	public void shouldPrintHeader(){
		List<TransactionDTO> transactions= Collections.emptyList();
		printer.print(transactions);
		Mockito.verify(console).printLine("DATE | AMOUNT | BALANCE");
	}
	
	@Test
	public void shouldPrintTrasactionsInOrder() {
		
		List<TransactionDTO> transactions= getTransactionDtos();
		printer.print(transactions);
		
		InOrder inOrder = Mockito.inOrder(console);
		inOrder.verify(console).printLine("10/04/2018 | 500.00 | 1400");
		inOrder.verify(console).printLine("02/04/2018 | -100.00 | 900");
		inOrder.verify(console).printLine("01/04/2018 | 1000.00 | 1000");
		
	}

	private List<TransactionDTO> getTransactionDtos() {
		TransactionDTO deposit1= new TransactionDTO(1000, LocalDate.of(2018,04,01), 0);
		TransactionDTO withdraw= new TransactionDTO(-100, LocalDate.of(2018,04,02), 0);
		TransactionDTO deposit2= new TransactionDTO(500, LocalDate.of(2018,04,10), 0);
		return Arrays.asList(deposit1, withdraw, deposit2);
	}

}
