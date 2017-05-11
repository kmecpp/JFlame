package com.kmecpp.jflame.value;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kmecpp.jflame.Json;
import com.kmecpp.jflame.JsonValue;
import com.kmecpp.jflame.util.Converter;

public class JsonObject extends JsonValue implements Iterable<Entry<String, JsonValue>> {

	private ArrayList<String> keys;
	private ArrayList<JsonValue> values;

	public JsonObject() {
		this.keys = new ArrayList<String>();
		this.values = new ArrayList<JsonValue>();
	}

	public JsonObject(Map<String, JsonValue> map) {
		this.keys = new ArrayList<>(map.keySet());
		this.values = new ArrayList<JsonValue>(map.values());
	}

	@Override
	public Object get() {
		HashMap<String, JsonValue> map = new HashMap<>();
		for (int i = 0; i < keys.size(); i++) {
			map.put(keys.get(i), values.get(i));
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

	public Map<String, JsonValue> asMap() {
		HashMap<String, JsonValue> map = new HashMap<>();
		for (Entry<String, JsonValue> entry : this) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	public <V> Map<String, V> asMap(Converter<JsonValue, V> converter) {
		HashMap<String, V> map = new HashMap<>();
		for (Entry<String, JsonValue> entry : this) {
			map.put(entry.getKey(), converter.convert(entry.getValue()));
		}
		return map;
	}

	//MAP METHODS
	public boolean containskey(String key) {
		return keys.indexOf(key) > -1;
	}

	public boolean containsValue(JsonValue value) {
		return values.indexOf(value) > -1;
	}

	public int size() {
		return keys.size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public List<String> getKeys() {
		return Collections.unmodifiableList(keys);
	}

	public List<JsonValue> getValues() {
		return Collections.unmodifiableList(values);
	}

	//ADD
	public JsonObject add(String key, boolean value) {
		return add(key, Json.fromBoolean(value));
	}

	public JsonObject add(String key, String value) {
		return add(key, Json.fromString(value));
	}

	public JsonObject add(String key, int value) {
		return add(key, Json.fromInt(value));
	}

	public JsonObject add(String key, long value) {
		return add(key, Json.fromLong(value));
	}

	public JsonObject add(String key, float value) {
		return add(key, Json.fromFloat(value));
	}

	public JsonObject add(String key, double value) {
		return add(key, Json.fromDouble(value));
	}

	public JsonObject add(String key, JsonValue value) {
		keys.add(key);
		values.add(value);
		return this;
	}

	public JsonObject add(JsonString key, JsonValue value) {
		keys.add(key.asString());
		values.add(value);
		return this;
	}

	//REMOVE
	public void remove(String key) {
		remove(keys.indexOf(key));
	}

	public void remove(int index) {
		keys.remove(index);
		values.remove(index);
	}

	//GET
	public JsonBoolean getBoolean(String key) {
		return (JsonBoolean) get(key);
	}

	public JsonString getString(String key) {
		return (JsonString) get(key);
	}

	public int getInt(String key) {
		return ((JsonNumber) get(key)).asInt();
	}

	public long getLong(String key) {
		return ((JsonNumber) get(key)).asLong();
	}

	public float getFloat(String key) {
		return ((JsonNumber) get(key)).asFloat();
	}

	public double getDouble(String key) {
		return ((JsonNumber) get(key)).asDouble();
	}

	public JsonValue get(String key) {
		return values.get(keys.indexOf(key));
	}

	//SET
	public JsonObject setBoolean(String key, boolean value) {
		return set(key, Json.fromBoolean(value));
	}

	public JsonObject setString(String key, String value) {
		return set(key, Json.fromString(value));
	}

	public JsonObject setInt(String key, int value) {
		return set(key, Json.fromInt(value));
	}

	public JsonObject setLong(String key, long value) {
		return set(key, Json.fromLong(value));
	}

	public JsonObject setFloat(String key, float value) {
		return set(key, Json.fromFloat(value));
	}

	public JsonObject setDouble(String key, double value) {
		return set(key, Json.fromDouble(value));
	}

	public JsonObject set(String key, JsonValue value) {
		int index = keys.indexOf(key);
		if (index == -1) {
			throw new IllegalArgumentException("key does not exist!");
		}
		values.set(index, value);
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");

		final int length = keys.size();
		for (int i = 0; i < length; i++) {
			sb.append("\"" + keys.get(i) + "\":" + values.get(i));
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
			String key = keys.get(i);
			JsonValue value = values.get(i);
			if (value.isFormattable()) { //Objects or Arrays
				//TODO Performance issue
				String[] lines = value.toString(true).split("\n");
				sb.append("\n" + indent + "\"" + key + "\": " + lines[0]);
				for (int line = 1; line < lines.length; line++) {
					sb.append("\n" + indent + lines[line]);
				}
				sb.append(i != length - 1 ? "," : "");
			} else {
				sb.append("\n" + indent + "\"" + key + "\": " + value + (i != length - 1 ? ", " : ""));
			}
		}
		return sb.append("\n}").toString();
	}

	@Override
	public Iterator<Entry<String, JsonValue>> iterator() {
		return new Iterator<Entry<String, JsonValue>>() {

			private int index;

			@Override
			public boolean hasNext() {
				return index < JsonObject.this.size();
			}

			@Override
			public Entry<String, JsonValue> next() {
				return new AbstractMap.SimpleEntry<>(keys.get(index), values.get(index++)); //Increment is evaluated last
			}

			@Override
			public void remove() {
				JsonObject.this.remove(index);
			}
		};
	}

}
