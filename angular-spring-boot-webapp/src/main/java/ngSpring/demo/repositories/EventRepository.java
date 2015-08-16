package ngSpring.demo.repositories;

import ngSpring.demo.domain.entities.Event;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, String> {

    Event findByEventIdAndDeleted(String eventId, boolean deleted, Sort sort);

}
