package de.accso.masspingpong;

import static de.accso.masspingpong.Constants.*;

import java.time.Instant;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

/**
 * Vert.x example 'mass-pingpong'
 * Example for a simple Java PongVerticle
 * 
 * Receives Json ping messages to 'ping-address', sends a Json pong message back to 'pong-address'
 *  
 * @author lehmann, schaal, grammes
 */
public class PongVerticle extends Verticle {
	@Override
	public void start() {
		// Handler fuer "Ping"-Nachrichten
		Handler<Message<JsonObject>> handler = new Handler<Message<JsonObject>>() {

			public void handle(Message<JsonObject> message) {
				// logInfoString(" receives message " + message.body().toString());

				// ID aus Message holen
				Integer id = message.body().getInteger(JSON_ATTR_ID);
				
				// Antwort auf den Bus schicken (empfangene Ping-Id wird zurueckgeschickt)
				JsonMessage msg = new JsonMessage(id, Instant.now());
				vertx.eventBus().send(BUS_ADDRESS_PONG, msg);
			}
		};
		
		// Obiger Handler wird fuer "Ping"-Nachrichten registriert
		vertx.eventBus().registerHandler(BUS_ADDRESS_PING, handler);
		logInfoString("PongVerticle has been registered for " + BUS_ADDRESS_PING);
	}	
	
	// Hilfsmethode zum Loggen
	private void logInfoString(String info) {
		container.logger().info(Instant.now() + ": [" + Thread.currentThread().getName() + "] " + this.toString() + info);
	}
}