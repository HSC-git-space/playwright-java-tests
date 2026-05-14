# playwright-java-tests

A UI test automation framework built with Java and Playwright, following the Page Object Model design pattern. Tests run against Sauce Demo and execute automatically on every push via GitHub Actions.

This repo is part of a broader SDET portfolio that includes Selenium, REST Assured, Cucumber BDD, and JMeter — each covering a different layer of the testing stack.

---

## Why Playwright?

Selenium sends HTTP commands and waits for responses. Playwright communicates via WebSocket and maintains a live connection to the browser — which means it always knows the current state of the page. That's why auto-waiting is built in. No explicit waits, fewer flaky tests, less boilerplate.

Playwright is growing fast in the Indian market, especially in fintech and product companies. Engineers who can work across both Selenium and Playwright are increasingly in demand.

---

## Tech Stack

| Tool | Version |
|------|---------|
| Java | 17 |
| Playwright | 1.44.0 |
| TestNG | 7.10.2 |
| Maven Surefire | 3.2.5 |
| CI | GitHub Actions |

---

## Project Structure
src/test/java/com/sdet/
├── base/
│   ├── BaseTest.java    # Browser lifecycle + screenshot on failure
│   └── BasePage.java    # Shared Page reference for all page classes
├── pages/
│   └── LoginPage.java   # Login page locators and actions
└── tests/
└── LoginTest.java   # Test logic only — no browser code, no raw selectors
src/test/resources/
└── testng.xml           # Single entry point for the suite
screenshots/             # Auto-populated on test failure, excluded from version control

---

## Key Design Decisions

**BaseTest and BasePage** — `BaseTest` owns the browser lifecycle. No test ever calls `Playwright.create()` directly. `BasePage` gives every page class automatic access to the `Page` object without passing it around manually.

**CSS Selectors** — All locators use IDs and data-test attributes. These survive layout changes. XPath breaks when DOM structure shifts even if the element itself hasn't changed.

**TestNG** — Chosen for its clean `@DataProvider` support and `ITestResult` injection in `@AfterMethod`, which powers the screenshot-on-failure feature.

---

## Running Locally

Install Playwright browsers once:
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"
```

Run the full suite:
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

---

## Test Coverage

| Test | Type | Description |
|------|------|-------------|
| `validLoginTest` | Functional | Valid credentials redirect to inventory page |
| `invalidLoginTest` | Negative | Invalid credentials show correct error message |
| `invalidLoginDataDrivenTest` | Data-driven | 3 invalid credential combinations via DataProvider |

**Total: 5 tests — all passing locally and in CI.**

---

## Screenshot on Failure

When a test fails, `BaseTest` captures a screenshot at the moment of failure and saves it to `screenshots/` named after the failing test. No need to reproduce locally to see what the browser looked like when things went wrong.

---

## CI/CD

Every push to `main` triggers the GitHub Actions pipeline. Steps: checkout → Java 17 setup → system dependencies → Chromium install → run suite via TestNG XML.

Tests run headless in CI. Same engine, same behaviour — just no window rendered on a server with no display.

---
## Docker

Tests are containerised using Docker, allowing the full suite to execute in any environment without local dependency setup.

### The Problem Docker Solves

Playwright requires specific Linux system libraries to launch Chromium. On a developer machine these may exist. On a fresh CI server they don't. Without containerisation, the test run depends on whatever happens to be installed on the host — which is exactly the kind of environment inconsistency that causes "works on my machine" failures.

Docker packages the entire execution environment — Java, Maven, system libraries, and Chromium — into a single image. Every run, everywhere, is identical.

### Why Chromium Is Installed at Build Time

Moving `playwright install` into the `RUN` layer of the Dockerfile means Chromium is baked into the image once during build. Every subsequent `docker run` skips the download entirely — faster execution, no network dependency at runtime.

### Commands

**Build the image:**
```bash
docker build -t hscdock/playwright-java-tests .
```

**Run the tests:**
```bash
docker run hscdock/playwright-java-tests
```

**Pull from Docker Hub:**
```bash
docker pull hscdock/playwright-java-tests
```

### Results

5/5 tests passing inside the container. The warning about missing deps on Run 1 is a known initialisation timing behaviour — Runs 2–5 pass cleanly. Documented as a known issue.

## Future Enhancements

- **Visual regression** — Percy or Applitools to catch UI changes functional tests miss
- **Extent Reports** — HTML reporting with embedded screenshots
- **Broader coverage** — post-login flows across product, cart, and checkout pages
- **Cross-browser** — Firefox and WebKit are supported by Playwright, framework is ready for it

---

## Portfolio

| Repo | Stack | Focus |
|------|-------|-------|
| selenium-testng-framework | Selenium + TestNG + Excel | UI automation, POM, data-driven |
| petstore-api-tests | RestAssured + TestNG | API testing, schema validation |
| behaviour-driven-qa | Cucumber + Selenium | BDD, Gherkin |
| jmeter-performance-tests | JMeter | Load testing, performance |
| playwright-java-tests | Playwright + TestNG | Modern UI automation |