package ngSpring.demo.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ngSpring.demo.domain.AbstractBaseEntity;
import ngSpring.demo.validation.ValidateDateRange;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ValidateDateRange(start = "startDate", end = "endDate", message = "{validation.date.range_error}", equal = true)
public class Event extends AbstractBaseEntity {

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid.hex")
    @Id
    private String eventId;

    @NotNull
    @Size(min = 1, max = 50)
    private String eventDescription;

    @NotNull
    private Date startDate;

    private Date endDate;

    public Event() {
        super();
    }

    public Event(String eventDescription, Date startDate, Date endDate) {
        super();
        this.eventDescription = eventDescription;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}