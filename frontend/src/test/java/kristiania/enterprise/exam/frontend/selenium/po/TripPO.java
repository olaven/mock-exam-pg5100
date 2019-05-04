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

    public int getDisplayedCost() {

        String text = getText("tripCost");
        int index = text.lastIndexOf(",-");
        if (index > 0) {
            text = text.substring(0, index);
        }

        return Integer.valueOf(text);
    }

    public void bookTrip() {

        clickAndWait("bookButton");
    }

    public void addToShoppingCart() {

        clickAndWait("addToShppingCarttButton");
    }
}
