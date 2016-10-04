package com.kmecpp.jflame;

public class InvalidJsonException extends RuntimeException {

	private static final long serialVersionUID = -8119107969701834012L;

	private int line;
	private int column;

	public InvalidJsonException(String message, int line, int column) {
		super("[" + JFlame.NAME + "]: " + message + " line: " + line + " column: " + column);
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
