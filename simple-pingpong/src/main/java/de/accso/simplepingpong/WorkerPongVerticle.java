package de.accso.simplepingpong;

import static de.accso.simplepingpong.Constants.*;

import java.time.Instant;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

/**
 * Vert.x example 'simple-pingpong'
 * Example for a simple Java Worker PongVerticle 
 *   (actually does not differ from PongVerticle except that this is started with run ... -worker)
 * 
 * Receives String ping messages to 'ping-address', sends a String pong message back to 'pong-address'.
 *  
 * @author lehmann, schaal, grammes
 */
public class WorkerPongVerticle extends Verticle {
	@Override
	public void start() {
		// Handler fuer "Ping"-Nachrichten
		Handler<Message<String>> handler = new Handler<Message<String>>() {

			public void handle(Message<String> message) {
				logInfoString(" receives message " + message.body());

				// blockierende Operation -> Worker-Thread!
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) { /*nope*/ }

				vertx.eventBus().send(BUS_ADDRESS_PONG, "pong");
			}
		};
		
		// obiger Handler wird fuer "Ping"-Nachrichten registriert
		vertx.eventBus().registerHandler(BUS_ADDRESS_PING, handler);
		logInfoString("WorkerPongVerticle has been registered for " + BUS_ADDRESS_PING);
	}	
	
	// Hilfsmethode zum Loggen
	private void logInfoString(String info) {
		container.logger().info(Instant.now() + ": [" + Thread.currentThread().getName() + "] " + this.toString() + info);
	}
}