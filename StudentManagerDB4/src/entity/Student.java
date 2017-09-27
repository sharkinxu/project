package entity;

import java.io.Serializable;

public class Student implements Serializable {
	private int id;

	public int getId() {
		return id;
	}

	private int bj_id;

	public int getBj_id() {
		return bj_id;
	}

	public void setBj_id(int bj_id) {
		this.bj_id = bj_id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name;
	private int age;
	private String sex;
	private Banji bj;//在类与类之间建立关系，可以在多的一方建立一的一个属性，可以



	public Banji getBj() {
		return bj;
	}

	public void setBj(Banji bj) {
		this.bj = bj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {

		if (age < 150 && age >= 0) {
			this.age = age;

		} else {
			this.age = 23;
		}
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
			this.sex = sex;
	}
}
