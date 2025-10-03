import workflowengine.EventBus;
import workflowengine.Subscriber;
import workflowengine.Workflow;

import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();

        final Function<List<Integer>, Integer> sumFunction = integers -> integers.stream().reduce(0, Integer::sum);
        final Function<Integer, Integer> doubleFunction = input -> input * 2;
        final Function<Integer, Integer> squareFunction = input -> input * input;

        final Workflow workflow1 = new Workflow();
        workflow1.addStep(sumFunction);
        workflow1.addStep(doubleFunction);
        workflow1.addStep(squareFunction);

        final Subscriber subscriber = new Subscriber("sub1", List.of(workflow1));

        eventBus.subscribe("numbers-topic", subscriber);
        eventBus.publish("numbers-topic", List.of(1, 2, 3));
    }
}
