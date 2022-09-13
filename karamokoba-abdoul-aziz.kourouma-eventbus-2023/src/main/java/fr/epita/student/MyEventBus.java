package fr.epita.student;

import fr.epita.eventbus.EventBus;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyEventBus implements EventBus {
    List<MyChannel> ChannelList = new ArrayList<>();
    public MyEventBus(){}

    @Override
    public @NotNull <MESSAGE_TYPE> Channel<MESSAGE_TYPE> createChannel(Class<MESSAGE_TYPE> messageClass, String channelName) throws DuplicatedChannelException {
        if(findChannel(channelName).isPresent())
            throw new DuplicatedChannelException("Channel already exists!");
        MyChannel<MESSAGE_TYPE> myChannel = new MyChannel<MESSAGE_TYPE>(messageClass, channelName);
        ChannelList.add(myChannel);
        return myChannel;
    }

    @Override
    public <MESSAGE_TYPE> Subscription<MESSAGE_TYPE> subscribe(String channelName) throws NoSuchChannelException {
        var myChannel = findChannel(channelName);
        if (myChannel.isEmpty())
            throw new NoSuchChannelException("Channel requested does not exist!");
        MySubscription<MESSAGE_TYPE> mySubscription = new MySubscription<MESSAGE_TYPE>((MyChannel) myChannel.get());
        ((MyChannel<MESSAGE_TYPE>) myChannel.get()).mySubscriptionList.add(mySubscription);
        return mySubscription;
    }

    @Override
    public @NotNull <MESSAGE_TYPE> Optional<Channel<MESSAGE_TYPE>> findChannel(String channelName) {
        for (var channel : ChannelList){
            if (channel.getName().equals(channelName))
                return Optional.of(channel);
        }
        return Optional.empty();
    }

    @Override
    public void close() {
        /*for (var c : ChannelList){
            c.close();
        }*/
        ChannelList.clear();
    }

    @Override
    public <MESSAGE_TYPE> void publish(Channel<MESSAGE_TYPE> channel, MESSAGE_TYPE message) {
        channel.publish(message);
    }

    @Override
    public <MESSAGE_TYPE> void send(Channel<MESSAGE_TYPE> channel, MESSAGE_TYPE message) {
        channel.send(message);
    }

    @Override
    public <MESSAGE_TYPE> void unSubscribe(Subscription<MESSAGE_TYPE> subscription) {
        subscription.close();
    }
}
