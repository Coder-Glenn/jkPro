package cn.graduation.entity;

public class TextCode {
	private ClassCode classCode;
	public ClassCode getClassCode() {
		return classCode;
	}
	public void setClassCode(ClassCode classCode) {
		this.classCode = classCode;
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
	private String id;
	private String name;
}
