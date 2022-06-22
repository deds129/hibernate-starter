package com.nchudinov.entity;

import com.nchudinov.converter.BirthdayConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data //generate equals + hashCode etc.
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User {
	
	@Id
	private String username;
	private String firstname;
	private String lastname;
	
	@Convert(converter = BirthdayConverter.class)
	@Column(name = "birth_date")
	private Birthday birthDate;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
}
