package com.kmecpp.jflame;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import com.kmecpp.jflame.util.JsonIO;
import com.kmecpp.jflame.value.JsonArray;
import com.kmecpp.jflame.value.JsonBoolean;
import com.kmecpp.jflame.value.JsonNull;
import com.kmecpp.jflame.value.JsonNumber;
import com.kmecpp.jflame.value.JsonObject;
import com.kmecpp.jflame.value.JsonString;

public final class Json {

	//Literals
	public static final JsonNull NULL = JsonNull.getInstance();
	public static final JsonBoolean TRUE = new JsonBoolean(true);
	public static final JsonBoolean FALSE = new JsonBoolean(false);

	private Json() {
	}

	/**
	 * Parses the given JSON string and returns a {@link JsonValue} that
	 * represents the data
	 * 
	 * @param json
	 *            the JSON string to parse
	 * @return a {@link JsonValue} representing the given string
	 * @throws InvalidJsonException
	 *             if the string is not valid JSON
	 */
	public static JsonValue parse(String json) throws InvalidJsonException {
		return new JsonParser(json).parse();
	}

	/**
	 * Generates a formatted string from a {@link JsonValue}. To generate a
	 * compressed string, call jsonValue.toString()
	 * 
	 * @param json
	 *            the json object to serialize
	 * @return a formatted String representation of the JSON object
	 */
	public static String generate(JsonValue json) {
		return json.getFormatted();
	}

	/**
	 * Reads a {@link JsonValue} from the file at the given path
	 * 
	 * @param path
	 *            the path of the file to read from
	 * @return a {@link JsonValue} representing the text of the contents of the
	 *         file
	 * @throws IOException
	 *             if an IOException occurs
	 */
	public static JsonValue parseFile(String path) throws IOException {
		return parseFile(new File(path));
	}

	/**
	 * Reads a {@link JsonValue} from the given File
	 * 
	 * @param file
	 *            the File to read from
	 * @return a {@link JsonValue} representing the text of the contents of the
	 *         given file
	 * @throws IOException
	 *             if an IOException occurs
	 */
	public static JsonValue parseFile(File file) throws IOException {
		return parse(JsonIO.read(new FileInputStream(file)));
	}

	/**
	 * Reads a {@link JsonValue} from the given URL
	 * 
	 * @param url
	 *            the URL to read from
	 * @return a {@link JsonValue} representing the text retrieved from the URL
	 * @throws IOException
	 *             if an IOException occurs
	 */
	public static JsonValue parseUrl(URL url) throws IOException {
		return parse(JsonIO.read(url));
	}

	/**
	 * Writes the given {@link JsonValue} to the URL
	 * 
	 * @param url
	 *            the URL to write to
	 * @param json
	 *            the {@link JsonValue} to write
	 * @throws IOException
	 *             if an IOException occurs
	 */
	public static void write(URL url, JsonValue json) throws IOException {
		JsonIO.postUrl(url, json);
	}

	/**
	 * Writes the given {@link JsonValue} to the file
	 * 
	 * @param file
	 *            the file to write to
	 * @param json
	 *            the {@link JsonValue} to write
	 * @throws IOException
	 *             if an IOException occurs
	 */
	public static void write(File file, JsonValue json) throws IOException {
		JsonIO.writeFile(file, json);
	}

	//VALUES
	public static JsonArray fromList(Iterable<JsonValue> iterable) {
		return new JsonArray(iterable);
	}

	public static JsonArray fromArray(JsonValue[] array) {
		return new JsonArray(array);
	}

	public static JsonObject fromMap(Map<String, JsonValue> map) {
		return new JsonObject(map);
	}

	public static JsonString fromString(String value) {
		return new JsonString(value);
	}

	public static JsonBoolean fromBoolean(Boolean value) {
		return new JsonBoolean(value);
	}

	public static JsonNumber fromInt(Integer value) {
		return new JsonNumber(value);
	}

	public static JsonNumber fromLong(Long value) {
		return new JsonNumber(value);
	}

	public static JsonNumber fromFloat(Float value) {
		return new JsonNumber(value);
	}

	public static JsonNumber fromDouble(Double value) {
		return new JsonNumber(value);
	}

}
