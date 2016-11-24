package system_source_code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;



public class Utility {

	public static String cvsSplitBy = ",";

	private static ArrayList<Course> courses;
	private static ArrayList<Instructor> instructors;
	private static ArrayList<Student> students;
	private static ArrayList<Record> records;
	private static ArrayList<ArrayList<Object>> assignments;

	private static String recordsFileName = "records";
	private static String recordscsvFile = "records.csv";
	private static String studentscsvFile = "students.csv";
	private static String instructorscsvFile = "instructors.csv";
	private static String coursescsvFile = "courses.csv";
	private static String prereqscsvFile = "prereqs.csv";
	private static String assignmentsCSVFile = "assignments";
	private static String requestscsvFile = "requests.csv";
	private static String modecsvFile = "mode.csv";
	
	//Amruta
	 private static final String COMMA_DELIMITER = ",";
	 private static final String NEW_LINE_SEPARATOR = "\n";
	 private static  String FILE_HEADER = "";
	  private static String projrecordsfile = "projectedrecords.csv";
	  private static String projectedRecordsFileName = "projectedrecords";
	  //Amruta
	
	private static  String new_line_separater = "\n";
	 private static  String file_header = "";

	protected static CourseCatalogue courseCatalogue;
	private static AppMode mode;

	Utility() {
		
		this.setMode(readMode());//Read current mode from mode.csv file.

	}
	
	Utility(AppMode amode)
	{
		this.setMode(amode);
	}
	
	//process records file as per current mode
	
	public  void processRecords(AppMode mode, int semId)
	{
		if(mode == AppMode.Resume)
		{
			recordscsvFile = recordsFileName + "_"+semId;
			projrecordsfile = projectedRecordsFileName+"_"+semId;
		}
		//Pre2: Load semester independent files records.csv and requests.csv, student.csv ....:To be Done
		String recordscsvFile = "records.csv";
		// for creating records
		
		ArrayList<Record> records = createRecords(new Digest()
								.parseCSV(recordscsvFile));
		setRecords(records);
		
	}

	//Amruta
	private AppMode readMode()
	{
		  BufferedReader br = null;
		  String path = modecsvFile;
		 AppMode mode = AppMode.Initial;
		 String line = "";
		  
		  try
		  {
			  br = new BufferedReader(new FileReader(path));
			  while ((line = br.readLine()) != null) {
				  
				  switch(line.toLowerCase())
				  {
				  	case "initial":
				  		mode = AppMode.Initial;
				  		break;
				  	case "resume":
				  		mode = AppMode.Resume;
				  		break;
				  }
			  }
		  }
		  catch (FileNotFoundException e) {
		         e.printStackTrace();
		     } catch (IOException e) {
		         e.printStackTrace();
		     }catch(Exception e)
		  	{
		     	  e.printStackTrace();
		  	}
		  	finally {
		         if (br != null) {
		             try {
		                 br.close();
		             } catch (IOException e) {
		                 e.printStackTrace();
		             }
		         }
		     }
		  
		  return mode;
	}
	
