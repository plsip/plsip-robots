package ai.makeitright.pages.schedule;

import org.openqa.selenium.WebElement;

public class DisplayedTriggers {

    private WebElement btnDelete;
    private WebElement btnPause;
    private WebElement btnResume;
    private String finishDate;
    private String nextRun;
    private WebElement row;
    private String scheduleTriggerName;
    private String triggerDetails;

    public WebElement getRow() {
        return row;
    }

    public DisplayedTriggers setBtnDelete(WebElement btnDelete) {
        this.btnDelete = btnDelete;
        return this;
    }

    public DisplayedTriggers setBtnPause(WebElement btnPause) {
        this.btnPause = btnPause;
        return this;
    }

    public DisplayedTriggers setBtnResume(WebElement btnResume) {
        this.btnResume = btnResume;
        return this;
    }

    public DisplayedTriggers setRow(WebElement row) {
        this.row = row;
        return this;
    }

    public DisplayedTriggers setTriggerDetails(String details) {
        this.triggerDetails = details;
        return this;
    }

    public DisplayedTriggers setScheduleTriggerName(String name) {
        this.scheduleTriggerName = name;
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

    public String getScheduleTriggerName() {
        return scheduleTriggerName;
    }

    public String getNextRun() {
        return nextRun;
    }

    public String getFinishDate() {
        return finishDate;
    }
}
