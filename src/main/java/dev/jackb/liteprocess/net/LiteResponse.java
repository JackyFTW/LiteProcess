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

package dev.jackb.liteprocess.net;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LiteResponse {

	private final int responseCode;
	private final String response;
	private final Map<String, List<String>> headers;

	public LiteResponse(int responseCode, Map<String, List<String>> headers, InputStream in, InputStream err) {
		this.responseCode = responseCode;
		this.headers = headers;
		this.response = readResponse(in, err);
	}

	private String readResponse(InputStream in, InputStream err) {
		BufferedReader br = this.responseCode >= 400 ?
				new BufferedReader(new InputStreamReader(err)) :
				new BufferedReader(new InputStreamReader(in));

		StringBuilder builder = new StringBuilder();
		br.lines().forEach(builder::append);
		return builder.toString();
	}

	public String getHeader(String key) {
		String value = String.join(", ", headers.getOrDefault(key, Collections.emptyList()));
		return value.isEmpty() ? null : value;
	}

	public String getAsString() {
		return this.response;
	}

	public JsonElement getAsJson() {
		return JsonParser.parseString(this.response);
	}

}
