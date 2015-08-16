package ngSpring.demo.domain.validation;

import ngSpring.demo.domain.entities.User;
import ngSpring.demo.domain.fixtures.UserFixture;
import ngSpring.demo.util.ValidationTestBase;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

public class UserValidationTest extends ValidationTestBase<User> {
    @Test
    public void shouldAcceptValidEntity() {
        // given
        User user = UserFixture.validData();
        // when
        Set<ConstraintViolation<User>> constraintViolations = validator
                .validate(user);
        // then
        List<String> messages = getValidationMessages(constraintViolations);
        assertThat(messages.size(), is(equalTo(0)));
    }

    @Test
    public void shouldValidateRequiredFields() {
        // given
        User user = UserFixture.invalidData();
        // when
        Set<ConstraintViolation<User>> constraintViolations = validator
                .validate(user);
        // then
        List<String> messages = getValidationMessages(constraintViolations);
        assertThat(constraintViolations.size(), is(equalTo(2)));
        assertThat(messages, containsInAnyOrder(
                "{javax.validation.constraints.NotNull.message}",
                "{javax.validation.constraints.NotNull.message}"));
    }
}
