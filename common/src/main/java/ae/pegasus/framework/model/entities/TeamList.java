package ae.pegasus.framework.model.entities;

import ae.pegasus.framework.model.Team;

class TeamList extends EntityList<Team> {

    @Override
    public Team getEntity(String name) {
        return entities.stream()
                .filter(team -> team.getFullName().equals(name))
                .findAny().orElse(null);
    }
}
