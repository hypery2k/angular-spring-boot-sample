package ngSpring.demo.domain;

import ngSpring.demo.domain.entities.Event;
import ngSpring.demo.domain.fixtures.EventFixture;
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
public class EventDomainTest {

    private Event eventToTest;

    public EventDomainTest(Event event) {
        this.eventToTest = event;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Event invalidEventWithInvalidBranch = EventFixture.buildInvalidEventDataWithoutBranch();
        Event invalidEventWithValidBranch = EventFixture.buildInvalidEventDataWithoutBranch();
        // add them as parameters
        final Object[][] data = new Object[][]{
                {EventFixture.buildValidEventData()},
                {invalidEventWithInvalidBranch},
                {invalidEventWithValidBranch},
                {EventFixture.buildInvalidEventData()},
        };
        return Arrays.asList(data);

    }

    @Test
    public void shouldHandleToString() {
        assertThat(this.eventToTest.toString(), notNullValue());
    }

    @Test
    public void shouldHandleHashCode() {
        assertThat(this.eventToTest.hashCode(), notNullValue());
    }
}
