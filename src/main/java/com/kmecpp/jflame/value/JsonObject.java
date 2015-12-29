package com.kmecpp.jflame.value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kmecpp.jflame.IFormattable;
import com.kmecpp.jflame.Json;
import com.kmecpp.jflame.JsonValue;

public class JsonObject extends JsonValue implements IFormattable {

	private ArrayList<String> names;
	private ArrayList<JsonValue> values;

	public JsonObject() {
		names = new ArrayList<String>();
		values = new ArrayList<JsonValue>();
	}

	@Override
	public boolean isObject() {
		return true;
	}

	@Override
	public JsonObject asObject() {
		return this;
	}

	//impl. add, set, remove, get

	//ADD
	public JsonObject add(String name, boolean value) {
		return add(name, Json.getValue(value));
	}

	public JsonObject add(String name, String value) {
		return add(name, Json.getValue(value));
	}

	public JsonObject add(String name, int value) {
		return add(name, Json.getValue(value));
	}

	public JsonObject add(String name, long value) {
		return add(name, Json.getValue(value));
	}

	public JsonObject add(String name, float value) {
		return add(name, Json.getValue(value));
	}

	public JsonObject add(String name, double value) {
		return add(name, Json.getValue(value));
	}

	public JsonObject add(String name, JsonValue value) {
		names.add(name);
		values.add(value);
		return this;
	}

	public JsonObject add(JsonString name, JsonValue value) {
		names.add(name.asString());
		values.add(value);
		return this;
	}

	//REMOVE
	public void remove(String name) {
		int index = names.indexOf(name);
		names.remove(index);
		values.remove(index);
	}

	//GET
	public JsonBoolean getBoolean(String name) {
		return (JsonBoolean) getValue(name);
	}

	public JsonString getString(String name) {
		return (JsonString) getValue(name);
	}

	public int getInt(String name) {
		return ((JsonNumber) getValue(name)).asInt();
	}

	public long getLong(String name) {
		return ((JsonNumber) getValue(name)).asLong();
	}

	public float getFloat(String name) {
		return ((JsonNumber) getValue(name)).asFloat();
	}

	public double getDouble(String name) {
		return ((JsonNumber) getValue(name)).asDouble();
	}

	public JsonValue getValue(String name) {
		return values.get(names.indexOf(name));
	}

	//SET
	public JsonObject setBoolean(String name, boolean value) {
		return setValue(name, Json.getValue(value));
	}

	public JsonObject setString(String name, String value) {
		return setValue(name, Json.getValue(value));
	}

	public JsonObject setInt(String name, int value) {
		return setValue(name, Json.getValue(value));
	}

	public JsonObject setLong(String name, long value) {
		return setValue(name, Json.getValue(value));
	}

	public JsonObject setFloat(String name, float value) {
		return setValue(name, Json.getValue(value));
	}

	public JsonObject setDouble(String name, double value) {
		return setValue(name, Json.getValue(value));
	}

	public JsonObject setValue(String name, JsonValue value) {
		values.set(names.indexOf(name), value);
		return this;
	}

	//MAP METHODS
	public boolean containsName(String name) {
		return name.indexOf(name) != -1;
	}

	public boolean containsValue(JsonValue value) {
		return values.indexOf(value) != -1;
	}

	public int size() {
		return names.size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public List<String> getNames() {
		return Collections.unmodifiableList(names);
	}

	public List<JsonValue> getValues() {
		return Collections.unmodifiableList(values);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");

		final int length = names.size();
		for (int i = 0; i < length; i++) {
			sb.append("\"" + names.get(i) + "\":" + values.get(i));
			if (i < length - 1) sb.append(",");
		}
		return sb.append("}").toString();
	}

	@Override
	public String getFormatted() {
		return getFormatted("  ");
	}

	@Override
	public String getFormatted(final String indent) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");

		final int length = values.size();
		if (length == 0) return sb.append("}").toString();

		for (int i = 0; i < length; i++) {
			String name = names.get(i);
			JsonValue value = values.get(i);
			if (value instanceof IFormattable) { //Objects or Arrays
				IFormattable formattable = (IFormattable) value;
				String[] lines = formattable.getFormatted().split("\n");
				sb.append("\n" + indent + "\"" + name + "\": " + lines[0]);
				for (int line = 1; line < lines.length; line++) {
					sb.append("\n" + indent + lines[line]);
				}
				sb.append(i != length - 1 ? "," : "");
			} else {
				sb.append("\n" + indent + "\"" + name + "\": " + value + (i != length - 1 ? ", " : ""));
			}
		}
		return sb.append("\n}").toString();
	}
}
