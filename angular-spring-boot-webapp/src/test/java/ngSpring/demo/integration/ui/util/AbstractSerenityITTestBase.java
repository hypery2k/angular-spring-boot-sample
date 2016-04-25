package ngSpring.demo.integration.ui.util;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.pages.Pages;
import net.thucydides.junit.annotations.Concurrent;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@Concurrent
@RunWith(SerenityRunner.class)
public abstract class AbstractSerenityITTestBase {

    @Managed
    public WebDriver webdriver;

    @ManagedPages
    public Pages pages;
}
