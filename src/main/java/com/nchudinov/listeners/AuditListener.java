package com.nchudinov.listeners;

import com.nchudinov.entity.AuditableEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

public class AuditListener {

	//Перед сохранением сущности
	@PrePersist
	public void prePersist(AuditableEntity<?> entity) {
		entity.setCreatedAt(Instant.now());
	}

	//перед обновлением
	@PreUpdate
	public void preUpdate(AuditableEntity<?> entity) {
		entity.setUpdatedAt(Instant.now());
	}
}
