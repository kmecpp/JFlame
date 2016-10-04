package com.kmecpp.jflame.value;

import com.kmecpp.jflame.Json;
import com.kmecpp.jflame.JsonValue;

public final class JsonNull extends JsonValue {

	private static final JsonNull INSTANCE = new JsonNull();

	private JsonNull() {
	}

	public static JsonNull getInstance() {
		return INSTANCE;
	}

	@Override
	public Object get() {
		return getInstance();
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
