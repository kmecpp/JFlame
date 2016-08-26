package com.kmecpp.jflame;

import static org.junit.Assert.*;

import org.junit.Test;

public class JsonTest {

	//VALUES
	private static final String STRING_TEST = "   \"  Test \"  ";
	private static final String INT_TEST = "    3 ";
	private static final String NUMBER_TEST = "   -3.2e-13 ";
	private static final String TRUE_TEST = "   true  ";
	private static final String FALSE_TEST = " false  ";
	private static final String NULL_TEST = "   null  ";

	//OBJECTS
	public static final String OBJECT_TEST_1 = " {  } ";
	public static final String OBJECT_TEST_2 = " { \" key \"  :  true } ";
	public static final String OBJECT_TEST_3 = " { \"name\": { \"name\": { \" key\": {\"subkey\": 3 } } } } ";

	public void run() {
		System.out.println("Ho");
	}

	@Test
	public void testValues() {
		parse(STRING_TEST);
		parse(INT_TEST);
		parse(NUMBER_TEST);
		assertEquals(Json.TRUE, parse(TRUE_TEST));
		assertEquals(Json.FALSE, parse(FALSE_TEST));
		assertEquals(Json.NULL, parse(NULL_TEST));
	}

	@Test
	public void testObjects() {
		parse(OBJECT_TEST_1);
		parse(OBJECT_TEST_2);
		parse(OBJECT_TEST_3);
	}

	private static JsonValue parse(String json) {
		return Json.parse(json);
	}

}
