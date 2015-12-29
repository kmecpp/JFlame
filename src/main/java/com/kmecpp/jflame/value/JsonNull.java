package com.kmecpp.jflame.value;

import com.kmecpp.jflame.Json;
import com.kmecpp.jflame.JsonValue;

public class JsonNull extends JsonValue {

	private static JsonNull value = new JsonNull();

	private JsonNull() {
	}

	public static JsonNull getInstance() {
		return value;
	}

	@Override
	public boolean isNull() {
		return true;
	}

	@Override
	public JsonNull asNull() {
		return Json.NULL;
	}

	@Override
	public String toString() {
		return "null";
	}
}