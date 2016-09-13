package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Matrix for generation and verification ingested data.
 * Used for generate test records 'to'/'from' target and with mention of target.
 * Each row contain Target with counters fields for generation/verification test data.
 */
public class GenerationMatrix {

    private List<GenerationMatrixRow> rows = new ArrayList<>();

    /**
     * Initialization generation matrix with list of target and random hit/mention parameters value for each.
     * @param targets list of test targets
     */
    public GenerationMatrix(List<Target> targets) {
        for (Target target : targets) {
            rows.add(new GenerationMatrixRow(target));
        }
    }

    public List<GenerationMatrixRow> getRows() {
        return rows;
    }

    /**
     * Return the total number of records, that should be generated by the GenerationMatrix
     * @return Total number of records
     */
    public int getTotalRecords() {
        int totalRecords = 0;
        for (GenerationMatrixRow row : rows) {
            totalRecords += row.getTotalRecords();
        }
        return totalRecords;
    }

    /**
     * Return the total number of records with target HIT, that should be generated by the GenerationMatrix.
     * Record with Target HIT: it mean that this record 'from/to' target phone.
     * @return Return the total number of records with target HIT
     */
    public int getTotalRecordsHit() {
        int totalRecordsHit = 0;
        for (GenerationMatrixRow row : rows) {
            totalRecordsHit += row.getTotalRecordsHit();
        }
        return totalRecordsHit;
    }

    /**
     * Return the total number of Targets with HIT records, that should be generated by the GenerationMatrix.
     * Targets with HIT: It mean that this target have any record 'from/to' target phone.
     * @return Return the total number of Targets with HIT records
     */
    public int getTotalTargersHit() {
        int totalTargetHit = 0;
        for (GenerationMatrixRow row : rows) {
            int hitCount = row.getFromNumberCount() + row.getToNumberCount();
            if (hitCount > 0) {
                totalTargetHit ++;
            }
        }
        return totalTargetHit;
    }

    /**
     * Return the total number of records with target MENTION, that should be generated by the GenerationMatrix.
     * Record with Target MENTION: it mean that this record contain any value of: target phone, target name, target keyword
     * @return Return the total number of records with target HIT
     */
    public int getTotalRecordsMention() {
        int totalRecordsMention = 0;
        for (GenerationMatrixRow row : rows) {
            totalRecordsMention += row.getTotalRecordsMention();
        }
        return totalRecordsMention;
    }

    /**
     * Return the total number of Target with MENTION, that should be generated by the GenerationMatrix.
     * Record with Target MENTION: it mean that this record contain any value of: target phone, target name, target keyword
     * @return Return the total number of Target with MENTION records
     */
    public int getTotalTargetMention() {
        int totalTargetMention = 0;
        for (GenerationMatrixRow row : rows) {
            int mentionCount = row.getNumberMention() + row.getKeywordMention() + row.getNameMention();
            if (mentionCount > 0) {
                totalTargetMention++;
            }
        }
        return totalTargetMention;
    }

    /**
     * Find GenerationMatrixRow by Target Name
     * @param name target name
     * @return GenerationMatrixRow
     */
    public GenerationMatrixRow getRowByTargetName(String name) {
        for (GenerationMatrixRow row : rows) {
            if (row.getTarget().getName().equals(name)) {
                return row;
            }
        }
        return null;
    }

}
