package com.nchudinov.mappers;

@FunctionalInterface
public interface Mapper<F, T> {
	T mapFrom(F object);
}
