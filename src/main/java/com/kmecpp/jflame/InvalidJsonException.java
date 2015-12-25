package com.kmecpp.jflame;

@SuppressWarnings("serial")
public class InvalidJsonException extends RuntimeException {

	private int line;
	private int column;

	public InvalidJsonException(String message, int line, int column) {
		super(message + " line: " + line + " column: " + column);
		this.line = line;
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
}