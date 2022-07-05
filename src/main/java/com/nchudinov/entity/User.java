package com.nchudinov.entity;

import lombok.*;

import javax.persistence.*;

@Data //generate equals + hashCode etc.
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"company"})
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
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;
	
}
