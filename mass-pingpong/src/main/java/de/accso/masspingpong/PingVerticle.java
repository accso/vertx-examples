package de.accso.masspingpong;

import static de.accso.masspingpong.Constants.*;

import java.time.Instant;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.shareddata.ConcurrentSharedMap;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

/**
 * Vert.x example 'mass-pingpong'
 * Example for a Java PingVerticle. 
 * 
 * Sends initial Json ping message to 'ping-address' and receives Json pong messages to 'pong-address'.
 *      Waits 1 sec and then sends the next ping message
 * Not: This verticle does publish(!) all messages on the bus, so that (in case of 2 or more PongVerticles), 
 *      the number of messages is doubled each time (i.e. exponential message growth).
 * Logs the messages ids and their counts to the console and shared this information
 *      in a shared-data map with the status monitor verticle.
 *  
 * @author lehmann, schaal, grammes
 */
public class PingVerticle extends Verticle {
	@Override
	public void start() {
		// Map, die zu jeder Pong-Id die Anzahl der erhaltenen Message mitzaehlt
		//    Wird auch vom StatusMonitorVerticle ausgelesen.
		final ConcurrentSharedMap<Integer, Long> messageCountingMap = vertx.sharedData().getMap(COUNTING_MAP);
		
		// Handler fuer "Pong"-Nachrichten
		Handler<Message<JsonObject>> handler = new Handler<Message<JsonObject>>() {
			public void handle(Message<JsonObject> message) {
				// logInfoString(" receives message " + message.body().toString());
				
				// Pong-ID aus Message holen
				final Integer id = message.body().getInteger(JSON_ATTR_ID);
								
				// In Shared-Data: Zaehler fuer diese Pong-Nummer erhoehen (oder initialisieren)
				//   (funktioniert nur bei einem PingVerticle, nicht parallel bei mehreren)
				Long idCounter = messageCountingMap.get(id);
				if (idCounter==null) idCounter = new Long(0);
				messageCountingMap.put(id, idCounter+1);
				
				// Wir antworten auf das Pong mit einem erneuten Ping nach Zaehler-Inkrement um 1, aber nur bis max. Ping-ID 99)
				//    Erst 1 sec warten, dann Publish (nicht: Send!) des Pings! Folge: exponentielles Messaging-Wachstum!
				if (id < 99) {
					vertx.setTimer(1000, new Handler<Long>() {
						public void handle(Long event) {
							JsonMessage msg = new JsonMessage(id + 1, Instant.now());
							vertx.eventBus().publish(BUS_ADDRESS_PING, msg);
						}
					});
				}
			}
		};

		// Obiger Handler wird fuer "Pong"-Nachrichten registriert
		vertx.eventBus().registerHandler(BUS_ADDRESS_PONG, handler);
		logInfoString("PingVerticle has been registered for " + BUS_ADDRESS_PONG);
		
		// ----------------------------------------------------------------------------------------

		// Bootstrap: Erster, initialer Publish an alle PongVerticles - mit initialer Ping-ID 1
		//    Alle PongVerticles muessen vorher gestartet sein, sonst geht der erste Ping verloren!
		JsonMessage msg = new JsonMessage(new Integer(1), Instant.now());
		logInfoString("PingVerticle will publish first message " + msg + " to " + BUS_ADDRESS_PING + " in 5sec...");
		vertx.setTimer(5000, new Handler<Long>() {
			public void handle(Long event) {
				logInfoString("PingVerticle will publish first message " + msg + " to " + BUS_ADDRESS_PING + " now.");
				vertx.eventBus().publish(BUS_ADDRESS_PING, msg);
			} 
		});

		// ----------------------------------------------------------------------------------------
		
		// Periodisch alle 5 sec eine Info-Ausgabe loggen
		//   (bei viel Traffic kommt Timer-Aufruf ggf. viel spaeter!)
		vertx.setPeriodic(5000, new Handler<Long>() {
			public void handle(Long event) {
				logStatus(messageCountingMap);
			}
		});
	}	

	// ----------------------------------------------------------------------------------------

	// logge die Anzahl der Messages (fuer jede Id) sowie die Gesamtzahl aller Messages
	private void logStatus(ConcurrentSharedMap<Integer, Long> messageCountingMap) {
		Long numMessagesTotal = 0L; // Gesamtzahl aller Messages
		
		// Logge die Anzahl aller Messages, aufgeteilt nach ihrer id
		for (Integer id : messageCountingMap.keySet()) {
			Long numMessagesForId = messageCountingMap.get(id);
			if  (numMessagesForId != null) {
				numMessagesTotal += numMessagesForId;
				String info = " has received " + numMessagesForId + " messages with id=" + id;
				logInfoString(info);
			}
		}

		// Gesamtzahl aller Messages ausgeben
		String info = ": Total number of messages received " + numMessagesTotal;
		logInfoString(info);
	}

	// Hilfsmethode zum Loggen
	private void logInfoString(String info) {
		container.logger().info(Instant.now() + ": [" + Thread.currentThread().getName() + "] " + this.toString() + info);
	}
}