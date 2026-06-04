package extentreport.peserta.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;

public class ExtentManager {
    private static ExtentReports extent;

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }

    private static ExtentReports createInstance() {
        String reportDir = "extent-reports";
        File directory = new File(reportDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String reportPath = reportDir + "/ExtentReport.html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        
        // Configure report aesthetics
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Praktikum Selenium Extent Report");
        sparkReporter.config().setReportName("SauceDemo Automation Test Report");
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        // Add environment system info
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Application", "Saucedemo");
        extent.setSystemInfo("Environment", "Production/Staging");
        extent.setSystemInfo("Suite Name", "Functional Automation Testing");

        return extent;
    }
}
