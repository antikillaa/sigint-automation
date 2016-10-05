package data_generator;

import model.GenerationMatrix;
import model.GenerationMatrixRow;
import model.XVoiceMetadata;

import java.util.ArrayList;
import java.util.List;

class XVoiceMetadataGenerator extends DataGenerator{

    XVoiceMetadataGenerator() {
        super(XVoiceMetadata.class);
    }

    @Override
    public List <XVoiceMetadata> produceListByMatrix(GenerationMatrix generationMatrix) {
        List<XVoiceMetadata> list = new ArrayList<>();

        /*
            Generate X-VoiceMetadata list for each target from GenerationMatrix,
            according to the current Generation matrix row with target and 'from/to' target records,
            or any mention about this target parameters for him
         */
        for (GenerationMatrixRow row : generationMatrix.getRows()) {
            String phone = getTargetPhone(row.getTarget());
            XVoiceMetadata xVoiceMetadata;

            // generate X-SMS list 'from' current target phone
            int from = 0;
            while (from < row.getFromNumberCount()) {
                xVoiceMetadata = (XVoiceMetadata) produce();
                xVoiceMetadata.setSender(phone);
                list.add(xVoiceMetadata);
                from++;
            }

            // generate X-SMS list 'to' current target phone
            int to = 0;
            while (to < row.getToNumberCount()) {
                xVoiceMetadata = (XVoiceMetadata) produce();
                xVoiceMetadata.setSender(phone);
                list.add(xVoiceMetadata);
                to++;
            }

            // generate randomly X-SMS list without any target hit/mention
            int withoutHitMention = 0;
            while (withoutHitMention < row.getWithoutHitMention()) {
                xVoiceMetadata = (XVoiceMetadata) produce();
                list.add(xVoiceMetadata);
                withoutHitMention++;
            }
        }

        return list;
    }

}
