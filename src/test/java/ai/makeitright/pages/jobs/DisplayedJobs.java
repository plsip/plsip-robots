package ai.makeitright.pages.jobs;

public class DisplayedJobs {

    private String createdBy;
    private String workflowName;
    private String status;

    public DisplayedJobs setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public DisplayedJobs setWorkflowName(String name) {
        this.workflowName = name;
        return this;
    }

    public DisplayedJobs setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public String getStatus() {
        return status;
    }

}
