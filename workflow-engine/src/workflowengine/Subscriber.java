package workflowengine;

import java.util.List;

public record Subscriber(String name, List<Workflow> workflows) {}
