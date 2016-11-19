package system_source_code;

import java.util.ArrayList;

public class Admin extends Person implements IPredicionTool {
	Utility utility;

	Admin(int UUID, String Name, String Address, String PhoneNumber) {
		super(UUID, Name, Address, PhoneNumber);
		utility = new Utility();
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

	public void processSemester(Integer semId) {

	}

}
