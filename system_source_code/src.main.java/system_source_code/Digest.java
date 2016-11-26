package system_source_code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * @author I072842
 *
 */
public class Digest {

	public static String cvsSplitBy = ",";
	public static ArrayList<String> displayrequst = new ArrayList<String>();
	static Utility util;
	static AppMode currentMode;
	

	public static void add_records(Integer studentID, Integer courseID,
			Integer instructorID, String instructorComment, Grade grade) {
		Record newrecord = new Record(studentID, courseID, instructorID,
				instructorComment, grade);
		util.getRecords().add(newrecord);
	}

	public static void add_seats(Integer courseId, Integer seats) {
		Course course = CourseCatalogue.getCourse(courseId);
		course.setTotalCapacity(course.getTotalCapacity() + seats);
		course.setAvailableCapacity(course.getAvailableCapacity() + seats);
	}

	//Moving to Utility
	  /*public static void check_request(Integer studentId, Integer courseId) {

		ArrayList<Student> students = util.getStudents();
		for (Iterator<Student> iterator = students.iterator(); iterator
				.hasNext();) {
			Student student = (Student) iterator.next();
			if (student.getUUID().equals(studentId)) {
				String result = student.enrollInCourse(courseId);
				System.out.println(result);
				if (result.equals(Student.validRequest)) {
					String res = student.getUUID().toString() + ',' + student.getName()
							+ ',' + courseId + ',' + getCourseName(courseId);
					displayrequst.add(res);
				}

			}

		}

	}*/

	public static void display_records() {
		ArrayList<Record> records = util.getRecords();
		for (Iterator<Record> iterator = records.iterator(); iterator.hasNext();) {
			Record record = (Record) iterator.next();
			System.out.println(record.getStudentID().toString() + ','
					+ record.getCourseID().toString() + ','
					+ record.getInstructorID().toString() + ','
					+ record.getInstructorsComment() + ','
					+ record.getGrade().toString());

		}
	}

	public static void display_request() {
		for (Iterator<String> iterator = displayrequst.iterator(); iterator
				.hasNext();) {
			String string = (String) iterator.next();

			System.out.println(string);

		}
	}

	public static void display_seats() {
		ArrayList<Course> courses = util.getCourses();
		for (Iterator<Course> iterator = courses.iterator(); iterator.hasNext();) {
			Course course = (Course) iterator.next();
			System.out.println(course.getCourseID().toString() + ','
					+ course.getCourseTitle() + ','
					+ course.getAvailableCapacity());
		}
	}

	/*Moving to Utility class
	 * public static void displayRequestdigest(HashMap<Integer, Integer> request,
			ArrayList<Student> students) {
		int validrequest = 0;
		int missingpre = 0;
		int alreadytaken = 0;
		int noseat = 0;
		for (Entry<?, ?> req : request.entrySet()) {
			for (Iterator<Student> iterator = students.iterator(); iterator
					.hasNext();) {
				Student student = (Student) iterator.next();
				if (student.getUUID().equals(req.getKey())) {
					String result = student
							.enrollInCourse((int) req.getValue());
					if (result.equals(Student.validRequest)) {
						validrequest = validrequest + 1;
						String res = student.getUUID().toString() + ','
								+ student.getName() + ',' + req.getValue() + ','
								+ getCourseName(req.getValue());
						displayrequst.add(res);
					}
					if (result.equals(Student.alreadyTakenCourse))
						alreadytaken = alreadytaken + 1;
					if (result.equals(Student.missingPrerequisite))
						missingpre = missingpre + 1;
					if (result.equals(Student.noSeatsAvailable))
						noseat = noseat + 1;

				}
			}
		}

		System.out.println(validrequest);
		System.out.println(missingpre);
		System.out.println(alreadytaken);
		System.out.println(noseat);
	}*/

	private static String getCourseName(Object value) {
		for (Iterator<?> iterator = util.getCourses().iterator(); iterator
				.hasNext();) {
			Course course = (Course) iterator.next();
			if (course.getCourseID() == value) {
				return course.getCourseTitle();
			}

		}
		return null;
	}

	public static void main(String args[]) throws URISyntaxException {
		
		/*
		 * execute below lines in debug mode to make it run
		 * args = new String[1];
           args[0] = "initial";
		 */
		
		
		args = new String[1];
		args[0] = "initial";
		
		String strMode = args[0];
		switch(strMode.toLowerCase())
		{
			case "initial":
				util = new Utility(AppMode.Initial);
				currentMode = AppMode.Initial;
				break;
			case "resume":
				util = new Utility(AppMode.Resume);
				currentMode = AppMode.Resume;
				break;
		}
	
		Admin admin = new Admin();
		admin.setUtility(util);//set same utility instance
		
	
		
		//Check how many semesters we are dealing with and process each semesters.
		int noOfSems = getNoOfSemesters();
		int currentSem = 1;
		if(currentMode == AppMode.Resume)
		{
			currentSem = getCurrentResumedSemNumber();
		}
		
		for(;currentSem<=noOfSems;currentSem++)
		{
			admin.processSemester(currentSem);
		}	
		
		
	}
	
	public static int getCurrentResumedSemNumber()
	{
		//todo
		return 1;
	}
	
	public static int getNoOfSemesters()
	{
		int num = 0;
		return 1;//todo count no of assignments file
		
	}


	public ArrayList<ArrayList<Object>> parseCSV(String csvFile) {

		BufferedReader br = null;
		String line = "";
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		try {
			InputStream stream = this.getClass().getClassLoader()
					.getResourceAsStream(csvFile);
			br = new BufferedReader(new InputStreamReader(stream));

			while ((line = br.readLine()) != null) {

				// use comma as separator
				Object[] elements = line.split(cvsSplitBy);
				result.add(new ArrayList<Object>(Arrays.asList(elements)));

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

}
