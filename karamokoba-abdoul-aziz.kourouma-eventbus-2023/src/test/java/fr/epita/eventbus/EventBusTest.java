package fr.epita.eventbus;

import fr.epita.reflect.ReflectionUtils;
import fr.epita.utils.ThreadUtils;
import org.junit.jupiter.api.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static fr.epita.reflect.Exceptions.runtime;

public class EventBusTest {

    private static final String CHANNEL_NAME = "/test";

    private EventBus eventBus;

    @AfterEach public void cleanUp() {
        this.eventBus.close();
    }

    @BeforeEach public void setUp() {
        this.eventBus = ReflectionUtils.instanceOf(EventBus.class, "fr.epita.student")
                .orElseThrow(() -> new RuntimeException("Cannot find or create an EventBus instance."));
    }

    @Test
    @DisplayName("Standard channel creation test.")
    public void testChannelCreate() throws EventBus.DuplicatedChannelException {
        final var channel = eventBus.createChannel(StringMessage.class, CHANNEL_NAME);

        Assertions.assertNotNull(channel);
        Assertions.assertEquals(channel.getName(), CHANNEL_NAME);
    }

    @Test
    @DisplayName("Duplicated channel creation test.")
    public void testChannelCreateDuplicate() throws EventBus.DuplicatedChannelException {
        eventBus.createChannel(StringMessage.class, CHANNEL_NAME);

        Assertions.assertThrows(EventBus.DuplicatedChannelException.class,
                () -> eventBus.createChannel(StringMessage.class, CHANNEL_NAME));
    }

    @Test
    @DisplayName("Find existent channel test.")
    public void testFindExistentChannel() {
        final var channel = eventBus.createChannel(StringMessage.class, CHANNEL_NAME);
        final var result = eventBus.findChannel(CHANNEL_NAME);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(channel, result.get());
    }

    @Test
    @DisplayName("Find non-existent channel test.")
    public void testFindNonExistentChannel() {
        final var channel = eventBus.createChannel(StringMessage.class, CHANNEL_NAME);
        final var result = eventBus.findChannel("pwet");

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Subscribe to any channel.")
    public void testSubscribeToAnyChannel() throws EventBus.NoSuchChannelException {
        eventBus.createChannel(StringMessage.class, CHANNEL_NAME);

        final var subscription = eventBus.subscribe(CHANNEL_NAME);

        Assertions.assertNotNull(subscription);
        Assertions.assertEquals(CHANNEL_NAME, subscription.getChannel().getName());
    }

    @Test
    @DisplayName("Subscribe to non-existent channel.")
    public void testSubscribeToNonExistentChannel() throws EventBus.NoSuchChannelException {
        eventBus.createChannel(StringMessage.class, CHANNEL_NAME);

        Assertions.assertThrows(EventBus.NoSuchChannelException.class,
                () -> eventBus.subscribe("fail"));
    }

    @Test
    @DisplayName("Publish a simple message with one subscriber.")
    public void testSimplePublishWithOneSubscriber() throws ExecutionException, InterruptedException {

        final var channel = eventBus.createChannel(StringMessage.class, CHANNEL_NAME);
        final var subscription = eventBus.<StringMessage>subscribe(CHANNEL_NAME);
        final var readerFuture = ThreadUtils.dispatch(subscription::next);

        channel.publish(new StringMessage("hello!"));
        final var message = readerFuture.get();

        Assertions.assertEquals("hello!", message.content);
    }


    @Test
    @DisplayName("Publish a simple message with multiple subscribers.")
    public void testSimplePublishWithMultipleSubscribers() throws ExecutionException, InterruptedException {

        final var channel = runtime(() -> eventBus.createChannel(StringMessage.class, CHANNEL_NAME));

        final var subscriber1 = eventBus.<StringMessage>subscribe(CHANNEL_NAME);
        final var subscriber2 = eventBus.<StringMessage>subscribe(CHANNEL_NAME);
        final var subscriber3 = eventBus.<StringMessage>subscribe(CHANNEL_NAME);

        final var readerFuture1 = ThreadUtils.dispatch(subscriber1::next);
        final var readerFuture2 = ThreadUtils.dispatch(subscriber2::next);
        final var readerFuture3 = ThreadUtils.dispatch(subscriber3::next);

        channel.publish(new StringMessage("hello!"));

        Assertions.assertEquals("hello!", readerFuture1.get().content);
        Assertions.assertEquals("hello!", readerFuture2.get().content);
        Assertions.assertEquals("hello!", readerFuture3.get().content);

        eventBus.close();
    }

    @Test
    @DisplayName("Send a simple message with one subscriber.")
    public void testSimpleSendWithOneSubscriber() throws ExecutionException, InterruptedException {

        final var channel = eventBus.createChannel(StringMessage.class, CHANNEL_NAME);
        final var subscriber1 = eventBus.<StringMessage>subscribe(CHANNEL_NAME);
        final var readerFuture = ThreadUtils.dispatch(subscriber1::next);

        channel.send(new StringMessage("hello!"));
        final var message = readerFuture.get();

        Assertions.assertEquals("hello!", message.content);
    }


    @Test
    @DisplayName("Send a simple message with multiple subscribers.")
    public void testSimpleSendWithMultipleSubscribers() throws ExecutionException, InterruptedException {

        final var channel = eventBus.createChannel(StringMessage.class, CHANNEL_NAME);

        final var subscriber1 = eventBus.<StringMessage>subscribe(CHANNEL_NAME);
        final var subscriber2 = eventBus.<StringMessage>subscribe(CHANNEL_NAME);

        final var readerFuture1 = ThreadUtils.dispatch(subscriber1::next);
        final var readerFuture2 = ThreadUtils.dispatch(subscriber2::next);

        channel.send(new StringMessage("hello!"));

        final var result = (StringMessage) CompletableFuture.anyOf(readerFuture1, readerFuture2).get();

        Assertions.assertEquals("hello!", result.content);

        if (readerFuture1.isDone()) {
            Assertions.assertFalse(readerFuture2.isDone());
            readerFuture2.cancel(true);
        } else if (readerFuture2.isDone()) {
            Assertions.assertFalse(readerFuture1.isDone());
            readerFuture1.cancel(true);
        }

        subscriber1.close();
        subscriber2.close();

        eventBus.close();
    }

    public static class StringMessage {
        public final String content;

        public StringMessage(final String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "StringMessage[content: '" + content + "']";
        }
    }
}
