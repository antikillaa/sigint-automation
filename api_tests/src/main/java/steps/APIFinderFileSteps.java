package steps;

import http.OperationResult;
import model.*;
import model.entities.Entities;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.FinderFileService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static json.JsonConverter.toJsonString;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class APIFinderFileSteps extends APISteps {

    private static FinderFileService service = new FinderFileService();
    private static Logger log = Logger.getLogger(APIFinderFileSteps.class);

    static FinderFile getRandomFinderFile() {
        return objectInitializer.randomEntity(FinderFile.class);
    }


    @When("I send create finder file request")
    public void sendCreateFileRequest() {
        FinderFile finderFile = getRandomFinderFile();

        context.put("finderFile", finderFile);
        service.add(finderFile);
    }


    @Then("Created finder file is correct")
    public void finderFileCorrect() {
        FinderFile contextFile = context.get("finderFile", FinderFile.class);
        FinderFile createdFile = Entities.getFinderFiles().getLatest();

        filesShouldBeEqual(contextFile, createdFile);
    }


    private void filesShouldBeEqual(FinderFile expected, FinderFile created) {
        assertEquals(expected.getType(), created.getType());
        assertEquals(expected.getBaseType(), created.getBaseType());
        assertEquals(expected.getClassification(), created.getClassification());
        assertEquals(expected.getName(), created.getName());
        assertEquals(expected.getDescription(), created.getDescription());
        assertEquals(expected.getDescription(), created.getDescription());
    }


    @When("I send delete finder file request")
    public void deleteFinderFileRequest() {
        FinderFile finderFile = Entities.getFinderFiles().getLatest();
        context.put("finderFile", finderFile);
        service.remove(finderFile);
    }


    @When("I send get list of finder file request")
    public void getListOfFinderFileRequest() {
        FinderFileSearchFilter filter = new FinderFileSearchFilter();
        filter.setSortField("name");
        filter.setPageSize(100);

        List<FinderFile> finderFiles = new ArrayList<>();
        OperationResult<List<FinderFile>> operationResult;
        do {
            operationResult = service.getTopLevelFiles(filter);
            finderFiles.addAll(operationResult.getEntity());
            filter.setPage(filter.getPage() + 1);
        } while (operationResult.getEntity().size() == filter.getPageSize());

        context.put("finderFileList", finderFiles);
    }


    @Then("Created finder file $criteria list")
    public void finderFilesContainNewFinderFile(String criteria) {
        FinderFile finderFile = Entities.getFinderFiles().getLatest();
        List<FinderFile> finderFiles = context.get("finderFileList", List.class);

        FinderFile foundFiles = finderFiles.stream()
                .filter(file -> Objects.equals(file.getId(), finderFile.getId()))
                .findFirst().orElse(null);

        switch (criteria.toLowerCase()) {
            case "in":
                Assert.assertNotNull(
                        "Finder file not founded in response. File:" + toJsonString(finderFile),
                        foundFiles);
                break;
            case "not in":
                Assert.assertNull(
                        "Finder file founded in response. File:" + toJsonString(finderFile),
                        foundFiles);
                break;
            default:
                throw new AssertionError("Incorrect argument passed to step");
        }
    }


    @When("I send create new child finder file request")
    public void creatNewChildFinderFile() {
        FinderFile finderFile = Entities.getFinderFiles().getLatest();
        FinderFile childFile = getRandomFinderFile();
        childFile.setParentFileId(finderFile.getId());

        context.put("parentFile", finderFile);
        context.put("childFile", childFile);
        service.add(childFile);
    }


    @When("I send get finder file details request")
    public void getFinderFileRequest() {
        FinderFile createdFinderFile = Entities.getFinderFiles().getLatest();
        OperationResult<FinderFile> operationResult = service.view(createdFinderFile.getId());
        context.put("finderFile", operationResult.getEntity());
    }


    @Then("created nested finder file is correct")
    public void createdNestedFinderFileShouldBeCorrect() {
        FinderFile createdFile = Entities.getFinderFiles().getLatest();
        FinderFile childFile = context.get("childFile", FinderFile.class);
        FinderFile parentFile = context.get("parentFile", FinderFile.class);

        filesShouldBeEqual(createdFile, childFile);
        assertEquals(createdFile.getParentFileId(), parentFile.getId());

        //TODO parentChain
        //Assert.assertTrue(createdFile.getParentChain().stream()
        // .anyMatch(parentChain -> parentChain.getId().equals(parentFile.getId())));
    }


    @When("I send get content of parent finder file")
    public void getNestedFinderFileDetails() {
        FinderFile nestedFile = Entities.getFinderFiles().getLatest();
        OperationResult<ProfileAndTargetGroup[]> operationResult = service.getContent(nestedFile.getParentFileId());
        context.put("finderFileContent", operationResult.getEntity());
    }


    @Then("Finder file content contains created nested file")
    public void finderFileContentShouldHaveNestedFile() {
        FinderFile finderFile = Entities.getFinderFiles().getLatest();
        ProfileAndTargetGroup[] profileAndFiles = context.get("finderFileContent", ProfileAndTargetGroup[].class);

        Assert.assertTrue(
                Arrays.stream(profileAndFiles)
                        .anyMatch(p -> p.getId().equals(finderFile.getId())));
    }

    @Then("Finder file content contains created profile")
    public void finderFileContentShouldHaveProfile() {
        Profile profile = Entities.getProfiles().getLatest();
        ProfileAndTargetGroup[] profileAndGroups = context.get("finderFileContent", ProfileAndTargetGroup[].class);

        Assert.assertTrue(
                Arrays.stream(profileAndGroups)
                        .anyMatch(p -> p.getId().equals(profile.getId())));
    }

    @When("I delete all QE auto files")
    public void deleteAllEmptyFiles() {
        getListOfFinderFileRequest();
        List<FinderFile> files = context.get("finderFileList", List.class);

        files.stream()
                .filter(file -> file.getName().contains("QE auto"))
                .forEach(service::remove);
    }


    @When("I search finder file members by query:$name")
    public void searchProfileByName(String name) {
        FinderFileSearchFilter filter = new FinderFileSearchFilter();
        filter.setQuery(name).setSortField("name").setPageSize(100);

        OperationResult<List<ProfileAndTargetGroup>> operationResult = service.searchFileMembers(filter);
        context.put("operationResult", operationResult);
    }


    @When("Get profiles from targets search results")
    public void extractProfiles() {
        OperationResult<List<ProfileAndTargetGroup>> operationResult =
                context.get("operationResult", OperationResult.class);

        List<Profile> profiles = service.extractProfilesFromResponse(operationResult);
        context.put("profileList", profiles);
    }


//    @Then("Viewed target group is correct")
//    public void viewedTargetGroupIsCorrect() {
//        TargetGroup createdTarget = Entities.getTargetGroups().getLatest();
//        TargetGroup viewedTargetGroup = context.get("targetGroup", TargetGroup.class);
//        equalsTargetGroups(viewedTargetGroup, createdTarget);
//    }
//
//
//    @When("I get random target group from targetGroup list")
//    public void getTargetGroupFrolList() {
//        List<TargetGroup> targetGroups = context.get("targetGroupList", List.class);
//        context.put("targetGroup", getRandomItemFromList(targetGroups));
//    }
//
//
//    @When("I send update target group request")
//    public void updateTargetGroupRequest() {
//        TargetGroup createdTargetGroup = Entities.getTargetGroups().getLatest();
//        TargetGroup updatedTargetGroup = updateTargetGroup(createdTargetGroup);
//        OperationResult<TargetGroup> operationResult = service.update(updatedTargetGroup);
//        context.put("updatedTargetGroup", operationResult.getEntity());
//    }
//
//    @Then("Target group updated correctly")
//    public void targetGroupUpdatedCorrectly() {
//        TargetGroup updatedTargetGroup = context.get("updatedTargetGroup", TargetGroup.class);
//        TargetGroup responseTargetGroup = Entities.getTargetGroups().getLatest();
//        equalsTargetGroups(responseTargetGroup, updatedTargetGroup);
//    }
//
//    @Then("existing group is listed in list only once")
//    public void groupNotDuplicated() {
//        List<TargetGroup> targetGroups = Entities.getTargets().getLatest().getGroups();
//        List<TargetGroup> groups = context.get("targetGroupList", List.class);
//        for (TargetGroup uploadedGroup : targetGroups) {
//            int count = 0;
//            for (TargetGroup group : groups) {
//                if (uploadedGroup.getName().equals(group.getName())) {
//                    Entities.getTargetGroups().updateEntity(uploadedGroup, group);
//                    count += 1;
//                }
//            }
//            Assert.assertTrue(count == 1);
//        }
//    }
//
//
//    static TargetGroup getRandomTargetGroup() {
//        return objectInitializer.randomEntity(TargetGroup.class);
//    }
//
//    private TargetGroup updateTargetGroup(TargetGroup targetGroup) {
//        targetGroup.setName(RandomStringUtils.randomAlphabetic(10));
//        targetGroup.setDescription(RandomStringUtils.randomAlphabetic(20));
//        return targetGroup;
//    }
//
//    @Then("Target group list size more than $size")
//    public void targetGroupListMoreThan(String size) {
//        List<TargetGroup> groups = context.get("targetGroupList", List.class);
//
//        Assert.assertTrue(groups.size() > Integer.valueOf(size));
//    }
//
//
//   /**
//     * Verify two targetGroups
//     *
//     * @param expected checked targetGroup
//     * @param actual   etalon targetGroup
//     */
//    static boolean equalsTargetGroups(TargetGroup expected, TargetGroup actual) {
//        if (expected == null && actual == null) {
//            return true;
//        } else if (expected == null || actual == null) {
//            log.error("At least of one of compared targetGroup is null" +
//                    ", actual: " + JsonConverter.toJsonString(actual) +
//                    ", expected: " + JsonConverter.toJsonString(expected));
//            return false;
//        }
//
//        try {
//            Verify.shouldBe(Conditions.equals(expected.getJsonType(), actual.getJsonType()));
//            Verify.shouldBe(Conditions.equals(expected.getName(), actual.getName()));
//            Verify.shouldBe(Conditions.equals(expected.getGroups(), actual.getGroups()));
//            Verify.shouldBe(Conditions.equals(expected.getNoGroups(), actual.getNoGroups()));
//            Verify.shouldBe(Conditions.equals(expected.getNoProfiles(), actual.getNoProfiles()));
//            Verify.shouldBe(Conditions.equals(expected.getNoSavedSearches(), actual.getNoSavedSearches()));
//            Verify.shouldBe(Conditions.equals(expected.getProfiles(), actual.getProfiles()));
//            Verify.shouldBe(Conditions.equals(expected.getThreatScore(), actual.getThreatScore()));
//            Verify.shouldBe(Conditions.equals(expected.isDeleted(), actual.isDeleted()));
//            Verify.shouldBe(Conditions.equals(expected.getAssignedTeams(), expected.getAssignedTeams()));
//            Verify.shouldBe(Conditions.equals(expected.getAssignedUsers(), expected.getAssignedUsers()));
//            Verify.shouldBe(Conditions.equals(expected.getAssignmentPriority(), expected.getAssignmentPriority()));
//            // Note: new targetGroups keep description in TargetGroupProperties
//            Assert.assertTrue(expected.getDescription() == null ?
//                    Objects.equals(expected.getProperties().getDescription(), actual.getProperties().getDescription()) :
//                    Objects.equals(expected.getDescription(), actual.getProperties().getDescription()));
//            Verify.shouldBe(Conditions.equals(
//                    JsonConverter.toJsonString(expected.getParentChain()),
//                    JsonConverter.toJsonString(actual.getParentChain())));
//            Assert.assertTrue(equalsTargetGroups(expected.getParent(), actual.getParent()));
//        } catch (Exception e) {
//            log.error(e);
//            return false;
//        }
//
//        return true;
//    }
//
//    static boolean equalsTargetGroups(List<TargetGroup> expected, List<TargetGroup> actual) {
//        if (actual.isEmpty() && expected.isEmpty()) {
//            return true;
//        } else if (actual.isEmpty() || expected.isEmpty()) {
//            log.error("At least of one of compared targetGroup lists is empty" +
//                    ", actual: " + JsonConverter.toJsonString(actual) +
//                    ", expected: " + JsonConverter.toJsonString(expected));
//            return false;
//        } else if (expected.size() != actual.size()) {
//            log.error("TargetGroup collections have different size! " +
//                    " ExpectedGroups size: " + expected.size() +
//                    " actualGroups size: " + expected.size());
//            return false;
//        }
//
//        TargetGroup[] expectedGroups = new TargetGroup[0];
//        TargetGroup[] actualGroups = new TargetGroup[0];
//        expectedGroups = expected.toArray(expectedGroups);
//        actualGroups = actual.toArray(actualGroups);
//
//        Arrays.sort(expectedGroups);
//        Arrays.sort(actualGroups);
//
//        for (int i = 0; i < expectedGroups.length; i++) {
//            if (!equalsTargetGroups(expectedGroups[i], actualGroups[i])) {
//                log.error("Target groups not equals" +
//                        ", " + JsonConverter.toJsonString(expectedGroups[i]) +
//                        ", " + JsonConverter.toJsonString(actualGroups[i]));
//                return false;
//            }
//        }
//        return true;
//    }
}
