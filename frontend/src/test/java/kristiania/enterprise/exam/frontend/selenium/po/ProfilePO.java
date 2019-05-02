package kristiania.enterprise.exam.frontend.selenium.po;

import kristiania.enterprise.exam.frontend.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProfilePO extends PageObject {

    public ProfilePO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().equals("Profile");
    }

    public int getAmountOfBookedTrips() {

        List<WebElement> rows = getDriver().findElements(By.xpath("//table[@id='bookedTripsTable']/tbody//Tr"));
        return rows.size();
    }

    public String getDisplayedGivenName() {

        return getText("profileGivenName");
    }

    public String getDisplayedFamliyName() {

        return getText("profileFamilyName");
    }

    public String getDisplayedEmail() {

        return getText("profileEmail");
    }

}
