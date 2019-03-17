package printStatement;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.journaldev.spring.dao.AccountRepository;
import com.journaldev.spring.model.TransactionEntity;

import junit.framework.Assert;


public class AccountRepositoryTest {
	
	private AccountRepository repo;


	@Before
	public void initialise(){
		repo= new AccountRepository();
	}
	
	@Test
	public void testDeposit(){
		TransactionEntity entity= new  TransactionEntity(1, 100, LocalDate.now(), "test");
		repo.deposit(entity);
		List<TransactionEntity> transactions = repo.findAll();
		Assert.assertTrue(!transactions.isEmpty());
	}
	
	@Test
	public void testWithdraw(){
		TransactionEntity entity= new  TransactionEntity(1, 100, LocalDate.now(), "test");
		repo.withdraw(entity);
		List<TransactionEntity> transactions = repo.findAll();
		Assert.assertTrue(!transactions.isEmpty());
	}
	
	

}
