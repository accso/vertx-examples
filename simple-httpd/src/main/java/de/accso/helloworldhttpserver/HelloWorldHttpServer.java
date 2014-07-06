package de.accso.helloworldhttpserver;

import java.time.Instant;

import org.vertx.java.platform.Verticle;
import org.vertx.java.core.http.HttpServerRequest;

/**
 * Vert.x example 'simple-httpd'
 * Example for a simple Java HTTP verticle
 * 
 * Listens to localhost:8123 and brings back the very first Hello World message ;-)
 * 
 * @author lehmann, schaal, grammes
 */
public class HelloWorldHttpServer extends Verticle {
  @Override
  public void start() {
	vertx.createHttpServer().requestHandler( (HttpServerRequest req) -> {
		logInfoString(" received request " + req.remoteAddress());
	    req.response().putHeader("content-type", "text/plain").end("Hello World!");
 	})
    .listen(8123);

	logInfoString(" has been registered as HTTP server on port 8123");
  }

  // Hilfsmethode zum Loggen
  private void logInfoString(String info) {
	container.logger().info(Instant.now() + ": [" + Thread.currentThread().getName() + "] "
	                        + this.toString() + info);
  }
}
