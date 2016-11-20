package system_source_code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.UUID;

public class Admin extends Person implements IPredicionTool {
	Utility utility;

	public static void main(String args[]) {
		Admin admin = new Admin(1, "Kanika", "abc", "123456");
		admin.processSemester(UUID.randomUUID());
	}

	Admin(int UUID, String Name, String Address, String PhoneNumber) {
		super(UUID, Name, Address, PhoneNumber);
	}

	@Override
	public ArrayList<?> readPredictions() {
		// TODO Auto-generated method stub
		return null;
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

	public void processSemester(UUID semId) {

		utility = new Utility(Mode.Initial);
		utility.designateSemester(semId);
		ArrayList<?> predictions = getDataPreditions();
		selectInstructorAssignments(predictions);

		ArrayList<CourseRequest> request = new ArrayList<CourseRequest>();
		request = createRequests(Utility.parseCSV(Utility.requests));

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
					String res = student.getUUID().toString() + ',' + student.getName()
							+ ',' + request.getCourseId() + ',' + Utility.getCourseName(request.getCourseId());
					//displayrequst.add(res);
				}

			}

		}

	}

	private void selectInstructorAssignments(ArrayList<?> predictions) {
		// TODO Auto-generated method stub

	}

}
