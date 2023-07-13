package io.github.mariazevedo88.travelsjavaapi.model.statistic;

import io.github.mariazevedo88.travelsjavaapi.dto.model.statistic.StatisticDTO;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Class that implements the Statistic structure.
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
@Table(name = "statistics")
public class Statistic implements Serializable {
	
	private static final long serialVersionUID = -7804600023031651840L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "travel_sum")
	private BigDecimal sum;
	
	@Column(name = "travel_avg")
	private BigDecimal avg;
	
	@Column(name = "travel_max")
	private BigDecimal max;
	
	@Column(name = "travel_min")
	private BigDecimal min;
	
	@Column(name = "travel_count")
	private long count;
	
	public Statistic (BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, long count) {
		this.sum = sum;
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.count = count;
	}
	
	/**
	 * Method to convert an Statistic entity to an Statistic DTO.
	 * 
	 * @author Mariana Azevedo
	 * @since 03/04/2020
	 * 
	 * @param statistic
	 * @return a <code>StatisticDTO</code> object
	 */
	public StatisticDTO convertEntityToDTO() {
		return new ModelMapper().map(this, StatisticDTO.class);
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Statistic statistic = (Statistic) o;
		return getId() != null && Objects.equals(getId(), statistic.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
