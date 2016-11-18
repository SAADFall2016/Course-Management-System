package system_source_code;

public class Record {

	private Integer courseID;
	private Grade grade; // myGrade;
	private Integer instructorID; // UUID of Instructor class
	private String instructorsComment;
	private Integer studentID; // UUID for student class

	public Record(Integer studentID, Integer courseID, Integer instructorID,
			String instructorComment, Grade grade) {
		this.studentID = studentID;
		this.courseID = courseID;
		this.instructorID = instructorID;
		this.instructorsComment = instructorComment;
		this.grade = grade;
	}

	public Integer getCourseID() {
		return courseID;
	}

	public Grade getGrade() {
		return grade;
	}

	public Instructor getInstructor() {
		return null;
	}

	public String getInstructorComment() {
		return instructorsComment;
	}

	public Integer getInstructorID() {
		return instructorID;
	}

	public String getInstructorsComment() {
		return instructorsComment;
	}

	public Integer getStudentID() {
		return studentID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public void setInstructorComment(String instructorComment) {
		this.instructorsComment = instructorComment;
	}

	public void setInstructorID(int instructorID) {
		this.instructorID = instructorID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

}