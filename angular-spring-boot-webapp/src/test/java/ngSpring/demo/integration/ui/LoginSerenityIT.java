package ngSpring.demo.integration.ui;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.*;
import net.thucydides.junit.annotations.UseTestDataFrom;
import ngSpring.demo.integration.ui.steps.LoginSteps;
import ngSpring.demo.integration.ui.util.AbstractSerenityITTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author mreinhardt
 */
@WithTags({
        @WithTag(type = "epic", name = "User management")
})
@Narrative(text = "Login")
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "testdata/login.csv")
public class LoginSerenityIT extends AbstractSerenityITTestBase {

    @Steps
    public LoginSteps loginSteps;

    // test data

    private String username;

    private String password;

    private boolean fail;

    @WithTags({
            @WithTag(type = "feature", name = "Login"),
            @WithTag(type = "feature", name = "User"),
            @WithTag(type = "testtype", name = "postdeploy")
    })
    @Test
    @Issues(value = {"#9"})
    public void loginShouldWork() {
        loginSteps.performLogin(this.username, this.password);
        if (fail) {
            loginSteps.userShouldSeeAnErrorMessage();
        } else {
            loginSteps.userShouldSeeNoErrorMessage();
        }
    }
}
