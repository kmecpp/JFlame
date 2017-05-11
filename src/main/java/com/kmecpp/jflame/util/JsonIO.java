package com.kmecpp.jflame.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.kmecpp.jflame.InvalidJsonException;
import com.kmecpp.jflame.JsonValue;

public class JsonIO {

	/**
	 * Reads the contents of the URL. The URL may be a local or remote.
	 * 
	 * @param url
	 *            the URL to read from
	 * @return the {@link JsonValue} representation of the retrieved data
	 * @throws InvalidJsonException
	 *             if the data is not valid JSON
	 * @throws IOException
	 *             if a problem occurred when reading the data
	 */
	public static String read(URL url) throws IOException {
		return read(url.openStream());

	}

	/**
	 * Writes the {@link JsonValue} to the file.
	 * 
	 * @param file
	 * @param json
	 * @throws IOException
	 */
	public static void writeFile(File file, JsonValue json) throws IOException {
		try (FileOutputStream writer = new FileOutputStream(file)) {
			writer.write(json.getFormatted().getBytes());
		}
	}

	/**
	 * Writes the {@link JsonValue} to the file.
	 * 
	 * @param file
	 * @param json
	 * @throws IOException
	 */
	public static void writeFile(File file, JsonValue json, boolean format) throws IOException {
		try (FileOutputStream writer = new FileOutputStream(file)) {
			writer.write(json.toString(format).getBytes());
		}
	}

	/**
	 * Sends an HTTP post request to the given URL with the {@link JsonValue} as
	 * the content
	 * 
	 * @param url
	 *            the URL to post to
	 * @param value
	 *            the JsonValue to post
	 * @throws IOException
	 *             if there was a problem posting to the URL
	 */
	public static void postUrl(URL url, JsonValue json) throws IOException {
		writeUrl(url, json, false);
	}

	/**
	 * Sends an HTTP post request to the given URL with the {@link JsonValue} as
	 * the content. Then reads and returns the response from the URL.
	 * 
	 * @param url
	 *            the URL to post to
	 * @param value
	 *            the JsonValue to post
	 * @return the response retrieved from the URL
	 * @throws IOException
	 *             if there was a problem posting to the URL
	 */
	public static String readPostUrl(URL url, JsonValue json) throws IOException {
		return writeUrl(url, json, true);
	}

	public static String writeUrl(URL url, JsonValue json, boolean readResponse) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");

		DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
		outputStream.writeUTF(json.toString());

		return readResponse
				? read(connection.getInputStream())
				: "";
	}

	/**
	 * High performance read from an {@link InputStream} into a String
	 * 
	 * @param inputStream
	 *            the input stream from which to read
	 * @return the string read from the reader
	 * @throws IOException
	 *             if an IOException occurs
	 */
	public static String read(InputStream inputStream) throws IOException {
		InputStreamReader reader = new InputStreamReader(inputStream);
		StringWriter sw = new StringWriter();
		char[] buffer = new char[4096];
		int pos = 0;
		while ((pos = reader.read(buffer)) != -1) {
			sw.write(buffer, 0, pos);
		}
		return sw.toString();
	}

}
