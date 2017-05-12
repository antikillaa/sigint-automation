package model;

/**
 * used in Profiler API:
 * GET /targetGroups/root
 */
public class TargetGroupSearchFilter extends SearchFilter<TargetGroup> {

    @Override
    public boolean isAppliedToEntity(TargetGroup entity) {
        return false;
    }
}
