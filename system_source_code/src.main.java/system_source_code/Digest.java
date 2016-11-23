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
	static Utility util = new Utility();
	

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

	private static void addAssignments(
			ArrayList<ArrayList<Object>> assignmentlist) {

		for (Iterator<ArrayList<Object>> iterator = assignmentlist.iterator(); iterator
				.hasNext();) {
			ArrayList<Object> arrayList = (ArrayList<Object>) iterator.next();

			Course course = CourseCatalogue.getCourse(Integer
					.parseInt((String) arrayList.get(1)));
			ArrayList<Instructor> ins = util.getInstructors();
			for (Iterator<Instructor> iterator2 = ins.iterator(); iterator2
					.hasNext();) {
				Instructor instructor = (Instructor) iterator2.next();
				if (instructor.getUUID() == Integer.parseInt((String) arrayList
						.get(0))) {
					HashMap<Person, Integer> hm = new HashMap<Person, Integer>();
					hm.put(instructor,
							Integer.parseInt((String) arrayList.get(2)));
					course.setInstructors_capacity(hm);
				}

			}

		}

	}

	private static void addPrerequisiteCourse(
			ArrayList<ArrayList<Object>> prerequistes, ArrayList<Course> courses) {

		for (Iterator<ArrayList<Object>> iterator = prerequistes.iterator(); iterator
				.hasNext();) {
			Set<Integer> pre = new HashSet<Integer>();
			ArrayList<Object> data = (ArrayList<Object>) iterator.next();
			pre.add(Integer.parseInt((String) data.get(0)));
			Course course = CourseCatalogue.getCourse(Integer
					.parseInt((String) data.get(1)));
			course.setprerequisteCourses(pre);
		}

	}

	public static void check_request(Integer studentId, Integer courseId) {

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

	}

	private static ArrayList<Course> createCourse(
			ArrayList<ArrayList<Object>> parseCSV) {

		ArrayList<Course> courses = new ArrayList<Course>();

		for (int i = 0; i < parseCSV.size(); i++) {
			try {
				int CourseID = Integer
						.parseInt((String) parseCSV.get(i).get(0));
				String CourseTitle = (String) parseCSV.get(i).get(1);

				ArrayList<Semesters> semestersOffered = new ArrayList<Semesters>();

				for (int j = 2; j < parseCSV.get(i).size(); j++) {
					semestersOffered.add(Semesters.valueOf((String) (parseCSV
							.get(i).get(j))));

				}

				Course course = new Course(CourseID, CourseTitle,
						semestersOffered);
				courses.add(course);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return courses;

	}

	private static ArrayList<Instructor> createInstructor(
			ArrayList<ArrayList<Object>> parseCSV) {
		ArrayList<Instructor> instructors = new ArrayList<Instructor>();

		for (int i = 0; i < parseCSV.size(); i++) {
			try {
				int UUID = Integer.parseInt((String) parseCSV.get(i).get(0));
				String Name = (String) parseCSV.get(i).get(1);
				String Address = (String) parseCSV.get(i).get(2);
				String PhoneNumber = (String) parseCSV.get(i).get(3);
				Instructor instructor = new Instructor(UUID, Name, Address,
						PhoneNumber);
				instructors.add(instructor);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return instructors;
	}

	private static ArrayList<Record> createRecords(
			ArrayList<ArrayList<Object>> parseCSV) {

		ArrayList<Record> records = new ArrayList<Record>();

		for (int i = 0; i < parseCSV.size(); i++) {

			try {
				int StudentID = Integer.parseInt((String) parseCSV.get(i)
						.get(0));
				int CourseID = Integer
						.parseInt((String) parseCSV.get(i).get(1));

				int InstructorID = Integer.parseInt((String) parseCSV.get(i)
						.get(2));
				String InstructorComment = (String) parseCSV.get(i).get(3);
				Grade grade = Grade.valueOf((String) parseCSV.get(i).get(4));
				Record record = new Record(StudentID, CourseID, InstructorID,
						InstructorComment, grade);
				records.add(record);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return records;
	}

	private static HashMap<Integer, Integer> createRequests(
			ArrayList<ArrayList<Object>> requests) {
		HashMap<Integer, Integer> hm = new LinkedHashMap<>();
		for (Iterator<ArrayList<Object>> iterator = requests.iterator(); iterator
				.hasNext();) {
			ArrayList<Object> arrayList = (ArrayList<Object>) iterator.next();
			hm.put(Integer.parseInt((String) arrayList.get(0)),
					Integer.parseInt((String) arrayList.get(1)));

		}
		return hm;

	}

	private static ArrayList<Student> createStudents(
			ArrayList<ArrayList<Object>> parseCSV) {
		ArrayList<Student> students = new ArrayList<Student>();

		for (int i = 0; i < parseCSV.size(); i++) {

			// System.out.println("creating" + i);

			try {
				int UUID = Integer.parseInt((String) parseCSV.get(i).get(0));
				String Name = (String) parseCSV.get(i).get(1);
				String Address = (String) parseCSV.get(i).get(2);
				String PhoneNumber = (String) parseCSV.get(i).get(3);
				Student student = new Student(UUID, Name, Address, PhoneNumber);
				students.add(student);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return students;
	}

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

	public static void displayRequestdigest(HashMap<Integer, Integer> request,
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
	}

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
		//Amruta : Removed code for old digest print
		
		//Pre1: Read the Mode.csv to check current mode :Code done
		
		//Pre2: Load semester independent files records.csv and requests.csv:Done
		String recordscsvFile = "records.csv";
		// for creating records
		ArrayList<Record> records = createRecords(new Digest()
						.parseCSV(recordscsvFile));
		util.setRecords(records);

		
		//Amruta: Todo : Why we do not have request object?
		/*String requestscsvFile = "requests.csv";
		// for creating requests
		ArrayList<Request> requests = createRecords(new Digest()
						.parseCSV(requestscsvFile));
		util.setRequests(requests);*/
		
		
		//Step1 :Convert records.csv to projectedrecords file:Done
		util.getProjectedRecords();
		
		//Step2 :Run Weka apriori and generate data analysis output:Done
		
		Admin admin = new Admin();
		admin.readPredictions();
		admin.setUtility(util);//set same utility instance
		
		int noOfSems = getNoOfSemesters();
		
		for(int i=0;i<noOfSems;i++)
		{
			admin.processSemester(i+1);
		}
		
		//Check how many semesters we are dealing with and process each semesters.
		
		//Steps 3 to 10 will be wrapped inside Admin processSemester method.
		//Step3 :Roster selection:> Display :ToDo
		
		//Step4 :Roster add/delete instructor assignment :ToDo
		
		//Step5 :Roster: Submit + Recalculate course capacity:Todo
		
		//Step6 : Load current Requests.csv file for semester N: Todo
		//Sem1 for initial mode, SemN forresume mode
		
		//Step7: Validate Student Requests : ToDo
		
		//Step8 :Add granted requests to records.csv file with random grades: Todo
		//Note: Do not modify original records.csv as it might be needed if user restarts in intial mode,instead of resuming.
		//Display updated records
		
		//Step9: Update Requests.csv with waitlisted requests: ToDo
		//Note: Do not modify original requests.csv as it might be needed if user restarts in intial mode,instead of resuming.
		//Display updated requests
		
		//Step10 :Repeat loop.
		
		
	}
	
	public static int getNoOfSemesters()
	{
		int num = 0;
		return 1;//todo count no of assignments file
		
	}

	// public ArrayList<ArrayList<Object>> parseCSV(String csvFile) throws
	// URISyntaxException {
	//
	// BufferedReader br = null;
	// // String line = "";
	// String cvsSplitBy = ",";
	// ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
	// try {
	// FileReader fr = new
	// FileReader(this.getClass().getClassLoader().getResource(csvFile).toURI().getPath());
	//
	// Scanner scanner = new Scanner(fr);
	// while (scanner.hasNextLine()) {
	// String line = scanner.nextLine();
	// Object[] elements = line.split(cvsSplitBy);
	// result.add(new ArrayList(Arrays.asList(elements)));
	// }
	//
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// if (br != null) {
	// try {
	// br.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// return result;
	// }

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
