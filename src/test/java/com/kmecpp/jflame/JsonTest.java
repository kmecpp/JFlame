package com.kmecpp.jflame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.kmecpp.jflame.value.JsonArray;

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
		assertTrue(Json.FALSE == parse(FALSE_TEST));
		assertTrue(Json.NULL == parse(NULL_TEST));
	}

	@Test
	public void testObjects() {
		parse(OBJECT_TEST_1);
		parse(OBJECT_TEST_2);
		parse(OBJECT_TEST_3);
	}

	@Test
	public void testPerformance() {
		String path = "src/test/java/users.json"; //getClass().getResource("/users.json");
		try {
			//			System.out.println(new JsonArray(new JsonNumber[] { new JsonNumber(1), new JsonNumber(2), new JsonNumber(3) }).getFormatted());
			//			Json.parse(JsonIO.read(url)).asArray().toString(true);
			//			Json.parse(JsonIO.read(url)).asArray().toString(false);

			long start = System.currentTimeMillis();
			JsonArray array = Json.parseFile(path).asArray();
			System.out.println("users.json Parse Time: " + (System.currentTimeMillis() - start) + "ms");

			start = System.currentTimeMillis();
			array.toString(true);
			System.out.println("users.json Generation Time (Formatted): " + (System.currentTimeMillis() - start) + "ms");

			start = System.currentTimeMillis();
			array.toString(false);
			System.out.println("users.json Generation Time (Compressed): " + (System.currentTimeMillis() - start) + "ms");
		} catch (InvalidJsonException | IOException e) {
			e.printStackTrace();
		}
	}

	//	private static String generate(JsonValue value) {
	//		return generate(value, true);
	//	}
	//
	//	private static String generate(JsonValue value, boolean format) {
	//		return format ? value.getFormatted() : value.toString();
	//	}

	private static JsonValue parse(String json) {
		return Json.parse(json);
	}

}
