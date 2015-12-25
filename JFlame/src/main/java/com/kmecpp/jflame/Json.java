package com.kmecpp.jflame;

import com.kmecpp.jflame.value.JsonBoolean;
import com.kmecpp.jflame.value.JsonNull;
import com.kmecpp.jflame.value.JsonNumber;
import com.kmecpp.jflame.value.JsonString;

public final class Json {

	//Literals
	public static final JsonNull NULL = JsonNull.getInstance();
	public static final JsonBoolean TRUE = new JsonBoolean(true);
	public static final JsonBoolean FALSE = new JsonBoolean(false);

	private Json() {
	}

	public static JsonValue parse(String json) throws InvalidJsonException {
		return new JsonParser(json).parse();
	}

	public static String generate(JsonValue json) {
		return json.isFormattable() ? ((IFormattable) json).getFormatted() : json.toString();
	}

	public static JsonString getValue(String value) {
		return new JsonString(value);
	}

	public static JsonBoolean getValue(boolean value) {
		return new JsonBoolean(value);
	}

	public static JsonNumber getValue(int value) {
		return new JsonNumber(Integer.toString(value, 10));
	}

	public static JsonNumber getValue(long value) {
		return new JsonNumber(Long.toString(value, 10));
	}

	public static JsonNumber getValue(float value) {
		return new JsonNumber(Float.toString(value));
	}

	public static JsonNumber getValue(double value) {
		return new JsonNumber(Double.toString(value));
	}
}