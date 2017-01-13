package org.devcorr.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Created by joeymadich on 1/10/17.
 */
public class DSWLoginPage extends BasePage {
    private static final String titleString = "Account Login";
    private static final By loginNameLocator = By.id("loginName");
    private static final By passwordLocator = By.id("password");

    public DSWLoginPage(WebDriver driver) {
        super(driver);

        //browse to the dsw.com login page for testing.
        this.setUrl("https://www.dsw.com/dsw_shoes/user/loginAccount.jsp");
        this.get();

        // Check that we're on the right page.
        Assert.assertEquals(this.getTitleString(), this.getTitle());
    }

    public DSWLoginPage typeUserName(String email) {
        this.getDriver().findElement(loginNameLocator).sendKeys(email);
        return this;
    }

    public DSWLoginPage typePassword(String password) {
        this.getDriver().findElement(passwordLocator).sendKeys(password);
        return this;
    }

    private String getTitleString() {
        return titleString;
    }
}
