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

package dev.jackb.liteprocess.util;

import java.util.Timer;
import java.util.TimerTask;

public class TimerUtil {

	public static void schedule(Runnable r, long delayMs, long periodMs) {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				r.run();
			}

		}, delayMs, periodMs);
	}

	public static void scheduleLater(Runnable r, long delayMs) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				r.run();
			}
		}, delayMs);
	}

}
