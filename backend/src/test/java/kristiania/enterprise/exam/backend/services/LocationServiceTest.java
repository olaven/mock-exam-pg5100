package kristiania.enterprise.exam.backend.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationServiceTest extends ServiceTestBase {

    @Test
    public void testCanCreateLocations() {

        int before = locationService.getAllLocations().size();

        int n = 10;
        for (int i = 0; i < n; i++) {
            persistLocation();
        }

        int after = locationService.getAllLocations().size();

        assertEquals(before + n, after);
    }
}