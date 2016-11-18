package system_source_code;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Utility {

	private static ArrayList<Course> courses;
	private static ArrayList<Instructor> instructors;
	private static ArrayList<Student> students;
	private static ArrayList<Record> records;

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		Utility.courses = courses;
	}

	public ArrayList<Instructor> getInstructors() {
		return instructors;
	}

	public void setInstructors(ArrayList<Instructor> instructors) {
		Utility.instructors = instructors;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public void setStudents(ArrayList<Student> students) {
		Utility.students = students;
	}

	public ArrayList<Record> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<Record> records) {
		Utility.records = records;
	}

	public static int getStudentsNotTakenAnyCourse() {
		// for number of students who havn't taken any class
		HashSet<Integer> studentIds = new HashSet<Integer>();
		for (Iterator<Student> iterator = students.iterator(); iterator.hasNext();) {
			studentIds.add(((Student) iterator.next()).getUUID());
		}
		for (Iterator<Record> iterator = records.iterator(); iterator.hasNext();) {

			Integer studentid = (Integer) ((Record) iterator.next())
					.getStudentID();

			if (studentIds.contains(studentid)) {
				studentIds.remove(studentid);
			}

		}

		return studentIds.size();
	}

	public static int getInstructorsNotTaughtAnyCourse() {
		// find number of instrutors who haven't taught any class
		HashSet<Integer> InstructorIds = new HashSet<Integer>();
		for (Iterator<Instructor> iterator = instructors.iterator(); iterator.hasNext();) {
			InstructorIds.add(((Instructor) iterator.next()).getUUID());
		}
		for (Iterator<Record> iterator = records.iterator(); iterator.hasNext();) {

			Integer instructorId = (Integer) ((Record) iterator.next())
					.getInstructorID();

			if (InstructorIds.contains(instructorId)) {
				InstructorIds.remove(instructorId);
			}

		}

		return InstructorIds.size();
	}

	public static int coursesNotTakenByAnyStudent() {
		// number of courses which aren't taken by any student

		HashSet<Integer> courseIds = new HashSet<Integer>();
		for (Iterator<Course> iterator = courses.iterator(); iterator.hasNext();) {
			courseIds.add(((Course) iterator.next()).getCourseID());
		}
		for (Iterator<Record> iterator = records.iterator(); iterator.hasNext();) {
			Integer courseid = (Integer) ((Record) iterator.next())
					.getCourseID();
			if (courseIds.contains(courseid)) {
				courseIds.remove(courseid);
			}
		}
		return courseIds.size();
	}

	public static ArrayList<Record> getRecords(int studentId) {
		ArrayList<Record> studentRecords = new ArrayList<Record>();
		for (Iterator<Record> iterator = records.iterator(); iterator.hasNext();) {
			Record record = (Record) iterator.next();
			if (record.getStudentID() == studentId) {
				studentRecords.add(record);
			}
		}
		return studentRecords;
	}
}
