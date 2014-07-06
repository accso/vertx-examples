package de.accso.simplepingpong;

import static de.accso.simplepingpong.Constants.*;

import java.time.Instant;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

/**
 * Vert.x example 'simple-pingpong'
 * 
 * Example for a Java PingVerticle. 
 * 
 * Sends initial ping String message to 'ping-address' and receives String pong messages to 'pong-address'.
 * Waits 1 sec and then sends the next ping message.
 * 
 * Note that the Java PongVerticle sends a message while the Groovy, Python and JavaScript pong verticles do a reply.
 *  
 * @author lehmann, schaal, grammes
 */
public class PingVerticle extends Verticle {
	@Override
	public void start() {
		// Handler fuer "Pong"-Nachrichten (sowohl fuer Replys als auch fuer Nachrichten an BUS_ADDRESS_PONG)
		Handler<Message<String>> handler = new Handler<Message<String>>() {
			public void handle(Message<String> message) {						
				logInfoString(" receives message " + message.body());

				Handler<Message<String>> replyHandler = this;
				
				// erneuten Ping verzoegert nach 1sec rausschicken
				vertx.setTimer(1000, new Handler<Long>() {
					public void handle(Long event) {
						vertx.eventBus().send(BUS_ADDRESS_PING, "ping", replyHandler);
					}
				});
			}
		};

		// Obiger Handler wird fuer "Pong"-Replys registriert
		vertx.eventBus().registerHandler(BUS_ADDRESS_PONG, handler);
		logInfoString("PingVerticle has been registered for " + BUS_ADDRESS_PONG);

		// Bootstrap: Erste, initiale Nachricht an alle PongVerticles - mit initialer Ping-ID 1
		//    Alle Pong-Verticles muessen vorher gestartet sein, sonst geht der erste Ping verloren!
		logInfoString("PingVerticle will now publish first message to " + BUS_ADDRESS_PING + " ...");
		vertx.eventBus().send(BUS_ADDRESS_PING, "ping", handler);
	}	
	
	// Hilfsmethode zum Loggen
	private void logInfoString(String info) {
		container.logger().info(Instant.now() + ": [" + Thread.currentThread().getName() + "] " + this.toString() + info);
	}
}