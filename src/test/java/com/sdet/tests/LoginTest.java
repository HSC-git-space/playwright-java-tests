package com.sdet.tests;

import com.sdet.base.BaseTest;
import com.sdet.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void validLoginTest() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(page.url().contains("inventory"), "Login failed — inventory page not loaded");
    }
    @Test
    public void invalidLoginTest() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        loginPage.login("invalid_user", "wrong_password");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                "Error message not displayed for invalid login");
    }
}