package data_for_entity.data_providers.profiler;

import data_for_entity.data_providers.EntityDataProvider;
import model.AssignmentPriority;

public class AssignmentPriorityProvider implements EntityDataProvider {

    @Override
    public AssignmentPriority generate(int length) {
        return AssignmentPriority.random();
    }
}
