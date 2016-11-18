package system_source_code;

public class Instructor extends Person {

	private int capacity;

	CourseCatalogue coursecatalogue;
	private String Skillset;

	public Instructor(int UUID, String Name, String Address, String PhoneNumber) {
		super(UUID, Name, Address, PhoneNumber);
	}

	public int getCapacity() {
		return capacity;
	}

	public String getSkillset() {
		return Skillset;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setSkillset(String skillset) {
		Skillset = skillset;
	}

}