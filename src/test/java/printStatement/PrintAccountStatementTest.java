package printStatement;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.journaldev.spring.dao.AccountRepository;
import com.journaldev.spring.model.Console;
import com.journaldev.spring.model.StatementPrinter;
import com.journaldev.spring.model.TransactionDTO;
import com.journaldev.spring.service.AccountService;
import com.journaldev.spring.service.AccountServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class PrintAccountStatementTest {

	@Mock
	private Console console;
	private AccountService accountService;
	private AccountRepository repo;
	private StatementPrinter printer;

	
	@Before
	public void initialise(){
		repo= new AccountRepository();
		printer= new StatementPrinter(console);
		accountService= new AccountServiceImpl(repo, printer);
	}
	
	@Test
	public void shouldPrintStatementInFormat(){
		
		accountService.deposit(new TransactionDTO(1000, LocalDate.of(2018,04,01), 0));
		accountService.withdraw(new TransactionDTO(-100, LocalDate.of(2018,04,02), 0));
		accountService.deposit(new TransactionDTO(500, LocalDate.of(2018,04,10), 0));
		accountService.printStatement();
		
		InOrder inOrder = Mockito.inOrder(console);
		inOrder.verify(console).printLine("DATE | AMOUNT | BALANCE");
		inOrder.verify(console).printLine("10/04/2018 | 500.00 | 1400");
		inOrder.verify(console).printLine("02/04/2018 | -100.00 | 900");
		inOrder.verify(console).printLine("01/04/2018 | 1000.00 | 1000");
		
	}
	
}
