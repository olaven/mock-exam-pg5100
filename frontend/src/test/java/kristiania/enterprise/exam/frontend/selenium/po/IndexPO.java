package kristiania.enterprise.exam.frontend.selenium.po;

import kristiania.enterprise.exam.frontend.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/*
NOTE: Parts of this file is copied from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/IndexPO.java
*/

public class IndexPO extends LayoutPO {


    public IndexPO(PageObject other) {
        super(other);
    }

    public IndexPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public void toStartingPage(){
        getDriver().get(host + ":" + port);
    }

    @Override
    public boolean isOnPage() {

        String title = getDriver().getTitle();
        return title.contains("Welcome");
    }

    public boolean loggedInContentVisible() {

        try {
            return getDriver()
                    .findElement(By.xpath("//div[@id='loggedInContent']"))
                    .isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public int getAmountOfTopTrips() {

        return getDriver()
                .findElements(By.xpath("//table[@id='topTripsTable']/tbody/tr"))
                .size();
    }

    public int getAmountOfAllTrips() {

        return getDriver()
                .findElements(By.xpath("//table[@id='allTripsTable']/tbody/tr"))
                .size();
    }

    public void goToDetailsOfTrip(int index) {

        WebElement button = getDriver().findElements(By.className("goToDetailsButton")).get(index);
        button.click();
        waitForPageToLoad();
    }

}
