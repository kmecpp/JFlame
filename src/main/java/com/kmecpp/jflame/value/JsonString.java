package com.kmecpp.jflame.value;

import com.kmecpp.jflame.JsonValue;

public class JsonString extends JsonValue {

	private final String value;

	public JsonString(String value) {
		this.value = value;
	}

	@Override
	public String get() {
		return value;
	}

	@Override
	public boolean isString() {
		return true;
	}

	@Override
	public String asString() {
		return value;
	}

	@Override
	public String toString() {
		return "\"" + value + "\"";
	}
}
