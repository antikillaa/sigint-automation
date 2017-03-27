package steps;

import abs.EntityList;
import app_context.entities.Entities;
import http.OperationResult;
import model.Profile;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.ProfileDraftService;
import services.ProfileService;

public class APIProfileSteps extends APISteps {

    private static final ProfileDraftService draftService = new ProfileDraftService();
    private static final ProfileService service = new ProfileService();

    @When("I send create profile draft request")
    public void createProfileDraft() {
        Profile profile = getRandomProfile();
        context.put("profileDraft", profile);

        OperationResult<Profile> operationResult = draftService.add(profile);
    }

    private static Profile getRandomProfile() {
        return objectInitializer.randomEntity(Profile.class);
    }

    @Then("Profile draft is correct")
    public void profileDraftIsCorrect() {
        Profile requestedProfile = context.get("profileDraft", Profile.class);
        Profile createdProfile = Entities.getProfiles().getLatest();

        profilesShouldBeEquals(requestedProfile, createdProfile);
    }

    private void profilesShouldBeEquals(Profile expected, Profile actual) {
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getType(), actual.getType());
        Assert.assertEquals(expected.getActive(), actual.getActive());
        Assert.assertEquals(expected.getProperties().getDescription(), actual.getProperties().getDescription());
        Assert.assertEquals(expected.getGroups(), actual.getGroups());
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
        Profile createdProfile = Entities.getProfiles().getLatest();
        context.put("profileDraft", createdProfile);
        OperationResult<Profile> operationResult = draftService.publish(createdProfile);
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
        OperationResult<EntityList<Profile>> operationResult = draftService.list();

        context.put("profileDraftsEntityList", operationResult.getEntity());
    }

    @Then("Profile drafts list size more than $size")
    public void profileDraftsListMoreThan(String size) {
        EntityList<Profile> profiles = context.get("profileDraftsEntityList", EntityList.class);

        Assert.assertTrue(profiles.size() > Integer.valueOf(size));
    }
}
