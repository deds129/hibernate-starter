package com.nchudinov.entity;

import com.nchudinov.converter.BirthdayConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	private String firstname;
	private String lastname;

	@Convert(converter = BirthdayConverter.class)
	private Birthday birthDate; 

}
