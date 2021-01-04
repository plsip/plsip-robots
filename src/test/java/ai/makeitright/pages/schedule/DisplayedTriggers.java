package ai.makeitright.pages.schedule;

public class DisplayedTriggers {

    private String workflowName;
    private String triggerDetails;
    private String nextRun;
    private String finishDate;

    public DisplayedTriggers setTriggerDetails(String details) {
        this.triggerDetails = details;
        return this;
    }

    public DisplayedTriggers setWorkflowName(String name) {
        this.workflowName = name;
        return this;
    }

    public DisplayedTriggers setNextRun(String date) {
        this.nextRun = date;
        return this;
    }

    public DisplayedTriggers setFinishDate(String date) {
        this.finishDate = date;
        return this;
    }

    public String getTriggerDetails() {
        return triggerDetails;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public String getNextRun() {
        return nextRun;
    }

    public String getFinishDate() {
        return finishDate;
    }
}
