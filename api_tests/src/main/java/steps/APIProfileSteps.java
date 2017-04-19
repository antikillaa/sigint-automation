package steps;

import abs.EntityList;
import app_context.entities.Entities;
import http.JsonConverter;
import http.OperationResult;
import model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.ProfileDraftService;
import services.ProfileService;

import java.util.ArrayList;
import java.util.List;

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
        Assert.assertEquals(
                JsonConverter.toJsonString(expected.getGroups()),
                JsonConverter.toJsonString(actual.getGroups()));
        Assert.assertEquals(expected.getEntities(), actual.getEntities());
        Assert.assertEquals(expected.getEntityCount(), actual.getEntityCount());

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
        service.view(profile.getId());
    }

    @Then("Profile is correct")
    public void profileIsCorrect() {
        Profile created = Entities.getProfiles().getLatest();
        Profile requested = context.get("profile", Profile.class);

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

    @Then("Profile drafts list size more than $size")
    public void profileDraftsListMoreThan(String size) {
        List<Profile> profiles = context.get("profileDraftsList", List.class);

        Assert.assertTrue(profiles.size() > Integer.valueOf(size));
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

        profile.setName(RandomStringUtils.randomAlphanumeric(10));
        profile.getProperties().setDescription(RandomStringUtils.randomAlphanumeric(20));
        profile.getProperties().setProfileVersion(profile.getProperties().getProfileVersion() + 1);
        profile.setCategory(ProfileCategory.random().getDisplayName());

        context.put("profileDraft", profile);

        draftService.update(profile);
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

        // name
        Assert.assertTrue("Merged target name should contain all names of original targets",
                mergedProfile.getName().contains(firstProfileToMerge.getName()));
        Assert.assertTrue("Merged target name should contain all names of original targets",
                mergedProfile.getName().contains(secondProfileToMerge.getName()));

        // target category
        if (firstProfileToMerge.getCategory().equals("Dangerous") || secondProfileToMerge.getCategory().equals("Dangerous")) {
            Assert.assertTrue(mergedProfile.getCategory().equals(ProfileCategory.Dangerous.getDisplayName()));
        } else {
            Assert.assertTrue(mergedProfile.getCategory().equals(ProfileCategory.POI.getDisplayName()));
        }

        // jsonType
        Assert.assertTrue(mergedProfile.getJsonType().equals(ProfileJsonType.Draft));

        // mergedIDs
        Assert.assertTrue(mergedProfile.getMergingProfilesIDs().contains(firstProfileToMerge.getId()));
        Assert.assertTrue(mergedProfile.getMergingProfilesIDs().contains(secondProfileToMerge.getId()));

        // active
        if (firstProfileToMerge.getActive() || secondProfileToMerge.getActive()) {
            Assert.assertTrue(mergedProfile.getActive());
        }

        // type, now it's only 'Individual'
        Assert.assertEquals(mergedProfile.getType(), ProfileType.Individual);

        // groups
        ArrayList<TargetGroup> groups = firstProfileToMerge.getGroups();
        groups.addAll(secondProfileToMerge.getGroups());

        String jsonMergedGroups = JsonConverter.toJsonString(mergedProfile.getGroups());
        for (TargetGroup group : groups) {
            String jsonGroup = JsonConverter.toJsonString(group);
            Assert.assertTrue(jsonMergedGroups.contains(jsonGroup));
        }

        // activeUntil
        if (firstProfileToMerge.getActiveUntil() != null ) {
            Assert.assertFalse(mergedProfile.getActiveUntil().after(firstProfileToMerge.getActiveUntil()));
        } else if (secondProfileToMerge.getActiveUntil() != null ) {
            Assert.assertFalse(mergedProfile.getActiveUntil().after(secondProfileToMerge.getActiveUntil()));
        }

        // entityCount
        if (mergedProfile.getEntityCount() == null || mergedProfile.getEntityCount().equals(0)) {
            Assert.assertTrue(mergedProfile.getEntities().isEmpty());
        } else {
            Assert.assertTrue(mergedProfile.getEntityCount() == mergedProfile.getEntities().size());
        }

        // entities
        ArrayList<ProfileEntity> entities = firstProfileToMerge.getEntities();
        entities.addAll(secondProfileToMerge.getEntities());
        for (ProfileEntity entity : entities) {
            Assert.assertTrue(mergedProfile.getEntities().contains(entity));
        }

        //TODO update validation for consolidatedAttributes
        Assert.assertNotNull(mergedProfile.getConsolidatedAttributes());
    }
}
