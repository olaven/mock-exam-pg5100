package kristiania.enterprise.exam.frontend.selenium.po;

import kristiania.enterprise.exam.frontend.selenium.PageObject;

public class TripPO extends LayoutPO {

    public TripPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().equals("Trip");
    }

    public String getDisplayedTitle() {

        return getText("tripTitle");
    }

    public String getDisplayedDescription() {

        return getText("tripDescription");
    }

    public String getDisplayedCost() {

        return getText("tripCost");
    }

    public void bookTrip() {

        clickAndWait("bookButton");
    }
}
