package net.scriptronix.snakegame.message;

/**
 * Interface for classes that want to listen to interface with the message bus.
 */
public interface IMessageHandler {
     void onMessage(Message msg);
}
