package com.kmecpp.jflame;

import java.lang.reflect.Field;

import com.kmecpp.jflame.value.JsonArray;
import com.kmecpp.jflame.value.JsonBoolean;
import com.kmecpp.jflame.value.JsonNull;
import com.kmecpp.jflame.value.JsonNumber;
import com.kmecpp.jflame.value.JsonObject;
import com.kmecpp.jflame.value.JsonString;

public final class JsonParser {

	private final char[] characters;
	private final int length;

	private int index = 0;
	private int line = 1;
	private int column = 1;

	public JsonParser(String json) {
		try {
			Field field = String.class.getDeclaredField("value");//Faster than .charAt() for longer Strings
			field.setAccessible(true);
			this.characters = (char[]) field.get(json);
			this.length = characters.length;

			if (length == 0) throw invalidJson("Empty Json");
		} catch (IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new Error("Error caught reading String class data");
		}
	}

	public JsonValue parse() {
		JsonValue result = readValue();

		//Verify no remaining text
		skipWhitespace();
		if (hasNext()) throw expected("END_OF_FILE");

		return result;
	}

	private JsonValue readValue() throws InvalidJsonException {
		skipWhitespace();

		if (characters[index] == '{') return readObject();
		else if (characters[index] == '[') return readArray();
		else if (characters[index] == '"') return readString();
		else if (characters[index] == 't') return readTrue();
		else if (characters[index] == 'f') return readFalse();
		else if (characters[index] == 'n') return readNull();
		else if (characters[index] == '-' || (characters[index] >= '0' && characters[index] <= '9')) return readNumber();
		else throw invalidJson("Unrecognized value '" + characters[index] + "'");
	}

	private JsonObject readObject() {
		JsonObject object = new JsonObject();

		next(); //Skip indicator (need to in order to skip whitespace)
		skipWhitespace();
		if (characters[index] == '}') {
			next();
			return object;
		}

		do {
			skipWhitespace();//Before name
			final String name = readString().asString();
			skipWhitespace();//After name
			if (!readChar(':')) throw expected("NAME_SEPARATOR");

			object.add(name, readValue());//readValue() skips whitespace

			skipWhitespace(); //After value
		} while (readChar(','));//This moves cursor passed end-object on last iteration
		if (characters[index - 1] != '}') throw expected("END_OBJECT");

		return object;
	}

	private JsonArray readArray() {
		JsonArray array = new JsonArray();

		next(); //Skip indicator (need to in order to skip whitespace)
		skipWhitespace();
		if (hasNext() && characters[index] == ']') {
			next();
			return array;
		}

		do {
			skipWhitespace();
			array.addValue(readValue());
			skipWhitespace(); //After value
		} while (readChar(','));//This moves cursor passed end-array on last iteration
		if (characters[index - 1] != ']') throw expected("END_ARRAY");

		return array;
	}

	private JsonNull readNull() {
		next();
		if (readChar('u') && readChar('l') && readChar('l')) return Json.NULL;
		throw expected("LITERAL_NULL");
	}

	private JsonBoolean readTrue() {
		next();
		if (readChar('r') && readChar('u') && readChar('e')) return Json.TRUE;
		throw expected("LITERAL_TRUE");
	}

	private JsonBoolean readFalse() {
		next();
		if (readChar('a') && readChar('l') && readChar('s') && readChar('e')) return Json.FALSE;
		throw expected("LITERAL_FALSE");
	}

