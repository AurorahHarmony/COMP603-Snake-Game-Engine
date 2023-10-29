package net.scriptronix.snakegame.message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class MessageBusTest {

    private IMessageHandler mockHandler;
    private Message testMessage;

    @Before
    public void setUp() throws Exception {
        // Initialize mock objects
        mockHandler = mock(IMessageHandler.class);
        testMessage = new Message("testCode", this, "testContent");

        // Ensure MessageBus is in a clean state before each test
        MessageBus.clearSubscriptions();
        MessageBus.clearMessageQueue();
    }

    @After
    public void tearDown() throws Exception {
        MessageBus.clearSubscriptions();
        MessageBus.clearMessageQueue();
    }

    /**
     * Test that MessageHandlers are called when they subscribe to a message
     */
    @Test
    public void testMessageIsReceivedBySubscribedHandler() {
        MessageBus.addSubscription("testCode", mockHandler);

        MessageBus.post(testMessage);

        MessageBus.update();
        verify(mockHandler).onMessage(testMessage);
        verify(mockHandler, times(1)).onMessage(testMessage);
    }

    /**
     * Tests that a MessageHandler does not receive any message after unsubscription
     */
    @Test
    public void testMessageIsNotReceivedByUnsubscribedHandler() {
        MessageBus.addSubscription("testCode", mockHandler);
        MessageBus.removeSubscription("testCode", mockHandler);

        MessageBus.post(testMessage);
        
        MessageBus.update();
        verify(mockHandler, never()).onMessage(any(Message.class));
    }
}
