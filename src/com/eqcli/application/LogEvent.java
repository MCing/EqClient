package com.eqcli.application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LogEvent {
	
	private StringProperty time;
	private StringProperty event;
	
	public LogEvent(){
		time = new SimpleStringProperty();
		event = new SimpleStringProperty();
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
	
	public String getEvent() {
		return event.get();
	}
	public void setEvent(String event) {
		this.event.set(event);
	}
	public StringProperty eventProperty(){
		return event;
	}
	

}
