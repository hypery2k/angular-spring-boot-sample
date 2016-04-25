package ngSpring.demo.integration.ui.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.webdriver.exceptions.ElementShouldBeInvisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Common page, e.g. for messages ...
 *
 * @author mreinhardt
 */
public abstract class CmsPage extends PageObject {

    public CmsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[contains(@class, 'alert-danger')]/button[contains(@class, 'close')]")
    private WebElement closeErrorMessageButton;

    @FindBy(xpath = "//*[contains(@class, 'alert-info')]/button[contains(@class, 'close')]")
    private WebElement closeInfoMessageButton;

    @FindBy(xpath = "//div[contains(@class,'block-ui-message-container')]//div[contains(@class,'block-ui-message')]")
    protected WebElement loadingIndicator;

    public boolean errorMessageIsVisible() {
        waitForLoader();
        return containsElements(".alert-danger");
    }

    public boolean infoMessageIsVisible() {
        waitForLoader();
        return containsElements(".alert-info");
    }

    public boolean onlyOneErrorMessageIsVisible() {
        waitForLoader();
        return findAll(".alert-danger").size() == 1;
    }

    public boolean onlyOneInfoMessageIsVisible() {
        waitForLoader();
        return findAll(".alert-info").size() == 1;
    }

    public void closeInfoMessageButton() {
        waitForLoader();
        element(closeInfoMessageButton).click();
    }

    public void closeErrorMessageButton() {
        waitForLoader();
        element(closeErrorMessageButton).click();
    }

    public void click(WebElement webElement) {
        waitForLoader();
        element(webElement).waitUntilEnabled();
        element(webElement).click();
        waitForLoader();
    }

    public void enterText(WebElement webElement, String text) {
        waitForLoader();
        element(webElement).waitUntilEnabled();
        element(webElement).clear();
        element(webElement).sendKeys(text);
    }


    public void select(WebElement webElement, String text) {
        waitForLoader();
        element(webElement).waitUntilEnabled();
        element(webElement).selectByVisibleText(text);
        waitForLoader();
    }

    public void waitForLoader() {

        try {
            element(loadingIndicator).waitUntilNotVisible();
        } catch (ElementShouldBeInvisibleException ignored) {
            // we ignore here the element not visible execption
        }
    }
}
