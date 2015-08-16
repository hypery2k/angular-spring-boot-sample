package ngSpring.demo.transformer;

import lombok.RequiredArgsConstructor;
import ngSpring.demo.domain.dto.UserDTO;
import ngSpring.demo.domain.entities.User;
import ngSpring.demo.domain.fixtures.UserFixture;
import ngSpring.demo.repositories.UserRepository;
import ngSpring.demo.transformer.impl.UserTransformer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class UserTransformerTest {

    @InjectMocks
    private UserTransformer transformer;

    @Mock
    private UserRepository userRepository;

    // test data

    private final User entity;

    private final boolean shouldFail;


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        // add them as parameters
        final Object[][] data = new Object[][]{
                // @formatter:off
                {UserFixture.validData(), false},
                {UserFixture.invalidData(), true},
                // @formatter:on
        };
        return Arrays.asList(data);

    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(userRepository.findByUsernameAndDeletedFalse(any(String.class))).thenReturn(entity);
    }

    @Test
    public void shouldTransformCorrectly() throws Exception {
        try {
            UserDTO dto = transformer.transformToDTO(entity);
            User entityTransformed = transformer.transformToEntity(dto);
            // ensure that transformation worked
            assertThat(this.entity, equalTo(entityTransformed));
        } catch (Exception e) {
            if (!this.shouldFail) {
                throw e;
            }
        }
    }
}
