package ngSpring.demo.integration.rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import ngSpring.demo.domain.entities.Event;
import ngSpring.demo.util.RestITBase;
import org.apache.commons.httpclient.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class EventControllerIT extends RestITBase {

    private Date startDate;

    private Date endDate;

    private Event sampleEvent1;

    private Event sampleEvent2;

    private Event sampleEvent3;

    @Before
    public void setUp() throws ParseException {
        super.setUp();

        startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2015-06-01");
        endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2015-06-02");
        sampleEvent1 = new Event("Event1", startDate, endDate);
        sampleEvent2 = new Event("Event2", startDate, endDate);
        sampleEvent3 = new Event("Event3", startDate, endDate);
        getEventRepository().save(Arrays.asList(sampleEvent1, sampleEvent2, sampleEvent3));
    }

    // Negative test cases

    @Test
    public void shouldNotPutEventSinceStartDateIsAfterEndDate() {
        String json = new JSONBuilder()
                .add("eventId", sampleEvent1.getEventId())
                .add("eventDescription", "MeinAngpasstesEvent")
                .add("startDate", "2015-06-15").add("endDate", "2015-06-11")
                .build();
        loginWithCorrectCredentials()
                .and()
                .body(json)
                .and()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .put("/api/events/{eventId}", sampleEvent1.getEventId());
    }

    @Test
    public void shouldNotPostEventSinceStartDateIsAfterEndDate() {
        String json = new JSONBuilder()
                .add("eventDescription", "MeinAngpasstesEvent")
                .add("startDate", "2015-06-15").add("endDate", "2015-06-11")
                .build();
        loginWithCorrectCredentials()
                .and()
                .body(json)
                .and()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post("/api/events");
    }

    @Test
    public void shouldFailWithWrongAuth() {
        try {
            loginWithIncorrectCredentials()
                    .expect()
                    .statusCode(HttpStatus.SC_UNAUTHORIZED)
                    .when()
                    .get("/api/events/{eventId}", sampleEvent1.getEventId());
        } finally {
            RestAssured.reset();
        }
    }

    @Test
    public void shouldFailWithNoAuth() throws Exception {
        try {
            loginWithEmptyCredentials()
                    .expect()
                    .statusCode(HttpStatus.SC_MOVED_TEMPORARILY)
                    .when()
                    .get("/api/events/{eventId}", sampleEvent1.getEventId());
        } finally {
            RestAssured.reset();
        }
    }

    @Test
    public void shouldFetchEvent() {
        loginWithCorrectCredentials()
                .expect()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .get("/api/events/{eventId}", sampleEvent1.getEventId());
    }

    @Test
    public void shouldNotFindEvent() {
        loginWithCorrectCredentials()
                .expect()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .when()
                .get("/api/events/{eventId}", "42");
    }

    // positive test cases

    @Test
    public void shouldFetchAll() {
        loginWithCorrectCredentials()
                .expect()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .get("/api/events");
    }

    @Test
    public void shouldPostEvent() {
        String json = new JSONBuilder()
                .add("eventDescription", "MeinAngpasstesEvent")
                .add("startDate", "2015-06-01").add("endDate", "2015-06-11")
                .build();
        loginWithCorrectCredentials()
                .and()
                .body(json)
                .and()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post("/api/events");
    }

    @Test
    public void shouldPutEvent() {
        String json = new JSONBuilder()
                .add("eventId", sampleEvent1.getEventId())
                .add("eventDescription", "MeinAngpasstesEvent")
                .add("startDate", "2015-06-01").add("endDate", "2015-06-11")
                .build();
        loginWithCorrectCredentials()
                .and()
                .body(json)
                .and()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .put("/api/events/{eventId}", sampleEvent1.getEventId());
    }

    @Test
    public void shouldDeleteEvent() {
        loginWithCorrectCredentials()
                .expect()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .delete("/api/events/{eventId}", sampleEvent1.getEventId());
    }
}
