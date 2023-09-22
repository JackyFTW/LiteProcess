package dev.jackb.liteprocess.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class LiteRequest {

	private final String urlString;
	private final RequestMethod method;
	private final HashMap<String, String> headers;
	private final HashMap<String, String> params;

	public LiteRequest(String urlString) {
		this.urlString = urlString;
		this.method = RequestMethod.GET;
		this.headers = new HashMap<>();
		this.params = new HashMap<>();
	}

	public CompletableFuture<LiteResponse> execute() {
		return CompletableFuture.supplyAsync(() -> {
			HttpURLConnection conn = null;
			try {
				conn = (HttpURLConnection) getURL().openConnection();
				conn.setRequestMethod(this.method.name());
				this.headers.forEach((conn::setRequestProperty));
				return new LiteResponse(conn.getResponseCode(),
						conn.getHeaderFields(),
						conn.getInputStream(),
						conn.getErrorStream());
			} catch (IOException ignored) {
				System.out.println("Error opening HTTP connection");
			} finally {
				if(conn != null) conn.disconnect();
			}

			return null;
		});
	}

	public LiteRequest setHeader(String key, String value) {
		this.headers.put(key, value);
		return this;
	}

	public LiteRequest addParam(String key, String value) {
		this.params.put(key, value);
		return this;
	}

	private URL getURL() throws MalformedURLException {
		StringBuilder builder = new StringBuilder();
		builder.append(this.urlString);

		if(!this.params.isEmpty() && !this.urlString.contains("?")) {
			builder.append("?");
			builder.append(getParamsString());
		}

		return URI.create(builder.toString()).toURL();
	}

	private String getParamsString() {
		StringBuilder builder = new StringBuilder();

		this.params.forEach((k, v) -> {
			builder.append(URLEncoder.encode(k, StandardCharsets.UTF_8));
			builder.append("=");
			builder.append(URLEncoder.encode(v, StandardCharsets.UTF_8));
			builder.append("&");
		});

		String result = builder.toString();
		return result.endsWith("&") ? result.substring(0, result.length() - 1) : result;
	}

}
