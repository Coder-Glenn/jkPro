package cn.graduation.entity;

import java.util.Set;

public class ClassCode {
	private Set<TextCode> textCodes;
	private String id;
	public Set<TextCode> getTextCodes() {
		return textCodes;
	}
	public void setTextCodes(Set<TextCode> textCodes) {
		this.textCodes = textCodes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String name;
}
