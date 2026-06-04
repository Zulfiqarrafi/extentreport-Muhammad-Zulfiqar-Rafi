package extentreport.peserta.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import extentreport.peserta.tests.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final ExtentReports extent = ExtentManager.getInstance();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        // Log suite start
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        test.set(extentTest);
        test.get().log(Status.INFO, "Started executing test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test '" + result.getMethod().getMethodName() + "' passed successfully.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test '" + result.getMethod().getMethodName() + "' failed.");
        test.get().log(Status.FAIL, result.getThrowable());

        Object testClassInstance = result.getInstance();
        if (testClassInstance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) testClassInstance).getDriver();
            if (driver != null) {
                String relativeScreenshotPath = ((BaseTest) testClassInstance).captureScreenshot(result.getMethod().getMethodName());
                if (relativeScreenshotPath != null) {
                    test.get().addScreenCaptureFromPath(relativeScreenshotPath, "Failure Screenshot");
                }
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test '" + result.getMethod().getMethodName() + "' was skipped.");
        test.get().log(Status.SKIP, result.getThrowable());
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}
