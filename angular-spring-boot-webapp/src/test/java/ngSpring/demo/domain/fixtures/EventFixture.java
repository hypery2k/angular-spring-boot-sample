package ngSpring.demo.domain.fixtures;

import ngSpring.demo.domain.entities.Event;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @author hypery2k
 */
public class EventFixture {


    public static Event buildValidEventData() {
        return Event.builder()
                .eventDescription("Test Event")
                .startDate(new Date())
                .endDate(DateUtils.addDays(new Date(), 2))
                .build();
    }

    public static Event buildInvalidEventDataWithoutBranch() {
        return Event.builder()
                .eventId(null)
                .eventDescription(null)
                .startDate(new Date())
                .endDate(DateUtils.addDays(new Date(), -2))
                .build();
    }

    public static Event buildInvalidEventDataWithoutBranchId() {
        return Event.builder()
                .eventId(null)
                .eventDescription(null)
                .startDate(new Date())
                .endDate(DateUtils.addDays(new Date(), -2))
                .build();
    }

    public static Event buildInvalidEventData() {
        return Event.builder()
                .eventId(null)
                .eventDescription(null)
                .startDate(new Date())
                .endDate(DateUtils.addDays(new Date(), -2))
                .build();
    }
}
