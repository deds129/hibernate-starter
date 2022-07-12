package com.nchudinov.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "users")
@Builder
@Entity
@Table(name = "company", schema = "public")
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	private Set<User> users;
	
	//@JoinColumn(name = "company_id")
	//private List<User> userList;
	
}
