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
	
	@Embedded
	private PersonalInfo personalInfo;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
}
