package com.nchudinov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Programmer extends User {
	
	@Enumerated(EnumType.STRING)
	private Language language;

	@Builder
	public Programmer(Long id, PersonalInfo personalInfo, String username, Role role, Company company, Profile profile, List<UserChat> usersChats, Language language) {
		super(id, personalInfo, username, role, company, profile, usersChats);
		this.language = language;
	}
}
