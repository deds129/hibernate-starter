package com.nchudinov.mappers;

import com.nchudinov.dto.CompanyReadDto;
import com.nchudinov.entity.Company;

public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {
	@Override
	public CompanyReadDto mapFrom(Company object) {
		return new CompanyReadDto(object.getId(),
				object.getName(),
				object.getLocales());
	}
}
