package com.kmecpp.jflame;

import java.io.IOException;
import java.net.URL;

import com.kmecpp.jflame.util.JsonNetworkUtil;
import com.kmecpp.jflame.value.JsonBoolean;
import com.kmecpp.jflame.value.JsonNull;
import com.kmecpp.jflame.value.JsonNumber;
import com.kmecpp.jflame.value.JsonString;

public final class Json {

	//Literals
	public static final JsonNull NULL = JsonNull.getInstance();
	public static final JsonBoolean TRUE = new JsonBoolean(true);
	public static final JsonBoolean FALSE = new JsonBoolean(false);

	private Json() {
	}

	/**
	 * Parses the given JSON string and returns a {@link JsonValue} that represents the data
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
	 * Generates a formatted string from a {@link JsonValue}. To generate a compressed string, call jsonValue.toString()
	 * 
	 * @param json
	 *            the json object to serialize
	 * @return a formatted String representation of the JSON object
	 */
	public static String generate(JsonValue json) {
		return json.isFormattable() ? ((IFormattable) json).getFormatted() : json.toString();
	}

	//NETWORK UTIL
	/**
	 * Reads and parses a JSON string from a URL
	 * <br>
	 * Convenience method for {@link JsonNetworkUtil#readJson(URL)}
	 * 
	 * @param url
	 *            the URL to read from
	 * @return a {@link JsonValue} representing the URL data
	 * @throws InvalidJsonException
	 *             if the data is not valid JSON
	 * @throws IOException
	 *             if there was a problem reading from the URL
	 */
	public static JsonValue readJsonFromUrl(String url) throws InvalidJsonException, IOException {
		return JsonNetworkUtil.readJson(new URL(url));
	}

	/**
	 * Reads the raw data from a URL
	 * <br>
	 * Convenience method for {@link JsonNetworkUtil#readRaw(URL)}
	 * 
	 * @param url
	 *            the URL to read from
	 * @return the data retrieved from the URL
	 * @throws IOException
	 *             if there was a problem reading from the URL
	 */
	public static String readFromUrl(String url) throws IOException {
		return JsonNetworkUtil.readRaw(new URL(url));
	}

	/**
	 * Posts only the raw JSON text to the URL and returns the response as a string
	 * <br>
	 * Convenience method for {@link JsonNetworkUtil#postJson(URL, JsonValue)}
	 * 
	 * @param url
	 *            the URL to post to
	 * @param value
	 *            the {@link JsonValue} to post
	 * @return the response retrieved from the URL
	 * @throws IOException
	 *             if there was a problem posting to the URL
	 */
	public static String postToUrl(String url, JsonValue value) throws IOException {
		return JsonNetworkUtil.postJson(new URL(url), value);
	}

	/**
	 * Posts the given string to the URL
	 * Convenience method for {@link JsonNetworkUtil#postRaw(URL, String)}
	 * 
	 * @param url
	 *            the URL to post to
	 * @param value
	 *            the data to post
	 * @return the response retrieved from the URL
	 * @throws IOException
	 *             if there was a problem posting to the URL
	 */
	public static String postToUrl(String url, String value) throws IOException {
		return JsonNetworkUtil.postRaw(new URL(url), value);
	}

	//VALUES
	public static JsonString getValue(String value) {
		return new JsonString(value);
	}

	public static JsonBoolean getValue(boolean value) {
		return new JsonBoolean(value);
	}

	public static JsonNumber getValue(int value) {
		return new JsonNumber(Integer.toString(value, 10));
	}

	public static JsonNumber getValue(long value) {
		return new JsonNumber(Long.toString(value, 10));
	}

	public static JsonNumber getValue(float value) {
		return new JsonNumber(Float.toString(value));
	}

	public static JsonNumber getValue(double value) {
		return new JsonNumber(Double.toString(value));
	}
}