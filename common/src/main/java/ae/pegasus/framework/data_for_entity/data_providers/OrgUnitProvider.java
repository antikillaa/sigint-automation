package ae.pegasus.framework.data_for_entity.data_providers;

import static ae.pegasus.framework.services.EntityService.appContext;
import static ae.pegasus.framework.utils.RandomGenerator.getRandomItemFromList;

public class OrgUnitProvider implements EntityDataProvider {

    @Override
    public String generate(int length) {
        return  appContext.getLoggedUser() == null ?
                null :
                getRandomItemFromList(appContext.getLoggedUser().getUser().getEffectivePermission().getRecord().getOrganizations());
    }
}
