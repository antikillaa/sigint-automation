package steps;

import errors.NullReturnException;
import http.OperationResult;
import model.Profile;
import model.TargetGroup;
import model.entities.Entities;
import model.entities.EntityList;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.ProfileDraftService;
import services.ProfileService;
import verification.profiler.ProfileMergeVerification;

import java.util.List;

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
    public void profileIsCorrect() throws NullReturnException {
        Profile requested = context.get("profile", Profile.class);
        Profile created = Entities.getProfiles().getEntity(requested.getName());

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

    @When("I send get $name merged profile details request")
    public void getOneOfMergedProfile(String name) {
        Profile firstProfileToMerge = context.get("firstProfileToMerge", Profile.class);
        Profile secondProfileToMerge = context.get("secondProfileToMerge", Profile.class);

        if (name.equals("first")) {
            service.view(firstProfileToMerge.getId());
        } else {
            service.view(secondProfileToMerge.getId());
        }
    }
}
