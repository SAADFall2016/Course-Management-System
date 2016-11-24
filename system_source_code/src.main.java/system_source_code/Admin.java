package system_source_code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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

	/*public static void main(String args[]) {
		Admin admin = new Admin(1, "Kanika", "389 Canterbury Drive 48531", "123456");
		admin.processSemester(UUID.randomUUID());
	}*/

	Admin(int UUID, String Name, String Address, String PhoneNumber) {
		super(UUID, Name, Address, PhoneNumber);
	}
	
	public Admin()
	{
		
	}

	@Override
	public void readPredictions() {
		
		 // load data
	    Instances data = null;
	    Instances newData = null;
		
			try {
				data = DataSource.read(projRecordsFilepah);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		
		if(data!=null)
		{
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

	public void assignInstructorForCourse(Integer courseId) {

	}

	public ArrayList<?> getDataPreditions() {
		return null;

	}

	public void setDataPredictions(ArrayList<?> predictions) {

	}

	public void calculateCourseCapacity(Integer courseId) {

	}
	
	

	public void processSemester(int semId) {
		
		System.out.println("Tmp comment: Processing semester number "+semId);
		
		AppMode currentMode = Utility.getMode();
		
		utility.processRecords(currentMode,semId);
		
		//Step1 :Convert records.csv to projectedrecords file:Done
		utility.getProjectedRecords();
		
		readPredictions();
		
		//Designate semester
		utility.designateSemester(semId);
		
		boolean isInvalidInput = false;
		do
		{
			System.out.print("$roster selection >");
			 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			 
			 String input = EmptyString;
			 try {
				input = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 switch(input.toLowerCase())
				{
				case "display":
				//Step3 :Roster selection:> Display :ToDo
					utility.display_assignments();
				 break;
				 default:
				case "quit":
					isInvalidInput = true;
					break;
				}
		}
		while(!isInvalidInput);
		
		System.out.println("stopping the command loop");
		
		
		
		/*utility = new Utility();
		utility.designateSemester(semId);
		ArrayList<?> predictions = getDataPreditions();
		selectInstructorAssignments(predictions);
		// for each course

		assignInstructorForCourse(1);// dummy val
		List<Course> courses = CourseCatalogue.getCourses();
		for (Iterator iterator = courses.iterator(); iterator.hasNext();) {
			Course course = (Course) iterator.next();
			calculateCourseCapacity(course.getCourseID());
		}

		ArrayList<CourseRequest> requests = new ArrayList<CourseRequest>();
		//Amruta: Commented below line due to error
		//requests = createRequests(Utility.parseCSV(Utility.requests));

		ArrayList<Student> students = Utility.getStudents();

		for (Iterator iterator = requests.iterator(); iterator.hasNext();) {
			CourseRequest request = (CourseRequest) iterator.next();
			for (Iterator<Student> iterator1 = students.iterator(); iterator1
					.hasNext();) {
				Student student = (Student) iterator1.next();
				if (student.getUUID().equals(request.getStudenttId())) {
					String result = student.enrollInCourse((int) request
							.getCourseId());

					Utility.processRequestStatus(result, request);

				}
			}

		}*/

	}

	private static ArrayList<CourseRequest> createRequests(
			ArrayList<ArrayList<Object>> requests) {
		ArrayList<CourseRequest> request = new ArrayList<CourseRequest>();
		for (Iterator<ArrayList<Object>> iterator = requests.iterator(); iterator
				.hasNext();) {
			ArrayList<Object> arrayList = (ArrayList<Object>) iterator.next();
			CourseRequest courserequest = new CourseRequest();
			courserequest.setStudenttId((int) arrayList.get(0));
			courserequest.setCourseId((int) arrayList.get(0));

		}
		return request;

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
