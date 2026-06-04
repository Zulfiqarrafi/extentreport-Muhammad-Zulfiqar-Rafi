package extentreport.peserta.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By headerTitle = By.cssSelector(".title");
    private final By sortDropdown = By.className("product_sort_container");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");
    private final By cartLink = By.className("shopping_cart_link");
    private final By cartBadge = By.className("shopping_cart_badge");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isHeaderDisplayed() {
        try {
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(headerTitle));
            return title.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getHeaderText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(headerTitle)).getText();
    }

    public void selectSortOption(String optionValue) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown));
        Select select = new Select(dropdown);
        select.selectByValue(optionValue);
    }

    public List<String> getProductNames() {
        // Wait for elements to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(productNames));
        List<WebElement> elements = driver.findElements(productNames);
        List<String> names = new ArrayList<>();
        for (WebElement element : elements) {
            names.add(element.getText().trim());
        }
        return names;
    }

    public List<Double> getProductPrices() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productPrices));
        List<WebElement> elements = driver.findElements(productPrices);
        List<Double> prices = new ArrayList<>();
        for (WebElement element : elements) {
            String priceText = element.getText().replace("$", "").trim();
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    public void addProductsToCart(int count) {
        String[] productIds = {
            "add-to-cart-sauce-labs-backpack",
            "add-to-cart-sauce-labs-bike-light",
            "add-to-cart-sauce-labs-bolt-t-shirt",
            "add-to-cart-sauce-labs-fleece-jacket",
            "add-to-cart-sauce-labs-onesie"
        };

        List<String> idList = new ArrayList<>();
        for (String id : productIds) {
            idList.add(id);
        }
        Collections.shuffle(idList);

        for (int i = 0; i < count; i++) {
            String id = idList.get(i);
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
            
            // Click using JavaScript directly to ensure it registers in headless Chrome
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", btn);
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        }
    }

    public int getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(badges.get(0).getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public void clickCart() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(cartLink));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }
}
