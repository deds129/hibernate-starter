package com.nchudinov.repository;

import com.nchudinov.entity.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

	private final Class<E> clazz;
	@Getter
	private final EntityManager entityManager; // работаем только с соединением

	@Override
	public E save(E entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public void delete(K id) {
		entityManager.detach(entityManager.find(clazz, id));
		entityManager.flush();
	}

	@Override
	public void update(E entity) {
		entityManager.merge(entity);
	}

	@Override
	public Optional<E> findById(K id, Map<String, Object> properties) {
		return Optional.ofNullable(entityManager.find(clazz, id, properties));
	}

	@Override
	public List<E> findAll() {
		var criteria = entityManager.getCriteriaBuilder().createQuery(clazz);
		criteria.from(clazz);
		return entityManager.createQuery(criteria)
				.getResultList();
	}
}