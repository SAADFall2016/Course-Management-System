package system_source_code;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class CourseCatalogue {

	static List<Course> courses;
	private UUID semesterID;
	
	public CourseCatalogue(UUID semesterID) {
		setSemesterID(semesterID);
	}

	public static Course getCourse(int courseId) {
		for (Iterator<Course> iterator = courses.iterator(); iterator.hasNext();) {
			Course course = (Course) iterator.next();
			if (course.getCourseID() == courseId)
				return course;

		}
		return null;
	}

	public static List<Course> getCourses() {
		return CourseCatalogue.courses;
	}

	public static int getCoursesOfferedIn(Semesters semester) {
		int number = 0;
		for (Iterator<Course> iterator = courses.iterator(); iterator.hasNext();) {
			Course course = (Course) iterator.next();
			if (course.getSemestersOffered().contains(semester))
			number = number + 1;

		}

		return number;
	}

	public static void setCourses(List<Course> courses) {
		CourseCatalogue.courses = courses;
	}

	public void addCourse(Course course) {
		CourseCatalogue.courses.add(course);
	}

	public UUID getSemesterID() {
		return semesterID;
	}

	public void setSemesterID(UUID semesterID) {
		semesterID = semesterID;
	}
}