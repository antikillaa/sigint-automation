package steps;

import errors.NullReturnException;
import http.OperationResult;
import model.*;
import model.entities.Entities;
import model.entities.EntityList;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.ProfileDraftService;
import services.ProfileService;
import verification.profiler.ProfileMergeVerification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ingestion.IngestionService.INJECTIONS_FILE;
import static json.JsonConverter.jsonToObject;
import static json.JsonConverter.toJsonString;
import static utils.FileHelper.readTxtFile;
import static utils.RandomGenerator.getRandomItemFromList;
import static utils.StepHelper.compareByCriteria;
import static utils.StringUtils.readStringFromFile;
import static utils.StringUtils.saveStringToFile;

@SuppressWarnings("unchecked")
public class APIProfileSteps extends APISteps {

    private static final ProfileDraftService draftService = new ProfileDraftService();
    private static final ProfileService service = new ProfileService();

    @When("I send create profile draft request")
    public void createProfileDraft() {
        Profile profile = getRandomProfile();
        context.put("profileDraft", profile);

        draftService.add(profile);
    }

    private static Profile getRandomProfile() {
        return objectInitializer.randomEntity(Profile.class);
    }

    @Then("Profile draft is correct")
    public void profileDraftIsCorrect() {
        Profile requestedProfile = context.get("profileDraft", Profile.class);
        Profile createdProfile = Entities.getProfiles().getLatest();

        profilesShouldBeEquals(requestedProfile, createdProfile);

        context.put("profileDraft", createdProfile);
    }

    private void profilesShouldBeEquals(Profile expected, Profile actual) {
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getType(), actual.getType());
        Assert.assertEquals(expected.getActive(), actual.getActive());
        Assert.assertEquals(expected.getProperties().getDescription(), actual.getProperties().getDescription());

        Assert.assertTrue(APITargetGroupSteps.equalsTargetGroups(actual.getGroups(), expected.getGroups()));

        Assert.assertEquals(expected.getEntities(), actual.getEntities());
        //FIXME Assert.assertEquals(expected.getEntityCount(), actual.getEntityCount());

        Assert.assertEquals(expected.getJsonType(), actual.getJsonType());
        Assert.assertEquals(expected.getCategory(), actual.getCategory());
        Assert.assertEquals(expected.getMergingProfilesIDs(), actual.getMergingProfilesIDs());
        Assert.assertEquals(expected.getCriminalRecord(), actual.getCriminalRecord());
        Assert.assertEquals(expected.getClassification(), actual.getClassification());

