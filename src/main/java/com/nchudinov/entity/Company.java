package com.nchudinov.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "username")
@ToString(exclude = "company")
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
			mappedBy = "company",
			fetch = FetchType.EAGER)
	private Set<User> users = new HashSet<>();
	
	public void addUser(User user) {
		user.setCompany(this);
		users.add(user);
	}
}
