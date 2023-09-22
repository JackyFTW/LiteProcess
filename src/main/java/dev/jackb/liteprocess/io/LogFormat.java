package dev.jackb.liteprocess.io;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum LogFormat {

	EXACT_TIME("'['MM-dd HH:mm:ss.S']' ");

	private final SimpleDateFormat format;
	private String prefix;

	LogFormat(String str) {
		this.format = new SimpleDateFormat(str);
		this.prefix = null;
	}

	public LogFormat setLoggingPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	String getNow() {
		return (this.prefix == null ? "" : "[" + this.prefix + "] ") + this.format.format(new Date());
	}

}
