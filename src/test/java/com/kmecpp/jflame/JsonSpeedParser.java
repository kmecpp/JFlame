package com.kmecpp.jflame;

import java.lang.reflect.Field;

import com.kmecpp.jflame.value.JsonArray;
import com.kmecpp.jflame.value.JsonNumber;
import com.kmecpp.jflame.value.JsonObject;
import com.kmecpp.jflame.value.JsonString;

public class JsonSpeedParser {

	private final char[] characters;
	private final int length;

	private int index = 0;
	private int line = 1;
	private int column = 1;

	public JsonSpeedParser(String json) { // 1µs
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
		JsonValue value = readValue();

		index = length; //TODO REMOVE
		while (index < length) {
			if (!isWhitespace(characters[index])) {
				throw invalidJson("Unexpected character");
			}
			index++;
		}

		return value;
	}

	private JsonValue readValue() {
		skipWhitespace();
		//READ OBJECT
		if (characters[index] == '{') {
			JsonObject object = new JsonObject();

			int i = index + 1;
			while (!isWhitespace(characters[i])) {
				i++;
			}
			if (characters[i] == '}') {
				next();
				return object;
			}

			do {
				next();
				skipWhitespace();
				object.add(readString(), readNextValue());
				skipWhitespace();
			} while (characters[index] == ',');
			skipWhitespace();
			if (characters[index] != '}') throw expected("END_OBJECT");

			next();
			return object;
		}
		//READ ARRAY
		else if (characters[index] == '[') {
			JsonArray array = new JsonArray();

			int i = index + 1;
			while (!isWhitespace(characters[i])) {
				i++;
			}
			if (characters[i] == ']') {
				next();
				return array;
			}

			do {
				next();
				skipWhitespace();
				array.addValue(readValue());
				skipWhitespace(); //After value
			} while (characters[index] == ',');//This moves cursor passed end-array on last iteration
			skipWhitespace();
			if (characters[index] != ']') throw expected("END_ARRAY");

			if (hasNext()) next();
			return array;
		}
		//READ STRING
		else if (characters[index] == '"') {
			return readString();
		}
		//READ TRUE
		else if (characters[index] == 't') {
			if (nextChar() == 'r' && nextChar() == 'u' && nextChar() == 'e') {
				next();
				return Json.TRUE;
			} else {
				throw expected("LITERAL_TRUE");
			}
		}
		//READ FALSE
		else if (characters[index] == 'f') {
			if (nextChar() == 'a' && nextChar() == 'l' && nextChar() == 's' && nextChar() == 'e') {
				next();
				return Json.FALSE;
			} else {
				throw expected("LITERAL_FALSE");
			}
		}
		//READ NULL
		else if (characters[index] == 'n') {
			if (nextChar() == 'u' && nextChar() == 'l' && nextChar() == 'l') {
				next();
				return Json.FALSE;
			} else {
				throw expected("LITERAL_NULL");
			}
		}
		//READ NUMBER
		else if (characters[index] == '-' || isDigit(characters[index])) {
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
				while (isDigit(characters[index])) {
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

				while (isDigit(characters[index])) {
					sb.append(characters[index]);
					next();
				}

			}
			//Read fraction
			else if (characters[index] == '.') {
				sb.append(characters[index]);
				next();

				while (isDigit(characters[index])) {
					sb.append(characters[index]);
					next();
				}
			}
			return new JsonNumber(sb.toString());
		}
		//UNEXPECTED CHARACTER
		else {
			throw invalidJson("Unexpected character '" + characters[index] + "'");
		}
	}

	private JsonValue readNextValue() {
		skipWhitespace();
		next();
		return readValue();
	}

	private final JsonString readString() {
		StringBuilder sb = new StringBuilder();
		next();
		while (characters[index] != '"') {
			//TODO INVALID CHARACTER CHECK
			sb.append(characters[index]);
			next();
		}
		next();
		return new JsonString(sb.toString());
	}

	private final void next() {
		index++;
		column++;
		if (characters[index] == '\n') {
			line++;
			column = 1;
		}
	}

	//	private final char readChar() {
	//		if (index < length - 1) next();
	//		return characters[index - 1];
	//	}

	private final char nextChar() {
		next();
		return characters[index];
	}

	private final void skipWhitespace() {
		while (hasNext() && isWhitespace(characters[index])) {
			if (!isWhitespace(characters[index])) {
				break;
			}
			next();
		}
	}

	private final boolean hasNext() {
		return index < length - 1;
	}

	private final boolean isWhitespace(final char c) {
		return c == ' ' || c == '\n' || c == '\t' || c == '\r';
	}

	private final boolean isDigit(final char c) {
		return c >= '0' && c <= '9';
	}

	//Exceptions
	private InvalidJsonException expected(String expected) {
		return invalidJson("Expected " + expected + " found '" + characters[index] + "'");
	}

	private InvalidJsonException invalidJson(String message) {
		return new InvalidJsonException(message, line, column);
	}
}