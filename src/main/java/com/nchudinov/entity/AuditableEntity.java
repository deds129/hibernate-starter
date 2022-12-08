package com.nchudinov.entity;

import com.nchudinov.listeners.AuditDateListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditDateListener.class)
public abstract class AuditableEntity<T extends Serializable> implements BaseEntity<T> {

    private Instant createdAt;

    private String createdBy;

	private Instant updatedAt;

	private String updatedBy;
}
