package ngSpring.demo.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Getter
@ToString
@SuppressWarnings("serial")
public class Message implements Serializable {

    private String message;

}
