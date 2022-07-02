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
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "user_gen")
	//@SequenceGenerator(name = "user_gen", sequenceName = "users_id_seq", allocationSize = 1)
	// hibernate_sequence in hibernate by default
	@TableGenerator(name = "user_gen",
			table = "all_sequence",
			allocationSize = 1,
			pkColumnName = "table_name",
			valueColumnName = "pk_value"
	)
	private Long id;
	
	@Column(unique = true)
	private String username;
	
	@Embedded
	private PersonalInfo personalInfo;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
}
