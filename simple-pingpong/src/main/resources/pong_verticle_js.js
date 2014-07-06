//
// Vert.x example 'simple-pingpong'
// Example for a simple JavaScript PongVerticle
// 
// Receives messages to 'ping-address' and replies with message
//  
// @author lehmann, schaal, grammes
//
var vertx = require('vertx')
var console = require('vertx/console')

vertx.eventBus.registerHandler('ping-address', function(message, replier) {
    console.log(new Date() + ' - Receives message: ' + message);
    replier('pong-from-javascript!');
    console.log('Sent back pong from JavaScript!');
});