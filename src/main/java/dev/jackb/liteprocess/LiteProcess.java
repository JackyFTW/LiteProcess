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