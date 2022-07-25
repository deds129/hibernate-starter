package com.nchudinov.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data //generate equals + hashCode etc.
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"company", "profile"})
@Entity
@Table(name = "users", schema = "public")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
	})
	private PersonalInfo personalInfo;
	
	@Column(unique = true)
	private String username;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;

	@OneToOne(mappedBy = "user",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			optional = false)
	private Profile profile;


	@ManyToMany()
	private List<Chat> chats = new ArrayList<>();
}
