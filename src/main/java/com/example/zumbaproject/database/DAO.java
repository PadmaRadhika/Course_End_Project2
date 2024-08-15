package com.example.zumbaproject.database;

import java.util.List;

public interface DAO<T> {
	T get(long id);
	List<T> getAll();
	int save(T object);
	int update(T object);
	void delete(long id);
	

}