	private JsonString readString() {
		StringBuilder sb = new StringBuilder();
		next();
		while (characters[index] != '"') {
			//TODO CHARACTER CHECK
			sb.append(characters[index]);
			next();
		}
		if (hasNext()) next();
		return new JsonString(sb.toString());

		//		next(); //Skip indicator
		//
		//		StringBuilder sb = new StringBuilder();
		//		while (characters[index] != '"') {
		//			if (characters[index] == '\\') {
		//				next();
		//				switch (characters[index]) {
		//				case '"':
		//				case '/':
		//				case '\\':
		//					sb.append(characters[index]);
		//					break;
		//				case 'b':
		//					sb.append('\b');
		//					break;
		//				case 'f':
		//					sb.append('\f');
		//					break;
		//				case 'n':
		//					sb.append('\n');
		//					break;
		//				case 'r':
		//					sb.append('\r');
		//					break;
		//				case 't':
		//					sb.append('\t');
		//					break;
		//				case 'u':
		//					//SIMILAR PERFORMANCE BUT OTHER IS MORE CONCISE
		//					//					char[] hexChars = new char[4];
		//					//					for (int i = 0; i < 4; i++) {
		//					//						next();
		//					//						hexChars[i] = characters[index];
		//					//					}
		//					//					try {
		//					//						sb.append((char) Integer.parseInt(new String(hexChars), 16));
		//					//					} catch (NumberFormatException e) {
		//					//						throw invalidJson("Invalid hexidecimal digits");
		//					//					}
		//					try {
		//						next(); //Move to first digit
		//						sb.append((char) Integer.parseInt(substring(index, index + 4), 16));
		//						next();
		//						next();
		//						next();
		//					} catch (NumberFormatException e) {
		//						throw invalidJson("Invalid hexidecimal digits");
		//					}
		//					break;
		//				default:
		//					throw invalidJson("Invalid escape sequence");
		//				}
		//			} else if (characters[index] < 0x20) { //Space
		//				throw invalidJson("Unescaped character");
		//			} else {
		//				sb.append(characters[index]);
		//			}
		//			next();
		//		}
		//		if (hasNext()) next();//Move cursor passed closing quotation
		//		return new JsonString(sb.toString());
	}

	private JsonNumber readNumber() {
		StringBuilder sb = new StringBuilder();
		if (characters[index] == '-') {
			sb.append('-');
			next();
		}
		//Read integer part
		if (characters[index] == '0') {
			sb.append(characters[index]);
			next();
		} else {
			while (isDigit()) {
				sb.append(characters[index]);
				next();
			}
		}

		//Read exponent
		if (characters[index] == 'e' || characters[index] == 'E') {
			sb.append(characters[index]);
			next();

			if (characters[index] == '+' || characters[index] == '-') {
				sb.append(characters[index]);
				next();
			}

			while (isDigit()) {
				sb.append(characters[index]);
				next();
			}

		}
		//Read fraction
		else if (characters[index] == '.') {
			sb.append(characters[index]);
			next();

			while (isDigit()) {
				sb.append(characters[index]);
				next();
			}
		}
		return new JsonNumber(sb.toString());

	}

	//UTILITY METHODS

	/**
	 * Reads the current character in the character array and tests whether or not it is equal to the given character, and then increments the index if the character was read successfully
	 * 
	 * @param c
	 *            the char to test
	 * @return true if the current character is equal to c and false if it's not
	 */
	private boolean readChar(final char c) {
		next();
		return characters[index - 1] == c;
	}

	/**
	 * Moves the cursor to the next position, increasing both the index, column or line as necessary
	 * 
	 * @throws InvalidJsonException
	 *             if the cursor is already end of the file, this allows a one character buffer at index == length allowing the cursor to be read passed the last character all the way to the end
	 */
	private void next() {
		index++;
		column++;
		if (characters[index - 1] == '\n') {
			line++;
			column = 1;
		}
		if (index > length) throw invalidJson("Reached end of text"); //One character buffer at index == length
	}

	/**
	 * @return true if there are more characters that can be read and false if there are not
	 */
	private final boolean hasNext() {
		return index < length - 1;
	}

	/**
	 * Reads the current character in the character array and tests whether or not it is equal to the given character without moving the cursor
	 * 
	 * @return true if the current character is a digit and false if it's not
	 */
	private boolean isDigit() {
		return characters[index] >= '0' && characters[index] <= '9';
	}

	/**
	 * @param c
	 *            the character to test
	 * @return true if the specified character is whitespace and false if it's not
	 */
	private boolean isWhitespace(final char c) {
		return c == ' ' || c == '\n' || c == '\t' || c == '\r';
	}

	/**
	 * Moves the cursor to the next non-whitespace character
	 */
	private void skipWhitespace() {
		while (hasNext() && isWhitespace(characters[index]))
			next();
	}

	//	/**
	//	 * @param from
	//	 *            the initial index (inclusive)
	//	 * @param to
	//	 *            the end index (exclusive)
	//	 * @return a substring of the given json text
	//	 */
	//	private String substring(final int from, final int to) {
	//		int size = to - from;
	//		char[] chars = new char[size];
	//		System.arraycopy(characters, from, chars, 0, size);
	//		return new String(chars);
	//	}

	//Exceptions
	private InvalidJsonException expected(String expected) {
		return invalidJson("Expected " + expected + " found '" + characters[index] + "'");
	}

	private InvalidJsonException invalidJson(String message) {
		return new InvalidJsonException(message, line, column);
	}
}