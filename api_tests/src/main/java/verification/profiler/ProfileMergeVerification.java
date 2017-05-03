package verification.profiler;

import model.Profile;

public class ProfileMergeVerification extends ProfileMergeAttributeVerification {

    @Override
    protected void verifyAttribute(Profile profile1, Profile profile2, Profile mergedProfile) {
        log.info("Build the chain of attributes verifications");

        setNext(new ProfileMergeNameVerification())
                .setNext(new ProfileMergeTargetCategoryVerification())
                .setNext(new ProfileMergeJsonTypeVerification())
                .setNext(new ProfileMergeMergedIDsVerification())
                .setNext(new ProfileMergeActiveVerification())
                .setNext(new ProfileMergeTypeVerification())
                .setNext(new ProfileMergeTargetGroupVerification())
                .setNext(new ProfileMergeActiveUntilVerification())
                .setNext(new ProfileMergeEntityCountVerification())
                .setNext(new ProfileMergeEntitiesVerification())
                .setNext(new ProfileMergeConsolidatedAttributesValidation());
    }
}
