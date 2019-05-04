package kristiania.enterprise.exam.frontend.selenium.po;

import kristiania.enterprise.exam.frontend.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class ShoppingCartPO extends LayoutPO {

    public ShoppingCartPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().equals("Shopping cart");
    }

    public boolean displayedTotalMatches(int total) {

        String text = getDriver().findElement(By.id("shoppingCartTotal")).getText();
        String totalAsString = String.valueOf(total);

        return text.contains(totalAsString);
    }

    public int amountOfDisplayedTrips() {

        try {

            return getDriver()
                    .findElements(By.xpath("//li/span[@class='shoppingCartListItem']"))
                    .size();
        } catch(NoSuchElementException e) {

            return 0;
        }
    }

    public boolean bookAllButtonPresent() {

        try {
            getDriver().findElement(By.id("bookAllButton"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void bookAll() {

        clickAndWait("bookAllButton");
    }

}
