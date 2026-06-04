package extentreport.peserta.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators - Information Step
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");

    // Locators - Overview Step
    private final By finishButton = By.id("finish");

    // Locators - Complete Step
    private final By completeHeader = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterInformation(String firstName, String lastName, String postalCode) {
        // Wait for the step-one page URL explicitly
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        
        // Wait for page to be completely stable
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        
        typeWithRetry(firstNameField, firstName);
        typeWithRetry(lastNameField, lastName);
        typeWithRetry(postalCodeField, postalCode);

        // Verification prints
        System.out.println("DEBUG: First Name value = " + driver.findElement(firstNameField).getAttribute("value"));
        System.out.println("DEBUG: Last Name value = " + driver.findElement(lastNameField).getAttribute("value"));
        System.out.println("DEBUG: Postal Code value = " + driver.findElement(postalCodeField).getAttribute("value"));
    }

    private void typeWithRetry(By locator, String text) {
        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                
                // Focus and clear
                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                js.executeScript("arguments[0].focus();", element);
                
                element.clear();
                element.sendKeys(text);
                
                Thread.sleep(200); // Wait for React to process
                
                if (element.getAttribute("value").equals(text)) {
                    return; // Success!
                }
            } catch (Exception e) {
                System.err.println("Attempt " + attempt + " failed for " + locator + ": " + e.getMessage());
            }
            try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        }
        
        // Final fallback: React tracker bypass
        System.out.println("WARNING: Standard typing failed. Applying React tracker bypass for locator: " + locator);
        try {
            WebElement element = driver.findElement(locator);
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            js.executeScript(
                "var el = arguments[0];" +
                "var val = arguments[1];" +
                "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                "setter.call(el, val);" +
                "el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "el.dispatchEvent(new Event('change', { bubbles: true }));",
                element, text
            );
            Thread.sleep(200);
        } catch (Exception e) {
            System.err.println("React bypass failed for " + locator + ": " + e.getMessage());
        }
    }

    public void clickContinue() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", btn);
        // Wait for overview page transition
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
    }

    public void clickFinish() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", btn);
        // Wait for complete page transition
        wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
    }

    public boolean isCompleteHeaderDisplayed() {
        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader));
            return header.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCompleteHeaderClassText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader)).getText();
    }
}
