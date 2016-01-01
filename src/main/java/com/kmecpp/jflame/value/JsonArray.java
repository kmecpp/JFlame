package com.kmecpp.jflame.value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kmecpp.jflame.IFormattable;
import com.kmecpp.jflame.Json;
import com.kmecpp.jflame.JsonValue;

public class JsonArray extends JsonValue implements IFormattable, Iterable<JsonValue> {

	private List<JsonValue> values;

	public JsonArray() {
		this(new ArrayList<JsonValue>());
	}

	public JsonArray(ArrayList<JsonValue> values) {
		this.values = values;
	}

	@Override
	public boolean isArray() {
		return true;
	}

	@Override
	public JsonArray asArray() {
		return this;
	}

	//ADD
	public JsonArray addBoolean(boolean value) {
		return addValue(Json.getValue(value));
	}

	public JsonArray addString(String value) {
		return addValue(Json.getValue(value));
	}

	public JsonArray addInteger(int value) {
		return addValue(Json.getValue(value));
	}

	public JsonArray addLong(long value) {
		return addValue(Json.getValue(value));
	}

	public JsonArray addFloat(long value) {
		return addValue(Json.getValue(value));
	}

	public JsonArray addDouble(double value) {
		return addValue(Json.getValue(value));
	}

	public JsonArray addValue(JsonValue value) {
		values.add(value);
		return this;
	}

	//REMOVE
	public JsonArray remove(int index) {
		values.remove(index);
		return this;
	}

	//GET
	public JsonValue get(int index) {
		return values.get(index);
	}

	//SET
	public JsonArray setBoolean(int index, boolean value) {
		return setValue(index, Json.getValue(value));
	}

	public JsonArray setString(int index, String value) {
		return setValue(index, Json.getValue(value));
	}

	public JsonArray setInteger(int index, int value) {
		return setValue(index, Json.getValue(value));
	}

	public JsonArray setLong(int index, long value) {
		return setValue(index, Json.getValue(value));
	}

	public JsonArray setFloat(int index, long value) {
		return setValue(index, Json.getValue(value));
	}

	public JsonArray setDouble(int index, double value) {
		return setValue(index, Json.getValue(value));
	}

	public JsonArray setValue(int index, JsonValue value) {
		values.set(index, value);
		return this;
	}

	@Override
	public Iterator<JsonValue> iterator() {
		return values.iterator();
	}

	public int size() {
		return values.size();
	}

	public List<JsonValue> getValues() {
		return values;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		final int length = values.size();
		for (int i = 0; i < length; i++) {
			sb.append(values.get(i).toString());
			if (i < length - 1) sb.append(",");
		}

		return sb.append("]").toString();
	}

	@Override
	public String getFormatted() {
		return getFormatted("  ");
	}

	@Override
	public String getFormatted(final String indent) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		final int length = values.size();
		if (length == 0) return sb.append("]").toString();

		for (int i = 0; i < length; i++) {
			JsonValue value = values.get(i);
			if (value instanceof IFormattable) {
				for (String line : ((IFormattable) value).getFormatted().split("\n")) {
					sb.append("\n" + indent + line);
				}
				sb.append(i != length - 1 ? "," : "");
			} else {
				sb.append("\n" + indent + value.toString() + (i != length - 1 ? ", " : ""));
			}
		}
		return sb.append("\n]").toString();
	}
}