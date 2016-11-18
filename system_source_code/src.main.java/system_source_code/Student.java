package system_source_code;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Student extends Person {

	public static String validRequest = "request is valid";
	public static String invalidRequest = "Invalid";
	public static String missingPrerequisite = "student is missing one or more prerequisites";
	public static String alreadyTakenCourse = "student has already taken the course with a grade of C or higher";
	public static String noSeatsAvailable = "no remaining seats available for the course at this time";
	public static String noofCurrentCourseExceeded = "already enrolled for 5 courses";

	public ArrayList<Integer> courseRequestRejected = new ArrayList<>();
	public ArrayList<Course> currentCourses = new ArrayList<Course>();

	Student(int UUID, String Name, String Address, String PhoneNumber) {
		super(UUID, Name, Address, PhoneNumber);
	}

	public String enrollInCourse(int courseId) {
		if(currentCourses.size() ==5)
		{
			return noofCurrentCourseExceeded;
		}
		
		String message;
		ArrayList<Record> studentrecord = Utility.getRecords(this.getUUID());

		// check prerequisites
		Course course = CourseCatalogue.getCourse(courseId);
		message = checkPrerequisites(course, studentrecord);
		boolean enrollmentresult = false;
		if (message.equals(validRequest)) {

			// check for course already taken
			boolean check = checkRecordExistsforCourse(courseId, studentrecord);
			if (check) {
				return alreadyTakenCourse;
			}

			enrollmentresult = course.enrollStudent(this.getUUID());
			if (enrollmentresult)
			{
				currentCourses.add(course);
				return validRequest;
				
			}

			else {
				courseRequestRejected.add(courseId);
				return noSeatsAvailable;
			}

		} else {
			return message;
		}

	}

	public String checkValidenrollment(int courseId) {
		String message;
		ArrayList<Record> studentrecord = Utility.getRecords(this.getUUID());

		// check prerequisites

		Course course = CourseCatalogue.getCourse(courseId);
		message = checkPrerequisites(course, studentrecord);
		boolean enrollmentresult = false;
		if (message.equals(validRequest)) {

			// check for course already taken
			boolean check = checkRecordExistsforCourse(courseId, studentrecord);
			if (check) {
				return alreadyTakenCourse;
			}

			enrollmentresult = course.checkIfValidRequest(this.getUUID());
			if (enrollmentresult)
				return validRequest;

			else {
				return noSeatsAvailable;
			}

		} else {
			return message;
		}

	}

	private String checkPrerequisites(Course course,
			ArrayList<Record> studentrecord) {
		Set<Integer> precourses = course.getprerequisteCourses();
		for (Iterator<Integer> iterator = precourses.iterator(); iterator
				.hasNext();) {
			Integer courseId = (Integer) iterator.next();
			boolean check = checkRecordExistsforCourse(courseId, studentrecord);
			if (check == false)
				return missingPrerequisite;

		}

		return validRequest;
	}

	private boolean checkRecordExistsforCourse(int courseId,
			ArrayList<Record> studentrecord) {

		for (Iterator<Record> iterator = studentrecord.iterator(); iterator
				.hasNext();) {
			Record record = (Record) iterator.next();
			if (record.getCourseID() == courseId)
				return true;

		}
		return false;

	}

}