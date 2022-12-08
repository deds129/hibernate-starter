package com.nchudinov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Audit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Serializable entityId;
	
	private String entityName;
	
	private String entityContent;
	
	@Enumerated(EnumType.STRING)
	private Operation operation;

	public enum Operation {
		SAVE, UPDATE, INSERT, DELETE
	}
}
