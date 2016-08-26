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
		return add(Json.fromBoolean(value));
	}

	public JsonArray addString(String value) {
		return add(Json.fromString(value));
	}

	public JsonArray addInteger(int value) {
		return add(Json.fromInt(value));
	}

	public JsonArray addLong(long value) {
		return add(Json.fromLong(value));
	}

	public JsonArray addFloat(long value) {
		return add(Json.fromLong(value));
	}

	public JsonArray addDouble(double value) {
		return add(Json.fromDouble(value));
	}

	public JsonArray add(JsonValue value) {
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
		return set(index, Json.fromBoolean(value));
	}

	public JsonArray setString(int index, String value) {
		return set(index, Json.fromString(value));
	}

	public JsonArray setInteger(int index, int value) {
		return set(index, Json.fromInt(value));
	}

	public JsonArray setLong(int index, long value) {
		return set(index, Json.fromLong(value));
	}

	public JsonArray setFloat(int index, long value) {
		return set(index, Json.fromLong(value));
	}

	public JsonArray setDouble(int index, double value) {
		return set(index, Json.fromDouble(value));
	}

	public JsonArray set(int index, JsonValue value) {
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
		StringBuilder sb = new StringBuilder("[");

		Iterator<JsonValue> iterator = values.iterator();

		boolean first = true;
		while (iterator.hasNext()) {
			if (!first) sb.append(",");
			sb.append(iterator.next().toString());
			if (first) first = false;
		}

		//		final int length = values.size();
		//		for (int i = 0; i < length; i++) {
		//			sb.append(values.get(i).toString());
		//			if (i < length - 1) sb.append(",");
		//		}

		return sb.append("]").toString();
	}

	@Override
	public String toFormattedString() {
		return toFormattedString("  ");
	}

	@Override
	public String toFormattedString(final String indent) {
		StringBuilder sb = new StringBuilder("[");

		final int length = values.size();
		if (length == 0) return sb.append("]").toString();

		for (int i = 0; i < length; i++) {
			JsonValue value = values.get(i);
			if (value instanceof IFormattable) {
				for (String line : ((IFormattable) value).toFormattedString().split("\n")) {
					sb.append("\n" + indent + line);
				}
				sb.append(i != length - 1 ? "," : "");
			} else {
				sb.append("\n" + indent + value.toString() + (i != length - 1 ? ", " : ""));
			}
		}
		return sb.append("\n]").toString();
	}

	public static JsonArray from(String[] stringArray) {
		ArrayList<JsonValue> list = new ArrayList<>();//TODO: Find a better way to do this
		for (String element : stringArray) {
			list.add(Json.fromString(element));
		}
		return new JsonArray(list);
	}

}
