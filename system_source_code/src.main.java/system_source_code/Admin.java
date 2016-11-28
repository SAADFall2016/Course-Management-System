package system_source_code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.UUID;

import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class Admin extends Person implements IPredicionTool {
	private Utility utility;
	String projRecordsFilepah = "projectedrecords.csv";
	private static final String EmptyString = "";

	/*
	 * public static void main(String args[]) { Admin admin = new Admin(1,
	 * "Kanika", "389 Canterbury Drive 48531", "123456");
	 * admin.processSemester(UUID.randomUUID()); }
	 */

	Admin(int UUID, String Name, String Address, String PhoneNumber) {
		super(UUID, Name, Address, PhoneNumber);
	}

	public Admin() {

	}

	@Override
	public void readPredictions() {

		// load data
		Instances data = null;
		Instances newData = null;

		try {
			data = DataSource.read(Utility.getProjrecordsfile());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (data != null) {
			data.setClassIndex(data.numAttributes() - 1);

			newData = data;

			NumericToNominal filter = new NumericToNominal();

			try {
				filter.setInputFormat(newData);
				newData = Filter.useFilter(newData, filter);

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// build associator
			Apriori apriori = new Apriori();
			apriori.setClassIndex(newData.classIndex());
			try {
				apriori.buildAssociations(newData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// output associator
			System.out.println(apriori);
		}

	}

	public void assignInstructorForCourse(Integer index) {
		
		if(utility.getUnselected().get(index) == null)
		{
			System.out.println("index doesn't exist in unselected list");
			return;
		}

		if (utility.selected.size() == 5) {
			System.out
					.println("All instructors selected - no available hiring position");
			return;
		}

		for (Entry<Integer, ArrayList<Object>> e : utility.getSelected()
				.entrySet()) {
			if (e.getValue().get(0).equals(utility.getUnselected().get(index)
					.get(0))) {
				System.out
						.println("Instructor already selected to teach course");
				return;
			}
		}

		if (utility.getSelected().containsKey(index)) {
			System.out.println("Already in selected list");
		} else {

			utility.getSelected()
					.put(index, utility.getUnselected().get(index));
			utility.getUnselected().remove(index);
			System.out.println("instructor selected!");
		}

	}

	public void deleteInstructorForCourse(Integer index) {
		
		if(utility.getSelected().get(index) != null)
		{
			utility.getUnselected().put(index,utility.getSelected().get(index));
			utility.getSelected().remove(index);
			System.out.println("instructor released!");
		}
		else
		{
			System.out.println("Instructor already in Unselected");
		}

	}

	public ArrayList<?> getDataPreditions() {
		return null;

	}

	public void setDataPredictions(ArrayList<?> predictions) {

	}

	public void calculateCourseCapacity(Integer courseId) {

	}

	public boolean processSemester(int semId) {

		System.out.println("Tmp comment: Processing semester number " + semId);

		AppMode currentMode = Utility.getMode();

		utility.processRecords(currentMode, semId);
		
		Utility.setCurrentSemId(semId);

		// Step1 :Convert records.csv to projectedrecords file:Done
		utility.createProjRecordsFile();

		readPredictions();

		// Designate semester
		utility.designateSemester(semId);

		boolean isInvalidInput = true;
		Scanner scin = new Scanner(System.in);
		// System.out.print("$roster selection >");
		String input = EmptyString;

		while (isInvalidInput) {
			System.out.print("$roster selection >");
			input = scin.nextLine();
			Object[] elements = input.split(Utility.csvSplitBy);
			elements[0] = ((String) elements[0]).toLowerCase();
			switch ((String) elements[0]) {
			case "display":
				// Step3 :Roster selection:> Display :ToDo
				utility.display_assignments();
				break;

			case "add":
				// to add instructor for a course
				this.assignInstructorForCourse(Integer
						.parseInt((String) elements[1]));
				break;

			case "delete":
				// to delete current selection
               this.deleteInstructorForCourse(Integer
						.parseInt((String) elements[1]));
				break;
			case "submit":
				// to do submit the selections till now
				//call next process semester now
				System.out.println("selections finalized - now processing requests for Semester "+semId);
				utility.addAssignments(utility.getSelected().values());
				
				isInvalidInput = false;
				break;
			default:
				break;
			}
		}

		//Step 7:Upload the student requests file.
		utility.uploadRequests(semId);
		
		//Print statistics
		utility.displayRequestdigest(utility.getRequestsHM(), utility.getStudents());
		
		System.out.println("$continue simulation? [yes/no]:");
		String toContinue = scin.next();
		
		boolean answer = false;
		
		switch(toContinue.toLowerCase())
		{
			case "yes":
				answer = true;
				break;
		}
		
		return answer;
		/*
		 * utility = new Utility(); utility.designateSemester(semId);
		 * ArrayList<?> predictions = getDataPreditions();
		 * selectInstructorAssignments(predictions); // for each course
		 * 
		 * assignInstructorForCourse(1);// dummy val List<Course> courses =
		 * CourseCatalogue.getCourses(); for (Iterator iterator =
		 * courses.iterator(); iterator.hasNext();) { Course course = (Course)
		 * iterator.next(); calculateCourseCapacity(course.getCourseID()); }
		 * 
		 * ArrayList<CourseRequest> requests = new ArrayList<CourseRequest>();
		 * //Amruta: Commented below line due to error //requests =
		 * createRequests(Utility.parseCSV(Utility.requests));
		 * 
		 * ArrayList<Student> students = Utility.getStudents();
		 * 
		 * for (Iterator iterator = requests.iterator(); iterator.hasNext();) {
		 * CourseRequest request = (CourseRequest) iterator.next(); for
		 * (Iterator<Student> iterator1 = students.iterator(); iterator1
		 * .hasNext();) { Student student = (Student) iterator1.next(); if
		 * (student.getUUID().equals(request.getStudenttId())) { String result =
		 * student.enrollInCourse((int) request .getCourseId());
		 * 
		 * Utility.processRequestStatus(result, request);
		 * 
		 * } }
		 * 
		 * }
		 */

	}



	public static void check_request(CourseRequest request) {

		ArrayList<Student> students = Utility.getStudents();
		for (Iterator<Student> iterator = students.iterator(); iterator
				.hasNext();) {
			Student student = (Student) iterator.next();
			if (student.getUUID().equals(request.getStudenttId())) {
				String result = student.enrollInCourse(request.getCourseId());
				System.out.println(result);
				if (result.equals(Student.validRequest)) {
					String res = student.getUUID().toString() + ','
							+ student.getName() + ',' + request.getCourseId()
							+ ','
							+ Utility.getCourseName(request.getCourseId());
					// displayrequst.add(res);
				}

			}

		}

	}

	private void selectInstructorAssignments(ArrayList<?> predictions) {
		// TODO Auto-generated method stub

	}

	public Utility getUtility() {
		return utility;
	}

	public void setUtility(Utility utility) {
		this.utility = utility;
	}

}
