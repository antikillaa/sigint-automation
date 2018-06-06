package ae.pegasus.framework.steps;

import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.model.*;
import ae.pegasus.framework.model.entities.Entities;
import ae.pegasus.framework.services.FinderCaseService;
import ae.pegasus.framework.services.FinderFileService;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ae.pegasus.framework.json.JsonConverter.toJsonString;
import static ae.pegasus.framework.utils.RandomGenerator.getRandomItemFromList;
import static ae.pegasus.framework.utils.StepHelper.compareByCriteria;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class APIFinderFileSteps extends APISteps {

    private static FinderFileService serviceFile = new FinderFileService();
    private static FinderCaseService serviceCase = new FinderCaseService();
    private static Logger log = Logger.getLogger(APIFinderFileSteps.class);

    static FinderFile getRandomFinderFile() {
        return objectInitializer.randomEntity(FinderFile.class);
    }

    static FinderCase getRandomFinderCase() {
        return objectInitializer.randomEntity(FinderCase.class);
    }


    @When("I send create finder file request")
    public void sendCreateFileRequest() {
        FinderFile finderFile = getRandomFinderFile();

        context.put("finderFile", finderFile);
        serviceFile.add(finderFile);
    }

    @When("I send create finder case request")
    public void sendCreateCaseRequest() {
        FinderCase finderCase = getRandomFinderCase();
        FinderFile finderFile = Entities.getFinderFiles().getLatest();
        finderCase.setParentFileId(finderFile.getId());

        context.put("finderCase", finderCase);
        serviceCase.add(finderCase);
    }

    @When("I send delete finder case request")
    public void deleteFinderCaseRequest() {
        FinderCase finderCase = Entities.getFinderCases().getLatest();

        context.put("finderCase", finderCase);
        serviceCase.remove(finderCase);
    }

    @When("View created finder case")
    public void viewCreatedFinderCase() {
        FinderCase finderCase = Entities.getFinderCases().getLatest();

        context.put("finderCase", finderCase);
        serviceCase.view(finderCase.getId());
    }

    @When("I send edit finder case request")
    public void editFinderCaseRequest() {
        FinderCase finderCase = Entities.getFinderCases().getLatest();
        FinderCase editedCase = getRandomFinderCase();

        editedCase.setName(getRandomFinderCase().getName());
        editedCase.setDescription(getRandomFinderCase().getName());
        editedCase.getReqPermissions().get(0).setClassification(
                editedCase.getReqPermissions().get(0).getClassification());

        context.put("finderCase", finderCase);
        serviceCase.update(finderCase);
    }

    @Then("Created finder file is correct")
    public void finderFileCorrect() {
        FinderFile contextFile = context.get("finderFile", FinderFile.class);
        FinderFile createdFile = Entities.getFinderFiles().getLatest();

        filesShouldBeEqual(contextFile, createdFile);
    }

    @Then("Created finder case is correct")
    public void finderCaseCorrect() {
        FinderCase contextCase = context.get("finderCase", FinderCase.class);
        FinderCase createdCase = Entities.getFinderCases().getLatest();

        casesShouldBeEqual(contextCase, createdCase);
    }

    private void casesShouldBeEqual(FinderCase expected, FinderCase created) {
        assertEquals(expected.getType(), created.getType());
        assertEquals(expected.getBaseType(), created.getBaseType());
        assertEquals(toJsonString(expected.getReqPermissions()), toJsonString(created.getReqPermissions()));
        assertEquals(toJsonString(expected.getParentChain()), toJsonString(created.getParentChain()));
        assertEquals(expected.getAggregatedTypeCounts(), created.getAggregatedTypeCounts());
        assertEquals(expected.getName(), created.getName());
        assertEquals(expected.getDescription(), created.getDescription());
        assertEquals(expected.getDescription(), created.getDescription());
    }

    private void filesShouldBeEqual(FinderFile expected, FinderFile created) {
        assertEquals(expected.getType(), created.getType());
        assertEquals(expected.getBaseType(), created.getBaseType());
        assertEquals(toJsonString(expected.getReqPermissions()), toJsonString(created.getReqPermissions()));
        assertEquals(toJsonString(expected.getParentChain()), toJsonString(created.getParentChain()));
        assertEquals(expected.getAggregatedTypeCounts(), created.getAggregatedTypeCounts());
        assertEquals(expected.getName(), created.getName());
        assertEquals(expected.getDescription(), created.getDescription());
        assertEquals(expected.getDescription(), created.getDescription());
    }


    @When("I send delete finder file request")
    public void deleteFinderFileRequest() {
        FinderFile finderFile = Entities.getFinderFiles().getLatest();
        context.put("finderFile", finderFile);
        serviceFile.remove(finderFile);
    }


    @When("I send get list of finder file request")
    public void getListOfFinderFileRequest() {
        FinderFileSearchFilter filter = new FinderFileSearchFilter();
        filter.setSortField("name");
        filter.setPageSize(100);

        List<FinderFile> finderFiles = new ArrayList<>();
        OperationResult<List<FinderFile>> operationResult;
        do {
            operationResult = serviceFile.getFilesRoot(filter);
            if (operationResult.isSuccess()) {
                finderFiles.addAll(operationResult.getEntity());
                filter.setPage(filter.getPage() + 1);
            } else break;
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
        serviceFile.add(childFile);
    }


    @When("I send get finder file details request")
    public void getFinderFileRequest() {
        FinderFile createdFinderFile = Entities.getFinderFiles().getLatest();
        OperationResult<FinderFile> operationResult = serviceFile.view(createdFinderFile.getId());
        context.put("finderFile", operationResult.getEntity());
    }


    @Then("created nested finder file is correct")
    public void createdNestedFinderFileShouldBeCorrect() {
        FinderFile createdFile = Entities.getFinderFiles().getLatest();
        FinderFile childFile = context.get("childFile", FinderFile.class);
        FinderFile parentFile = context.get("parentFile", FinderFile.class);

        filesShouldBeEqual(createdFile, childFile);
        assertEquals(createdFile.getParentFileId(), parentFile.getId());
    }


    @When("I send get content of parent finder file")
    public void getNestedFinderFileDetails() {
        FinderFile nestedFile = Entities.getFinderFiles().getLatest();
        OperationResult<ProfileAndTargetGroup[]> operationResult = serviceFile.getContent(nestedFile.getParentFileId());
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

    @When("I delete all empty QE auto files")
    public void deleteAllEmptyQEFiles() {
        List<FinderFile> files = context.get("finderFileList", List.class);
        files.stream()
                .filter(file -> file.getName().contains("QE auto")
                        && file.getPayload() != null
                        && !file.getPayload().getHasContents())
                .forEach(serviceFile::remove);
    }

    @When("I delete all empty files")
    public void deleteAllEmptyFiles() {
        List<FinderFile> files = context.get("finderFileList", List.class);
        files.stream()
                .filter(file -> !file.getHasContents())
                .forEach(serviceFile::remove);
    }

    @When("I search finder file members by query:$name")
    public void searchProfileByName(String name) {
        FinderFileSearchFilter filter = new FinderFileSearchFilter();
        filter.setQuery(name).setSortField("name").setPageSize(100);

        OperationResult<List<ProfileAndTargetGroup>> operationResult = serviceFile.searchFileMembers(filter);
        context.put("operationResult", operationResult);
    }

    @When("I send CB Finder search with query:$query")
    public void cbFinderSearch(String query) {
        FinderFileSearchFilter filter = new FinderFileSearchFilter();
        filter.setQuery(query).setSortField("name").setPageSize(100);

        OperationResult<List<ProfileAndTargetGroup>> operationResult = serviceFile.cbFinderSearch(filter);
        context.put("operationResult", operationResult);
    }

    @When("Get random $type from from CBFinder search results")
    public void getRandomFileFromCBFinderSearchResults(String type) {
        OperationResult<List<ProfileAndTargetGroup>> operationResult =
                context.get("operationResult", OperationResult.class);

        switch (type) {
            case "profile":
                List<Profile> profiles = serviceFile.extractEntitiesByTypeFromResponse(operationResult, ProfileJsonType.Profile);
                Profile profile = getRandomItemFromList(profiles);
                context.put("profile", profile);
                Entities.getProfiles().addOrUpdateEntity(profile);
                break;
            case "file":
                List<FinderFile> files = serviceFile.extractEntitiesByTypeFromResponse(operationResult, ProfileJsonType.File);
                FinderFile file = getRandomItemFromList(files);
                context.put("finderFile", file);
                Entities.getFinderFiles().addOrUpdateEntity(file);
                break;
            default:
                throw new AssertionError("Unknown step parameter:" + type);
        }
    }

    @When("Get $type from CBFinder search results")
    public void extractProfiles(String type) {
        OperationResult<List<ProfileAndTargetGroup>> operationResult =
                context.get("operationResult", OperationResult.class);

        switch (type) {
            case "profiles":
                List<Profile> profiles = serviceFile.extractEntitiesByTypeFromResponse(operationResult, ProfileJsonType.Profile);
                context.put("profileList", profiles);
                break;
            case "files":
                List<FinderFile> files = serviceFile.extractEntitiesByTypeFromResponse(operationResult, ProfileJsonType.File);
                context.put("finderFileList", files);
                break;
            default:
                throw new AssertionError("Unknown step parameter:" + type);
        }
    }

    @Then("CB Finder search result list size $criteria $size")
    public void cbFinderSearchResultsListSizeShouldBe(String criteria, String value) {
        OperationResult<List<ProfileAndTargetGroup>> operationResult = context.get("operationResult", OperationResult.class);

        int expectedCount = Integer.valueOf(value);
        List<ProfileAndTargetGroup> entities = operationResult.getEntity();
        boolean condition = compareByCriteria(criteria, entities.size(), expectedCount);
        assertTrue("Expected cb finder search results count " + criteria + " " + value + ", but was: " + entities.size(), condition);
    }


    @Then("Finder files list size $criteria $size")
    public void targetGroupListMoreThan(String criteria, String size) {
        List<FinderFile> files = context.get("finderFileList", List.class);

        int expectedCount = Integer.valueOf(size);
        boolean condition = compareByCriteria(criteria, files.size(), expectedCount);
        assertTrue("Expected cb finder search results count " + criteria + " " + size + ", but was: " + files.size(), condition);
    }


    @When("I send update finder file request")
    public void updateTargetGroupRequest() {
        FinderFile createdFinderFile = Entities.getFinderFiles().getLatest();
        FinderFile randomFinderFile = getRandomFinderFile();

        createdFinderFile.setName(randomFinderFile.getName());
        createdFinderFile.setDescription(randomFinderFile.getDescription());
        createdFinderFile.getReqPermissions().get(0).setClassification(
                randomFinderFile.getReqPermissions().get(0).getClassification());

        context.put("finderFile", createdFinderFile);
        serviceFile.update(createdFinderFile);
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
