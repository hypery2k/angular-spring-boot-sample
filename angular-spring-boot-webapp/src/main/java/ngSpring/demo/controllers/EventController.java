package ngSpring.demo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ngSpring.demo.domain.dto.EventDTO;
import ngSpring.demo.domain.entities.Event;
import ngSpring.demo.exceptions.BusinessException;
import ngSpring.demo.exceptions.EntityNotFoundException;
import ngSpring.demo.exceptions.ValidationException;
import ngSpring.demo.repositories.EventRepository;
import ngSpring.demo.transformer.impl.EventTransformer;
import ngSpring.demo.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Api(basePath = "/api/events", value = "Events", description = "Operations with Events", produces = "application/json")
@RestController
@RequestMapping(value = "/api/events")
@ResponseStatus(HttpStatus.OK)
public class EventController {

    @Autowired
    private EventTransformer eventTransformer;

    @Autowired
    private EventRepository eventRepository;

    private Sort sort = new Sort(new Order(Sort.Direction.ASC, "startDate"));

    @RequestMapping(method = RequestMethod.GET)
    public List<EventDTO> getEvents(@RequestParam(required = false) boolean includeDeleted) {
        Iterator<Event> iterator = eventRepository.findAll().iterator();
        List<Event> events = new ArrayList<>();
        while (iterator.hasNext()) {
            events.add(iterator.next());
        }
        return eventTransformer.transformToDTOs(events);
    }

    @ApiOperation(value = "Update an event", notes = "Updates an existing event dataset")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Fields are with validation errors")})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> setEvent(@RequestBody EventDTO dto) throws EntityNotFoundException {
        save(dto);
        return new ResponseEntity<Message>(new Message("The event has been properly entered"), HttpStatus.OK);
    }

    @ApiOperation(value = "Get event", notes = "Read event dataset")
    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
    public EventDTO getEventByEventId(@PathVariable String eventId) throws EntityNotFoundException {
        Event event = loadEvent(eventId);
        return eventTransformer.transformToDTO(event);
    }

    @ApiOperation(value = "Create new event", notes = "Creates new event dataset")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Fields are with validation errors")})
    @RequestMapping(value = "/{eventId}", method = RequestMethod.PUT)
    public ResponseEntity<Message> updateEventByEventId(@PathVariable String eventId,
                                                        @RequestBody EventDTO event) throws BusinessException {
        validateEventId(eventId, event);
        save(event);
        return new ResponseEntity<Message>(new Message("The event has been properly updated"), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete anevent", notes = "Delete an event dataset")
    @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteEventByEventId(@PathVariable String eventId) throws EntityNotFoundException {
        Event event = loadEvent(eventId);
        event.setDeleted(true);
        eventRepository.save(event);
        return new ResponseEntity<Message>(new Message("The event has been properly deleted"), HttpStatus.OK);
    }

    private Event loadEvent(String eventId) throws EntityNotFoundException {
        Event event = eventRepository.findByEventIdAndDeleted(eventId, false, sort);
        if (event == null) {
            throw new EntityNotFoundException(eventId);
        }
        return event;
    }

    private void validateEventId(String eventId, EventDTO event) throws ValidationException {
        if (!event.getEventId().equals(eventId)) {
            throw new ValidationException("The eventId does not match the given event in body");
        }
    }

    private Event save(EventDTO dto) throws EntityNotFoundException {
        return eventRepository.save(eventTransformer.transformToEntity(dto));
    }
}
