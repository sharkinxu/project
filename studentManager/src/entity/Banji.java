package entity;

import java.util.List;

public class Banji {
	private int id;
	private String name;
	private int stuNums;
private List<Subject> subject;
	public List<Subject> getSubject() {
	return subject;
}

public void setSubject(List<Subject> subject) {
	this.subject = subject;
}

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

	public int getStuNums() {
		return stuNums;
	}

	public void setStuNums(int stuNums) {
		this.stuNums = stuNums;
	}
}
