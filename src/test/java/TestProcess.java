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

import dev.jackb.liteprocess.LiteProcess;
import dev.jackb.liteprocess.io.LogFormat;
import dev.jackb.liteprocess.net.LiteRequest;
import dev.jackb.liteprocess.net.LiteResponse;

import java.util.concurrent.ExecutionException;

public class TestProcess extends LiteProcess {

	public TestProcess() {
		super(LogFormat.EXACT_TIME.setLoggingPrefix("TestProcess"));
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		TestProcess process = new TestProcess();

		LiteResponse res = new LiteRequest("https://jackb.dev/api/users")
				.setHeader("Accept", "application/json")
				.execute()
				.get();

		System.out.println(res.getAsJson());
	}

}
