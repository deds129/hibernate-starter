package com.nchudinov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Payment extends  AuditableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;
	
	//Перед сохранением сущности
	@PrePersist 
	public void prePersist() {
		setCreatedAt(Instant.now());
		//setCreatedBy(SecurityContext.getUser());
	}
	
	//перед обновлением
	@PreUpdate
	public void preUpdate() {
		setUpdatedAt(Instant.now());
	}
}
