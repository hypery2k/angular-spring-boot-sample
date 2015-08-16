package ngSpring.demo.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


@Builder
@AllArgsConstructor
@Getter
@ToString
@SuppressWarnings("serial")
public class ValidationMessage implements Serializable {

    private String entity;

    private String messageTemplate;

    private List<String> propertyList;
}
