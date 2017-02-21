package com.app2mobile.metadata;

import java.io.Serializable;

public class RestaurantTagsMetadata implements Serializable{
private String tagId,tagName;

public String getTagId() {
	return tagId;
}

public void setTagId(String tagId) {
	this.tagId = tagId;
}

public String getTagName() {
	return tagName;
}

public void setTagName(String tagName) {
	this.tagName = tagName;
}

}
