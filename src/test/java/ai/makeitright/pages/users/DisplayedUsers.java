package ai.makeitright.pages.users;

import org.openqa.selenium.WebElement;

public class DisplayedUsers {

    private String email;
    private String firstLastName;
    private WebElement lnkUserDetails;
    private String role;
    private String srcAvatarImage;


    public String getFirstLastName() {
        return firstLastName;
    }

    public String getEmail() {
        return email;
    }

    public WebElement getLnkUserDetails() {
        return lnkUserDetails;
    }

    public String getRole() {
        return role;
    }

    public String getSrcAvatarImage() {
        return srcAvatarImage;
    }

    public DisplayedUsers setEmail(String email) {
        this.email = email;
        return this;
    }

    public DisplayedUsers setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
        return this;
    }

    public DisplayedUsers setLnkUserDetails(WebElement lnkUserDetails) {
        this.lnkUserDetails = lnkUserDetails;
        return this;
    }

    public DisplayedUsers setRole(String role) {
        this.role = role;
        return this;
    }

    public DisplayedUsers setSrcAvatarImage(String srcAvatarImage) {
        this.srcAvatarImage = srcAvatarImage;
        return this;
    }


}
