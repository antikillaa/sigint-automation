package ae.pegasus.framework.verification.profiler;

import ae.pegasus.framework.json.JsonConverter;
import ae.pegasus.framework.model.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

abstract class ProfileMergeAttributeVerification {

    protected Logger log = Logger.getLogger(ProfileMergeAttributeVerification.class);

    // The next element in the chain of responsibility
    private ProfileMergeAttributeVerification next;

    ProfileMergeAttributeVerification setNext(ProfileMergeAttributeVerification verification) {
        next = verification;
        return verification;
    }

    public void verify(Profile profile1, Profile profile2, Profile mergedProfile) {

        verifyAttribute(profile1, profile2, mergedProfile);

        if (next != null) {
            next.verify(profile1, profile2, mergedProfile);
        }
    }

    abstract protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile);

    class ProfileMergeActiveUntilVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: ActiveUntil of merged profile");

            if (profile1.getActiveUntil() != null) {
                assertFalse(
                        mergedProfile.getActiveUntil().before(profile1.getActiveUntil())
                );
            } else if (profile2.getActiveUntil() != null) {
                assertFalse(
                        mergedProfile.getActiveUntil().before(profile2.getActiveUntil())
                );
            }
        }
    }

    class ProfileMergeActiveVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: isActive of merged profile");

            if (profile1.getActive().equals(TargetStatus.ACTIVE) || profile2.getActive().equals(TargetStatus.ACTIVE)) {
                assertTrue("At least one old merged target was ACTIVE, new target shouldBe ACTIVE too",
                        mergedProfile.getActive().equals(TargetStatus.ACTIVE));
            } else if (profile1.getActive().equals(TargetStatus.INACTIVE) || profile2.getActive().equals(TargetStatus.INACTIVE)) {
                assertTrue("At least one old merged target was INACTIVE, new target shouldBe INACTIVE too",
                        mergedProfile.getActive().equals(TargetStatus.INACTIVE));
            } else {
                assertTrue("At least one old merged target was ARCHIVED, new target shouldBe ARCHIVED too",
                        mergedProfile.getActive().equals(TargetStatus.ARCHIVED));
            }
        }
    }

    class ProfileMergeConsolidatedAttributesValidation extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: ConsolidatedAttributes of merged profile");

            //TODO update validation for consolidatedAttributes
            assertNotNull(mergedProfile.getConsolidatedAttributes());
        }
    }

    class ProfileMergeEntitiesVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: Entities of merged profile");

            List<SearchRecord> entities = profile1.getEntities();
            entities.addAll(profile2.getEntities());

            for (SearchRecord entity : entities) {
                assertTrue(mergedProfile.getEntities().contains(entity));
            }
        }
    }

    class ProfileMergeEntityCountVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: EntityCount of merged profile");

            // entityCount
            if (mergedProfile.getIdentifierCount() == null || mergedProfile.getIdentifierCount().equals(0)) {
                assertTrue(mergedProfile.getEntities().isEmpty());
            } else {
                assertTrue(mergedProfile.getIdentifierCount() == mergedProfile.getEntities().size());
            }
        }
    }

    class ProfileMergeJsonTypeVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: JsonType of merged profile");

            assertTrue(mergedProfile.getJsonType().equals(ProfileType.Draft));
        }
    }

    class ProfileMergeMergedIDsVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: MergedIDs of merged profile");

            assertTrue(mergedProfile.getMergingProfilesIDs().contains(profile1.getId()));
            assertTrue(mergedProfile.getMergingProfilesIDs().contains(profile2.getId()));
        }
    }

    class ProfileMergeNameVerification extends ProfileMergeAttributeVerification {

        @Override
        public void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: Name of merged profile");

            assertTrue("Merged target name should contain all names of original targets",
                    mergedProfile.getName().contains(profile1.getName()));
            assertTrue("Merged target name should contain all names of original targets",
                    mergedProfile.getName().contains(profile2.getName()));
        }
    }

    class ProfileMergeTargetCategoryVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: TargetCategory of merged profile");

            // target category
            if (profile1.getCategory().equals("Dangerous") || profile2.getCategory().equals("Dangerous")) {
                assertTrue(mergedProfile.getCategory().equals(ProfileCategory.Dangerous.getDisplayName()));
            } else {
                assertTrue(mergedProfile.getCategory().equals(ProfileCategory.POI.getDisplayName()));
            }
        }
    }

    class ProfileMergeTargetGroupVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: TargetGroups of merged profile");

            ArrayList<TargetGroup> groups = profile1.getGroups();
            groups.addAll(profile2.getGroups());

            String jsonMergedGroups = JsonConverter.toJsonString(mergedProfile.getGroups());
            for (TargetGroup group : groups) {
                String jsonGroup = JsonConverter.toJsonString(group);
                assertTrue(jsonMergedGroups.contains(jsonGroup));
            }
        }
    }

    class ProfileMergeTypeVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: Type of merged profile");

            // now it's only 'Individual' value
            assertEquals(mergedProfile.getType(), profile1.getType());
            assertEquals(mergedProfile.getType(), profile2.getType());
        }
    }

}