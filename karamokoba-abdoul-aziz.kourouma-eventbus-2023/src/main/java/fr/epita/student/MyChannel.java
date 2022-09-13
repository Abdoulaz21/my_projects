package fr.epita.student;

import fr.epita.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MyChannel<MESSAGE_TYPE> implements EventBus.Channel {
    private final String name;
    Class<MESSAGE_TYPE> messageClass;

    public List<MySubscription> mySubscriptionList;
    public List<MESSAGE_TYPE> messages = new ArrayList<>();

    public MyChannel(Class<MESSAGE_TYPE> messageClass, String name){
        this.name = name;
        this.messageClass = messageClass;
        mySubscriptionList = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void publish(Object message) {
        if (mySubscriptionList.isEmpty())
            messages.add((MESSAGE_TYPE) message);
        else {
            for (var sub : mySubscriptionList){
                sub.post(message);
                for (var saved : messages){
                    sub.post(saved);
                }
            }
            messages.clear();
        }
    }

    @Override
    public void send(Object message) {
        if (mySubscriptionList.isEmpty())
            messages.add((MESSAGE_TYPE) message);
        else {
            var random = mySubscriptionList.get(new Random().nextInt(mySubscriptionList.size()));
            random.post(message);
            for (var saved : messages){
                for (var sub : mySubscriptionList) {
                    sub.post(saved);
                }
            }
            messages.clear();
        }
    }

    public void close() {
        for (var sub : mySubscriptionList){
            sub.close();
        }
    }
}
