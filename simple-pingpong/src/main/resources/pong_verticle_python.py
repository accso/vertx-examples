#
#  Vert.x example 'simple-pingpong'
#  Example for a simple Groovy PongVerticle
# 
#  Receives messages to 'ping-address' and replies with message
#  
#  @author lehmann, schaal, grammes
# 
import vertx
from core.event_bus import EventBus

def handler(msg):
    msg.reply('pong-from-python!')
    print 'sent back pong from Python!'

EventBus.register_handler('ping-address', handler=handler)