/*
 *     Copyright (C) 2023  Jack Barter
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
