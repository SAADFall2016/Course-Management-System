package system_source_code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class Utility {

	public static String csvSplitBy = ",";

	private static ArrayList<Course> courses;
	private static ArrayList<Instructor> instructors;
	private static ArrayList<Student> students;
	private static ArrayList<Record> records;
	private static ArrayList<CourseRequest> waitListedRequests;
	private static ArrayList<CourseRequest> grantedRequests;
	private static ArrayList<ArrayList<Object>> assignments;

	public static void setAssignments(ArrayList<ArrayList<Object>> assignments) {
		Utility.assignments = assignments;
	}

	// private HashMap<Integer, Integer> requestsHM;
	private static ArrayList<CourseRequest> CourseRequests;

	public static ArrayList<CourseRequest> getCourseRequests() {
		return CourseRequests;
	}

	public static void setCourseRequests(ArrayList<CourseRequest> courseRequests) {
		CourseRequests = courseRequests;
	}

	private static String baseFolderTemp = System.getProperty("user.dir")+"//TEMP_FILES//";
	private static String baseFolderTest = System.getProperty("user.dir")+"//TEST_FILES//";
	
	private static String recordsFileName = "records";
	private static String waitList = "waitlist";
	private static String requests = "requests";
	private static String recordscsvFile = baseFolderTest+"records.csv";
	private static String waitListedFileName = baseFolderTemp + waitList;

	private static String studentscsvFile = baseFolderTest+"students.csv";
	private static String instructorscsvFile = baseFolderTest+"instructors.csv";
	private static String coursescsvFile = baseFolderTest+"courses.csv";
	private static String prereqscsvFile = baseFolderTest+"prereqs.csv";
	private static String assignmentsFileName = baseFolderTest+"assignments";
	private static String requestsFileName = baseFolderTest+"requests";
	private static String requestscsvFile = baseFolderTest+"requests.csv";
	private static String modecsvFile = "mode.csv";
	private static String isselected = "S";
	private static String isunselected = "U";

	// Amruta
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static String FILE_HEADER = "";
	//private static String baseFolder = "src.main.java//";
	
	private static String projrecordsfile = baseFolderTemp + "projectedrecords.csv";
	private static String projectedRecordsFileName = baseFolderTemp
			+ "projectedrecords";
	private static int currentSemId;
	
	private static int noOfTotalWaitListedHistory = 0;
	private static int noOfTotalGrantedHistory = 0;
	private static int noOfTotalRequestsHistory = 0;

	// Amruta

	// Girish
	private static String grantedRecordsFile = "grantedrecords.csv";
	private static String grantedRecordsFileName = "grantedrecords";
	// Girish

	private static String new_line_separater = "\n";
	private static String file_header = "";

	protected static CourseCatalogue courseCatalogue;
	private static AppMode mode;

	public static ArrayList<String> displayrequst = new ArrayList<String>();

	public static HashMap<Integer, ArrayList<Object>> selected = new HashMap<Integer, ArrayList<Object>>();

	public static HashMap<Integer, ArrayList<Object>> getSelected() {
		return selected;
	}

	public static void setSelected(HashMap<Integer, ArrayList<Object>> selected) {
		Utility.selected = selected;
	}

	public static HashMap<Integer, ArrayList<Object>> getUnselected() {
		return unselected;
	}

	public static void setUnselected(
			HashMap<Integer, ArrayList<Object>> unselected) {
		Utility.unselected = unselected;
	}

	public static HashMap<Integer, ArrayList<Object>> unselected = new HashMap<Integer, ArrayList<Object>>();

	Utility() {

		this.setMode(readMode());// Read current mode from mode.csv file.

	}

	Utility(AppMode amode) {
		this.setMode(amode);
	}

	// process records file as per current mode

	public void processRecords(AppMode mode, int semId) {
		
		//String workDirPath = System.getProperty("user.dir")+"//src.main.java//";

		if (semId != 1)
		{
			recordscsvFile = baseFolderTemp+ recordsFileName + "_" + (semId - 1) + ".csv";
			setProjrecordsfile(projectedRecordsFileName + "_" + (semId - 1)
					+ ".csv");
		}

		try {

			ArrayList<Record> records = createRecords(
					parseCSV(recordscsvFile));
			System.out.println("total records.." + records.size());
			setRecords(records);
		} catch (Exception ex) {
			System.out.println("msg " + ex.getMessage());
		}

	}

	// Amruta
	private AppMode readMode() {
		BufferedReader br = null;
		String path = modecsvFile;
		AppMode mode = AppMode.Initial;
		String line = "";

		try {
			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {

				switch (line.toLowerCase()) {
				case "initial":
					mode = AppMode.Initial;
					break;
				case "resume":
					mode = AppMode.Resume;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
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

		return mode;
	}

	public static void addAssignments(
			Collection<ArrayList<Object>> assignmentlist) {

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

	public static ArrayList<ArrayList<Object>> getAssignments() {
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
		for (Iterator<Course> iterator = getCourses().iterator(); iterator
				.hasNext();) {
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
			
			if(stream!=null)
				br = new BufferedReader(new InputStreamReader(stream));
			else
				  br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) {

				// use comma as separator
				Object[] elements = line.split(csvSplitBy);
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

	protected void designateSemester(int semId) {

		// load Students csv
		// for creating students
		ArrayList<Student> students = createStudents(
				parseCSV(studentscsvFile));
		setStudents(students);

		// for creating Instructors
		ArrayList<Instructor> instructors = createInstructor(
				parseCSV(instructorscsvFile));
		setInstructors(instructors);

		// designate upcoming semeste
		// for creating Courses
		ArrayList<Course> courses = createCourse(parseCSV(coursescsvFile));
		CourseCatalogue.setCourses(courses);// set the catalogue
		setCourses(courses);

		// adding Prerequisite to Courses
		ArrayList<ArrayList<Object>> prerequistes = parseCSV(prereqscsvFile);
		addPrerequisiteCourse(prerequistes, courses);

		
		// upload Instructor assignment file
		assignments = parseCSV(assignmentsFileName + "_" + semId + ".csv");
		setAssignments(assignments);
		
		if(semId>1)
		{
			//maintain history of requests
		noOfTotalWaitListedHistory = getTotalWaitlistedRequests(semId);
		noOfTotalGrantedHistory = getTotalGrantedRequests(semId);
		noOfTotalRequestsHistory = getTotalExaminedRequests(semId);
		
		
		}

	}

	//get waitlisting before given semester
	private int getTotalWaitlistedRequests(int semester)
	{
		String workDirPath = baseFolderTemp;
		File folder = new File(workDirPath);
		File[] listOfFiles = folder.listFiles();
		int lineCount = 0;
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile())
		      {
		    	  if(listOfFiles[i].getName().startsWith(waitList))
		    	  {
		    		  BufferedReader bufferedReader;
					try {
						bufferedReader = new BufferedReader(new FileReader(listOfFiles[i].getPath()));
						 String input;
		    		    
		    		     while((input = bufferedReader.readLine()) != null)
		    		     {
		    		    	 lineCount++;
		    		     }
					} catch (FileNotFoundException e) {

						e.printStackTrace();
					}
					catch(IOException ioe)
					{
						ioe.printStackTrace();
					}
		    		    
		    	}
		    		 
		      }
		    }
		return lineCount;
	}
	
	//get granted before given semester
	private int getTotalGrantedRequests(int semester)
	{
		String workDirPath = baseFolderTemp;
		File folder = new File(workDirPath);
		File[] listOfFiles = folder.listFiles();
		int lineCount = 0;
		int recordFileNo = semester-1;
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile())
		      {
		    	  if(listOfFiles[i].getName().startsWith(recordsFileName+"_"+recordFileNo))
		    	  {
		    		  BufferedReader bufferedReader;
					try {
						bufferedReader = new BufferedReader(new FileReader(listOfFiles[i].getPath()));
						 String input;
		    		    
		    		     while((input = bufferedReader.readLine()) != null)
		    		     {
		    		    	 if(input.contains("Random Comment"))
		    		    		 lineCount++;
		    		     }
					} catch (FileNotFoundException e) {

						e.printStackTrace();
					}
					catch(IOException ioe)
					{
						ioe.printStackTrace();
					}
		    		    
		    	}
		    		 
		      }
		    }
		return lineCount;
	}
	
	//get granted before given semester
	private int getTotalExaminedRequests(int semester)
	{
		String workDirPath = baseFolderTest;
		File folder = new File(workDirPath);
		File[] listOfFiles = folder.listFiles();
		int lineCount = 0;
		int requestFileNo = semester-1;
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile())
		      {
		    	  if(listOfFiles[i].getName().startsWith(requests+"_"+requestFileNo))
		    	  {
		    		  BufferedReader bufferedReader;
					try {
						bufferedReader = new BufferedReader(new FileReader(listOfFiles[i].getPath()));
						 String input;
		    		    
		    		     while((input = bufferedReader.readLine()) != null)
		    		     {
		    		    	 lineCount++;
		    		     }
					} catch (FileNotFoundException e) {

						e.printStackTrace();
					}
					catch(IOException ioe)
					{
						ioe.printStackTrace();
					}    
		    	}
		    		 
		      }
		    }
		return lineCount;
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

	public static void processRequestStatus(String message,
			CourseRequest courserequest) {

	}

	// Amruta

	public static void createWaitListFile(
			ArrayList<CourseRequest> waitedRequests) {
		FileWriter fileWriter = null;
		try {

			fileWriter = new FileWriter(waitListedFileName + "_"
					+ getCurrentSemId() + ".csv");
			for (CourseRequest request : waitedRequests) {
				fileWriter.append(String.valueOf(request.getStudenttId()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(request.getCourseId()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
		} catch (IOException e) {

			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {

				fileWriter.flush();

				fileWriter.close();

			} catch (IOException e) {

				System.out
						.println("Error while flushing/closing fileWriter !!!");

				e.printStackTrace();

			}
		}
		System.out
				.println("Tmp Msg: Waitlisted CSV file was created successfully !!!");

	}

	public void createProjRecordsFile() {

		List<Integer> uniqueStudents = new ArrayList<Integer>();
		List<Integer> uniqueCourses = new ArrayList<Integer>();

		System.out.println("tmp msg records count " + records.size());
		FILE_HEADER = "";

		for (Record rec : records) {
			int sid = rec.getStudentID();
			int cid = rec.getCourseID();

			if (!uniqueStudents.contains(sid)) {
				uniqueStudents.add(sid);
			}
			if (!uniqueCourses.contains(cid)) {
				uniqueCourses.add(cid);
			}

		}

		for (Integer cid : uniqueCourses) {
			FILE_HEADER = FILE_HEADER + "Course" + cid + COMMA_DELIMITER;
		}

		FILE_HEADER = "Student," + FILE_HEADER;
		FILE_HEADER = FILE_HEADER.substring(0, FILE_HEADER.lastIndexOf(','));

		// System.out.println("file header "+FILE_HEADER);

		FileWriter fileWriter = null;

		try {

			System.out.println("tmp msg projfilepath " + getProjrecordsfile());
			fileWriter = new FileWriter(getProjrecordsfile());
			// Add header
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);

			for (Integer sid : uniqueStudents) {
				fileWriter.append(String.valueOf(sid));
				fileWriter.append(COMMA_DELIMITER);

				for (int i = 0; i < uniqueCourses.size(); i++) {
					if (studentCoursePairExists(sid, uniqueCourses.get(i)))
						fileWriter.append("taken");
					else
						fileWriter.append("none");

					if (i != uniqueCourses.size() - 1)
						fileWriter.append(COMMA_DELIMITER);

				}

				fileWriter.append(NEW_LINE_SEPARATOR);

			}

			//System.out
				//	.println("Tmp Msg:Projected records CSV file was created successfully !!!");

		} catch (IOException e) {

			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {

				fileWriter.flush();

				fileWriter.close();

			} catch (IOException e) {

				System.out
						.println("Error while flushing/closing fileWriter !!!");

				e.printStackTrace();

			}
		}

	}// Amruta

	// Amruta
	public static boolean studentCoursePairExists(int sid, int cid) {
		for (Record rec : records) {
			if (rec.getCourseID() == cid && rec.getStudentID() == sid)
				return true;
		}
		return false;
	}// Amruta

	// Amruta
	public static void display_assignments() {

		ArrayList<ArrayList<Object>> assignments = getAssignments();

		if (selected.size() == 0 && unselected.size() == 0) {
			for (int i = 0; i < assignments.size(); i++) {
				unselected.put(i, assignments.get(i));

			}
		}

		// print the selection
		System.out.println("%------ selected -----");
		for (Entry<Integer, ArrayList<Object>> e : getSelected().entrySet()) {

			System.out.println(e.getKey() + ": " + e.getValue().get(0) + ", "
					+ e.getValue().get(1) + ", " + e.getValue().get(2));
		}

		// print unselected
		System.out.println("%------ unselected -----");
		for (Entry<Integer, ArrayList<Object>> e : getUnselected().entrySet()) {

			System.out.println(e.getKey() + ": " + e.getValue().get(0) + ", "
					+ e.getValue().get(1) + ", " + e.getValue().get(2));
		}

	}// Amruta

	public static AppMode getMode() {
		return mode;
	}

	public static void setMode(AppMode mode) {
		Utility.mode = mode;
	}

	// Girish
	private static Grade getrandomGrades() {
		double gr = Math.random();

		double[] probabilities = { 0.10, 0.30, 0.40, 0.10, 0.10 };

		Grade[] grades = Grade.values();

		double cdf = 0.0;

		for (int i = 0; i < grades.length; i++) {
			cdf += probabilities[i];
			if (gr < cdf)
				return grades[i];
		}
		return grades[grades.length - 1];

	}

	public static ArrayList<Record> createGrantedRecords(
			ArrayList<CourseRequest> cRequests) {

		/*
		 * cRequests = new ArrayList<CourseRequest>();
		 * 
		 * CourseRequest cr1 = new CourseRequest(); cr1.setCourseId(2);
		 * //cr1.setinstructorid(10); cr1.setStudenttId(20);
		 * 
		 * CourseRequest cr2 = new CourseRequest(); cr2.setCourseId(4);
		 * //cr2.setinstructorid(11); cr2.setStudenttId(21);
		 * 
		 * CourseRequest cr3 = new CourseRequest(); cr3.setCourseId(6);
		 * //cr3.setinstructorid(12); cr3.setStudenttId(22);
		 * 
		 * CourseRequest cr4 = new CourseRequest(); cr4.setCourseId(8);
		 * //cr4.setinstructorid(13); cr4.setStudenttId(23);
		 * 
		 * CourseRequest cr5 = new CourseRequest(); cr5.setCourseId(10);
		 * //cr5.setinstructorid(10); cr5.setStudenttId(24);
		 * 
		 * CourseRequest cr6 = new CourseRequest(); cr6.setCourseId(13);
		 * //cr6.setinstructorid(14); cr6.setStudenttId(25);
		 * 
		 * CourseRequest cr7 = new CourseRequest(); cr7.setCourseId(16);
		 * //cr7.setinstructorid(15); cr7.setStudenttId(26);
		 * 
		 * CourseRequest cr8 = new CourseRequest(); cr8.setCourseId(17);
		 * //cr8.setinstructorid(16); cr8.setStudenttId(27);
		 * 
		 * CourseRequest cr9 = new CourseRequest(); cr9.setCourseId(19);
		 * //cr9.setinstructorid(17); cr9.setStudenttId(28);
		 * 
		 * CourseRequest cr10 = new CourseRequest(); cr10.setCourseId(20);
		 * //cr10.setinstructorid(18); cr10.setStudenttId(29);
		 * 
		 * cRequests.add(cr1); cRequests.add(cr2); cRequests.add(cr3);
		 * cRequests.add(cr4); cRequests.add(cr5); cRequests.add(cr6);
		 * cRequests.add(cr6); cRequests.add(cr8); cRequests.add(cr9);
		 * cRequests.add(cr10);
		 */

		ArrayList<Record> cGrantedRecords = new ArrayList<Record>();

		for (CourseRequest cR : cRequests) {
			List<Course> courses = CourseCatalogue.getCourses();

			int cid = cR.getCourseId();
			Course c = CourseCatalogue.getCourse(cid);

			HashMap<Person, Integer> instructorsMap = c
					.getInstructors_capacity();
			Instructor ins = null;
			Record rec = null;

			if (instructorsMap != null && !instructorsMap.isEmpty()) {
				Set<Person> pset = instructorsMap.keySet();
				Object[] persons = pset.toArray();
				ins = (Instructor) (persons[0]);

			}

			if (ins != null) {
				rec = new Record(cR.getStudenttId(), cR.getCourseId(),
						ins.getUUID(), "Random Comment", getrandomGrades());

				if (!cGrantedRecords.contains(rec))
					cGrantedRecords.add(rec);
			}
		}

		if (cGrantedRecords != null && !cGrantedRecords.isEmpty()
				&& records != null) {
			for (Record r : cGrantedRecords) {
				if (!records.contains(r))
					records.add(r);
			}
		}

		FileWriter fileWriter = null;
		try {
			String tmpRecords = "";
			tmpRecords = baseFolderTemp + recordsFileName;
			fileWriter = new FileWriter(tmpRecords + "_" + getCurrentSemId()
					+ ".csv");
			for (Record r : records) {
				fileWriter.append(String.valueOf(r.getStudentID()));
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(String.valueOf(r.getCourseID()));
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(String.valueOf(r.getInstructorID()));
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(String.valueOf(r.getInstructorComment()));
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(String.valueOf(r.getGrade()));

				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			//System.out
				//	.println("Tmp Msg: GrantedRecords CSV file was created successfully !!!");
		} catch (IOException e) {

			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();

		} finally {
			try {

				fileWriter.flush();

				fileWriter.close();

			} catch (IOException e) {

				System.out
						.println("Error while flushing/closing fileWriter !!!");

				e.printStackTrace();

			}
		}

		return cGrantedRecords;

	}

	// Girish

	// Amruta
	public void uploadRequests(int semId) {

		ArrayList<CourseRequest> allRequests = new ArrayList<CourseRequest>();
		ArrayList<CourseRequest> waitedRequests =  new ArrayList<CourseRequest>();
		ArrayList<CourseRequest> fileRequests =  new ArrayList<CourseRequest>();
		
		if (semId > 1) {
		
			if(Utility.getMode() == AppMode.Resume)
				waitedRequests = createCourseRequests((parseCSV(Utility.waitListedFileName
					+ "_" + (semId - 1) + ".csv")));
			else
				waitedRequests = waitListedRequests;
			
			allRequests.addAll(waitedRequests);
			System.out.println("waited: "+waitedRequests.size());
		}
		fileRequests = createCourseRequests((parseCSV(Utility.requestsFileName
				+ "_" + semId + ".csv")));
		allRequests.addAll(fileRequests);
		setCourseRequests(allRequests);
		
		
	}

	// section copied methods from Digest class
	public static void check_request(Integer studentId, Integer courseId) {

		ArrayList<Student> students = getStudents();
		for (Iterator<Student> iterator = students.iterator(); iterator
				.hasNext();) {
			Student student = (Student) iterator.next();
			if (student.getUUID().equals(studentId)) {
				String result = student.enrollInCourse(courseId);
				System.out.println(result);
				if (result.equals(Student.validRequest)) {
					String res = student.getUUID().toString() + ','
							+ student.getName() + ',' + courseId + ','
							+ getCourseName(courseId);
					displayrequst.add(res);
				}

			}

		}

	}

	// section copied methods from Digest class
	public static void displayRequestdigest(ArrayList<CourseRequest> request, // HashMap<Integer,
																				// Integer>
			ArrayList<Student> students) {
		int validrequest = 0;
		int missingpre = 0;
		int alreadytaken = 0;
		int noseat = 0;
		grantedRequests = new ArrayList<CourseRequest>();
		waitListedRequests = new ArrayList<CourseRequest>();
		
		//CourseRequest cReq = new CourseRequest();

		System.out.println("Processed Requests");

		// for (Entry<?, ?> req : request.entrySet())
		for (Iterator iterator1 = request.iterator(); iterator1.hasNext();) {
			CourseRequest req = (CourseRequest) iterator1.next();

			// }
			{
				for (Iterator<Student> iterator = students.iterator(); iterator
						.hasNext();) {
					Student student = (Student) iterator.next();
					Integer studentId = student.getUUID();

					if (studentId.equals(req.getStudenttId())) {
						int courseId = (int) req.getCourseId();
//						cReq = new CourseRequest();
//						cReq.setCourseId(courseId);
//						cReq.setStudenttId(studentId);

						String result = student.enrollInCourse(courseId);
						if (result.equals(Student.validRequest)) {
							validrequest = validrequest + 1;
							String res = student.getUUID().toString() + ','
									+ student.getName() + ','
									+ req.getCourseId() + ','
									+ getCourseName(req.getCourseId());

							displayrequst.add(res);

							grantedRequests.add(req);

							System.out.println("request ("
									+ student.getUUID().toString() + ", "
									+ (int) req.getCourseId() + "): valid");

						}
						if (result.equals(Student.alreadyTakenCourse)) {
							System.out	
									.println("request ("
											+ student.getUUID().toString()
											+ ", "
											+ (int) req.getCourseId()
											+ "): student has already taken the course with a grade of C or higher");
							alreadytaken = alreadytaken + 1;
						}
						if (result.equals(Student.missingPrerequisite)) {
							missingpre = missingpre + 1;
							System.out
									.println("request ("
											+ student.getUUID().toString()
											+ ", "
											+ (int) req.getCourseId()
											+ "): student is missing one or more prerequisites");
						}
						if (result.equals(Student.noSeatsAvailable)) {
							noseat = noseat + 1;
							if(!waitListedRequests.contains(req))
								waitListedRequests.add(req);
							System.out
									.println("request ("
											+ student.getUUID().toString()
											+ ", "
											+ (int) req.getCourseId()
											+ "): no remaining seats at this time: (re-)added to waitlist");
						}

					}
				}
			}
		}

			int totalReqInSem = validrequest + alreadytaken + missingpre
					+ noseat;

			System.out.println("");
			System.out.println("Semester Statistics");
			System.out.println("Examined: " + totalReqInSem + " Granted: "
					+ validrequest + " Failed: " + (alreadytaken + missingpre)
					+ " Listed: " + noseat);
			
			System.out.println("Total Statistics");

			noseat = noOfTotalWaitListedHistory + noseat;
			totalReqInSem = totalReqInSem + noOfTotalRequestsHistory;
			validrequest = validrequest + noOfTotalGrantedHistory;
			int failedTotal = totalReqInSem - (noseat + validrequest);
			
			System.out.println("Examined: " + totalReqInSem + " Granted: "
					+ validrequest + " Failed: " + failedTotal
					+ " Listed: " + noseat);

			// add granted requests to records file with random grades
			if(!grantedRequests.isEmpty())
				createGrantedRecords(grantedRequests);

			// add waited requests to waitlist_semid file
			if(!waitListedRequests.isEmpty())
				createWaitListFile(waitListedRequests);
		

	}

	public ArrayList<CourseRequest> createCourseRequests(
			ArrayList<ArrayList<Object>> requests) {
		ArrayList<CourseRequest> cr = new ArrayList<CourseRequest>();
		for (Iterator<ArrayList<Object>> iterator = requests.iterator(); iterator
				.hasNext();) {
			ArrayList<Object> arrayList = (ArrayList<Object>) iterator.next();
			CourseRequest courserequest = new CourseRequest();
			courserequest.setCourseId(Integer.parseInt((String) arrayList
					.get(1)));
			courserequest.setStudenttId(Integer.parseInt((String) arrayList
					.get(0)));
			cr.add(courserequest);

		}
		return cr;

	}

	// section copied methods from Digest class
	public HashMap<Integer, Integer> createRequests(
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

	//
	// public HashMap<Integer, Integer> getRequestsHM() {
	// return requestsHM;
	// }

	// public void setRequestsHM(HashMap<Integer, Integer> requestsHM) {
	// this.requestsHM = requestsHM;
	// }

	public static int getCurrentSemId() {
		return currentSemId;
	}

	public static void setCurrentSemId(int currentSemId) {
		Utility.currentSemId = currentSemId;
	}

	public static String getProjrecordsfile() {
		return projrecordsfile;
	}

	public static void setProjrecordsfile(String projrecordsfile) {
		Utility.projrecordsfile = projrecordsfile;
	}

}
