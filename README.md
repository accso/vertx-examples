vertx-examples
==============

Vert.x examples for Accso's JavaSPEKTRUM article, to be published in 2014

1. simple-httpd: Simple HTTP server verticle, listens on port 8123. Returns a static hello world http response.
   Start with: 
     a) cd simple-httpd\src\main\java\de\accso\helloworldhttpserver
     b) vertx run HelloWorldHttpServer.java
     c) Access via http://localhost:8123/
     
2. simple-pingpong: PingVerticle and PongVerticle(s), sending event bus messages (via send, i.e. point to point):
   Start PongVerticle first (one or more):
     a) cd simple-pingpong\src\main\java\de\accso\simplepingpong
     b) vertx run PongVerticle.java -cluster
   Start PingVerticle then:
     c) vertx run PingVerticle.java -cluster
   
   You can also start the JavaScript verticle (which does reply to ping messages)
     d) cd simple-pingpong\src\main\resources
     e) vertx run pong_verticle_js.js -cluster
   
3. mass-pingpong: PingVerticle and PongVerticle(s), sending event bus messages (ping messages via publish)
   Start PongVerticle first (one or more - if more than one, then exponential message growth):
     a) cd mass-pingpong\src\main\java\de\accso\masspingpong
     b) vertx run PongVerticle.java -cluster
   Start PingVerticle then:
     c) vertx run PingVerticle.java -cluster
   
   Alternatively to c): Start module (see src\main\resources\app.js), ie both PingVerticle and StatusMonitorVerticle:
     d) build with maven
     e) cd mass-pingpong\src\target
     f) vertx runzip mass-pingpong-0.1-mod.zip
 