package org.devcorr.tests;

import org.devcorr.pages.DSWLoginPage;
import org.testng.annotations.Test;

/**
 * Created by joeymadich on 1/12/17.
 */
public class FrameworkTest2 extends BaseTest {

    @Test(testName = "dsw login page verification")
    public void dswTest() {
        DSWLoginPage dswLoginPage = new DSWLoginPage(driver);
        dswLoginPage.typeUserName("test@test.com");
        dswLoginPage.typePassword("test1234");
    }

}
