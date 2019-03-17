package printStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.BDDMockito.*;
import com.journaldev.spring.dao.AccountRepository;
import com.journaldev.spring.model.StatementPrinter;
import com.journaldev.spring.model.TransactionDTO;
import com.journaldev.spring.model.TransactionEntity;
import com.journaldev.spring.service.AccountService;
import com.journaldev.spring.service.AccountServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

	@Mock
	private AccountRepository accountRepo;
	@Mock
	StatementPrinter statementPrinter;
	
	private AccountService accountService;

	
	@Before
	public void initialise(){
		accountService= new AccountServiceImpl(accountRepo, statementPrinter);
	
	}
	
	@Test
	public void testDeposit(){
		TransactionDTO dto = new TransactionDTO(100);
		dto.setUser("TEST");
		accountService.deposit(dto);
		Mockito.verify(accountRepo).deposit(dto.toEntity());
	}
	
	@Test
	public void testWithdraw(){
		TransactionDTO dto = new TransactionDTO(-100);
		dto.setUser("TEST");
		accountService.withdraw(dto);
		Mockito.verify(accountRepo).withdraw(dto.toEntity());
	}
	
	
	@Test
	public void testPrintStatement(){
		List<TransactionEntity> entityList = getEntityList();
		when(accountRepo.findAll()).thenReturn(entityList);
		List<TransactionDTO> dtoList = entityList.stream()
				  .map(entity -> TransactionDTO.toDto(entity))
				  .collect(Collectors.toList());
				  
		accountService.printStatement();
		Mockito.verify(statementPrinter).print(dtoList);
	}

	private List<TransactionEntity> getEntityList() {
		TransactionEntity entity1= new TransactionEntity(1, 100, LocalDate.now(), "TEST");
		TransactionEntity entity2= new TransactionEntity(1, 100, LocalDate.now(), "TEST");
		TransactionEntity entity3= new TransactionEntity(1, 100, LocalDate.now(), "TEST");
		
		return Arrays.asList(entity1, entity2, entity3);
	}	
}
