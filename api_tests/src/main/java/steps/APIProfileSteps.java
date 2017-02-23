package steps;

import app_context.entities.Entities;
import http.OperationResult;
import http.OperationsResults;
import model.Profile;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.ProfileDraftService;

public class APIProfileSteps extends APISteps {

    private static final ProfileDraftService service = new ProfileDraftService();

    @When("I send create profile draft request")
    public void createProfileDraft() {
        Profile profile = getRandomProfile();
        context.put("profileDraft", profile);

        OperationResult<Profile> operationResult = service.add(profile);
        OperationsResults.setResult(operationResult);
    }

    private static Profile getRandomProfile() {
        return objectInitializer.randomEntity(Profile.class);
    }

    @Then("Profile draft is correct")
    public void profileDraftIsCorrect() {
        Profile requestedProfile = context.get("profileDraft", Profile.class);
        Profile createdProfile = Entities.getProfiles().getLatest();

        Assert.assertEquals(requestedProfile.getName(), createdProfile.getName());
        Assert.assertEquals(requestedProfile.getType(), createdProfile.getType());
        Assert.assertEquals(requestedProfile.getActive(), createdProfile.getActive());
        Assert.assertEquals(requestedProfile.getProperties().getDescription(), createdProfile.getProperties().getDescription());
        Assert.assertEquals(requestedProfile.getTarget(), createdProfile.getTarget());
        Assert.assertEquals(requestedProfile.getGroups(), createdProfile.getGroups());
        Assert.assertEquals(requestedProfile.getEntities(), createdProfile.getEntities());
        Assert.assertEquals(requestedProfile.getEntityCount(), requestedProfile.getEntityCount());
    }

    @When("I send delete profile draft request")
    public void deleteLatestProfile() {
        Profile profile = Entities.getProfiles().getLatest();

        OperationResult operationResult = service.remove(profile);
        OperationsResults.setResult(operationResult);
    }

    @When("I send get profile draft details request")
    public void getProfileDraftDetails() {
        Profile createdProfile = Entities.getProfiles().getLatest();
        context.put("profileDraft", createdProfile);

        OperationResult<Profile> operationResult = service.view(createdProfile.getId());
        OperationsResults.setResult(operationResult);
    }

}
