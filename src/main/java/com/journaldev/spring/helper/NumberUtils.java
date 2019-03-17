package com.journaldev.spring.helper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumberUtils {

	public static boolean isPrime(int num) {
		return IntStream.range(2, num)
				 		.noneMatch(e -> num % e == 0);

	}

	public static List<Double> sqrt100Prime() {
		return Stream.iterate(1, e -> e + 1)
				     .filter(NumberUtils::isPrime)
				     .map(Math::sqrt)
				     .limit(100)
				     .collect(Collectors.toList());
	}
}
