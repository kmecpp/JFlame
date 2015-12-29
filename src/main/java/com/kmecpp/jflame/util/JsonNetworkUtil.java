package com.kmecpp.jflame.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.kmecpp.jflame.InvalidJsonException;
import com.kmecpp.jflame.Json;
import com.kmecpp.jflame.JsonValue;

public class JsonNetworkUtil {

	/**
	 * Convenience method for reading a JSON string from a URL
	 * 
	 * @param url
	 *            the URL to read from
	 * @return the data fetched from the URL
	 * @throws IOException
	 *             if a problem occurred when reading the data
	 */
	public static String readRaw(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setReadTimeout(5000);
		connection.setConnectTimeout(5000);
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		connection.addRequestProperty("Content-Type", "application/json");
		connection.addRequestProperty("Accept", "application/json");

		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder content = new StringBuilder();
		String line = "";
		while ((line = br.readLine()) != null) {
			content.append(line);
		}
		return content.toString();
	}

	/**
	 * Convenience method for reading a {@link JsonValue} from a URL
	 * 
	 * @param url
	 *            the URL to read from
	 * @return the {@link JsonValue} representation of the retrieved data
	 * @throws InvalidJsonException
	 *             if the data is not valid JSON
	 * @throws IOException
	 *             if a problem occurred when reading the data
	 */
	public static JsonValue readJson(URL url) throws InvalidJsonException, IOException {
		return Json.parse(readRaw(url));
	}

	/**
	 * Posts the given string to the URL
	 * 
	 * @param url
	 *            the URL to post to
	 * @param params
	 *            the data to post
	 * @return the response retrieved from the URL
	 * @throws IOException
	 *             if there was a problem posting to the URL
	 */
	public static String postRaw(URL url, String params) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		connection.setRequestProperty("Content-Length", "" + Integer.toString(params.getBytes().length));

		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);

		//Send Post
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(params);
		wr.flush();
		wr.close();

		//Get Response
		BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder response = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		return response.toString();
	}

	/**
	 * Posts only the raw JSON text to the URL and returns the response as a string
	 * 
	 * @param url
	 *            the URL to post to
	 * @param value
	 *            the {@link JsonValue} to post
	 * @return the response retrieved from the URL
	 * @throws IOException
	 *             if there was a problem posting to the URL
	 */
	public static String postJson(URL url, JsonValue value) throws IOException {
		return postRaw(url, value.toString());
	}
}