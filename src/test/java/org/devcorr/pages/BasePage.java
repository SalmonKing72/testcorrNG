package org.devcorr.pages;

import org.openqa.selenium.WebDriver;

/**
 * Created by joeymadich on 1/10/17.
 */
public abstract class BasePage {
    private final WebDriver driver;
    private String url;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public synchronized void get() {
        driver.get(this.getUrl());
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public String getTitle() {
        return this.driver.getTitle();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
