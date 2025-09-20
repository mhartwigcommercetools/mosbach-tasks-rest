
package com.mosbach.demo.model.task;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
// import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Name",
    "Module",
    "Deadline",
    "Recipient",
    "Description",
    "Location",
    "Team-Members",
    "Priority",
    "Grade",
    "DurationInHours",
    "Status"
})
//@Generated("jsonschema2pojo")
public class Task {

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Module")
    private String module;
    @JsonProperty("Deadline")
    private String deadline;
    @JsonProperty("Recipient")
    private String recipient;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Location")
    private String location;
    @JsonProperty("Team-Members")
    private List<TeamMember> teamMembers;
    @JsonProperty("Priority")
    private Integer priority;
    @JsonProperty("Grade")
    private Double grade;
    @JsonProperty("DurationInHours")
    private Integer durationInHours;
    @JsonProperty("Status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Task() {
    }

    public Task(String name, String module, String deadline, String recipient, String description, String location, List<TeamMember> teamMembers, Integer priority, Double grade, Integer durationInHours, String status) {
        super();
        this.name = name;
        this.module = module;
        this.deadline = deadline;
        this.recipient = recipient;
        this.description = description;
        this.location = location;
        this.teamMembers = teamMembers;
        this.priority = priority;
        this.grade = grade;
        this.durationInHours = durationInHours;
        this.status = status;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Module")
    public String getModule() {
        return module;
    }

    @JsonProperty("Module")
    public void setModule(String module) {
        this.module = module;
    }

    @JsonProperty("Deadline")
    public String getDeadline() {
        return deadline;
    }

    @JsonProperty("Deadline")
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @JsonProperty("Recipient")
    public String getRecipient() {
        return recipient;
    }

    @JsonProperty("Recipient")
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @JsonProperty("Description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("Description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("Location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("Location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("Team-Members")
    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    @JsonProperty("Team-Members")
    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    @JsonProperty("Priority")
    public Integer getPriority() {
        return priority;
    }

    @JsonProperty("Priority")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @JsonProperty("Grade")
    public Double getGrade() {
        return grade;
    }

    @JsonProperty("Grade")
    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @JsonProperty("DurationInHours")
    public Integer getDurationInHours() {
        return durationInHours;
    }

    @JsonProperty("DurationInHours")
    public void setDurationInHours(Integer durationInHours) {
        this.durationInHours = durationInHours;
    }

    @JsonProperty("Status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Task.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("module");
        sb.append('=');
        sb.append(((this.module == null)?"<null>":this.module));
        sb.append(',');
        sb.append("deadline");
        sb.append('=');
        sb.append(((this.deadline == null)?"<null>":this.deadline));
        sb.append(',');
        sb.append("recipient");
        sb.append('=');
        sb.append(((this.recipient == null)?"<null>":this.recipient));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("location");
        sb.append('=');
        sb.append(((this.location == null)?"<null>":this.location));
        sb.append(',');
        sb.append("teamMembers");
        sb.append('=');
        sb.append(((this.teamMembers == null)?"<null>":this.teamMembers));
        sb.append(',');
        sb.append("priority");
        sb.append('=');
        sb.append(((this.priority == null)?"<null>":this.priority));
        sb.append(',');
        sb.append("grade");
        sb.append('=');
        sb.append(((this.grade == null)?"<null>":this.grade));
        sb.append(',');
        sb.append("durationInHours");
        sb.append('=');
        sb.append(((this.durationInHours == null)?"<null>":this.durationInHours));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null)?"<null>":this.status));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
