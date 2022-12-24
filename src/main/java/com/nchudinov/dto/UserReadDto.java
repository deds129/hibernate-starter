package com.nchudinov.dto;

import com.nchudinov.entity.Company;
import com.nchudinov.entity.PersonalInfo;
import com.nchudinov.entity.Role;

public record UserReadDto(Long id,
						  PersonalInfo personalInfo,
						  String username,
						  Role role,
						  CompanyReadDto company) {
}
