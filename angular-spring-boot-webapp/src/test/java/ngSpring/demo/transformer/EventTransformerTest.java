package ngSpring.demo.transformer;

import lombok.RequiredArgsConstructor;
import ngSpring.demo.domain.dto.EventDTO;
import ngSpring.demo.domain.entities.Event;
import ngSpring.demo.domain.fixtures.EventFixture;
import ngSpring.demo.transformer.impl.EventTransformer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class EventTransformerTest {

    @InjectMocks
    private EventTransformer transformer;

    // test data

    private final Event entity;

    private final boolean shouldFail;


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        // add them as parameters
        final Object[][] data = new Object[][]{
                // @formatter:off
                {EventFixture.buildValidEventData(), false},
                {EventFixture.buildInvalidEventData(), true},
                {EventFixture.buildInvalidEventDataWithoutBranchId(), true},
                {EventFixture.buildInvalidEventDataWithoutBranch(), true},
                // @formatter:on
        };
        return Arrays.asList(data);

    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldTransformCorrectly() throws Exception {
        try {
            EventDTO dto = transformer.transformToDTO(entity);
            Event entityTransformed = transformer.transformToEntity(dto);
            // ensure that transformation worked
            assertThat(this.entity, equalTo(entityTransformed));
        } catch (Exception e) {
            if (!this.shouldFail) {
                throw e;
            }
        }
    }
}
