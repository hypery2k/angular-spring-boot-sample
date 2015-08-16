package ngSpring.demo.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
/**
 * @author hypery2k
 */
public class EventDTO {

    @NotNull
    private String eventId;

    @NotNull
    @Size(min = 1, max = 32)
    private String eventDescription;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "CET")
    @NotNull
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "CET")
    private Date endDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "CET")
    protected Date insertDate;
}
