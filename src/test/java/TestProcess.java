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
