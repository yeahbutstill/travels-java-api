package io.github.mariazevedo88.travelsjavaapi.model.account;

import io.github.mariazevedo88.travelsjavaapi.dto.model.account.AccountDTO;
import io.github.mariazevedo88.travelsjavaapi.enumeration.AccountTypeEnum;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class that implements the Account structure.
 * 
 * @author Mariana Azevedo
 * @since 16/12/2020
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account implements Serializable {

	private static final long serialVersionUID = -6762432601286928295L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "account_number")
	private String accountNumber;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "account_type")
	private AccountTypeEnum accountType; 
	
	public Account(Long id) {
		this.id = id;
	}

	/**
	 * Method to convert an Account entity to a Account DTO.
	 * 
	 * @author Mariana Azevedo
	 * @since 31/10/2020
	 * 
	 * @param account
	 * @return a <code>AccountDTO</code> object
	 */
	public AccountDTO convertEntityToDTO() {
		return new ModelMapper().map(this, AccountDTO.class);
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Account account = (Account) o;
		return getId() != null && Objects.equals(getId(), account.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
