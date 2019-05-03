package kristiania.enterprise.exam.frontend.selenium;

import kristiania.enterprise.exam.frontend.selenium.po.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

/*
NOTE: Parts of this file is copied from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/SeleniumTestBase.java
*/

public abstract class SeleniumTestBase {


    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();

    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId() {
        return "foo_SeleniumLocalIT_" + counter.getAndIncrement() + "@mail.com";
    }


    private IndexPO home;


    private IndexPO createNewUser(String email, String givenName, String familyName, String password) {

        home.toStartingPage();

        SignUpPO signUpPO = home.toSignUp();

        IndexPO indexPO = signUpPO.createUser(email, givenName, familyName, password);
        assertNotNull(indexPO);

        return indexPO;
    }

    @BeforeEach
    public void initTest() {

        /*
            we want to have a new session, otherwise the tests
            will share the same Session beans
         */
        getDriver().manage().deleteAllCookies();

        home = new IndexPO(getDriver(), getServerHost(), getServerPort());

        home.toStartingPage();

        assertTrue(home.isOnPage(), "Failed to start from Home Page");
    }

    @Test
    public void testCreateAndLogoutUser() {

        assertFalse(home.isLoggedIn());

        String email = getUniqueId();
        String givenName = "given test";
        String familyName = "family test";
        String password = "123456789";

        home = createNewUser(email, givenName, familyName, password);

        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(email));

        home.doLogout();

        assertFalse(home.isLoggedIn());
        assertFalse(home.getDriver().getPageSource().contains(email));
    }

    @Test
    public void testLoggedInDivVisibleWhenLoggedIn() {

        assertFalse(home.loggedInContentVisible());
        createNewUser(getUniqueId(), "given test", "family test", "password");
        assertTrue(home.loggedInContentVisible());
    }


    @Test
    public void testDefaultTrips() {

        int n = 5;
        home = createNewUser(getUniqueId(), "test given", "test family", "test password");
        assertTrue(home.isOnPage());

        int defaultTripsAmount = home.getAmountOfAllTrips();
        assertTrue(defaultTripsAmount > n);
    }

    @Test
    public void testDisplayTripDetails() {

        home = createNewUser(getUniqueId(), "test given", "test family", "test password");
        assertTrue(home.isLoggedIn());

        home.goToDetailsOfTrip(0);
        TripPO trip = new TripPO(home);

        assertTrue(trip.isOnPage());
        trip.bookTrip();

        trip.goToProfilePage();
        ProfilePO profile = new ProfilePO(trip);

        assertTrue(profile.isOnPage());

        assertEquals(1, profile.getAmountOfBookedTrips());
    }


    @Test
    public void testDisplayUserInfo() {

        String email = getUniqueId();
        String givenName = "test given";
        String familyName = "test family";
        String password = "test password";

        home = createNewUser(email, givenName, familyName, password);
        assertTrue(home.isLoggedIn());

        home.goToProfilePage();

        ProfilePO profile = new ProfilePO(home);

        assertEquals(email, profile.getDisplayedEmail());
        assertEquals(givenName, profile.getDisplayedGivenName());
        assertEquals(familyName, profile.getDisplayedFamliyName());
    }

    @Test
    public void testSearch() {

        home = createNewUser(getUniqueId(), "test given", "test fam", "test family");
        home.goToSearchPage();

        SearchPO search = new SearchPO(home);
        assertTrue(search.isOnPage());

        assertFalse(search.getDisplayedcount() > 0);

        search.enterQuery("a");
        search.selectLocation("Alaska");
        search.doSearch();

        assertTrue(search.getDisplayedcount() > 0);
    }

    @Test
    public void testNoLocationShowsError() {

        home = createNewUser(getUniqueId(), "given", "family", "password");
        home.goToSearchPage();

        SearchPO search = new SearchPO(home);
        search.enterQuery("query");
        // NOTE no location
        search.doSearch();

        assertTrue(search.showsError());
    }

    @Test
    public void testNoQueryShowsError() {

        home = createNewUser(getUniqueId(), "given", "family", "password");
        home.goToSearchPage();

        SearchPO search = new SearchPO(home);
        // NOTE no query
        search.selectLocation("Alaska");
        search.doSearch();

        assertTrue(search.showsError());
    }

    @Test
    public void testDoesNotShowErrorOnValidInput() {

        home = createNewUser(getUniqueId(), "given", "family", "password");
        home.goToSearchPage();

        SearchPO search = new SearchPO(home);

        search.enterQuery("a");
        search.selectLocation("Alaska");
        search.doSearch();

        assertFalse(search.showsError());
    }

}
