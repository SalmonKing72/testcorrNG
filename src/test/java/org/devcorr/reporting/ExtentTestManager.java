package org.devcorr.reporting;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joeymadich on 1/12/17.
 */
public class ExtentTestManager {
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    private static ExtentReports extent = ExtentManager.getReporter();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized void endTest() {
        extent.endTest(extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }

    public static synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.startTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);

        return test;
    }

    public static synchronized void reportTestResult(ITestResult result) {
        ExtentTest test = getTest();
        int resultStatus = result.getStatus();
        LogStatus status;

        switch(resultStatus) {
            case ITestResult.FAILURE:
                status = LogStatus.FAIL;
                break;
            case ITestResult.SKIP:
                status = LogStatus.SKIP;
                break;
            case ITestResult.SUCCESS:
                status = LogStatus.PASS;
                break;
            default:
                status = LogStatus.UNKNOWN;
        }

        test.getTest().setStartedTime(getTime(result.getStartMillis()));
        test.getTest().setEndedTime(getTime(result.getEndMillis()));

        for (String group : result.getMethod().getGroups())
            test.assignCategory(group);

        String message = "Test " + status.toString().toLowerCase();

        if (result.getThrowable() != null)
            message = result.getThrowable().getMessage();

        test.log(status, message);
    }

    private static synchronized Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}