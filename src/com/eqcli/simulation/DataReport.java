package com.eqcli.simulation;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *	模拟数据列表模型
 *
 */
public class DataReport {

	private StringProperty time;
	private StringProperty type;
	private IntegerProperty id;
	
	public DataReport(){
		
		time = new SimpleStringProperty();
		type = new SimpleStringProperty();
		id = new SimpleIntegerProperty();
	}
	

	public String getTime() {
		return time.get();
	}
	public void setTime(String time) {
		this.time.set(time);
	}
	public StringProperty timeProperty(){
		return time;
	}
	
	public String getType() {
		return type.get();
	}
	public void setType(String type) {
		this.type.set(type);
	}
	public StringProperty typeProperty(){
		return type;
	}
	
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public IntegerProperty idProperty(){
		return id;
	}
}
