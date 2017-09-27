package entity;

public class Score {
private int id;
private int stu_id;
private int sub_id;
private int score;
private Student stu;
private Subject sub;
private String grade;
public String getGrade() {
	return grade;
}
public void setGrade(String grade) {
	this.grade = grade;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getStu_id() {
	return stu_id;
}
public void setStu_id(int stu_id) {
	this.stu_id = stu_id;
}
public int getSub_id() {
	return sub_id;
}
public void setSub_id(int sub_id) {
	this.sub_id = sub_id;
}
public int getScore() {
	return score;
}
public void setScore(int score) {
	this.score = score;
}
public Student getStu() {
	return stu;
}
public void setStu(Student stu) {
	this.stu = stu;
}
public Subject getSub() {
	return sub;
}
public void setSub(Subject sub) {
	this.sub = sub;
}
}
