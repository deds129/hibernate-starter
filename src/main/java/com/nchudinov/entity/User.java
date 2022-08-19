package com.nchudinov.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data //generate equals + hashCode etc.
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"company", "profile", "usersChats"})
@Entity
@Table(name = "users", schema = "public")
public class User implements BaseEntity<Long> {
	
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


	@Builder.Default
	@OneToMany(mappedBy = "user") // ссылка на user в сущности UserChat
//	@JoinTable(name = "users_chat", // связующая таблица
//			joinColumns = @JoinColumn(name = "user_id"), // колонка связанная с объектами данного кдасса
//			inverseJoinColumns = @JoinColumn(name = "chat_id")) //связанная сущность
	private List<UsersChat> usersChats = new ArrayList<>();
	
}
