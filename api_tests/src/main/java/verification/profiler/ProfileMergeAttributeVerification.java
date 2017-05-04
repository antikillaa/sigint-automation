package verification.profiler;

import json.JsonConverter;
import model.*;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

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
                Assert.assertFalse(
                        mergedProfile.getActiveUntil().before(profile1.getActiveUntil())
                );
            } else if (profile2.getActiveUntil() != null) {
                Assert.assertFalse(
                        mergedProfile.getActiveUntil().before(profile2.getActiveUntil())
                );
            }
        }
    }

    class ProfileMergeActiveVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: isActive of merged profile");

            if (profile1.getActive() || profile2.getActive()) {
                Assert.assertTrue(mergedProfile.getActive());
            }
        }
    }

    class ProfileMergeConsolidatedAttributesValidation extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: ConsolidatedAttributes of merged profile");

            //TODO update validation for consolidatedAttributes
            Assert.assertNotNull(mergedProfile.getConsolidatedAttributes());
        }
    }

    class ProfileMergeEntitiesVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: Entities of merged profile");

            List<ProfileEntity> entities = profile1.getEntities();
            entities.addAll(profile2.getEntities());

            for (ProfileEntity entity : entities) {
                Assert.assertTrue(mergedProfile.getEntities().contains(entity));
            }
        }
    }

    class ProfileMergeEntityCountVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: EntityCount of merged profile");

            // entityCount
            if (mergedProfile.getEntityCount() == null || mergedProfile.getEntityCount().equals(0)) {
                Assert.assertTrue(mergedProfile.getEntities().isEmpty());
            } else {
                Assert.assertTrue(mergedProfile.getEntityCount() == mergedProfile.getEntities().size());
            }
        }
    }

    class ProfileMergeJsonTypeVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: JsonType of merged profile");

            Assert.assertTrue(mergedProfile.getJsonType().equals(ProfileJsonType.Draft));
        }
    }

    class ProfileMergeMergedIDsVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: MergedIDs of merged profile");

            Assert.assertTrue(mergedProfile.getMergingProfilesIDs().contains(profile1.getId()));
            Assert.assertTrue(mergedProfile.getMergingProfilesIDs().contains(profile2.getId()));
        }
    }

    class ProfileMergeNameVerification extends ProfileMergeAttributeVerification {

        @Override
        public void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: Name of merged profile");

            Assert.assertTrue("Merged target name should contain all names of original targets",
                    mergedProfile.getName().contains(profile1.getName()));
            Assert.assertTrue("Merged target name should contain all names of original targets",
                    mergedProfile.getName().contains(profile2.getName()));
        }
    }

    class ProfileMergeTargetCategoryVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: TargetCategory of merged profile");

            // target category
            if (profile1.getCategory().equals("Dangerous") || profile2.getCategory().equals("Dangerous")) {
                Assert.assertTrue(mergedProfile.getCategory().equals(ProfileCategory.Dangerous.getDisplayName()));
            } else {
                Assert.assertTrue(mergedProfile.getCategory().equals(ProfileCategory.POI.getDisplayName()));
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
                Assert.assertTrue(jsonMergedGroups.contains(jsonGroup));
            }
        }
    }

    class ProfileMergeTypeVerification extends ProfileMergeAttributeVerification {

        @Override
        protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
            log.info("Verification: Type of merged profile");

            // type, now it's only 'Individual'
            Assert.assertEquals(mergedProfile.getType(), ProfileType.Individual);
        }
    }

}