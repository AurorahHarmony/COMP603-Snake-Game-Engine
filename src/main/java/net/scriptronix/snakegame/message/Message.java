package net.scriptronix.snakegame.message;

/**
 * Container class for messages sent through the message bus.
 */
public class Message {

    private final String code;
    private final Object context;
    private final Object sender;

    public Message(String code,  Object sender, Object context) {
        this.code = code;
        this.context = context;
        this.sender = sender;
    }

    /**
     * @return the message code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the message context
     */
    public Object getContext() {
        return context;
    }

    /**
     * @return the message sender
     */
    public Object getSender() {
        return sender;
    }

    /**
     * Sends a message through the message bus.
     * @param code
     * @param sender
     * @param context 
     */
    public static void send(String code, Object sender, Object context) {
        MessageBus.post(new Message(code, sender, context));
    }

    /**
     * Subscribes to a message within the message bus.
     * @param code
     * @param handler 
     */
    public static void subscribe(String code, IMessageHandler handler) {
        MessageBus.addSubscription(code, handler);
    }

    /**
     * Removes a subscription from the message bus.
     * @param code
     * @param handler 
     */
    public static void unsubscribe(String code, IMessageHandler handler) {
        MessageBus.removeSubscription(code, handler);
    }

}