        if (expected.getTarget() == null) {
            Assert.assertTrue(actual.getTarget() == null || !actual.getTarget());
        } else {
            Assert.assertEquals(expected.getTarget(), actual.getTarget());
        }
    }

    @When("I send delete profile draft request")
    public void deleteLatestProfile() {
        Profile profile = Entities.getProfiles().getLatest();
        draftService.remove(profile);
    }

    @When("I send get profile draft details request")
    public void getProfileDraftDetails() {
        Profile createdProfile = Entities.getProfiles().getLatest();
        context.put("profileDraft", createdProfile);
        draftService.view(createdProfile.getId());
    }

    @When("I send publish profile draft request")
    public void publishProfileDraft() {
        Profile profile = context.get("profileDraft", Profile.class);
        OperationResult<Profile> operationResult = draftService.publish(profile);
        context.put("profile", operationResult.getEntity());
    }

    @When("I send get profile details request")
    public void getProfileDetails() {
        Profile profile = context.get("profile", Profile.class);
        OperationResult<Profile> result = service.view(profile.getId());

        if (result.isSuccess()) {
            context.put("profile", result.getEntity());
        }
    }

    @Then("Profile is correct")
    public void profileIsCorrect() throws NullReturnException {
        Profile requested = context.get("profile", Profile.class);
        Profile created = service.getByName(requested.getName());

        profilesShouldBeEquals(requested, created);
    }

    @When("I send delete profile request")
    public void deleteProfile() {
        Profile profile = Entities.getProfiles().getLatest();
        context.put("profile", profile);
        service.remove(profile);
    }

    @When("I send get list of profile drafts request")
    public void getProfileDraftsList() {
        OperationResult<List<Profile>> operationResult = draftService.list();

        context.put("profileDraftsList", operationResult.getEntity());
    }

    @Then("Profile drafts list size $criteria $value")
    public void profileDraftsListMoreThan(String criteria, String value) {
        List<Profile> profiles = context.get("profileDraftsList", List.class);

        Assert.assertTrue(compareByCriteria(criteria, profiles.size(), Integer.valueOf(value)));
    }

    @Then("Profile list size $criteria $value")
    public void profileListMoreThan(String criteria, String value) {
        List<Profile> profiles = context.get("profileList", List.class);

        Assert.assertTrue(compareByCriteria(criteria, profiles.size(), Integer.valueOf(value)));
    }

    @When("I add profile draft to target group")
    public void addProfileDraftToTargetGroup() {
        Profile profile = Entities.getProfiles().getLatest();
        TargetGroup targetGroup = Entities.getTargetGroups().getLatest();

        profile.getGroups().add(targetGroup);

        context.put("profileDraft", profile);
    }

    @When("I send update profile request")
    public void updateProfile() {
        Profile profile = context.get("profileDraft", Profile.class);

        Profile updatedProfile = getRandomProfile();
        updatedProfile.setId(profile.getId());
        updatedProfile.setGroups(profile.getGroups());
        context.put("profileDraft", updatedProfile);

        draftService.update(updatedProfile);
    }

    @When("I send merge two profile into one request")
    public void mergeTwoProfileIntoOne() {
        EntityList<Profile> profileEntityList = Entities.getProfiles();
        Assert.assertTrue("Profile EntityList size shouldHave at least 2 profiles", profileEntityList.size() > 1);
        Profile firstProfile = profileEntityList.getLatest();
        Profile secondProfile = profileEntityList.getEntities().get(profileEntityList.size() - 2);
        Assert.assertNotNull(firstProfile.getId());
        Assert.assertNotNull(secondProfile.getId());

        context.put("firstProfileToMerge", firstProfile);
        context.put("secondProfileToMerge", secondProfile);

        OperationResult<Profile> operationResult = service.merge(firstProfile, secondProfile);
        context.put("profileDraft", operationResult.getEntity());
    }

    @Then("Merged profile draft is correct")
    public void mergedProfileShouldBeCorrect() {
        Profile firstProfileToMerge = context.get("firstProfileToMerge", Profile.class);
        Profile secondProfileToMerge = context.get("secondProfileToMerge", Profile.class);
        Profile mergedProfile = context.get("profileDraft", Profile.class);

        ProfileMergeVerification verification = new ProfileMergeVerification();
        verification.verify(firstProfileToMerge, secondProfileToMerge, mergedProfile);
    }

    @When("I send get $number merged profile details request")
    public void getOneOfMergedProfile(String number) {
        Profile firstProfileToMerge = context.get("firstProfileToMerge", Profile.class);
        Profile secondProfileToMerge = context.get("secondProfileToMerge", Profile.class);

        if (number.equals("first")) {
            service.view(firstProfileToMerge.getId());
        } else {
            service.view(secondProfileToMerge.getId());
        }
    }


    @Then("Remove all draft profiles")
    public void removeAllDrafts() {
        List<Profile> profiles = context.get("profileDraftsList", List.class);

        profiles.forEach(draftService::remove);
    }

    @When("get random profile from profile list")
    public void getRandomProfileFromList() {
        List<Profile> profiles = context.get("profileList", List.class);

        context.put("profile", getRandomItemFromList(profiles));
    }


    @When("add $count $type from profile:$profileName to injection file")
    public void addPhoneToInjectionFile(String count, String type, String profileName) {
        Profile profile = service.getByName(profileName);

        IdentifierType identifierType = IdentifierType.valueOf(type);
        List<String> identifiersList = getTargetIdentifiersList(profile, identifierType, Integer.valueOf(count));

        DataInjection injections = new File(INJECTIONS_FILE.toString()).exists() ?
                jsonToObject(readStringFromFile(INJECTIONS_FILE.toString()), DataInjection.class) : new DataInjection();

        switch (identifierType) {
            case PHONE_NUMBER:
                Assert.assertTrue(injections.getPhones().add(getRandomItemFromList(identifiersList)));
                break;
            case IMSI:
                List<Integer> imsis = identifiersList.stream().map(Integer::valueOf).collect(Collectors.toList());
                Assert.assertTrue(injections.getImsis().add(getRandomItemFromList(imsis)));
                break;
            case IMEI:
                List<Integer> imeis = identifiersList.stream().map(Integer::valueOf).collect(Collectors.toList());
                Assert.assertTrue(injections.getImeis().add(getRandomItemFromList(imeis)));
                break;
            default:
                throw new AssertionError("Unsupported identifierType:" + identifierType);
        }

        saveStringToFile(toJsonString(injections), INJECTIONS_FILE.toString());
    }

    private List<String> getTargetIdentifiersList(Profile profile, IdentifierType identifierType, Integer count) {
        List<String> targetIdentifiers = new ArrayList<>();
        profile.getIdentifiers().stream()
                .filter(identifier -> identifier.getType() == identifierType)
                .forEach(identifier -> targetIdentifiers.add(identifier.getValue()));

        List<String> identifiers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            identifiers.add(getRandomItemFromList(targetIdentifiers));
        }
        return identifiers;
    }

    @When("send get profile identifierAggregations request")
    public void getProfileidentifierAggregations() {
        Profile profile = context.get("profile", Profile.class);

        OperationResult<List<IdentifierSummary>> result = service.getIdentifierAggregations(profile.getId());
        context.put("identifierSummaryList", result.getEntity());
    }


    @Then("identifierAggregations hit counts for:$identifierType of profile:$name should incremented")
    public void identifierAggregationsCountsShouldIncremented(String identifierType, String name) {
        Profile profile = service.getByName(name);
        List<IdentifierSummary> identifiers = profile.getIdentifiersSummary();
        List<IdentifierSummary> updatedIdentifiers = service.getIdentifierAggregations(profile.getId()).getEntity();

        final Integer[] hitCount = {0};
        identifiers.stream()
                .filter(identifierSummary -> identifierSummary.getType() == IdentifierType.valueOf(identifierType))
                .forEach(identifier -> hitCount[0] -= identifier.getTargetHitsCount());
        updatedIdentifiers.stream()
                .filter(identifierSummary -> identifierSummary.getType() == IdentifierType.valueOf(identifierType))
                .forEach(identifier -> hitCount[0] += identifier.getTargetHitsCount());

        Assert.assertTrue(hitCount[0].equals(1));
    }

    @Given("Create test target from json:$target_file")
    public void uploadTestTarget(String targetFile) {
        Profile target = jsonToObject(readTxtFile(targetFile), Profile.class);
        OperationResult<Profile> result = draftService.add(target);
    }

    @When("I send get profile summary request")
    public void getProfileSummary() {
        Profile profile = Entities.getProfiles().getLatest();
        OperationResult<Profile> result = service.summary(profile.getId());

        if (result.isSuccess()) {
            context.put("profile", result.getEntity());
        }
    }

    @When("I send getOrCreate profile request")
    public void getOrCreateProfileRequest() {
        Profile profile = Entities.getProfiles().getLatest();

        if (profile != null) {
            OperationResult<Profile> result = service.getOrCreateDraft(profile.getId());
        } else {
            throw new AssertionError("Profile doesn't created");
        }
    }

    @AfterStory
    public void cleanUp() {
        new ArrayList<>(Entities.getProfiles().getEntities())
                .stream()
                .filter(profile -> profile.getName().contains("QE auto"))
                .forEach(service::remove);
    }
}
