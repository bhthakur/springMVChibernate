package com.journaldev.spring.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.journaldev.spring.dao.PersonDAO;
import com.journaldev.spring.model.Person;

@Service
public class PersonServiceImpl implements PersonService {
	
	private PersonDAO personDAO;

	/**
	 * Generic method to filter a list of T for a given predicate
	 * @param list
	 * @param predicate
	 * @return
	 */
	public static <T> List<T> filterByPredicate(List<T> list, Predicate<T> predicate ){
		/*List<T>  result= new ArrayList<T>();
		for(T e: list){
			if(predicate.test(e)){
				result.add(e);
			}
		}
		
		return result;*/
		
		return list.stream()
			.filter(predicate)
			.collect(Collectors.toList());
		
	}
	
	
	/**
	 * Generic method to find max in a range
	 * @param list
	 * @param begin
	 * @param end
	 * @return
	 */
	public static <T extends Comparable<? super T>> T maxByRange(List<T> list, int begin, int end ){
		
	/*	T max = list.get(begin);
		for(int index=begin; index <= end; index++){
			
			T t = list.get(index);
			if(t.compareTo(max) > 0){
				max= t;
			}
		}
		return max;*/
		
		 return list.subList(begin, end)
		  	.stream()
			.max(Comparator.<T> naturalOrder())
			.get();
			
		
	}
	
	/**
	 * Generic method to sort 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public static <T> List<T> sort(List<T> list, Comparator<T> comparator){
		
		return list.stream()
				   .sorted(comparator)
				   .collect(Collectors.toList());
	}
	
	
	public static <T extends Integer> T max(T x, T y) {
	      return x > y ? x : y;
    }
	
	public static <T> void print(List<T> list ){
		for(T e: list){
			System.out.println(e);
		}
	}
	
	public static <T> void printArr(T[] list ){
		for(T e: list){
			System.out.println(e);
		}
	}
	
	public <T> T[] exchange(T[] pArr, T p1, T p2){
		
		if( pArr.length== 0 || p1.equals(p2)){
			return pArr;
		}
		int p1Pos=0, p2Pos=0;
		
		/*for(int index=0; index < pArr.length; index++){
			if(pArr[index].equals(p1)){
				p1Pos= index;
			}
			if(pArr[index].equals(p2)){
				p2Pos= index;
			}
			
		}
		T temp= pArr[p1Pos];
		pArr[p1Pos]= pArr[p2Pos];
		pArr[p2Pos]= temp;*/
		
		
		List<T> list = Arrays.asList(pArr);
		int p1Index = list.indexOf(p1);
		int p2Index = list.indexOf(p2);
		list.set(p1Index, p2);
		list.set(p2Index, p1);
		
		return pArr;
	}
	
	 
	
	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public void addPerson(Person p) {
		this.personDAO.addPerson(p);
	}

	@Override
	@Transactional
	public void updatePerson(Person p) {
		this.personDAO.updatePerson(p);
	}

	@Override
	@Transactional
	public List<Person> listPersons() {
		return this.personDAO.listPersons();
	}

	@Override
	@Transactional
	public Person getPersonById(int id) {
		return this.personDAO.getPersonById(id);
	}

	@Override
	@Transactional
	public void removePerson(int id) {
		this.personDAO.removePerson(id);
	}

}
