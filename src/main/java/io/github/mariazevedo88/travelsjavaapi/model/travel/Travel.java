package io.github.mariazevedo88.travelsjavaapi.model.travel;

import io.github.mariazevedo88.travelsjavaapi.dto.model.travel.TravelDTO;
import io.github.mariazevedo88.travelsjavaapi.enumeration.TravelTypeEnum;
import io.github.mariazevedo88.travelsjavaapi.model.account.Account;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Class that implements the Travel structure.
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "travel")
public class Travel implements Serializable {
	
	private static final long serialVersionUID = -3656431259068389491L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String orderNumber;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	private TravelTypeEnum type;
	
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;
	
	public Travel (TravelTypeEnum type){
		this.type = type;
	}
	
	/**
	 * Method to convert an Travel entity to a Travel DTO.
	 * 
	 * @author Mariana Azevedo
	 * @since 03/04/2020
	 * 
	 * @return a <code>TravelDTO</code> object
	 */
	public TravelDTO convertEntityToDTO() {
		return new ModelMapper().map(this, TravelDTO.class);
	}
	
}
