package ngSpring.demo.domain;

import ngSpring.demo.domain.entities.User;
import ngSpring.demo.domain.fixtures.UserFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author hypery2k
 */
@RunWith(Parameterized.class)
public class UserDomainTest {

    private User userToTest;

    public UserDomainTest(User user) {
        this.userToTest = user;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        User userWithName = UserFixture.invalidData();
        userWithName.setUsername("test");
        // add them as parameters
        final Object[][] data = new Object[][]{
                {UserFixture.validData()},
                {userWithName},
                {UserFixture.invalidData()}
        };
        return Arrays.asList(data);

    }

    @Test
    public void shouldHandleToString() {
        assertThat(this.userToTest.toString(), notNullValue());
    }

    @Test
    public void shouldHandleHashCode() {
        assertThat(this.userToTest.hashCode(), notNullValue());
    }
}
