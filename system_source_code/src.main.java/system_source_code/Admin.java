package system_source_code;

import java.util.ArrayList;
import java.util.UUID;

public class Admin extends Person implements IPredicionTool {
	Utility utility;

	
	public static void main(String args[])
	{
		Admin admin = new Admin(1,"Kanika","abc","123456");
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
		
	}

}
