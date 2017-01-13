package org.devcorr.tests;

import org.devcorr.pages.DSWLoginPage;
import org.devcorr.pages.GooglePage;
import org.testng.annotations.Test;

/**
 * Created by joeymadich on 1/10/17.
 */
public class FrameworkTest extends BaseTest {

    @Test(testName = "google page verification")
    public void googleTest() {
        GooglePage googlePage = new GooglePage(driver);
    }

}
