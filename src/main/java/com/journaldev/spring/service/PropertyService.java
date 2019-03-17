package com.journaldev.spring.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropertyService {

	private String fileName;
	private Properties properties= new Properties();

	public PropertyService(String fileName) {
		this.fileName= fileName;
		try {
			//ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
			
			this.properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getProperty(String key){
		return this.properties.getProperty(key);
	}

	public void saveProperties(Properties prop){
		
	}

	/**
	 * Java 8  br.lines give stream. 
	 * mapToInt gives stream of int
	 * max gives max number inside Optional
	 * getAsInt gives the result
	 * 
	 * @return
	 */
	public int getMaxLength() {
		BufferedReader br = getReader();
		int result= br.lines()
		.mapToInt(String::length)
		.max()
		.getAsInt();
		
				
		return result;
	}

	
	/**
	 * Reduce method takes accumulator that takes two parameters of same type and return one of same type
	 * l1 is the accumulator that accumulates the return type.
	 * 
	 * @return
	 */
	public String getMaxLengthLine() {
		BufferedReader br = getReader();
/*		String result = br.lines()
		.reduce((l1, l2) -> {
			if(l1.length() > l2.length()){
				return l1;
			}
			return l2;
		})
		.get();
*/	
		String result = br.lines()
				 		  .max(Comparator.comparingInt(line -> line.length()))
				 		  .orElse("");

		
		return result;
	}
	
	/**
	 * sort lines obj using comparator
	 * @return
	 */
	public List<String> sortedLines(){
		BufferedReader br = getReader();
		
		 List<String> result = br.lines()
				 .sorted((l1, l2) -> l2.length() - l1.length())
				 .collect(Collectors.toList())
				 ;
		
		return result;
	}
	
	/**
	 * sort lines obj using comparator
	 * @return
	 */
	public List<String> sortedLinesUsingComparator(){
		BufferedReader br = getReader();
		
		 List<String> result = br.lines()
				 .sorted(Comparator.comparingInt(String::length).reversed())
				 .collect(Collectors.toList())
				 ;
		
		return result;
	}
	/**
	 * Not efficient but another way to get the max len line using sort
	 * @return
	 */
	public String sortedMaxLengthLine(){
		BufferedReader br = getReader();
		String result = br.lines()
						  .sorted((l1, l2) -> l2.length() - l1.length())
						  .findFirst().get();
		return result;
	}
	
	
	/**
	 * Recursion using java7 Memory issues
	 * However can traverse items without keeping track of state
	 * 
	 * 
	 * @param longest with default as ""
	 * @return
	 * @throws IOException 
	 */
	
	public String findMaxLenLine(BufferedReader br, String longest) throws IOException{
		String line= null;
		line = br.readLine();
		if(line == null){
			return longest;
		}
		if(line.length() > longest.length()){
			longest= line;
		}
		
		return findMaxLenLine(br, longest);
	}
	
	
	public BufferedReader getReader() {
		InputStreamReader in = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(this.fileName));
		BufferedReader br = new BufferedReader(in);
		return br;
	}
	
}
