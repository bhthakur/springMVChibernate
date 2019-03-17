import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.journaldev.spring.service.PropertyService;

public class PropertyServiceTest {

	
	@Test
	public void testReadProperty() {
		PropertyService service= new PropertyService("test.properties");
		String actual = service.getProperty("job");
		String expected = "developer";
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void shouldTestReturnMaxLength(){
		PropertyService service= new PropertyService("test.properties");
		int size= service.getMaxLength();
		assertEquals(13, size);
	}
	
	
	@Test
	public void shouldTestReturnMaxLengthLine(){
		PropertyService service= new PropertyService("test.properties");
		String line= service.getMaxLengthLine();
		assertEquals("job=developer", line);
	}
	
	
	@Test
	public void shouldReturnSortedLines(){
		PropertyService service= new PropertyService("test.properties");
		List<String> sortedLines = service.sortedLines();
		System.out.println(sortedLines);
		List<String> sLines = service.sortedLinesUsingComparator();
		System.out.println("Pring Sline: ");
		
		System.out.println(sLines);
		
	}
	
	@Test
	public void shouldReturnMaxLenghtLine(){
		PropertyService service= new PropertyService("test.properties");
		String result = service.sortedMaxLengthLine();
		System.out.println(result);
		assertEquals("job=developer", result);
	}

	@Test
	public void shouldReturnMaxLenLineUsingRecursion() throws IOException{
		PropertyService service= new PropertyService("test.properties");
		BufferedReader br= service.getReader();
		String longest= "";
		String result = service.findMaxLenLine(br, longest);
		System.out.println(result);
		assertEquals("job=developer", result);
	}
	
}
