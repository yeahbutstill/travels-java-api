package io.github.mariazevedo88.travelsjavaapi.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Class that implements a generic response error object to the API end-points.
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ResponseError {
	
	@NotNull(message="Timestamp cannot be null")
	private LocalDateTime timestamp;
	
	@NotNull(message="Details cannot be null")
    private String details;

}