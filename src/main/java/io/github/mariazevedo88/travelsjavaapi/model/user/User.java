package io.github.mariazevedo88.travelsjavaapi.model.user;

import io.github.mariazevedo88.travelsjavaapi.dto.model.user.UserDTO;
import io.github.mariazevedo88.travelsjavaapi.enumeration.RoleEnum;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class that implements an User entity in the API.
 * 
 * @author Mariana Azevedo
 * @since 11/10/2020
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 5514528747731992863L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	
	public User(Long id) {
		this.id = id;	
	}
	
	/**
	 * Method that verifies if the user is admin
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @return boolean
	 */
	public boolean isAdmin() {
		return RoleEnum.ROLE_ADMIN.toString().equals(this.role.toString());
	}
	
	/**
	 * Method to convert an User entity to an User DTO
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param user
	 * @return an UserDTO object
	 */
	public UserDTO convertEntityToDTO() {
		return new ModelMapper().map(this, UserDTO.class);
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		User user = (User) o;
		return getId() != null && Objects.equals(getId(), user.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
