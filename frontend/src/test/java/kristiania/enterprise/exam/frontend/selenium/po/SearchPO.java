package kristiania.enterprise.exam.frontend.selenium.po;

import kristiania.enterprise.exam.frontend.selenium.PageObject;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SearchPO extends PageObject {

    public SearchPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().equals("Search");
    }

    public void enterQuery(String query) {

        setText("searchQuery", query);
    }

    public void selectLocation(String name){

        Select select = new Select(getDriver().findElement(By.id("locationSelect")));
        select.selectByVisibleText(name);
    }


    public int getDisplayedcount() {

        try {
            return getDriver()
                    .findElements(By.xpath("//table[@id='resultTripsTable']/tbody/tr"))
                    .size();
        } catch (NoSuchElementException e) {
            return 0; // table not rendered when no rows
        }
    }
    public void doSearch() {

        clickAndWait("doSearchButton");
    }

    public boolean showsError() {

        try {
            return getDriver()
                    .findElement(By.id("searchError"))
                    .isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
