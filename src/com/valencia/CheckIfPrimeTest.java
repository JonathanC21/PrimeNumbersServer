package com.valencia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CheckIfPrimeTest {

	@Test
	void test() {
		
		assertEquals(true,Server.checkIfPrime(7919));
	}

}
