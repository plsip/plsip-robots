package ai.makeitright.pages.jobs;

import org.openqa.selenium.WebElement;

public class DisplayedTasks {

    public WebElement btnShowResults;
    public String endDate;
    public WebElement lnkName;
    public String name;
    public String status;
    public String usedCommit;


    public WebElement getLnkName() {
        return lnkName;
    }

    public String getName() {
        return name;
    }

    public DisplayedTasks setBtnShowResults(WebElement btnShowResults) {
        this.btnShowResults = btnShowResults;
        return this;
    }

    public DisplayedTasks setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public DisplayedTasks setLnkName(WebElement lnkName) {
        this.lnkName = lnkName;
        return this;
    }

    public DisplayedTasks setName(String name) {
        this.name = name;
        return this;
    }

    public DisplayedTasks setStatus(String status) {
        this.status = status;
        return this;
    }

    public DisplayedTasks setUsedCommit(String usedCommit) {
        this.usedCommit = usedCommit;
        return this;
    }
}
