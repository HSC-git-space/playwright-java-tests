package com.sdet.tests;

import com.sdet.base.BaseTest;
import com.sdet.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

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
    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][] {
                {"invalid_user", "secret_sauce"},
                {"standard_user", "wrong_password"},
                {"", ""},
        };
    }

    @Test(dataProvider = "invalidCredentials")
    public void invalidLoginDataDrivenTest(String username, String password) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        loginPage.login(username, password);
        Assert.assertTrue(loginPage.getErrorMessage().contains("Epic sadface"),
                "Error message not shown for: " + username);
    }
}