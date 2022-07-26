package com.nchudinov.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
			mappedBy = "company",
			fetch = FetchType.LAZY,
	orphanRemoval = true)
	private List<User> users = new ArrayList<>();
	
	public void addUser(User user) {
		user.setCompany(this);
		users.add(user);
	}
}
