package ngSpring.demo.domain.validation;

import ngSpring.demo.domain.entities.Event;
import ngSpring.demo.domain.fixtures.EventFixture;
import ngSpring.demo.util.ValidationTestBase;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;


/**
 * @author hypery2k
 */
public class EventValidationTest extends ValidationTestBase<Event> {

    @Test
    public void shouldAcceptValidEntity() {
        //given
        Event event = EventFixture.buildValidEventData();
        //when
        Set<ConstraintViolation<Event>> constraintViolations = validator.validate(event);
        //then
        List<String> messages = getValidationMessages(constraintViolations);
        assertThat(messages.size(), is(equalTo(0)));
    }

    @Test
    public void shouldValidateRequiredFields() {
        //given
        Event eventWithValidationErrors = EventFixture.buildInvalidEventData();
        //when
        Set<ConstraintViolation<Event>> constraintViolations = validator.validate(eventWithValidationErrors);
        //then
        List<String> messages = getValidationMessages(constraintViolations);
        assertThat(constraintViolations.size(), is(equalTo(2)));
        assertThat(messages, containsInAnyOrder(
                "{validation.date.range_error}",
                "{javax.validation.constraints.NotNull.message}"));
    }
}
