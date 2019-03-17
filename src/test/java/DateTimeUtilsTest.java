import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

import com.journaldev.spring.helper.DateUtils;
import com.journaldev.spring.helper.State;

import junit.framework.Assert;

public class DateTimeUtilsTest {
	
	@Test
	public void testToday(){
		//today
		LocalDate today= LocalDate.now();
		
		//lastday minus 2 days
		LocalDate payday = today.with(TemporalAdjusters.lastDayOfMonth())
								.minusDays(2);
		
		//create date
		LocalDate dob = LocalDate.of(1982, Month.MAY, 5);
		
		//upcoming sunday
		LocalDate upcomingSunday = dob.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
		
		System.out.println("DOb:: "+ dob.getDayOfWeek());
		System.out.println("DOb:: "+ upcomingSunday);
		
		//parse string to date
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate parse = today.parse("20/12/2019", pattern);
		
		LocalDate dateOfBirth = LocalDate.of(2019, Month.FEBRUARY, 14);
		LocalDate firstBirthday = dateOfBirth.plusYears(1);
		LocalDate lastday = firstBirthday.with(TemporalAdjusters.lastDayOfMonth());
		System.out.println(today);
		System.out.println(payday);
		System.out.println(firstBirthday);
		System.out.println(lastday);
		System.out.println("Parsed date year "+ parse);
		
		MonthDay date = MonthDay.of(Month.FEBRUARY, 29);
		boolean validLeapYear = date.isValidYear(2010);
	}
	
	@Test
	public void shouldProvideXMLdate() throws DatatypeConfigurationException{
		LocalDateTime localDate= LocalDateTime.now();
		XMLGregorianCalendar xmlDate = DateUtils.getXmlDate(localDate);
		ZonedDateTime zdt= ZonedDateTime.now();
		ZoneId zone = zdt.getZone();
		int minutes= zdt.getOffset().getTotalSeconds()/60;
		XMLGregorianCalendar xmlDate2 = DateUtils.getXmlDate2(localDate, minutes);
		System.out.println(xmlDate);
		System.out.println(xmlDate2);
		
	}
	
	
	public LocalDate getDateFromTimeStamp(Timestamp timestamp){
		return timestamp.toLocalDateTime().toLocalDate();
	}
	
	/**
	 * Print all Monday dates for given month for this year
	 */
	@Test
	public void shouldPrintMondaysOfGivenMonth(){
		List<LocalDate> dates = DateUtils.getMondaysForMonth(2);
		dates.forEach(System.out::println);
		
	}

	@Test
	public void shouldProvideTimezone(){
		State state= State.NSW;
		ZoneId timeZone = DateUtils.getStateTimeZone(state);
		Assert.assertTrue(timeZone.equals(ZoneId.of(State.NSW.getTimeZone())));
		
		LocalDateTime dateTime= LocalDateTime.of(2019, 02, 01, 12, 35);
		LocalDateTime dateTimeByState = DateUtils.getDateTimeByState(dateTime, State.QLD);
		System.out.println(dateTimeByState );
	}
	
	@Test
	public void shouldGetDateByState(){
		
		State state= State.ACT;
		ZoneId timeZone = DateUtils.getStateTimeZone(state);
		ZoneId tZone = timeZone.of("Australia/ACT");
		Assert.assertTrue(timeZone.equals(tZone));
		
		Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
		LocalDateTime localDt = LocalDateTime.now();
		
		List<String> list = new ArrayList<String>(availableZoneIds);
		Collections.sort(list);
		
		String indianTimezone = "Asia/Calcutta";
		ZoneId indiaZone = ZoneId.of(indianTimezone);
		ZoneId localZone = ZoneId.systemDefault();
		
		
		ZonedDateTime indZone = localDt.atZone(indiaZone);
		ZoneOffset indoffset = indZone.getOffset();
		
		ZonedDateTime localZoneTime = localDt.atZone(localZone);
		ZoneOffset localOffset = localZoneTime.getOffset();
		long seconds=0;
		LocalDateTime dateTime = null;
		if(indoffset.compareTo(localOffset) > 0){
			seconds= (localOffset.getTotalSeconds() - indoffset.getTotalSeconds());
			dateTime =localDt.minusSeconds(seconds);
		}else{
			seconds=  indoffset.getTotalSeconds()- localOffset.getTotalSeconds();
			dateTime =localDt.plusSeconds(seconds);
		}
		
		System.out.println("Indian zoned time:: " + dateTime);
		
		for(String zone: list){
			ZoneId zoneId = ZoneId.of(zone);
			ZonedDateTime zonedt = localDt.atZone(zoneId);
			ZoneOffset offset = zonedt.getOffset();
			int secondsOfHour= offset.getTotalSeconds() % (60 * 60);
			String out = String.format("%35s %25s %15s%n", zoneId, offset, zonedt);
				
			if(secondsOfHour !=0){
				System.out.println(out);
			}
		}
	}
	
	@Test
	public void shouldParse(){
		String dateStr= "14/12/2018";
		String pattern= "dd/MM/yyyy";
		LocalDate date = DateUtils.parseDate(dateStr, pattern);
		System.out.println(date);
		
		Assert.assertTrue(date.get(ChronoField.DAY_OF_MONTH)==14);
		Assert.assertTrue(date.getMonthValue() == 12);
		Assert.assertTrue(date.getYear() == 2018);
	}
	
	@Test
	public void shouldFormat(){
		LocalDate localDate= LocalDate.now();
		String pattern= "dd/MM/yyyy";
		String date= DateUtils.formatDate(localDate, pattern);
		System.out.println(date);
	}
	
}
