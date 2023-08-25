package net.scriptronix.snakegame.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Facilitates the transaction of messages throughout the application.
 */
public class MessageBus {

    private static HashMap<String, ArrayList<IMessageHandler>> subscriptions = new HashMap<>(); // <code, handlers>

    private static LinkedList<MessageSubscriptionNode> messageQueue = new LinkedList<>();

    private MessageBus() {
    } // Hide constructor

    /**
     * Subscribes a message handler to a message
     *
     * @param code
     * @param handler
     */
    public static void addSubscription(String code, IMessageHandler handler) {
        if (!MessageBus.subscriptions.containsKey(code)) {
            MessageBus.subscriptions.put(code, new ArrayList<>());
        }

        if (MessageBus.subscriptions.get(code).indexOf(handler) != -1) {
            // TODO: Warn about duplicate handler
        } else {
            MessageBus.subscriptions.get(code).add(handler);
        }
    }

    /**
     * Removes a message handler's subscription
     *
     * @param code
     * @param handler
     */
    public static void removeSubscription(String code, IMessageHandler handler) {
        if (!MessageBus.subscriptions.containsKey(code)) {
            // TODO: Warn thatwe cannot unsubscribe from a code that is not subscribed to.
            return;
        }

        MessageBus.subscriptions.get(code).remove(handler);

    }

    /**
     * Adds the message to the message queue, to be processed on the next tick
     *
     * @param msg
     */
    public static void post(Message msg) {
        ArrayList<IMessageHandler> handlers = MessageBus.subscriptions.get(msg.getCode());

        if (handlers == null) {
            return;
        }

        for (IMessageHandler handler : handlers) {
            MessageBus.messageQueue.add(new MessageSubscriptionNode(msg, handler));
        }
    }

    /**
     * Processes the message queue, broadcasting to each subscribed class
     */
    public static void update() {
        if (MessageBus.messageQueue.isEmpty()) {
            return;
        }
        
        int messageSendCount = MessageBus.messageQueue.size();
        for (int i = 0; i < messageSendCount; i++) {
            MessageSubscriptionNode node = MessageBus.messageQueue.remove();
            node.getHandler().onMessage(node.getMessage());
        }
    }
}
