package org.devcorr.reporting;

import com.relevantcodes.extentreports.ExtentReports;

/**
 * Created by joeymadich on 1/12/17.
 */
public class ExtentManager {
    static ExtentReports extent;
    final static String filePath = "reports/ExtentReportTestNG.html";

    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            extent = new ExtentReports(filePath, true);
        }

        return extent;
    }
}