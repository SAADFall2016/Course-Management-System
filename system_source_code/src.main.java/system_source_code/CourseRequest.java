package system_source_code;


public class CourseRequest implements Comparable<CourseRequest> {
	private int studenttId;
	private int courseId;

	public int getStudenttId() {
		return studenttId;
	}

	public void setStudenttId(int studenttId) {
		this.studenttId = studenttId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	
	
	@Override
	public int compareTo(CourseRequest o) {
		
		if(this.studenttId == o.studenttId && this.courseId==o.courseId)
			return 0;
		else 
			return -1;
	}

}
