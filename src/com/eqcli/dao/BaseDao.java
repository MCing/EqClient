package com.eqcli.dao;

import java.sql.Connection;
import java.util.List;

public abstract class BaseDao<T> {

	public abstract boolean save(T t);
	
	public abstract boolean delete(T t);
	
	public abstract List<T> get();
}
