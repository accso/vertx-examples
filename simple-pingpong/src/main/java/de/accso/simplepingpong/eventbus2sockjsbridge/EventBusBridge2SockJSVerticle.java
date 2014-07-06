package de.accso.simplepingpong.eventbus2sockjsbridge;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.sockjs.EventBusBridgeHook;
import org.vertx.java.core.sockjs.SockJSSocket;
import org.vertx.java.platform.Verticle;

/**
 * Vert.x example 'simple-pingpong'
 * 
 * Java Verticle which serves as a Event-bus <-> SockJS bridge.
 *    Start the client-side HTML/JavaScript sample 'pongverticle_js_browser_sockjs.html' in browser after
 *    having started this verticle. You should see a connection message in the browsers javascript console then.
 * 
 * @author lehmann, schaal, grammes
 */
public class EventBusBridge2SockJSVerticle extends Verticle implements EventBusBridgeHook {
	@Override
	public void start() {
		HttpServer server = vertx.createHttpServer();

		JsonObject config   = new JsonObject().putString("prefix", "/eventbus");
		JsonArray permitted = new JsonArray().add(new JsonObject());
		vertx.createSockJSServer(server).setHook(this).bridge(config, permitted, permitted);

		server.listen(8645);

		container.logger().info("Event-bus <-> SockJS bridge started at localhost:8645/eventbus");
	}

	// ----------------------------------------------------------------------------------------
	// implementation of EventBusBridgeHook interface methods
	
	public boolean handleSocketCreated(SockJSSocket sock) {
		container.logger().info("EventBusBridge2SockJSVerticle.handleSocketCreated: " + sock.writeHandlerID());
		return true;
	}

	public void handleSocketClosed(SockJSSocket sock) {
		container.logger().info("EventBusBridge2SockJSVerticle.handleSocketClosed: " + sock.writeHandlerID());
	}

	public boolean handleSendOrPub(SockJSSocket sock, boolean send, JsonObject msg, String address) {
		container.logger().info("EventBusBridge2SockJSVerticle.handleSendOrPub: " + sock.writeHandlerID());
		return true;
	}

	public boolean handlePreRegister(SockJSSocket sock, String address) {
		container.logger().info("EventBusBridge2SockJSVerticle.handlePreRegister: " + sock.writeHandlerID());
		return true;
	}

	public void handlePostRegister(SockJSSocket sock, String address) {
		container.logger().info("EventBusBridge2SockJSVerticle.handlePostRegister: " + sock.writeHandlerID());
	}

	public boolean handleUnregister(SockJSSocket sock, String address) {
		container.logger().info("EventBusBridge2SockJSVerticle.handleUnregister: " + sock.writeHandlerID());
		return true;
	}

	public boolean handleAuthorise(JsonObject message, String sessionID, Handler<AsyncResult<Boolean>> handler) {
		container.logger().info("EventBusBridge2SockJSVerticle.handleAuthorise: " + sessionID);
		return true;
	}
}
