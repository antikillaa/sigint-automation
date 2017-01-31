package model;

import data_for_entity.annotations.WithFieldDataType;
import data_for_entity.data_types.FieldDataType;

import java.util.Date;

/**
 * Target teams. X-VoiceMetadata source.
 *
 * Example:
 * EventTime	        Sender	        Receiver
 * 16.03.22 23:03:52	923312824935	787
 * 16.03.22 21:14:57	971509155084	38970281738
 */
public class FVoiceMetadata extends G4Record {

    @WithFieldDataType(FieldDataType.DATE)
    private Date eventTime;

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

}
