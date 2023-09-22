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

package dev.jackb.liteprocess;

import dev.jackb.liteprocess.io.ConsolePrintStream;
import dev.jackb.liteprocess.io.LogFormat;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public abstract class LiteProcess {

	private final HashMap<String, Thread> inputListeners;

	public LiteProcess(LogFormat logPrefix) {
		this.inputListeners = new HashMap<>();

		System.setOut(new ConsolePrintStream(logPrefix));
		System.setErr(new PrintStream(System.err) {
			@Override
			public void println(String s) {
				if(!s.startsWith("SLF4J")) super.println(s);
			}
		});
	}

	public void addInputListener(String regex, boolean ignoreCase, Consumer<String> onMatch) {
		Thread thread = new Thread(() -> {
			Scanner in = new Scanner(System.in);
			while(!Thread.currentThread().isInterrupted()) {
				String line = in.nextLine();
				if(Pattern.matches(regex, ignoreCase ? line.toLowerCase() : line)) {
					onMatch.accept(line);
				}
			}
		});
		thread.start();
		this.inputListeners.put(regex, thread);
	}

	public void removeInputListener(String regex) {
		if(this.inputListeners.containsKey(regex)) {
			this.inputListeners.get(regex).interrupt();
			this.inputListeners.remove(regex);
		}
	}

	public void addProcessEndListener(Runnable onEnd) {
		Runtime.getRuntime().addShutdownHook(new Thread(onEnd));
	}

}