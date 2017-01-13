package org.devcorr.tests;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.devcorr.reporting.ExtentManager;
import org.devcorr.reporting.ExtentTestManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


/**
 * Created by joeymadich on 1/10/17.
 */
public abstract class BaseTest {

    //package-private given no access modifier present.
    WebDriver driver;

    @BeforeSuite
    @Parameters({"browser", "screenshotDir"})
    public void setupReporting(@Optional("phantomjs") String browser, String screenshotDir) throws IOException {
        FileUtils.cleanDirectory(new File(screenshotDir));
        ExtentManager.getReporter();
        ExtentManager.getReporter().addSystemInfo("Browser", browser);
    }

    @BeforeClass
    @Parameters("browser")
    public void setupEnvironment(@Optional("phantomjs") String browser) {
        switch(browser) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "phantomjs":
                driver = new PhantomJSDriver();
                break;
            default:
                throw new IllegalArgumentException("Invalid browser type, expected ('chrome', 'phantomjs') and received " + browser);
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @BeforeMethod
    @Parameters("browser")
    public void startTestReporting(@Optional("phantomjs") String browser, Method method) {
        ExtentTestManager.startTest(method.getName()).log(LogStatus.INFO, "starting test with browser " + browser);
    }

    @AfterMethod
    @Parameters({"browser", "projectRoot"})
    public void endTestReporting(@Optional("phantomjs") String browser, String projectRoot, ITestResult result) {
        ExtentTest test = ExtentTestManager.getTest();
        test.log(LogStatus.INFO, "finishing " + test.getTest().getName() + " with browser " + browser);

        if(result.getStatus() == ITestResult.FAILURE) {
            String screenshot_path = this.captureScreenShot(result.getName(), projectRoot);
            if (!screenshot_path.isEmpty()) {
                String image = test.addScreenCapture(screenshot_path);
                test.log(LogStatus.INFO, "screenshot taken", image);
            }
        }

        ExtentTestManager.reportTestResult(result);
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();
    }

    @AfterClass
    public void tearDownTest() {
        //write reporting output

        driver.close();
        driver.quit();
    }

    @AfterSuite
    public void tearDown() {
        ExtentManager.getReporter().close();
    }

    /**
     *
     * @param testName the name of the test for reporting
     * @param projectRoot the project root for appropriate directory placement
     *                    passed in from the testng config file.
     * @return the path to the image to log as an entry in the report
     */
    private String captureScreenShot(String testName, String projectRoot) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            File dest = new File("reports/screenshots/" + testName + System.currentTimeMillis() + ".png");
            // now copy the  screenshot to desired location using copyFile method
            FileUtils.copyFile(src, dest);
            return "/" + projectRoot + dest.getPath();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}