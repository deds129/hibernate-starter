package com.nchudinov.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(exclude = "users")
@Builder
@Entity
@Table(name = "company", schema = "public")
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy = "company", orphanRemoval = true)
	//@OrderBy("username DESC, personalInfo.lastname ASC")
	@MapKey(name = "username")
	private Map<String, User> users = new HashMap<>();
	
	@Builder.Default
	@ElementCollection //default - company_locales
	@CollectionTable(name = "company_locale", joinColumns = @JoinColumn(name = "company_id"))
	//@AttributeOverride(name = "lang", column = @Column(name = "language")) // если бы были другие зазвания в базе
	
	//@Column(name = "description") // если вставляем только поле description - только чтение
	private List<LocaleInfo> locales = new ArrayList<>();
	
	public void addUser(User user) {
		user.setCompany(this);
		users.put(user.getUsername(), user);
	}
}
