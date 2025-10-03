package workflowengine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Workflow {
    private final List<Function<Object, Object>> functions = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public <I, O> void addStep(Function<I, O> function) {
        functions.add((Function<Object, Object>) function);
    }

    public Object execute(Object args) {
        for (final Function<Object, Object> function : functions) {
            args = function.apply(args);
        }
        return args;
    }
}
