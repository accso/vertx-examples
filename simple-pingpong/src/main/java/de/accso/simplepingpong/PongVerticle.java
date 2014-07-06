package de.accso.simplepingpong;

import static de.accso.simplepingpong.Constants.*;

import java.time.Instant;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

/**
 * Vert.x example 'simple-pingpong'
 * Example for a simple Java PongVerticle
 * 
 * Receives String ping messages to 'ping-address', sends a String pong message back to 'pong-address'.
 *  
 * @author lehmann, schaal, grammes
 */
public class PongVerticle extends Verticle {
	@Override
	public void start() {
		// Handler fuer "Ping"-Nachrichten
		Handler<Message<String>> handler = new Handler<Message<String>>() {

			public void handle(Message<String> message) {
				logInfoString(" receives message " + message.body());
				vertx.eventBus().send(BUS_ADDRESS_PONG, "pong");
			}
		};
		
		// obiger Handler wird fuer "Ping"-Nachrichten registriert
		vertx.eventBus().registerHandler(BUS_ADDRESS_PING, handler);
		logInfoString("PongVerticle has been registered for " + BUS_ADDRESS_PING);
	}	
	
	// Hilfsmethode zum Loggen
	private void logInfoString(String info) {
		container.logger().info(Instant.now() + ": [" + Thread.currentThread().getName() + "] " + this.toString() + info);
	}
}