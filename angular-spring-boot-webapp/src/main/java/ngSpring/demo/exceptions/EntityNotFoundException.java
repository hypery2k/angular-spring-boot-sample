package ngSpring.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// TODO add generic information
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@SuppressWarnings("serial")
public class EntityNotFoundException extends BusinessException {

    private String id;

    public EntityNotFoundException(String entityId) {
        super("could not find entity with id '" + entityId + "'.");
        this.id = entityId;
    }

    public EntityNotFoundException() {
        super("could not find entity");
    }

    public String getId() {
        return id;
    }

    public EntityNotFoundException setId(String id) {
        this.id = id;
        return this;
    }
}
