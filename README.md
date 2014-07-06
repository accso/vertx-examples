vertx-examples
==============

Vert.x examples for Accso's JavaSPEKTRUM article, to be published in 2014. Link to article will be put here as soon as its available.

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

Start one or more `PongVerticle`s first (with sends a pong messages whenever they receive a ping message):
```
cd simple-pingpong\src\main\java\de\accso\simplepingpong
vertx run PongVerticle.java -cluster
```

Start `PingVerticle` then (with sends the ping messages incl. the initial message):
```
vertx run PingVerticle.java -cluster
```
   
You can also start the JavaScript PongVerticle (which does reply to a ping messages, different to the Java PongVerticles)
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

Alternatively to the direct start of the PingVerticle, you can also start the module, i.e. both `PingVerticle` and `StatusMonitorVerticle` as follows:
```
# Build with Maven (not shown, see pom.xml)
# maven install ...

# see which verticles are the main verticles of the module (should be both `PingVerticle` and `StatusMonitorVerticle`)
cd mass-pingpong\src\main\resources
cat app.js

# switch to target directory where Maven has created the module
cd mass-pingpong\src\target
vertx runzip mass-pingpong-0.1-mod.zip -cluster

# Now the PingVerticle shows the counters for all messages ids regularly (based on a Vert.x timer).
# In parallel one can also access these information via http from the StatusMonitorVerticle, i.e. via http://localhost:8123
```
 