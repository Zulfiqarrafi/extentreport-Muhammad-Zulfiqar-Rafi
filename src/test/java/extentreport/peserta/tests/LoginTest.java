package extentreport.peserta.tests;

import extentreport.peserta.pages.LoginPage;
import extentreport.peserta.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Verify successful login using valid credentials")
    public void testPositiveLogin() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);

        // Perform login
        loginPage.login("standard_user", "secret_sauce");

        // Verify successful login (directed to Products page)
        Assert.assertTrue(inventoryPage.isHeaderDisplayed(), "Header title is not displayed.");
        Assert.assertEquals(inventoryPage.getHeaderText(), "Products", "Header title text does not match 'Products'.");
    }

    @Test(description = "Verify login is rejected when using invalid credentials")
    public void testNegativeLogin() {
        LoginPage loginPage = new LoginPage(driver);

        // Perform login with wrong password
        loginPage.login("standard_user", "wrong_password");

        // Verify failure and error message
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message was not displayed.");
        String expectedError = "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(loginPage.getErrorMessageText(), expectedError, "Error message text is incorrect.");
    }
}
