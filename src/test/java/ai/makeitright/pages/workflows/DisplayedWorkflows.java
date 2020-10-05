package ai.makeitright.pages.workflows;

import org.openqa.selenium.WebElement;

public class DisplayedWorkflows {

    private String createdBy;
    private String dateCreated;
    private WebElement lnkName;
    private String name;
    private String type;

    public DisplayedWorkflows setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public DisplayedWorkflows setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public DisplayedWorkflows setLnkName(WebElement lnkName) {
        this.lnkName = lnkName;
        return this;
    }

    public DisplayedWorkflows setName(String name) {
        this.name = name;
        return this;
    }

    public DisplayedWorkflows setType(String type) {
        this.type = type;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public WebElement getLnkName() {
        return lnkName;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }


}
