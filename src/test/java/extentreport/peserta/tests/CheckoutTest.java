package extentreport.peserta.tests;

import extentreport.peserta.pages.LoginPage;
import extentreport.peserta.pages.InventoryPage;
import extentreport.peserta.pages.CartPage;
import extentreport.peserta.pages.CheckoutPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    @Test(description = "Verify successful checkout flow after adding two products to the cart")
    public void testSuccessfulCheckout() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        // 1. Login
        loginPage.login("standard_user", "secret_sauce");

        // 2. Select minimal 2 products and add to cart
        inventoryPage.addProductsToCart(2);
        
        // Verify cart badge has 2 items
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 2, "Cart badge count is not 2.");

        // 3. Navigate to Cart
        inventoryPage.clickCart();

        // 4. Click Checkout
        cartPage.clickCheckout();

        // 5. Fill information & Continue
        checkoutPage.enterInformation("TestFirst", "TestLast", "12345");
        checkoutPage.clickContinue();

        // 6. Finish checkout on Overview page
        checkoutPage.clickFinish();

        // 7. Verify success page displays "Thank you for your order!"
        Assert.assertTrue(checkoutPage.isCompleteHeaderDisplayed(), "Checkout complete header is not displayed.");
        Assert.assertEquals(checkoutPage.getCompleteHeaderClassText(), "Thank you for your order!", "Checkout complete message is incorrect.");
    }
}
