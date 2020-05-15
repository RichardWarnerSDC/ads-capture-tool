package offlineads;

import java.time.Instant;

public class Utilities {

	public static String getTimeNowUTC() {
		return Instant.now().toString().replaceAll("[TZ]{1}", " ").trim();
	}
	
}
