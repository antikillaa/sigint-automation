package model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;

public class SearchFilters {

    @JsonProperty("workflow.teamIds")
    private List<String> teamIds;
    @JsonProperty("workflow.ownerId")
    private List<String> ownerId;
    @JsonProperty("workflow.tags")
    private List<String> tags;
    @JsonProperty("workflow.priority")
    private Integer priority;
    @JsonProperty("worfklow.recordStatus")
    private String worfklowRecordStatus; // "report.reportIds" // FIXME
    @JsonProperty("workflow.recordStatus")
    private String recordStatus; // 'UNPROCESSED"
    @JsonProperty("workflow.includeUnassignedRecords")
    private Boolean includeUnassignedRecords;
    @JsonProperty("workflow.designations")
    private List<String> designations;
    private AdvancedSearch advancedSearch;
    private List<String> dataSource; // ["TWITTER"]
    private List<String> type; // ["TWEET"]
    private String eventFeed; // "OSINT"
    private SearchObjectType objectType; // "event"
    private String personName;
    private String personPassportNumber;
    private String travellerReference;
    private Boolean hasLocation;
    private EventTime eventTime;
    private String targetMatchType;
    private List<String> target;
    private List<String> groupIds;
    private Boolean includeChildGroups;
    private List<String> profileCountry;
    private List<String> language;
    private List<String> tweetShowing;
    private FavoriteCount favoriteCount;
    private String youtubeChannelName;
    private List<String> youtubeCategory;
    private String youtubeChannelId;
    private String twitterAccountId;
    private String twitterUsername;
    private String insUsername;
    private String insUserId;
    private Boolean includeSpam;

    public List<String> getDataSource() {
        return dataSource;
    }

    public void setDataSource(List<String> dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getEventFeed() {
        return eventFeed;
    }

    public void setEventFeed(String eventFeed) {
        this.eventFeed = eventFeed;
    }

    public SearchObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(SearchObjectType objectType) {
        this.objectType = objectType;
    }

    public List<String> getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(List<String> teamIds) {
        this.teamIds = teamIds;
    }

    public List<String> getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(List<String> ownerId) {
        this.ownerId = ownerId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public AdvancedSearch getAdvancedSearch() {
        return advancedSearch;
    }

    public void setAdvancedSearch(AdvancedSearch advancedSearch) {
        this.advancedSearch = advancedSearch;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Boolean getIncludeUnassignedRecords() {
        return includeUnassignedRecords;
    }

    public void setIncludeUnassignedRecords(Boolean includeUnassignedRecords) {
        this.includeUnassignedRecords = includeUnassignedRecords;
    }

    public Boolean getHasLocation() {
        return hasLocation;
    }

    public void setHasLocation(Boolean hasLocation) {
        this.hasLocation = hasLocation;
    }


    public EventTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(EventTime eventTime) {
        this.eventTime = eventTime;
    }

    public List<String> getProfileCountry() {
        return profileCountry;
    }

    public void setProfileCountry(List<String> profileCountry) {
        this.profileCountry = profileCountry;
    }

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }

    public List<String> getTweetShowing() {
        return tweetShowing;
    }

    public void setTweetShowing(List<String> tweetShowing) {
        this.tweetShowing = tweetShowing;
    }

    public FavoriteCount getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(FavoriteCount favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public List<String> getYoutubeCategory() {
        return youtubeCategory;
    }

    public void setYoutubeCategory(List<String> youtubeCategory) {
        this.youtubeCategory = youtubeCategory;
    }

    public String getTwitterAccountId() {
        return twitterAccountId;
    }

    public void setTwitterAccountId(String twitterAccountId) {
        this.twitterAccountId = twitterAccountId;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public String getYoutubeChannelName() {
        return youtubeChannelName;
    }

    public void setYoutubeChannelName(String youtubeChannelName) {
        this.youtubeChannelName = youtubeChannelName;
    }

    public String getYoutubeChannelId() {
        return youtubeChannelId;
    }

    public void setYoutubeChannelId(String youtubeChannelId) {
        this.youtubeChannelId = youtubeChannelId;
    }

    public String getInsUsername() {
        return insUsername;
    }

    public void setInsUsername(String insUsername) {
        this.insUsername = insUsername;
    }

    public String getInsUserId() {
        return insUserId;
    }

    public void setInsUserId(String insUserId) {
        this.insUserId = insUserId;
    }

    public Boolean getIncludeSpam() {
        return includeSpam;
    }

    public void setIncludeSpam(Boolean includeSpam) {
        this.includeSpam = includeSpam;
    }

    public List<String> getTarget() {
        return target;
    }

    public void setTarget(List<String> target) {
        this.target = target;
    }

    public String getTargetMatchType() {
        return targetMatchType;
    }

    public void setTargetMatchType(String targetMatchType) {
        this.targetMatchType = targetMatchType;
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    public Boolean getIncludeChildGroups() {
        return includeChildGroups;
    }

    public void setIncludeChildGroups(Boolean includeChildGroups) {
        this.includeChildGroups = includeChildGroups;
    }

    public List<String> getDesignations() {
        return designations;
    }

    public void setDesignations(List<String> designations) {
        this.designations = designations;
    }

    public String getWorfklowRecordStatus() {
        return worfklowRecordStatus;
    }

    public void setWorfklowRecordStatus(String worfklowRecordStatus) {
        this.worfklowRecordStatus = worfklowRecordStatus;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getTravellerReference() {
        return travellerReference;
    }

    public void setTravellerReference(String travellerReference) {
        this.travellerReference = travellerReference;
    }

    public String getPersonPassportNumber() {
        return personPassportNumber;
    }

    public void setPersonPassportNumber(String personPassportNumber) {
        this.personPassportNumber = personPassportNumber;
    }
}

class FavoriteCount {
    private Integer from;

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }
}

class EventTime {
    private Date from;
    private Date to;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}