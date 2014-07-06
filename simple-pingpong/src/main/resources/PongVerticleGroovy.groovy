/**
 * Vert.x example 'simple-pingpong'
 * Example for a simple Groovy PongVerticle
 * 
 * Receives messages to 'ping-address' and replies with message
 *  
 * @author lehmann, schaal, grammes
 */
vertx.eventBus.registerHandler("ping-address") { message ->
    message.reply("pong-from-groovy!")
    container.logger.info("Sent back pong from Groovy!")
}