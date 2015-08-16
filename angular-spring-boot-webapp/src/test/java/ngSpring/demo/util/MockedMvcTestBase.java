package ngSpring.demo.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ngSpring.demo.AngularSpringApplication;
import ngSpring.demo.domain.entities.Event;
import ngSpring.demo.repositories.EventRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringApplicationConfiguration(classes = AngularSpringApplication.class)
@WebAppConfiguration
public abstract class MockedMvcTestBase {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected EventRepository eventRepository;

    protected MockMvc mockMvc;

    protected DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd");

    protected MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.eventRepository.deleteAll();
    }

    protected List<Event> loadEvents() throws ParseException {
        List<Event> eventList = new ArrayList<Event>();
        eventList.add(createEvent("Viele neue Preise", dfmt.parse("2015-06-01"), null));
        eventList.add(createEvent("voll geile Mucke!", dfmt.parse("2014-01-03"), dfmt.parse("2015-02-01")));
        eventList.add(createEvent("Gedöns", dfmt.parse("2015-01-01"), dfmt.parse("2015-11-01")));
        return eventList;
    }

    // ENTITY HELPERS

    protected Event createEvent(String eventDescription, Date startDate, Date endDate) {
        return eventRepository.save(new Event(eventDescription, startDate, endDate));
    }

    // HELPER

    protected String createJSON(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        mapper.setDateFormat(outputFormat);
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        return mapper.writeValueAsString(object);
    }

}
