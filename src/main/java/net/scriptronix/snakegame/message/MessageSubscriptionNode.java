package net.scriptronix.snakegame.message;

/**
 * Encapsulates a message signal, for processing by the MessageBus queue
 */
public class MessageSubscriptionNode {
    final private Message message;
    final private IMessageHandler handler;

    public MessageSubscriptionNode(Message message, IMessageHandler handler) {
        this.message = message;
        this.handler = handler;
    }

    /**
     * @return the message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * @return the handler
     */
    public IMessageHandler getHandler() {
        return handler;
    }
    
}
