package ai.makeitright.pages.argumentscollections;

import org.openqa.selenium.WebElement;

public class DisplayedGlobalArguments {

    private String author;
    private WebElement btnArgumentsCollectionName;
    private WebElement btnDelete;
    private String createDate;
    private String globalArgumentsCollectionName;

    public String getAuthor() {
        return author;
    }

    public WebElement getBtnArgumentsCollectionName() {
        return btnArgumentsCollectionName;
    }

    public WebElement getBtnDelete() {
        return btnDelete;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getGlobalArgumentsCollectionName() {
        return globalArgumentsCollectionName;
    }

    public DisplayedGlobalArguments setAuthor(String author) {
        this.author = author;
        return this;
    }

    public DisplayedGlobalArguments setBtnArgumentsCollectionName(WebElement btnArgumentsCollectionName) {
        this.btnArgumentsCollectionName = btnArgumentsCollectionName;
        return this;
    }

    public DisplayedGlobalArguments setBtnDelete(WebElement btnDelete) {
        this.btnDelete = btnDelete;
        return this;
    }

    public DisplayedGlobalArguments setCollectionName(String globalArgumentsCollectionName) {
        this.globalArgumentsCollectionName = globalArgumentsCollectionName;
        return this;
    }

    public DisplayedGlobalArguments setCreatedDate(String createdDate) {
        this.createDate = createdDate;
        return this;
    }


}
