package fr.epita.student;

import fr.epita.eventbus.EventBus;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class MySubscription<MESSAGE_TYPE> implements EventBus.Subscription {
    MyChannel<MESSAGE_TYPE> myChannel;
    BlockingQueue<MESSAGE_TYPE> saved;

    public MySubscription(MyChannel myChannel){
        this.myChannel = myChannel;
        saved = new LinkedBlockingDeque<>();
    }

    @Override
    public EventBus.Channel getChannel() {
        return myChannel;
    }

    @Override
    public void close() {
        myChannel.mySubscriptionList.remove(this);
    }

    @Override
    public Object next() {
        try {
            return saved.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    @Override
    public Optional next(long timeout) {
        try {
            return Optional.of(saved.poll(timeout, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            return Optional.empty();
        }
    }

    @Override
    public void onMessage(Consumer consumer) {
        while (true){
            MESSAGE_TYPE message = null;
            try {
                message = saved.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            consumer.accept(message);
        }
    }

    @Override
    public void post(Object message) {
        saved.add((MESSAGE_TYPE) message);
    }
}