	private static void addAssignments(
			ArrayList<ArrayList<Object>> assignmentlist) {

		for (Iterator<ArrayList<Object>> iterator = assignmentlist.iterator(); iterator
				.hasNext();) {
			ArrayList<Object> arrayList = (ArrayList<Object>) iterator.next();

			Course course = CourseCatalogue.getCourse(Integer
					.parseInt((String) arrayList.get(1)));
			ArrayList<Instructor> ins = getInstructors();
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

	public static ArrayList<Course> getCourses() {
		return courses;
	}

	public static void setCourses(ArrayList<Course> courses) {
		Utility.courses = courses;
		courseCatalogue = new CourseCatalogue(UUID.randomUUID());
		CourseCatalogue.setCourses(courses);
	}

	public static ArrayList<Instructor> getInstructors() {
		return instructors;
	}

	public static void setInstructors(ArrayList<Instructor> instructors) {
		Utility.instructors = instructors;
	}

	public static ArrayList<Student> getStudents() {
		return students;
	}

	public static void setStudents(ArrayList<Student> students) {
		Utility.students = students;
	}

	public static ArrayList<Record> getRecords() {
		return records;
	}

	public static void setRecords(ArrayList<Record> records) {
		Utility.records = records;
	}
	
	public static ArrayList<ArrayList<Object>> getAssignments()
	{
		return assignments;
	}
	
	public static int getStudentsNotTakenAnyCourse() {
		// for number of students who havn't taken any class
		HashSet<Integer> studentIds = new HashSet<Integer>();
		for (Iterator<Student> iterator = students.iterator(); iterator
				.hasNext();) {
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
		for (Iterator<Instructor> iterator = instructors.iterator(); iterator
				.hasNext();) {
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

	public static ArrayList<ArrayList<Object>> parseCSV(String csvFile) {

		BufferedReader br = null;
		String line = "";
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		try {
			InputStream stream = Utility.class.getClassLoader()
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

	protected  void designateSemester(int semId) {
		
		// load Students csv
					// for creating students
		ArrayList<Student> students = createStudents(new Digest()
				.parseCSV(studentscsvFile));
				setStudents(students);

		// for creating Instructors
			ArrayList<Instructor> instructors = createInstructor(new Digest()
						.parseCSV(instructorscsvFile));
			setInstructors(instructors);
			
			// designate upcoming semeste
			// for creating Courses
				ArrayList<Course> courses = createCourse(parseCSV(coursescsvFile));
				setCourses(courses);

		// adding Prerequisite to Courses
			ArrayList<ArrayList<Object>> prerequistes = parseCSV(prereqscsvFile);
			addPrerequisiteCourse(prerequistes, courses);


		if (getMode().equals(AppMode.Initial)) {

			//records file has been already loaded.
			
			// upload Instructor assignment file
			assignments = parseCSV(assignmentsCSVFile+"_"+semId+".csv");
			addAssignments(assignments);
			
		}
		else
		{
			//todo use intermediate assignments file for semester N+1
		}
		
	}
	
	public static String getCourseName(Object value) {
		for (Iterator<?> iterator = Utility.getCourses().iterator(); iterator
				.hasNext();) {
			Course course = (Course) iterator.next();
			if (course.getCourseID() == value) {
				return course.getCourseTitle();
			}

		}
		return null;
	}
	
	public static void processRequestStatus(String message,CourseRequest courserequest)
	{
		
	}
	
	//Amruta
		public void getProjectedRecords() 
		{
			List<Integer> uniqueStudents = new ArrayList<Integer>();
			List<Integer> uniqueCourses = new ArrayList<Integer>();
		
			
			for(Record rec:records)
			{
				int sid = rec.getStudentID();
				int cid = rec.getCourseID();
				
				if(!uniqueStudents.contains(sid))
						{
							uniqueStudents.add(sid);
						}
				if(!uniqueCourses.contains(cid))
				{
					uniqueCourses.add(cid);
				}
			
			}
			
			
			for(Integer cid:uniqueCourses)
			{
				FILE_HEADER = FILE_HEADER + "Course"+cid+COMMA_DELIMITER;
			}
			
			FILE_HEADER = "Student,"+FILE_HEADER;
			FILE_HEADER = FILE_HEADER.substring(0, FILE_HEADER.lastIndexOf(','));
			
			//System.out.println("file header "+FILE_HEADER);
			
			 FileWriter fileWriter = null;

			 try {
				
				 fileWriter = new FileWriter(projrecordsfile);
				//Add header
				 fileWriter.append(FILE_HEADER.toString());
				 fileWriter.append(NEW_LINE_SEPARATOR);
					
				 for(Integer sid:uniqueStudents)
					{
						fileWriter.append(String.valueOf(sid));
						fileWriter.append(COMMA_DELIMITER);
						
						for(int i = 0; i <uniqueCourses.size();i++)
						{
							if(studentCoursePairExists(sid,uniqueCourses.get(i)))
								fileWriter.append("taken");
							else
								fileWriter.append("none");
							
							if(i!=uniqueCourses.size()-1)
								fileWriter.append(COMMA_DELIMITER);
								
						}
						
						
						fileWriter.append(NEW_LINE_SEPARATOR);

					}

				 System.out.println("Tmp Msg: CSV file was created successfully !!!");

				 
			} catch (IOException e) {
				
				System.out.println("Error in CsvFileWriter !!!");
				e.printStackTrace();
			}
			 finally {
				          try {
				 
				                 fileWriter.flush();
				 
				                 fileWriter.close();
				 
				             } catch (IOException e) {
				 
				                 System.out.println("Error while flushing/closing fileWriter !!!");
				 
				                 e.printStackTrace();
				 
				             }
			 }
				              

			
		}//Amruta
		
		//Amruta
		public static boolean studentCoursePairExists(int sid,int cid)
		{
			for(Record rec:records)
			{
				if(rec.getCourseID()==cid && rec.getStudentID()==sid)
					return true;
			}
			return false;
		}//Amruta
		
		//Amruta
		public static void display_assignments() {
			
			/*ArrayList<ArrayList<Object>> assignments = getAssignments();
			
			for (Iterator<ArrayList<Object>> iterator = assignments.iterator(); iterator
					.hasNext();) {
				ArrayList<Object> arrayList = (ArrayList<Object>) iterator.next();

				//todo print the selection

				}
			*/	
			System.out.println("ToDo print assignments here..");
			
		}//Amruta

		public static AppMode getMode() {
			return mode;
		}

		public static void setMode(AppMode mode) {
			Utility.mode = mode;
		}
}
