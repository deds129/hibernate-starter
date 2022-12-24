package com.nchudinov.dto;

import java.util.Map;

public record CompanyReadDto(Integer id,
							 String companyName,
							 Map<String, String> locales) {
}
