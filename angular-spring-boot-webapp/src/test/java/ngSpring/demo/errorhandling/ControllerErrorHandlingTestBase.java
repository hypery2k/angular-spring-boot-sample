package ngSpring.demo.errorhandling;

import ngSpring.demo.util.MockedMvcTestBase;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ControllerErrorHandlingTestBase extends MockedMvcTestBase {

    @Test
    public void shouldHandleInvalidURLWith404() throws Exception {
        // given
        String uri = "/really/unkown/";
        // when and then
        this.mockMvc
                .perform(get(uri))
                .andExpect(status().isNotFound());
    }
}
