package com.sdet.base;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterMethod
    public void tearDown() {
        page.close();
        context.close();
        browser.close();
        playwright.close();
    }
}