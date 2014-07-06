vertx-examples
==============

Vert.x examples for Accso's JavaSPEKTRUM article, to be published in 2014

##simple-httpd
Simple HTTP server verticle, listens on port 8123. Returns a static `Hello World!` http response.

Start with:
```
cd simple-httpd\src\main\java\de\accso\helloworldhttpserver
vertx run HelloWorldHttpServer.java
```

Access via `http://localhost:8123/`

##simple-pingpong
PingVerticle and PongVerticle(s), sending event bus messages (via send, i.e. point to point):

Start `PongVerticle` first (one or more):
```
cd simple-pingpong\src\main\java\de\accso\simplepingpong
vertx run PongVerticle.java -cluster
```

Start `PingVerticle` then:
```
vertx run PingVerticle.java -cluster
```
   
You can also start the JavaScript PongVerticle (which does reply to ping messages)
```
cd simple-pingpong\src\main\resources
vertx run pong_verticle_js.js -cluster
```
   
##mass-pingpong
PingVerticle and PongVerticle(s), sending event bus messages (ping messages via publish)

Start `PongVerticle` first (which receives messages to ping-address). If you start two or more `PongVerticle`s, then this will produce exponential message growth.
```
cd mass-pingpong\src\main\java\de\accso\masspingpong
vertx run PongVerticle.java -cluster
```

Start `PingVerticle` then (with publishes the ping messages incl. the initial message)
```
vertx run PingVerticle.java -cluster
```

Alternatively to the direct start of the PingVerticle, you can also start module (i.e. both `PingVerticle` and `StatusMonitorVerticle`):
```
# Build with Maven (not shown, see pom.xml)
...
cd src\main\resources
# see which verticles are the main verticles of the module
cat app.js
# switch to target directory where Maven has created the module
cd mass-pingpong\src\target
vertx runzip mass-pingpong-0.1-mod.zip -cluster
```
 