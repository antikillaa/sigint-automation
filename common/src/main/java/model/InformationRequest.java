package model;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Boolean.TRUE;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class InformationRequest extends TeelaEntity {

    private String internalRequestNumber;
    private String externalRequestNumber;
    private Integer priority;
    private String createdBy;
    private String assignedTo;
    private String subject;
    private String description;
    private String goals;
    private Date createdDate;
    private Date lastRespondTime;
    private Date dueDate;
    private String requestSource;
    private String state;
    private InformationRequestSearchType searchType;
    private List<InformationRequestDistribution> distributionList = new ArrayList<>();
    private List<InformationRequestTaskCategory> taskCategories = new ArrayList<>();
    @JsonIgnore
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:MM:");
    public List<FileAttachment> getFileAttachments() {
        return fileAttachments;
    }

    public void addFileAttachment(FileAttachment fileAttachment) {
        this.fileAttachments.add(fileAttachment);
    }

    @JsonIgnore
    private List<FileAttachment> fileAttachments = new ArrayList<>();
    private List<String> previousRequests;
    private List<String> targets;

    public void setSearchType(InformationRequestSearchType searchType) {
        this.searchType = searchType;
    }

    public InformationRequestSearchType getSearchType() {
        return searchType;
    }

    private Calendar getCalendar(){
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    public String getInternalRequestNumber() {
        return internalRequestNumber;
    }

    public void setInternalRequestNumber(String internalRequestNumber) {
        this.internalRequestNumber = internalRequestNumber;
    }

    public String getExternalRequestNumber() {
        return externalRequestNumber;
    }

    public InformationRequest setExternalRequestNumber(String externalRequestNumber) {
        this.externalRequestNumber = externalRequestNumber;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(Integer priority) {
        this.priority = priority;

    }

    public InformationRequest setPriority(Priority priority) {
        this.priority = priority.getPriority();
        Calendar calendar = getCalendar();
        calendar.add(Calendar.DATE, priority.getDaysSwitch());
        setDueDate(calendar.getTime());
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getSubject() {
        return subject;
    }

    public InformationRequest setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InformationRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getGoals() {
        return goals;
    }

    public InformationRequest setGoals(String goals) {
        this.goals = goals;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public InformationRequest setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Date getLastRespondTime() {
        return lastRespondTime;
    }

    public void setLastRespondTime(Date lastRespondTime) {
        this.lastRespondTime = lastRespondTime;
    }

    public Date getDueDate() {
        return dueDate;
    }

    private void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getRequestSource() {
        return requestSource;
    }

    public InformationRequest setRequestSource(String requestSource) {
        this.requestSource = requestSource;
        return this;
    }

    public String getState() {
        return state;
    }

    public InformationRequest setState(String state) {
        this.state = state;
        return this;
    }

    public List<InformationRequestDistribution> getDistributionList() {
        if (distributionList.size()== 0) {
            return null;
        }
        return distributionList;
    }

    public InformationRequest setDistributionList(List<InformationRequestDistribution> distributionList) {
        this.distributionList = distributionList;
        return this;
    }

    public List<InformationRequestTaskCategory> getTaskCategories() {
        if (taskCategories.size() == 0) {
            return null;
        }
        return taskCategories;
    }

    public InformationRequest setTaskCategories(List<InformationRequestTaskCategory> taskCategories) {
        this.taskCategories = taskCategories;
        return this;
    }

    public List<String> getPreviousRequests() {
        return previousRequests;
    }

    public void setPreviousRequests(List<String> previousRequests) {
        this.previousRequests = previousRequests;
    }

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }


    public InformationRequest generate() {
        this    .setCreatedDate(new Date())
                .setRequestSource(RandomStringUtils.randomAlphabetic(10))
                .setExternalRequestNumber(RandomStringUtils.randomAlphabetic(10))
                .setState("PENDING")
                .setPriority(InformationRequestPriorities.randomPriorityMap())
                .setSubject(RandomStringUtils.randomAlphabetic(10))
                .setDescription(RandomStringUtils.randomAlphabetic(20))
                .setDistributionList(InformationRequestDistribution.getRandom())
                .setTaskCategories(InformationRequestTaskCategory.getRandom())
                .setGoals(RandomStringUtils.randomAlphabetic(10))
                .setSearchType(InformationRequestSearchType.getRandom());

        return this;
    }

    public Boolean equals(InformationRequest object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Boolean equals = TRUE;
        for (Field field: object.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            String originalValue = BeanUtils.getProperty(this, field.getName());
            String requestValue = BeanUtils.getProperty(object, field.getName());
            if (originalValue == null && requestValue == null) {
                continue;
            }
            equals = originalValue.equals(requestValue);
            if (!equals) {
                return equals;
            }
        }
        return equals;
    }
}