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
	public int hashCode() {
		return JsonNull.class.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj == this || obj instanceof JsonNull;
	}

	@Override
	public String toString() {
		return "null";
	}
}