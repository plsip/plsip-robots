package ai.makeitright.pages.tasks;


import org.openqa.selenium.WebElement;

public class DisplayedTasks {

    private String createdBy;
    private String dateCreated;
    private String lastCommit;
    private WebElement lnkName;
    private String name;
    private String technology;


    public String getCreatedBy() {
        return createdBy;
    }

    public WebElement getLnkName() {
        return lnkName;
    }

    public String getName() {
        return name;
    }

    public String getTechnology() {
        return technology;
    }

    public DisplayedTasks setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public DisplayedTasks setDateCreated(final String dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public DisplayedTasks setLastCommmit(final String lastCommit) {
        this.lastCommit = lastCommit;
        return this;
    }

    public DisplayedTasks setLnkName(WebElement element) {
        this.lnkName = element;
        return this;
    }

    public DisplayedTasks setName(final String name) {
        this.name = name;
        return this;
    }

    public DisplayedTasks setTechnology(final String technology) {
        this.technology = technology;
        return this;
    }

}
