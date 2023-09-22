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
