package com.journaldev.spring.helper;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtils {


	public static List<LocalDate> getMondaysForMonth(int input) {
		List<LocalDate> list= new ArrayList<>();
		Month month = Month.of(input);
		LocalDate mondayDate = Year.now().atMonth(month).atDay(1).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
		while(month == mondayDate.getMonth()){
			list.add(mondayDate);
			mondayDate = mondayDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
		}
		return list;
	}

	public static String formatDate(LocalDate date, String pattern) {
		try{
			return date.format(DateTimeFormatter.ofPattern(pattern));	
		}catch(DateTimeException e){
			System.out.println("Exception parsing the date");
			throw new DateTimeException("Exception formating the date", e);
		}
		
	}

	public static LocalDate parseDate(String date, String pattern) {
		try{
			return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
		}catch(DateTimeParseException e){
			System.out.println("Exception parsing the date");
			throw new DateTimeParseException("Exception parsing the date", date, e.getErrorIndex(), e);
		}
	}

	public static ZoneId getStateTimeZone(State state) {
		return ZoneId.of(state.getTimeZone());
		
	}
	

	public static LocalDateTime getDateTimeByState(LocalDateTime lDt, State state) {
			ZoneId stateTimeZone = getStateTimeZone(state);
			ZoneId locaTimeZone = ZoneId.systemDefault();
			
			ZonedDateTime stateZoneTime = lDt.atZone(stateTimeZone);
			ZonedDateTime localZoneTime = lDt.atZone(locaTimeZone);
			
			ZoneOffset stateOffset = stateZoneTime.getOffset();
			ZoneOffset localOffset = localZoneTime.getOffset();
			
			
			return calculateLocalTimeByComparingOffset(lDt, stateOffset, localOffset);
		
	}

	private static LocalDateTime calculateLocalTimeByComparingOffset(LocalDateTime lDt, ZoneOffset stateOffset, ZoneOffset localOffset) {
		long seconds= 0;
		LocalDateTime dateTime = null;
		if(stateOffset.compareTo(localOffset) > 0){
			seconds= (localOffset.getTotalSeconds() - stateOffset.getTotalSeconds());
			dateTime =lDt.minusSeconds(seconds);
		}else{
			seconds=  localOffset.getTotalSeconds()- localOffset.getTotalSeconds();
			dateTime =lDt.plusSeconds(seconds);
		}
		return dateTime;
	}

	public static XMLGregorianCalendar getXmlDate(LocalDateTime localDate) throws DatatypeConfigurationException {
		DatatypeFactory factory = DatatypeFactory.newInstance();
		GregorianCalendar cal= new GregorianCalendar();
		cal.clear();
		long timeInMilliseconds = Timestamp.valueOf(localDate).getTime();
		cal.setTimeInMillis(timeInMilliseconds);
		return factory.newXMLGregorianCalendar(cal);
		
	}
	
	public static XMLGregorianCalendar getXmlDate2(LocalDateTime localDate, int timezone)
			throws DatatypeConfigurationException {
		DatatypeFactory factory = DatatypeFactory.newInstance();
		
		XMLGregorianCalendar newXMLGregorianCalendar = factory.newXMLGregorianCalendar(localDate.getYear(), localDate.getMonthValue(),
				localDate.getDayOfMonth(), localDate.getHour(), localDate.getMinute(), localDate.getSecond(), 0,
				timezone);
		//newXMLGregorianCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		return newXMLGregorianCalendar;
	}
	
	public LocalDate getToday(){
		return LocalDate.now();
	}
	
}
