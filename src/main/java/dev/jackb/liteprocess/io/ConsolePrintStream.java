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

import java.io.PrintStream;
import java.util.Arrays;

public class ConsolePrintStream extends PrintStream {

	private final LogFormat logPrefix;

	public ConsolePrintStream(LogFormat logPrefix) {
		super(System.out, true);
		this.logPrefix = logPrefix;
	}

	@Override
	public void print(boolean b) {
		super.print(this.logPrefix.getNow() + b);
	}

	@Override
	public void print(char c) {
		super.print(this.logPrefix.getNow() + c);
	}

	@Override
	public void print(int i) {
		super.print(this.logPrefix.getNow() + i);
	}

	@Override
	public void print(long l) {
		super.print(this.logPrefix.getNow() + l);
	}

	@Override
	public void print(float f) {
		super.print(this.logPrefix.getNow() + f);
	}

	@Override
	public void print(double d) {
		super.print(this.logPrefix.getNow() + d);
	}

	@Override
	public void print(char[] s) {
		super.print(this.logPrefix.getNow() + Arrays.toString(s));
	}

	@Override
	public void print(String s) {
		super.print(this.logPrefix.getNow() + s);
	}

	@Override
	public void print(Object obj) {
		super.print(this.logPrefix.getNow() + obj);
	}

}
