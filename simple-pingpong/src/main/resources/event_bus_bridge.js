//
// Vert.x example 'simple-pingpong'
//
// Simple JavaScript verticle that starts the event bus bridge in the webserver module 
//  
// @author lehmann, schaal, grammes
//
var container = require('vertx/container');

container.deployModule("io.vertx~mod-web-server~2.0.0-final", {
  port: 8645, bridge: true,
  inbound_permitted: [ { address_re: 'pong-address' } ],
  outbound_permitted: [ { address: 'ping-address' } ]
});