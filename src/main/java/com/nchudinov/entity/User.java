package com.nchudinov.entity;

import com.nchudinov.converter.BirthdayConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
	
	@Type(type = "com.vladmihalcea.hibernate.type.json.JsonBinaryType")
	private String info;
	
}
