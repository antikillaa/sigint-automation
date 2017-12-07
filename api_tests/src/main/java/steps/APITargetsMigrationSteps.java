package steps;

import http.OperationResult;
import model.G4File;
import model.Profile;
import model.TargetGroup;
import org.jbehave.core.annotations.When;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static services.TargetsMigrationService.*;
import static utils.RandomGenerator.*;

public class APITargetsMigrationSteps extends APISteps {

    private static List<Profile> targets = new ArrayList<>();
    private static List<TargetGroup> groups = new ArrayList<>();
    private static List<String> teamNames = new ArrayList<>(Arrays.asList("migration second team","migration team"));
    private static List<String> groupNames = new ArrayList<>(Arrays.asList("QE Group","Test's Group","DEV Group","Minsk Group","Support Group", "Shared Group", "Robbers", "Terrorists", "Others people"));

    @When("Produce targets $targetCount, groups $groupsCount")
    public void produceTargets(String targetCount, String groupsCount) {

        int targetsListSize = Integer.valueOf(targetCount);
        int groupsListSize = Integer.valueOf(groupsCount);

        for (int i = 0; i < groupsListSize; i++) {
            TargetGroup group = objectInitializer.randomEntity(TargetGroup.class);

            group.setId(generateID()); // update ID
            group.setName(getRandomItemFromList(groupNames)); // update name

            // update team
            List<String> teams = new ArrayList<>();
            Integer teamsCount = generateRandomInteger(1, 3);
            while (teams.size() < teamsCount) {
                teams.add(getRandomItemFromList(teamNames));
            }
            group.setAssignedTeams(teams);

            groups.add(group);
        }

        for (int i = 0; i < targetsListSize; i++) {
            Profile profile = objectInitializer.randomEntity(Profile.class);
            profile.setId(generateID()); // set ID

            Integer targetGroupsCount = generateRandomInteger(1, 5);
            ArrayList<TargetGroup> targetGroups = new ArrayList<>();
            for (int j = 0; j < targetGroupsCount; j++) {
                targetGroups.add(getRandomItemFromList(groups));
            }
            profile.setGroups(targetGroups); //set targetGroupsID

            targets.add(profile);
        }
    }

    @When("Write targets to file")
    public void writeTargetsToFile() {
        writeGroups(groups);
        writeTargets(targets);
        writeIdentifiers(targets);
        writeManualAttributes(targets);
        G4File file = writeFile();

        context.put("importTargetsFile", file);
    }

    @When("Upload targets excel file")
    public void uploadTargets() {
        G4File file = context.get("importTargetsFile", G4File.class);
        OperationResult operationResult = uploadTargetsFile(file);

        context.put("operationResult", operationResult);
    }
}
