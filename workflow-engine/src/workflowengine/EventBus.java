package workflowengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private final Map<String, List<Subscriber>> topicToSubscribers = new HashMap<>();

    public void subscribe(String topic, Subscriber subscriber) {
        topicToSubscribers.computeIfAbsent(topic, _ -> new ArrayList<>()).add(subscriber);
    }

    public void publish(String topic, Object input) {
        final List<Subscriber> subscribers = topicToSubscribers.getOrDefault(topic, new ArrayList<>());
        for (final Subscriber subscriber : subscribers) {
            for (final Workflow workflow : subscriber.workflows()) {
                final Object result = workflow.execute(input);
                System.out.printf("[%s] %s: %s\n", topic, subscriber.name(), result);
            }
        }
    }
}