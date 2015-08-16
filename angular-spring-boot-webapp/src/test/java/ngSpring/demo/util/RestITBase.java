package ngSpring.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;
import ngSpring.demo.AngularSpringApplication;
import ngSpring.demo.domain.entities.User;
import ngSpring.demo.repositories.EventRepository;
import ngSpring.demo.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringApplicationConfiguration(classes = AngularSpringApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public abstract class RestITBase {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${local.server.port}")
    protected int port;

    @Before
    public void setUp() throws ParseException {
        RestAssured.port = port;
        userRepository.save(User.builder()
                .enabled(true)
                .username("user")
                .password(new BCryptPasswordEncoder().encode("password"))
                .build());
    }

    @After
    public void clean() {
        try {
            this.userRepository.deleteAll();
            this.eventRepository.deleteAll();
        } catch (Exception ignored) {
        }
    }

    protected UserRepository getUserRepository() {
        return userRepository;
    }

    public EventRepository getEventRepository() {
        return eventRepository;
    }

    protected int getPort() {
        return port;
    }

    protected RequestSpecification login(String user, String password) {
        return given().auth().preemptive().basic(user, password).redirects()
                .follow(false);
    }

    protected String toJSON(Object entity) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(entity);
        } catch (Exception e) {
            return "";
        }
    }

    protected String toJSON(Map<String, String> map) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(map);
        } catch (Exception ignored) {
            return "";
        }
    }

    // HELPERS
    protected RequestSpecification loginWithCorrectCredentials() {
        return login("user", "password");
    }

    protected RequestSpecification loginWithIncorrectCredentials() {
        return login("user", "blub");
    }

    protected RequestSpecification loginWithEmptyCredentials() {
        return given().auth().none().redirects().follow(false);
    }

    public class JSONBuilder {

        private Map<String, String> properties = new HashMap<String, String>();

        public JSONBuilder add(String key, String value) {
            this.properties.put(key, value);
            return this;
        }

        public String build() {
            return toJSON(this.properties);
        }
    }

}
