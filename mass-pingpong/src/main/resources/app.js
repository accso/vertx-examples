//
// Starts all Verticles (2 PongVerticles, 1 StatusMonitor and 1 PingVerticle)
//    Especially StatusMonitorVerticle and PingVerticle must be in the same vert.x instance
//    to allow them to share data in the counting map!
// @author lehmann, schaal, grammes
//

var container = require("vertx/container");

// 2 instances of PongVerticle
// container.deployVerticle('de.accso.masspingpong.PongVerticle', 2);

// instance of the StatusMonitorVerticle
container.deployVerticle('de.accso.masspingpong.StatusMonitorVerticle');

// instance of the PingVerticle
container.deployVerticle('de.accso.masspingpong.PingVerticle');
