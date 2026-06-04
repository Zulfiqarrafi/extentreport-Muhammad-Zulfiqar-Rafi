package extentreport.peserta.tests;

import extentreport.peserta.pages.LoginPage;
import extentreport.peserta.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortTest extends BaseTest {

    @Test(description = "Verify product list can be sorted by Name (Z to A)")
    public void testSortNameZtoA() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);

        // Login first
        loginPage.login("standard_user", "secret_sauce");

        // Sort by Z to A
        inventoryPage.selectSortOption("za");

        // Get actual names after sorting
        List<String> actualNames = inventoryPage.getProductNames();

        // Create a copy and sort it descending
        List<String> sortedNames = new ArrayList<>(actualNames);
        Collections.sort(sortedNames);
        Collections.reverse(sortedNames);

        // Verify order
        Assert.assertEquals(actualNames, sortedNames, "Product names are not sorted in Z to A order.");
    }

    @Test(description = "Verify product list can be sorted by Price (Low to High)")
    public void testSortPriceLowToHigh() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);

        // Login first
        loginPage.login("standard_user", "secret_sauce");

        // Sort by Price (Low to High)
        inventoryPage.selectSortOption("lohi");

        // Get actual prices after sorting
        List<Double> actualPrices = inventoryPage.getProductPrices();

        // Create a copy and sort it ascending
        List<Double> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices);

        // Verify order
        Assert.assertEquals(actualPrices, sortedPrices, "Product prices are not sorted in Low to High order.");
    }
}
