package com.app2mobile.metadata;

import java.io.Serializable;

public class SpinnerItemMetadata {
	private String name,value;

	private int id;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public SpinnerItemMetadata(String name, String value, int id) {
		super();
		this.name = name;
		this.value = value;
		this.id = id;
	}

	
	

}
