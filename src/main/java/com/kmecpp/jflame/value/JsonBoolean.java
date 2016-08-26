package com.kmecpp.jflame.value;

import com.kmecpp.jflame.JsonValue;

public class JsonBoolean extends JsonValue {

	private boolean value;

	public JsonBoolean(boolean value) {
		this.value = value;
	}

	@Override
	public boolean isTrue() {
		return value;
	}

	@Override
	public boolean isFalse() {
		return !value;
	}

	@Override
	public boolean asBoolean() {
		return value;
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}
}
