package ngSpring.demo.transformer.impl;

import ngSpring.demo.domain.dto.EventDTO;
import ngSpring.demo.domain.entities.Event;
import ngSpring.demo.exceptions.EntityNotFoundException;
import ngSpring.demo.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

@Component
public class EventTransformer extends GenericTransformer<Event, EventDTO> {

    @Override
    public EventDTO transformToDTO(Event entity) {
        if (entity != null) {
            return EventDTO.builder()
                    .eventId(entity.getEventId())
                    .eventDescription(entity.getEventDescription())
                    .startDate(entity.getStartDate())
                    .endDate(entity.getEndDate())
                    .insertDate(entity.getInsertDate())
                    .build();
        }
        return null;
    }

    @Override
    public Event transformToEntity(EventDTO dto) throws EntityNotFoundException {
        if (dto != null) {
            Event event = Event.builder()
                    .eventId(dto.getEventId())
                    .eventDescription(dto.getEventDescription())
                    .startDate(dto.getStartDate())
                    .endDate(dto.getEndDate())
                    .build();
            event.setInsertDate(dto.getInsertDate());
            return event;
        }
        return null;
    }
}
