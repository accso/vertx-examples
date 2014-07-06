package de.accso.masspingpong;

import java.time.Instant;

import static de.accso.masspingpong.Constants.*;

import org.vertx.java.core.json.JsonObject;

/**
 * Vert.x example 'mass-pingpong'
 *    JsonMessage object which is passed between Ping- and PongVerticle.
 *    Contains id and (creation) timestamp.
 * 
 * @author lehmann, schaal, grammes
 */
public final class JsonMessage extends JsonObject {
	private static final long serialVersionUID = 6153622655682551512L;

	public JsonMessage(Integer id, Instant timestamp) {
		putNumber(JSON_ATTR_ID, id);
		putString(JSON_ATTR_TIMESTAMP, Instant.now().toString());
	}
}
