package fr.epita.eventbus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Definition of an event bus.
 */
public interface EventBus {

    /**
     * Create the channel with the given {@code channelName} name. This channel will transport messages of the
     * {@code MESSAGE_TYPE} class.
     *
     * @param messageClass   The class of transported messages, may not be null.
     * @param channelName    The name of the channel, may not be blank or null.
     * @param <MESSAGE_TYPE> The type of transported messages.
     * @return The created channel.
     */
    <MESSAGE_TYPE>
    @NotNull Channel<MESSAGE_TYPE> createChannel(@NotNull final Class<MESSAGE_TYPE> messageClass,
                                                 @NotBlank final String channelName) throws DuplicatedChannelException;

    /**
     * Subscribe to the given channel.
     *
     * @param channelName    The name of the channel to subscribe to.
     * @param <MESSAGE_TYPE> The type of message transiting on the channel.
     * @return The subscription object.
     * @throws NoSuchChannelException If the requested channel does not exist (yet?).
     */
    <MESSAGE_TYPE>
    Subscription<MESSAGE_TYPE> subscribe(@NotBlank final String channelName) throws NoSuchChannelException;

    /**
     * Find the channel with the given {@code channelName} name.
     *
     * @param channelName    The name of the channel to find, may not be blank or null.
     * @param <MESSAGE_TYPE> The type of transported messages.
     * @return An option of the found channel.
     */
    <MESSAGE_TYPE>
    @NotNull Optional<Channel<MESSAGE_TYPE>> findChannel(@NotBlank final String channelName);

    /**
     * Close the event bus, and all associated resources.
     */
    void close();

    /**
     * Publish the given {@code message} to the given channel, all subscriptions on the channel will receive it.
     * If no subscriber is registered, the message is kept for further dispatch.
     *
     * @param channel The channel to publish to, may not be null.
     * @param message The message to publish, may not be null.
     */
    <MESSAGE_TYPE>
    void publish(@NotNull final Channel<MESSAGE_TYPE> channel, @NotNull final MESSAGE_TYPE message);

    /**
     * Publish the given {@code message} to the given channel, only one subscriber(randomized) will receive it.
     * If no subscriber is registered, the message is kept for further dispatch.
     *
     * @param channel The channel to publish to, may not be null.
     * @param message The message to publish, may not be null.
     */
    <MESSAGE_TYPE>
    void send(@NotNull final Channel<MESSAGE_TYPE> channel, @NotNull final MESSAGE_TYPE message);

    /**
     * Close the given subscription.
     *
     * @param subscription   The subscription to close, may not be null.
     * @param <MESSAGE_TYPE> The type of message transiting though the subscription.
     */
    <MESSAGE_TYPE>
    void unSubscribe(@NotNull Subscription<MESSAGE_TYPE> subscription);

    /**
     * A communication channel on the event bus. Each channel supports only one message type.
     *
     * @param <MESSAGE_TYPE> The type of messages expected on this channel.
     */
    interface Channel<MESSAGE_TYPE> {

        /**
         * Return the unique name of the channel
         *
         * @return The name of the channel.
         */
        @NotNull String getName();

        /**
         * Publish the given {@code message} to this channel, all subscriptions on the channel will receive it.
         * If no subscriber is registered, the message is kept for further dispatch.
         *
         * @param message The message to publish, may not be null.
         */
        void publish(@NotNull final MESSAGE_TYPE message);

        /**
         * Publish the given {@code message} to this channel, only one subscriber (randomized) will receive it.
         * If no subscriber is registered, the message is kept for further dispatch.
         *
         * @param message The message to publish, may not be null.
         */
        void send(@NotNull final MESSAGE_TYPE message);

    }

    /**
     * Record of a registration to the event bus.
     *
     * @param <MESSAGE_TYPE> Type of messages transiting on this channel.
     */
    interface Subscription<MESSAGE_TYPE> {

        /**
         * Get the channel of this subscription. May not be {@code null}.
         *
         * @return The channel of this subscription.
         */
        @NotNull Channel<MESSAGE_TYPE> getChannel();

        /**
         * Close the subscription.
         */
        void close();

        /**
         * Get the next message on the channel. Blocks until a message is present.
         *
         * @return The next message on the channel.
         */
        @NotNull MESSAGE_TYPE next();

        /**
         * Get the next message on the channel.
         * Blocks up to {@code timeout} milliseconds or until a message is present.
         *
         * @return An optional containing either the next message or {@code null} if the timeout was hit.
         */
        @NotNull Optional<MESSAGE_TYPE> next(final long timeout);

        /**
         * Listen to the channel continuously, and for each received message apply the given consumer.
         *
         * @param consumer The consumer to apply to all received messages.
         */
        void onMessage(@NotNull final Consumer<MESSAGE_TYPE> consumer);

        /**
         * Post one message. Will be used by the event bus to propagate incoming messages.
         *
         * @param message The message to post.
         */
        void post(@NotNull final MESSAGE_TYPE message);

    }

    /**
     * Exception class for invalid null parameters.
     */
    class InvalidNullParameter extends RuntimeException {

        /**
         * Name of the faulty parameter value.
         */
        public final @NotBlank String parameterName;

        /**
         * Default CTor.
         *
         * @param parameterName Initializer.
         */
        public InvalidNullParameter(@NotBlank final String parameterName) {
            super("Parameter " + parameterName + " has been set to null.");
            this.parameterName = parameterName;
        }
    }

    /**
     * Exception class for invalid blank parameters.
     */
    class InvalidBlankParameter extends RuntimeException {

        /**
         * Name of the faulty parameter value.
         */
        public final @NotBlank String parameterName;

        /**
         * Default CTor.
         *
         * @param parameterName Initializer.
         */
        public InvalidBlankParameter(@NotBlank final String parameterName) {
            super("Parameter " + parameterName + " has been set to blank or null.");
            this.parameterName = parameterName;
        }
    }

    /**
     * Exception class for channel duplication.
     */
    class DuplicatedChannelException extends RuntimeException {

        /**
         * Name of the duplicated channel.
         */
        public final @NotBlank String channelName;

        /**
         * Default CTor.
         *
         * @param channelName initializer.
         */
        public DuplicatedChannelException(@NotBlank final String channelName) {
            super("A channel with the given name has already been created: " + channelName);
            this.channelName = channelName;
        }
    }

    /**
     * Exception class for invalid channel resolution.
     */
    class NoSuchChannelException extends RuntimeException {

        /**
         * Name of the channel not existing.
         */
        public final @NotBlank String channelName;

        /**
         * Default CTor.
         *
         * @param channelName initializer.
         */
        public NoSuchChannelException(@NotBlank final String channelName) {
            super("No channel with this name exists: " + channelName);
            this.channelName = channelName;
        }
    }
}