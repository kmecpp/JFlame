package com.kmecpp.jflame.value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kmecpp.jflame.Json;
import com.kmecpp.jflame.JsonValue;

public class JsonObject extends JsonValue {

	private ArrayList<String> names;
	private ArrayList<JsonValue> values;

	public JsonObject() {
		this.names = new ArrayList<String>();
		this.values = new ArrayList<JsonValue>();
	}

	public JsonObject(Map<String, JsonValue> map) {
		this.names = new ArrayList<>(map.keySet());
		this.values = new ArrayList<JsonValue>(map.values());
	}

	@Override
	public Object get() {
		HashMap<String, JsonValue> map = new HashMap<>();
		for (int i = 0; i < names.size(); i++) {
			map.put(names.get(i), values.get(i));
		}
		return map;
	}

	@Override
	public boolean isObject() {
		return true;
	}

	@Override
	public JsonObject asObject() {
		return this;
	}

	//MAP METHODS
	public boolean containsName(String name) {
		return names.indexOf(name) > -1;
	}

	public boolean containsValue(JsonValue value) {
		return values.indexOf(value) > -1;
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

	//ADD
	public JsonObject add(String name, boolean value) {
		return add(name, Json.fromBoolean(value));
	}

	public JsonObject add(String name, String value) {
		return add(name, Json.fromString(value));
	}

	public JsonObject add(String name, int value) {
		return add(name, Json.fromInt(value));
	}

	public JsonObject add(String name, long value) {
		return add(name, Json.fromLong(value));
	}

	public JsonObject add(String name, float value) {
		return add(name, Json.fromFloat(value));
	}

	public JsonObject add(String name, double value) {
		return add(name, Json.fromDouble(value));
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
		return (JsonBoolean) get(name);
	}

	public JsonString getString(String name) {
		return (JsonString) get(name);
	}

	public int getInt(String name) {
		return ((JsonNumber) get(name)).asInt();
	}

	public long getLong(String name) {
		return ((JsonNumber) get(name)).asLong();
	}

	public float getFloat(String name) {
		return ((JsonNumber) get(name)).asFloat();
	}

	public double getDouble(String name) {
		return ((JsonNumber) get(name)).asDouble();
	}

	public JsonValue get(String name) {
		return values.get(names.indexOf(name));
	}

	//SET
	public JsonObject setBoolean(String name, boolean value) {
		return set(name, Json.fromBoolean(value));
	}

	public JsonObject setString(String name, String value) {
		return set(name, Json.fromString(value));
	}

	public JsonObject setInt(String name, int value) {
		return set(name, Json.fromInt(value));
	}

	public JsonObject setLong(String name, long value) {
		return set(name, Json.fromLong(value));
	}

	public JsonObject setFloat(String name, float value) {
		return set(name, Json.fromFloat(value));
	}

	public JsonObject setDouble(String name, double value) {
		return set(name, Json.fromDouble(value));
	}

	public JsonObject set(String name, JsonValue value) {
		int index = names.indexOf(name);
		if (index == -1) {
			throw new IllegalArgumentException("Name does not exist!");
		}
		values.set(index, value);
		return this;
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
	public boolean isFormattable() {
		return true;
	}

	@Override
	public String getFormatted(String indent) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");

		final int length = values.size();
		if (length == 0) return sb.append("}").toString();

		for (int i = 0; i < length; i++) {
			String name = names.get(i);
			JsonValue value = values.get(i);
			if (value.isFormattable()) { //Objects or Arrays
				//TODO Performance issue
				String[] lines = value.toString(true).split("\n");
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
