package ai.makeitright.pages.jobs;

import org.openqa.selenium.WebElement;

public class DisplayedJobs {

    private String createdBy;
    private String dateCreated;
    private String endDate;
    private String ID;
    private WebElement lnkID;
    private String startDate;
    private String status;
    private String workflowName;

    public String getCreatedBy() {
        return createdBy;
    }

    public WebElement getLnkID() {
        return lnkID;
    }

    public String getStatus() {
        return status;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public DisplayedJobs setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public DisplayedJobs setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public DisplayedJobs setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public DisplayedJobs setID(String ID) {
        this.ID = ID;
        return this;
    }

    public DisplayedJobs setLnkID(WebElement lnkID) {
        this.lnkID = lnkID;
        return this;
    }

    public DisplayedJobs setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public DisplayedJobs setStatus(String status) {
        this.status = status;
        return this;
    }

    public DisplayedJobs setWorkflowName(String name) {
        this.workflowName = name;
        return this;
    }



}
