package com.sdet.pages;

import com.sdet.base.BasePage;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {

    private final String USERNAME_INPUT = "#user-name";
    private final String PASSWORD_INPUT = "#password";
    private final String LOGIN_BUTTON = "#login-button";
    private final String ERROR_MESSAGE = "[data-test='error']";

    public LoginPage(Page page) {
        super(page);
    }

    public void navigate() {
        page.navigate("https://www.saucedemo.com");
    }

    public void login(String username, String password) {
        page.fill(USERNAME_INPUT, username);
        page.fill(PASSWORD_INPUT, password);
        page.click(LOGIN_BUTTON);
    }

    public String getErrorMessage() {
        return page.textContent(ERROR_MESSAGE);
    }
}