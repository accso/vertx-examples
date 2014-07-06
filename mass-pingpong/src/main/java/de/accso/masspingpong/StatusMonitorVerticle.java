package de.accso.masspingpong;

import static de.accso.masspingpong.Constants.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.vertx.java.core.shareddata.ConcurrentSharedMap;
import org.vertx.java.platform.Verticle;

/**
 * Vert.x example 'mass-pingpong'
 * Example for a simple Java StatusMonitor Verticle
 * 
 * Gets the status information (number of messages for each id) from the shared-data map 
 *      and shows this status at http://localhost:8123
 *  
 * @author lehmann, schaal, grammes
 */
public class StatusMonitorVerticle extends Verticle {
	String statusString = "no status yet defined";

	@Override
	public void start() {
		// Zugriff auf Map, die zu jeder Pong-Id die Anzahl der erhaltenen Message mitzaehlt.
		//   Die Map wird vom PingVerticle gefuellt und via Shared Data mit dem StatusMonitor geteilt.
		final ConcurrentSharedMap<Integer, Long> messageCountingMap = vertx.sharedData().getMap(COUNTING_MAP);

		vertx.createHttpServer().requestHandler(req -> {
			LocalDateTime ldt = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());

			statusString = "";
			for (Integer pongId : messageCountingMap.keySet()) {
				statusString += "Received " + messageCountingMap.get(pongId)
  							 + " messages for pong-id=" + pongId
							 + "\n";
			}

			req.response()
					.putHeader("content-type", "text/plain")
					.end(ldt + "\n" + statusString);
		})
		.listen(8123);
		
		logInfoString("StatusMonitorVerticle has been registered as HTTP server on port 8123");
	}

	// Hilfsmethode zum Loggen
	private void logInfoString(String info) {
		container.logger().info(Instant.now() + ": [" + Thread.currentThread().getName() + "] " + this.toString() + info);
	}
}
