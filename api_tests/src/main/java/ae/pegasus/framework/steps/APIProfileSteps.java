package ae.pegasus.framework.steps;

import ae.pegasus.framework.errors.NullReturnException;
import ae.pegasus.framework.errors.OperationResultError;
import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.model.*;
import ae.pegasus.framework.model.entities.CBEntityList;
import ae.pegasus.framework.model.entities.Entities;
import ae.pegasus.framework.model.entities.EntityList;
import ae.pegasus.framework.services.FinderFileService;
import ae.pegasus.framework.services.ProfileDraftService;
import ae.pegasus.framework.services.ProfileService;
import ae.pegasus.framework.utils.DateHelper;
import ae.pegasus.framework.verification.profiler.ProfileMergeVerification;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ae.pegasus.framework.ingestion.IngestionService.INJECTIONS_FILE;
import static ae.pegasus.framework.json.JsonConverter.jsonToObject;
import static ae.pegasus.framework.json.JsonConverter.toJsonString;
import static ae.pegasus.framework.steps.APIFinderFileSteps.getRandomFinderFile;
import static ae.pegasus.framework.utils.DateHelper.isTimeout;
import static ae.pegasus.framework.utils.FileHelper.readTxtFile;
import static ae.pegasus.framework.utils.RandomGenerator.getRandomItemFromList;
import static ae.pegasus.framework.utils.StepHelper.compareByCriteria;
import static ae.pegasus.framework.utils.StringUtils.readStringFromFile;
import static ae.pegasus.framework.utils.StringUtils.saveStringToFile;
import static org.junit.Assert.*;

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
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getActive(), actual.getActive());
        assertEquals(expected.getProperties().getDescription(), actual.getProperties().getDescription());

        assertTrue(APITargetGroupSteps.equalsTargetGroups(actual.getGroups(), expected.getGroups()));

        assertEquals(expected.getEntities(), actual.getEntities());
        //FIXME Assert.assertEquals(expected.getIdentifierCount(), actual.getIdentifierCount());

        assertEquals(expected.getJsonType(), actual.getJsonType());
        assertEquals(expected.getCategory(), actual.getCategory());
        assertEquals(expected.getMergingProfilesIDs(), actual.getMergingProfilesIDs());
        assertEquals(expected.getCriminalRecord(), actual.getCriminalRecord());
        assertEquals(expected.getClassification(), actual.getClassification());

        if (expected.getTarget() == null) {
            assertTrue(actual.getTarget() == null || !actual.getTarget());
        } else {
            assertEquals(expected.getTarget(), actual.getTarget());
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

        assertTrue(compareByCriteria(criteria, profiles.size(), Integer.valueOf(value)));
    }

    @Then("Profile list size $criteria $value")
    public void profileListMoreThan(String criteria, String value) {
        List<Profile> profiles = context.get("profileList", List.class);

        assertTrue(compareByCriteria(criteria, profiles.size(), Integer.valueOf(value)));
    }

    @When("I add profile draft to finder file")
    public void addProfileDraftToFinderFile() {
        Profile profile = Entities.getProfiles().getLatest();
        FinderFile finderFile = Entities.getFinderFiles().getLatest();

        service.addToFile(profile, finderFile);
        context.put("profileDraft", profile);
    }

    @When("I send update profile request")
    public void updateProfile() {
        Profile profile = context.get("profileDraft", Profile.class);

        Profile updatedProfile = getRandomProfile();
        updatedProfile.setId(profile.getId());
        updatedProfile.setFiles(profile.getFiles());
        context.put("profileDraft", updatedProfile);

        draftService.update(updatedProfile);
    }

    @When("I send merge two profile into one request")
    public void mergeTwoProfileIntoOne() {
        EntityList<Profile> profileEntityList = Entities.getProfiles();
        assertTrue("Profile EntityList size shouldHave at least 2 profiles", profileEntityList.size() > 1);
        Profile firstProfile = profileEntityList.getLatest();
        Profile secondProfile = profileEntityList.getEntities().get(profileEntityList.size() - 2);
        assertNotNull(firstProfile.getId());
        assertNotNull(secondProfile.getId());

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
                assertTrue(injections.getPhones().add(getRandomItemFromList(identifiersList)));
                break;
            case IMSI:
                List<Long> imsis = identifiersList.stream().map(Long::valueOf).collect(Collectors.toList());
                assertTrue(injections.getImsis().add(getRandomItemFromList(imsis)));
                break;
            case IMEI:
                List<Long> imeis = identifiersList.stream().map(Long::valueOf).collect(Collectors.toList());
                assertTrue(injections.getImeis().add(getRandomItemFromList(imeis)));
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

        assertTrue(hitCount[0].equals(1));
    }

    @Given("Find or create test target from json:$target_file")
    public void uploadTestTarget(String targetFile) {
        Profile target = jsonToObject(readTxtFile(targetFile), Profile.class);

        // find target in system
        log.info("Try find target:" + target.getName() + " in system...");
        FinderFileService fileService = new FinderFileService();
        FinderFileSearchFilter filter = new FinderFileSearchFilter();
        filter
                .setQuery(target.getName())
                .setSortField("name")
                .setPageSize(100);

        OperationResult<List<ProfileAndTargetGroup>> operationResult = fileService.searchFileMembers(filter);
        Profile finalTarget = target;
        List<Profile> profiles = fileService
                .extractProfilesFromResponse(operationResult)
                .stream()
                .filter(profile -> profile.getName().contains(finalTarget.getName()))
                .collect(Collectors.toList());

        if (profiles.isEmpty()) {
            log.info("Target not found, create and publish target from:" + targetFile);
            // create draft target
            OperationResult<Profile> result = draftService.add(target);
            if (result.isSuccess()) {
                // add target to file
                target = result.getEntity();
                OperationResult<FinderFile> fileOperationResult = fileService.add(getRandomFinderFile());
                if (fileOperationResult.isSuccess()) {
                    target = service.addToFile(target, fileOperationResult.getEntity());
                } else {
                    throw new OperationResultError(fileOperationResult);
                }
                // publish target
                result = draftService.publish(target);
                if (!result.isSuccess()) {
                    throw new OperationResultError(result);
                } else {
                    context.put("profile", result.getEntity());
                }
            } else {
                throw new OperationResultError(result);
            }
        } else {
            log.info("Target " + target.getName() + " already exist in system");
            context.put("profile", getRandomItemFromList(profiles));
        }
    }

    @When("I send get profile summary request")
    public void getProfileSummary() {
        Profile profile = Entities.getProfiles().getLatest();
        OperationResult<Profile> result = service.summary(profile.getId());

        if (result.isSuccess()) {
            context.put("profile", result.getEntity());
        }
    }

    @When("Edit (create draft of) existing profile")
    public void getOrCreateProfileRequest() {
        Profile profile = context.get("profile", Profile.class);

        if (profile != null) {
            OperationResult<Profile> result = service.getOrCreateDraft(profile.getId());
            if (result.isSuccess()) {
                context.put("profileDraft", result.getEntity());
            } else {
                throw new OperationResultError(result);
            }
        } else {
            throw new AssertionError("Profile doesn't exist");
        }
    }

    @AfterStory
    public void cleanUp() {
        new ArrayList<>(Entities.getProfiles().getEntities())
                .stream()
                .filter(profile -> profile.getName().contains("QE auto"))
                .forEach(service::remove);
    }

    @When("upload new target image:$image to target")
    public void uploadTargetImage(String image) {
        Profile profile = context.get("profileDraft", Profile.class);

        URL url = getClass().getClassLoader().getResource(image);
        if (url != null) {
            File file = new File(url.getFile());
            OperationResult<FileMetaData> result = draftService.uploadImage(profile, file);
            profile.setUploadedImage(result.getEntity().getFileId());

            context.put("profileDraft", profile);
            context.put("fileMetaData", result.getEntity());
        } else {
            throw new AssertionError("File: " + image + " not found");
        }
    }

    @When("delete all profile drafts")
    public void deleteAllPrifileDrafts() {
        OperationResult<List<Profile>> operationResult = draftService.list();

        if (operationResult.isSuccess()) {
            operationResult.getEntity()
                    .stream()
                    .filter(profile -> Objects.equals(profile.getJsonType(), ProfileJsonType.Draft))
                    .forEach(service::remove);
        }
    }

    @Then("Profile contain uploaded image")
    public void profileShouldContainUploadedImage() {
        Profile profile = context.get("profile", Profile.class);
        FileMetaData fileMetaData = context.get("fileMetaData", FileMetaData.class);

        Pattern pattern = Pattern.compile("(/api/upload-platform/files/).*(/content)");
        assertTrue(pattern.matcher(profile.getUploadedImage()).find());
        assertTrue(pattern.matcher(fileMetaData.getFileId()).find());
    }

    @When("upload audio file to profile")
    public void uploadAudioFile() {
        List<File> files = context.get("g4files", List.class);
        Profile draft = context.get("profileDraft", Profile.class);

        File audioFile = files.stream()
                .filter(file -> file.getName().contains(".wav"))
                .findFirst().orElse(null);
        assertNotNull(audioFile);

        OperationResult<VoiceFile> result = draftService.uploadAudioFile(draft, audioFile);

        context.put("voiceFile", result.getEntity());
        context.put("profileDraft", draft);
    }

    @Then("uploaded audio file is processed")
    public void uploadedAudioFileIsProcesse() {
        VoiceFile voiceFile = context.get("voiceFile", VoiceFile.class);
        Profile draft = context.get("profileDraft", Profile.class);

        Integer WAITING_TIME = 3 * 60;
        Integer POLLING_INTERVAL = 10;
        Date deadline = DateHelper.getDateWithShift(WAITING_TIME);

        CBEntityList entities = new CBEntityList();
        entities.setEntities(draftService.getVoiceEvents(draft).getEntity());

        while (entities.getEntity(voiceFile.getVoiceEventId()) == null && !isTimeout(deadline)) {
            DateHelper.waitTime(POLLING_INTERVAL);
            entities.setEntities(draftService.getVoiceEvents(draft).getEntity());
        }
        assertNotNull("Unable process uploaded audio voice", entities.getEntity(voiceFile.getVoiceEventId()));

        context.put("voiceEvents", entities.getEntities());
    }

    @When("get voice events from draft profile")
    public void createVoiceID() {
        Profile draft = context.get("profileDraft", Profile.class);

        OperationResult<List<CBEntity>> result = draftService.getVoiceEvents(draft);
        context.put("voiceEvents", result.getEntity());
    }

    @When("create voiceID for draft profile")
    public void createVoiceIDForDraftProfile() {
        List<CBEntity> voiceEvents = context.get("voiceEvents", List.class);
        Profile draft = context.get("profileDraft", Profile.class);

        CBEntity voiceEvent = voiceEvents.stream()
                .filter(cbEntity -> cbEntity.getAttributes() != null)
                .findAny().orElse(null);
        assertNotNull("Unable find voiceEvent with filled attributes", voiceEvent);
        log.info(toJsonString(voiceEvent));

        // create VoiceID from random channel of random voice event in this target
        VoiceRecord record = new VoiceRecord();
        record.setId(voiceEvent.getId());
        List<String> channelId = (ArrayList<String>) voiceEvent.getAttributes().get("CHANNEL_ID");
        assertFalse("CHANNEL_ID field is empty!\n" + toJsonString(voiceEvent), channelId.isEmpty());
        record.setChannel(Integer.valueOf(getRandomItemFromList(channelId)));

        Voice voice = new Voice();
        voice.getRecords().add(record);

        OperationResult<Voice> result = draftService.createVoiceModel(draft, voice);
        context.put("voice", result.getEntity());
        context.put("profileDraft", draft);
    }

    @Then("profile contain created voiceID")
    public void profileContainVoiceID() {
        Voice voice = context.get("voice", Voice.class);
        Profile profile = context.get("profile", Profile.class);

        assertEquals(voice.getId(), profile.getVoiceModelId());
    }
}