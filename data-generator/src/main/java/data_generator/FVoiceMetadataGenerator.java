package data_generator;

import model.G4Record;
import model.GenerationMatrix;
import model.GenerationMatrixRow;
import model.FVoiceMetadata;

import java.util.ArrayList;
import java.util.List;

class FVoiceMetadataGenerator extends DataGenerator{

    FVoiceMetadataGenerator() {
        super(FVoiceMetadata.class);
    }

    @Override
    public List <G4Record> produceListByMatrix(GenerationMatrix generationMatrix) {
        List<G4Record> list = new ArrayList<>();

        /*
            Generate X-VoiceMetadata list for each target from GenerationMatrix,
            according to the current Generation matrix row with target and 'from/to' target records,
            or any mention about this target parameters for him
         */
        for (GenerationMatrixRow row : generationMatrix.getRows()) {
            String phone = getTargetPhone(row.getTarget());
            FVoiceMetadata fVoiceMetadata;

            // generate X-SMS list 'from' current target phone
            int from = 0;
            while (from < row.getFromNumberCount()) {
                fVoiceMetadata = (FVoiceMetadata) produce();
                fVoiceMetadata.setFromNumber(phone);
                list.add(fVoiceMetadata);
                from++;
            }

            // generate X-SMS list 'to' current target phone
            int to = 0;
            while (to < row.getToNumberCount()) {
                fVoiceMetadata = (FVoiceMetadata) produce();
                fVoiceMetadata.setToNumber(phone);
                list.add(fVoiceMetadata);
                to++;
            }

            // generate randomly X-SMS list without any target hit/mention
            int withoutHitMention = 0;
            while (withoutHitMention < row.getWithoutHitMention()) {
                fVoiceMetadata = (FVoiceMetadata) produce();
                list.add(fVoiceMetadata);
                withoutHitMention++;
            }
        }

        return list;
    }

}
