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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DataStore {

	private final File file;
	private final boolean autoSave;
	private final HashMap<String, Object> data;
	private final HashMap<String, Class<?>> dataTypes;
	private final HashMap<String, Supplier<?>> defaultData;
	private boolean usable;

	public DataStore(String jsonName, boolean autoSave) {
		this.file = new File(jsonName + ".json");
		this.autoSave = autoSave;
		this.data = new HashMap<>();
		this.dataTypes = new HashMap<>();
		this.defaultData = new HashMap<>();
		this.usable = false;
	}

	public <V> DataStore addKey(String key, Class<V> type, Supplier<V> defaultValue) {
		this.dataTypes.put(key, type);
		this.defaultData.put(key, defaultValue);
		return this;
	}

	public <V> V get(String key, Class<V> type) {
		if(!this.usable) throw new IllegalStateException("DataStore is not in a usable state");

		if(!this.dataTypes.get(key).getName().equals(type.getName())) {
			throw new IllegalArgumentException("Invalid type specified for key, expected " + this.dataTypes.get(key).getName() + " instead");
		}

		return type.cast(this.data.get(key));
	}

	public void set(String key, Object val) {
		if(!this.usable) throw new IllegalStateException("DataStore is not in a usable state");

		if(!this.dataTypes.get(key).getName().equals(val.getClass().getName())) {
			throw new IllegalArgumentException("Invalid type specified for key, expected " + this.dataTypes.get(key).getName() + " instead");
		}

		this.data.replace(key, val);
		if(this.autoSave) save();
	}

	public DataStore create() {
		try {
			if(!this.file.exists()) {
				boolean success = this.file.createNewFile();
				System.out.println((success ? "Created" : "Failed to create") + " DataStore file for " + this.file.getName());
				if(!success) return this;

				loadDefaults();
			} else {
				load();
			}

			save();
			this.usable = true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	private void loadDefaults() {
		for(Map.Entry<String, Supplier<?>> defaults : defaultData.entrySet()) {
			this.data.put(defaults.getKey(), defaults.getValue().get());
		}
		this.defaultData.clear();
	}

	private void load() throws FileNotFoundException {
		JsonObject data = JsonParser.parseReader(new FileReader(this.file)).getAsJsonObject();

		for(Map.Entry<String, JsonElement> entry : data.asMap().entrySet()) {
			String key = entry.getKey();
			JsonElement val = entry.getValue();

			if(defaultData.containsKey(key)) {
				this.data.put(key, new Gson().fromJson(val, this.dataTypes.get(key)));
				this.defaultData.remove(key);
			}
		}

		loadDefaults();
	}

	public void save() {
		JsonObject out = new JsonObject();

		for(Map.Entry<String, Object> entry : data.entrySet()) {
			out.add(entry.getKey(), new Gson().toJsonTree(entry.getValue(), dataTypes.get(entry.getKey())));
		}

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(this.file));
			bw.write(new GsonBuilder().setPrettyPrinting().create().toJson(out));
			bw.flush();
			bw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
