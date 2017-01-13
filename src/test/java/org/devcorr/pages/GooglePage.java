package org.devcorr.pages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Created by joeymadich on 1/10/17.
 */
public class GooglePage extends BasePage {

    private static final String titleString = "Google";

    public GooglePage(WebDriver driver) {
        super(driver);

        //browse to the google home page for testing.
        this.setUrl("http://www.google.com");
        this.get();

        // Check that we're on the right page.
        Assert.assertEquals(this.getTitleString(), this.getTitle());
    }

    private String getTitleString() {
        return titleString;
    }
}
