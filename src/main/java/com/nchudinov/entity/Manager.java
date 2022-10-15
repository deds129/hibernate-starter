package com.nchudinov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager extends User {
	
	private String projectName;

	@Builder
	public Manager(Long id, PersonalInfo personalInfo, String username, Role role, Company company, Profile profile, List<UserChat> usersChats, String projectName) {
		super(id, personalInfo, username, role, company, profile, usersChats);
		this.projectName = projectName;
	}
}
